package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.TicketGetter;
import pl.tcs.oopproject.model.history.HistoryLongTermTicket;
import pl.tcs.oopproject.model.history.HistorySingleJourneyTicket;
import pl.tcs.oopproject.model.users.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicketGet implements TicketGetter {
    @Override
    public ArrayList<HistorySingleJourneyTicket> getSingleUseTickets(String login) {
        return null;
    }

    @Override
    public ArrayList<HistoryLongTermTicket> getLongTermTickets(String login) throws SQLException {
        ArrayList<HistoryLongTermTicket> tickets = new ArrayList<>();
        PreparedStatement ps =DB.connection.prepareStatement("select * from konto ko join klienci kl on ko.id_klienta = kl.id_klienta " +
                "join zamowienia z on kl.id_klienta = z.id_klienta " +
                "join bilety_okresowe_zamowienia boz on z.id_zamowienia = boz.id_zamowienia " +
                "join bilety_okresowe bo on boz.id_bilety_okresowe_zamowienia = bo.id_bilety_okresowe_zamowienia " +
                "join cennik_biletow_okresowych cbo on bo.id_typ_biletu = cbo.id_typ_biletu " +
                "where ko.login=?;");
        PreparedStatement getPodroznik = DB.connection.prepareStatement("select * from klienci kl where id_klienta=?");
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Person person=null;
//            if(rs.getString("id_podrozujacego")==null){
//                person=new Person(rs.getString("imie"),rs.getString("nazwisko"),rs.getDate("data_urodzenia").toLocalDate(),rs.getString("email"),rs.getString("nr_telefonu"));
//            } else{
//                getPodroznik.setInt(1,);
//                person
//            }
//            tickets.add(new HistoryLongTermTicket())
        }
        return tickets;
    }
}
