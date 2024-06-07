package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.assignedSeat.TrainsAssignedSeats;
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
        int id_zamowienia=0;
        if (login != null) {
            id_zamowienia = insertIntoZamowieniaByLogin(startDate, login);
        } else {
            Checkers c = new Checkers();
            if (!c.checkIfPersonExists(person)) {
                new InsertNewPersonToDatabase().insert(person, null, null);
            }
            id_zamowienia=insertIntoZamowieniaByEmail(startDate, person.getEmailAddress());
        }
        for (LongTermTicketType ticket : ticketType) {

        }



        return ticketsToReturn;
    }

    @Override
    public ArrayList<SingleJourneyTrainTicket> saveSingleJourneyTicket(Person person, Discount discount, Voucher voucher, Details details, ArrayList<TrainsAssignedSeats> seats, String login) throws SQLException {
        return null;
    }


    @Override
    public int returnTicket(int id) {
        return 0;
    }

    private int insertIntoZamowieniaByLogin(LocalDateTime startDate, String login) throws SQLException {
        PreparedStatement main = DB.connection.prepareStatement("insert into zamowienia(id_klienta, timestamp_kupna) values (?,?)");
        PreparedStatement sub = DB.connection.prepareStatement("select getklientid(?)");
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
        PreparedStatement sub = DB.connection.prepareStatement("select k.id_klienta from klienci k where k.email=?");
        sub.setString(1, email);
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


}
