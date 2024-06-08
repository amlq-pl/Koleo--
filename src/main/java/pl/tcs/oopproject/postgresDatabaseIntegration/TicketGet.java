package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.TicketGetter;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.PricePLN;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.history.HistoryLongTermTicket;
import pl.tcs.oopproject.model.history.HistorySingleJourneyTicket;
import pl.tcs.oopproject.model.ticket.LongTermTicketType;
import pl.tcs.oopproject.model.users.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Period;
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
                "join przewoznicy p on cbo.id_przewoznika = p.id_przewoznika " +
                "join ulgi u on boz.id_ulgi = u.id_ulgi " +
                "join rabaty r on boz.id_rabatu = r.id_rabatu " +
                "where ko.login=?;");
        PreparedStatement getPodroznik = DB.connection.prepareStatement("select * from klienci kl where id_klienta=?");
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Discount discount=null;
            Voucher voucher=null;
            Person person;
            if(rs.getString("id_podrozujacego")==null){
                person=new Person(rs.getString("imie"),rs.getString("nazwisko"),rs.getDate("data_urodzenia").toLocalDate(),rs.getString("email"),rs.getString("nr_telefonu"));
            } else{
                getPodroznik.setInt(1,rs.getInt("id_podrozujacego"));
                ResultSet klient=getPodroznik.executeQuery();
                klient.next();
                person=new Person(klient.getString("imie"),klient.getString("nazwisko"),klient.getDate("data_urodzenia").toLocalDate(),klient.getString("email"),klient.getString("nr_telefonu"));
            }

            String[] okres_waznosci=rs.getString("okres_waznosci").split(" ");

            if(rs.getString("r.id_rabatu")!=null){
                voucher=new Voucher(rs.getString("r.nazwa"),rs.getDouble("r.znizka"));
            }
            if(rs.getString("u.id_ulgi")!=null){
                discount=new Discount(rs.getString("u.nazwa"),rs.getDouble("u.znika"));
            }
            tickets.add(new HistoryLongTermTicket(rs.getDate("timestamp_od").toLocalDate(),new LongTermTicketType(Period.ofDays(Integer.parseInt(okres_waznosci[0])),new PricePLN(rs.getDouble("cena_bazowa")),rs.getString("nazwa_skrocony")),discount,voucher,rs.getInt("id_bilety_okresowe_zamowienia"),person));
        }
        return tickets;
    }
}
