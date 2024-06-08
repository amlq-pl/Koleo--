package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.assignedSeat.AssignedSeat;
import pl.tcs.oopproject.model.assignedSeat.TrainsAssignedSeats;
import pl.tcs.oopproject.model.connection.ScheduledTrain;
import pl.tcs.oopproject.model.databaseIntegration.CreateOrRefactorTicket;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.ticket.Details;
import pl.tcs.oopproject.model.ticket.LongTermTicketType;
import pl.tcs.oopproject.model.ticket.LongTermTrainTicket;
import pl.tcs.oopproject.model.ticket.SingleJourneyTrainTicket;
import pl.tcs.oopproject.model.users.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CreateOrRefactor implements CreateOrRefactorTicket {
    @Override
    public ArrayList<LongTermTrainTicket> saveLongTermTicket(LocalDateTime startDate, Discount discount, Voucher voucher, String login, ArrayList<LongTermTicketType> ticketType, Person person) throws SQLException {
        ArrayList<LongTermTrainTicket> ticketsToReturn = new ArrayList<>();
        int id_zamowienia;
        if (login != null) {
            id_zamowienia = insertIntoZamowieniaByLogin(LocalDateTime.now(), login);
        } else {
            Checkers c = new Checkers();
            if (!c.checkIfPersonExists(person)) {
                new InsertNewPersonToDatabase().insert(person, null, null);
            }
            id_zamowienia = insertIntoZamowieniaByEmail(LocalDateTime.now(), person.getEmailAddress());
        }
        for (LongTermTicketType ticket : ticketType) {
            int idBiletyOkresoweZamowienia = insertIntoBiletyOkresoweZamowienia(id_zamowienia, extractIdUlgi(discount), extractIdRabatu(voucher));
            insertIntoBiletyOkresowe(idBiletyOkresoweZamowienia, getIdPodrozujacegoByEmail(person.getEmailAddress()), startDate, extractIdTypBiletu(ticket));
            ticketsToReturn.add(new LongTermTrainTicket(startDate.toLocalDate(), ticket, discount, voucher, idBiletyOkresoweZamowienia, person));
        }
        return ticketsToReturn;
    }

    @Override
    public ArrayList<SingleJourneyTrainTicket> saveSingleJourneyTicket(Person person, Discount discount, Voucher voucher, Details details, ArrayList<TrainsAssignedSeats> seats, String login) throws SQLException {
        ArrayList<SingleJourneyTrainTicket> ticketsToReturn = new ArrayList<>();
        int id_zamowienia;
        if (login != null) {
            id_zamowienia = insertIntoZamowieniaByLogin(LocalDateTime.now(), login);
        } else {
            Checkers c = new Checkers();
            if (!c.checkIfPersonExists(person)) {
                new InsertNewPersonToDatabase().insert(person, null, null);
            }
            id_zamowienia = insertIntoZamowieniaByEmail(LocalDateTime.now(), person.getEmailAddress());
        }
        for (TrainsAssignedSeats seat : seats) {
            int idBiletyJednorazoweZamowienia = insertIntoBiletyJednorazoweZamowienia(id_zamowienia, extractIdUlgi(discount), extractIdRabatu(voucher));
            for (int i=0;i< seat.getConnection().trains().size();i++){
                insertSingleTicketIntoBiletyJednorazowe(idBiletyJednorazoweZamowienia,seat.seatList().get(i),seat.getConnection().trains().get(i),getIdPodrozujacegoByEmail(person.getEmailAddress()),extractIdSzczegolow(details));
            }
            ticketsToReturn.add(1,new SingleJourneyTrainTicket(seat,discount,voucher,idBiletyJednorazoweZamowienia,details,seat.getConnection(),person));
        }
        return ticketsToReturn;
    }

    private int extractIdSzczegolow(Details details) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select getidszczegolow(?,?,?)");
        ps.setBoolean(1,details.bike().active());
        ps.setBoolean(2,details.luggage().active());
        ps.setBoolean(3,details.animal().active());
        ResultSet rs= ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    private void insertSingleTicketIntoBiletyJednorazowe(int idBiletyJednorazoweZamowienia, AssignedSeat assignedSeat, ScheduledTrain scheduledTrain, int idPodroznika, int idDetails) throws SQLException {
        insertIntoBiletyJednorazowe(idBiletyJednorazoweZamowienia,scheduledTrain.getNumber(),idPodroznika, scheduledTrain.getIndexOfStation(scheduledTrain.originStation().town()),
                scheduledTrain.getIndexOfStation(scheduledTrain.destinationStation().town()),assignedSeat.carriage().number(), assignedSeat.seat().number(),idDetails);
    }


    @Override
    public int returnTicket(int id) {
        return 0;
    }

    private int getIdPodrozujacegoByEmail(String email) throws SQLException {
        PreparedStatement sub = DB.connection.prepareStatement("select getklientidbyemail(?)");
        sub.setString(1, email);
        ResultSet rs = sub.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    private int insertIntoZamowieniaByLogin(LocalDateTime startDate, String login) throws SQLException {
        PreparedStatement main = DB.connection.prepareStatement("insert into zamowienia(id_klienta, timestamp_kupna) values (?,?)");
        PreparedStatement sub = DB.connection.prepareStatement("select getklientidbylogin(?)");
        sub.setString(1, login);
        ResultSet rs = sub.executeQuery();
        rs.next();
        main.setInt(1, rs.getInt(1));
        main.setString(2, startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        main.executeUpdate();
        PreparedStatement sub2 = DB.connection.prepareStatement("select max(z.id_zamowienia) from zamowienia z");
        ResultSet rs2 = sub2.executeQuery();
        rs2.next();
        return rs2.getInt(1);
    }

    private int insertIntoZamowieniaByEmail(LocalDateTime startDate, String email) throws SQLException {
        PreparedStatement main = DB.connection.prepareStatement("insert into zamowienia(id_klienta, timestamp_kupna) values (?,?)");
        main.setInt(1, getIdPodrozujacegoByEmail(email));
        main.setString(2, startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        main.executeUpdate();
        PreparedStatement sub = DB.connection.prepareStatement("select max(z.id_zamowienia) from zamowienia z");
        ResultSet rs = sub.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    private int extractIdUlgi(Discount discount) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select getIdUlgi(?)");
        ps.setString(1, discount.discount());
        ps.executeQuery();
        ResultSet rs = ps.getResultSet();
        rs.next();
        return rs.getInt(1);
    }

    private int extractIdRabatu(Voucher voucher) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select getidrabatu(?)");
        ps.setString(1, voucher.discount());
        ps.executeQuery();
        ResultSet rs = ps.getResultSet();
        rs.next();
        return rs.getInt(1);
    }

    private int extractIdTypBiletu(LongTermTicketType ticketType) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select cbo.id_typ_biletu from cennik_biletow_okresowych cbo " +
                "join przewoznicy p on cbo.id_przewoznika = p.id_przewoznika where p.nazwa_skrocona=? and extract(days from cbo.okres_waznosci)::int=?;");
        ps.setString(1, ticketType.company());
        ps.setInt(2, ticketType.period().getDays());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    private int insertIntoBiletyOkresoweZamowienia(int idZamowienia, int idUlgi, int idRabatu) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("insert into bilety_okresowe_zamowienia(id_zamowienia, timestamp_zwrotu, id_ulgi, id_rabatu) " +
                "values (?,null,?,?);");
        ps.setInt(1, idZamowienia);
        ps.setInt(2, idUlgi);
        ps.setInt(3, idRabatu);
        ps.executeUpdate();
        PreparedStatement ps2 = DB.connection.prepareStatement("select max(boz.id_bilety_okresowe_zamowienia) from bilety_okresowe_zamowienia boz;");
        ResultSet rs2 = ps2.executeQuery();
        rs2.next();
        return rs2.getInt(1);
    }

    private int insertIntoBiletyJednorazoweZamowienia(int idZamowienia, int idUlgi, int idRabatu) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("insert into bilety_jednorazowe_zamowienia(id_zamowienia, timestamp_zwrotu, id_ulgi, id_rabatu) " +
                "values (?,null,?,?);");
        ps.setInt(1, idZamowienia);
        ps.setInt(2, idUlgi);
        ps.setInt(3, idRabatu);
        ps.executeUpdate();
        PreparedStatement ps2 = DB.connection.prepareStatement("select max(bjz.id_bilety_jednorazowe_zamowienia) from bilety_jednorazowe_zamowienia bjz;");
        ResultSet rs2 = ps2.executeQuery();
        rs2.next();
        return rs2.getInt(1);
    }

    private void insertIntoBiletyOkresowe(int idBiletyOkresoweZamowienia, int idPodrozujacego, LocalDateTime startDate, int idTypBiletu) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("insert into bilety_okresowe(id_bilety_okresowe_zamowienia, id_podrozujacego, timestamp_od, id_typ_biletu) " +
                "values (?,?,?,?);");
        ps.setInt(1, idBiletyOkresoweZamowienia);
        ps.setInt(2, idPodrozujacego);
        ps.setString(3, startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        ps.setInt(4, idTypBiletu);
        ps.executeUpdate();
//        PreparedStatement ps2 = DB.connection.prepareStatement("select max(bo.id_biletu_okresowego) from bilety_okresowe bo;");
//        ResultSet rs2 = ps2.executeQuery();
//        rs2.next();
//        return rs2.getInt(1);
    }

    private void insertIntoBiletyJednorazowe(int idBiletyJednorazoweZamowienia,int idPrzejazdu, int idPodrozujacego, int startStation, int endStation, int nrWagonu, int nrMiejsca, int idSzegolow) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("insert into bilety_jednorazowe(id_bilety_jednorazowe_zamowienia, id_przejazdu, id_podrozujacego, od_stacji, do_stacji, nr_wagonu, nr_miejsca, id_szczegolow) " +
                "values (?,?,?,?,?,?,?,?);");
        ps.setInt(1, idBiletyJednorazoweZamowienia);
        ps.setInt(2, idPrzejazdu);
        ps.setInt(3, idPodrozujacego);
        ps.setInt(4, startStation);
        ps.setInt(5, endStation);
        ps.setInt(6, nrWagonu);
        ps.setInt(7, nrMiejsca);
        ps.setInt(8, idSzegolow);
        ps.executeUpdate();
//        PreparedStatement ps2 = DB.connection.prepareStatement("select max(bj.id_biletu_jednorazowego) from bilety_jednorazowe bj;");
//        ResultSet rs2 = ps2.executeQuery();
//        rs2.next();
//        return rs2.getInt(1);
    }


}
