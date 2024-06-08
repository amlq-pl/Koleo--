package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.carriage.Carriage;
import pl.tcs.oopproject.model.carriage.CarriageClass;
import pl.tcs.oopproject.model.carriage.CarriageType;
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.connection.ScheduledTrain;
import pl.tcs.oopproject.model.databaseIntegration.FindPlacesForConnectionWithTransfersInterface;
import pl.tcs.oopproject.model.assignedSeat.TrainsAssignedSeats;
import pl.tcs.oopproject.model.assignedSeat.AssignedSeat;
import pl.tcs.oopproject.model.seat.Seat;
import pl.tcs.oopproject.model.seat.SeatType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class FindPlacesForConnectionWithTransfers implements FindPlacesForConnectionWithTransfersInterface {
    @Override
    public ArrayList<TrainsAssignedSeats> findPlacesForConnectionWithTransfers(MultiStopRoute multiStopRoute, int numOfPlaces) throws SQLException {
        ArrayList<ArrayList<AssignedSeat>> places = new ArrayList<>();
        for (ScheduledTrain scheduledTrain : multiStopRoute.trains()) {
            places.add(findSpecificSeat(scheduledTrain, numOfPlaces));
        }
        ArrayList<TrainsAssignedSeats> toReturn = new ArrayList<>();
        ArrayList<ArrayList<AssignedSeat>> placesToConstruct = new ArrayList<>();
        for (ArrayList<AssignedSeat> place : places) {
            for (int i = 0; i < place.size(); i++) {
                if (placesToConstruct.size() < i + 1) {
                    placesToConstruct.add(new ArrayList<>());
                }
                placesToConstruct.get(i).add(place.get(i));
            }
        }
        for (ArrayList<AssignedSeat> place : placesToConstruct) {
            if (places.size() == multiStopRoute.trains().size()) {
                toReturn.add(new TrainsAssignedSeats(multiStopRoute, place));
            }
        }
        return toReturn;
    }

    private ArrayList<AssignedSeat> findSpecificSeat(ScheduledTrain scheduledTrain, int numOfPlaces) throws SQLException {
        int startStation = getNumOfStation(scheduledTrain.getNumber(), scheduledTrain.originStation().town()),
                endStation = getNumOfStation(scheduledTrain.getNumber(), scheduledTrain.destinationStation().town());

        ArrayList<AssignedSeat> assignedSeats = new ArrayList<>();

        int carriageSeats;
        int numOfNeededDifferentCarriageConfigurationsInConnection = getNumOfIntersectingCarriageChangesInConnection(scheduledTrain.getNumber(), startStation, endStation);
        for (int inspectedCarriage = 1; inspectedCarriage <= getNumOfMaxCarriages(scheduledTrain.getNumber()); inspectedCarriage++) {
            if (!isCarriageCorrect(scheduledTrain.getNumber(), inspectedCarriage, numOfNeededDifferentCarriageConfigurationsInConnection))
                continue;
            carriageSeats = getNumOfSeatsInCarriage(scheduledTrain.getNumber(), inspectedCarriage);
            for (int inspectedSeat = 1; inspectedSeat <= carriageSeats; inspectedSeat++) {
                if (!checkSeatAvailability(scheduledTrain.getNumber(), inspectedCarriage, inspectedSeat, startStation, endStation))
                    continue;
                assignedSeats.add(getSpecificSeat(scheduledTrain.getNumber(), inspectedCarriage, inspectedSeat));
                if (assignedSeats.size() == numOfPlaces) return assignedSeats;
            }
        }
        return assignedSeats;
    }

    private int getNumOfStation(int idConnection, String stationName) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select getnumofstation(?,?)");
        ps.setInt(1, idConnection);
        ps.setString(2, stationName);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    private int getNumOfMaxCarriages(int idConnection) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select max(psc.nr_wagonu) from przejazdy p " +
                "join przejazdy_sklad ps on p.id_przejazdu = ps.id_przejazdu " +
                "join przejazdy_sklad_czesci psc on psc.id_przejazdu_skladu = ps.id_przejazdu_skladu " +
                "where p.id_przejazdu = ?");
        ps.setInt(1, idConnection);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }


    private int getNumOfIntersectingCarriageChangesInConnection(int idConnection, int startStation, int endStation) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select count(*) from przejazdy p join przejazdy_sklad ps on p.id_przejazdu = ps.id_przejazdu " +
                "where p.id_przejazdu = ? and  greatest(ps.od_stacji,?) <  least(ps.do_stacji,?)  ");
        ps.setInt(1, idConnection);
        ps.setInt(2, startStation);
        ps.setInt(3, endStation);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    private boolean isCarriageCorrect(int connectionId, int carriageNum, int needed) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select count(*) from przejazdy p " +
                "join przejazdy_sklad ps on p.id_przejazdu = ps.id_przejazdu " +
                "join przejazdy_sklad_czesci psc on ps.id_przejazdu_skladu = psc.id_przejazdu_skladu " +
                "where p.id_przejazdu = ? and psc.nr_wagonu = ? ");
        ps.setInt(1, connectionId);
        ps.setInt(2, carriageNum);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) >= needed;
    }

    private int getNumOfSeatsInCarriage(int connectionId, int carriageNum) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select w.liczba_miejsc,w.id_wagonu " +
                "from przejazdy p join przejazdy_sklad ps on p.id_przejazdu = ps.id_przejazdu " +
                "join przejazdy_sklad_czesci psc on ps.id_przejazdu_skladu = psc.id_przejazdu_skladu " +
                "join wagony w on psc.id_wagonu = w.id_wagonu " +
                "where p.id_przejazdu = ? and psc.nr_wagonu = ? " +
                "limit 1");
        ps.setInt(1, connectionId);
        ps.setInt(2, carriageNum);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    private boolean checkSeatAvailability(int connectionId, int carriageNum, int seatNum, int startStation, int endStation) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select exists(select * from przejazdy p " +
                "join bilety_jednorazowe bj on p.id_przejazdu = bj.id_przejazdu " +
                "join bilety_jednorazowe_zamowienia bjz on bj.id_bilety_jednorazowe_zamowienia = bjz.id_bilety_jednorazowe_zamowienia " +
                "where p.id_przejazdu=? and bj.nr_wagonu=? and bj.nr_miejsca=? and bjz.timestamp_zwrotu is null and " +
                "greatest(bj.od_stacji,?) <  least(bj.do_stacji,?));");
        ps.setInt(1, connectionId);
        ps.setInt(2, carriageNum);
        ps.setInt(3, seatNum);
        ps.setInt(4, startStation);
        ps.setInt(5, endStation);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return !rs.getBoolean(1);
    }

    private AssignedSeat getSpecificSeat(int connectionId, int carriageNum, int seatNum) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select w.typ_wagonu,w.klasa,w.liczba_miejsc,wtm.miejsce_mod,wtm.typ_miejsca " +
                        "from przejazdy p join przejazdy_sklad ps on p.id_przejazdu = ps.id_przejazdu " +
                        "join przejazdy_sklad_czesci psc on ps.id_przejazdu_skladu = psc.id_przejazdu_skladu " +
                        "join wagony w on psc.id_wagonu = w.id_wagonu " +
                        "join wagony_typy_miejsc wtm on w.id_wagonu = wtm.id_wagonu " +
                        "where p.id_przejazdu=? and psc.nr_wagonu=?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1, connectionId);
        ps.setInt(2, carriageNum);
        ResultSet rs = ps.executeQuery();
        rs.next();
        CarriageType ct;
        String carriageType = rs.getString("typ_wagonu");
        if (carriageType.startsWith("Bezprzedziałowy")) {
            ct = CarriageType.SINGLE_COMPARTMENT_CARRIAGE;
        } else if (carriageType.startsWith("Przedziałowy")) {
            ct = CarriageType.COMPARTMENT_CARRIAGE;
        } else if (carriageType.startsWith("Sypialny")) {
            ct = CarriageType.SLEEPER;
        } else ct = CarriageType.WARS;
        Carriage c = new Carriage(rs.getInt("klasa") == 2 ? CarriageClass.SECOND_CLASS : CarriageClass.FIRST_CLASS,
                ct, carriageNum, rs.getInt("liczba_miejsc"));

        //point for correct seat type
        rs.last();
        int rsSize = rs.getRow();
        int relativeSeat = seatNum % rsSize;
        if (relativeSeat == 0) relativeSeat = rsSize;
        rs.absolute(relativeSeat);

        SeatType st = null;
        String seatType = rs.getString("typ_miejsca");
        if (seatType == null || seatType.isEmpty()) {
            st = SeatType.BERTH;
        } else if (seatType.equals("Okno")) {
            st = SeatType.WINDOW;
        } else if (seatType.equals("Środek")) {
            st = SeatType.MIDDLE;
        } else if (seatType.equals("Korytarz")) {
            st = SeatType.CORRIDOR;
        }
        return new AssignedSeat(c, new Seat(st, seatNum));
    }
}
