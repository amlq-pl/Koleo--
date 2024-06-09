package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.TicketGetter;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.PricePLN;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.history.HistoryLongTermTicket;
import pl.tcs.oopproject.model.history.HistorySingleJourneyTicket;
import pl.tcs.oopproject.model.ticket.Addition;
import pl.tcs.oopproject.model.ticket.Details;
import pl.tcs.oopproject.model.ticket.LongTermTicketType;
import pl.tcs.oopproject.model.users.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Period;
import java.util.ArrayList;

public class TicketGet implements TicketGetter {
    @Override
    public ArrayList<HistorySingleJourneyTicket> getSingleUseTickets(String login) throws SQLException {
        FindPlacesForConnectionWithTransfers finder = new FindPlacesForConnectionWithTransfers();
        ArrayList<HistorySingleJourneyTicket> tickets = new ArrayList<>();
        PreparedStatement ps = DB.connection.prepareStatement("select *,r.nazwa as r_nazwa,r.znizka as r_znizka,u.nazwa as u_nazwa,u.znizka as u_znizka from konto ko join klienci kl on ko.id_klienta = kl.id_klienta " +
                "join zamowienia z on kl.id_klienta = z.id_klienta " +
                "join bilety_jednorazowe_zamowienia bjz on z.id_zamowienia = bjz.id_zamowienia " +
                "join bilety_jednorazowe bj on bjz.id_bilety_jednorazowe_zamowienia = bj.id_bilety_jednorazowe_zamowienia " +
                "join przejazdy prze on bj.id_przejazdu = prze.id_przejazdu " +
                "join trasy_przewoznicy tp on prze.id_trasy_przewoznika = tp.id_trasy_przewoznika  " +
                "join przewoznicy p on tp.id_przewoznika = p.id_przewoznika " +
                "left join ulgi u on bjz.id_ulgi = u.id_ulgi " +
                "left join rabaty r on bjz.id_rabatu = r.id_rabatu " +
                "where ko.login=?;");
        PreparedStatement numOfStations = DB.connection.prepareStatement("select howmanystations(?)");
        PreparedStatement getPodroznik = DB.connection.prepareStatement("select * from klienci kl where id_klienta=?");
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            boolean refunded;
            Discount discount = null;
            Voucher voucher = null;
            Person person;
            if (rs.getString("id_podrozujacego") == null) {
                person = new Person(rs.getString("imie"), rs.getString("nazwisko"), rs.getDate("data_urodzenia").toLocalDate(), rs.getString("email"), rs.getString("nr_telefonu"));
            } else {
                getPodroznik.setInt(1, rs.getInt("id_podrozujacego"));
                ResultSet klient = getPodroznik.executeQuery();
                klient.next();
                person = new Person(klient.getString("imie"), klient.getString("nazwisko"), klient.getDate("data_urodzenia").toLocalDate(), klient.getString("email"), klient.getString("nr_telefonu"));
            }

            if (rs.getString("id_rabatu") != null) {
                voucher = new Voucher(rs.getString("r_nazwa"), rs.getDouble("r_znizka"));
            }
            if (rs.getString("id_ulgi") != null) {
                discount = new Discount(rs.getString("u_nazwa"), rs.getDouble("u_znizka"));
            }
            refunded = rs.getString("timestamp_zwrotu") != null;
            numOfStations.setInt(1,rs.getInt("id_przejazdu"));
            ResultSet stations = numOfStations.executeQuery();
            stations.next();
            int liczbaMiniPrzejazdow=stations.getInt(1)-1;
            tickets.add(new HistorySingleJourneyTicket(discount, voucher, finder.getSpecificSeat(rs.getInt("id_przejazdu"), rs.getInt("nr_wagonu"), rs.getInt("nr_miejsca")),
                    getDepartureTime(rs.getInt("id_przejazdu"), rs.getInt("od_stacji")).toLocalDateTime(), getArrivalTime(rs.getInt("id_przejazdu"), rs.getInt("do_stacji")).toLocalDateTime(),
                    getStationName(rs.getInt("id_przejazdu"), rs.getInt("od_stacji")), rs.getInt("id_bilety_jednorazowe_zamowienia"), getDetails(rs.getInt("id_szczegolow")),
                    getStationName(rs.getInt("id_przejazdu"), rs.getInt("do_stacji")), person, rs.getDouble("koszt_bazowy")*((rs.getDouble("do_stacji")-rs.getDouble("od_stacji"))/(double)liczbaMiniPrzejazdow), refunded));
        }
        return tickets;
    }

    private String getStationName(int idPrzejazdu, int nrStacji) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select *,s.nazwa as s_nazwa from przejazdy p " +
                "join trasy_przewoznicy tp on p.id_trasy_przewoznika = tp.id_trasy_przewoznika " +
                "join trasy t on tp.id_trasy = t.id_trasy " +
                "join stacje_posrednie sp on t.id_trasy = sp.id_trasy " +
                "join stacje s on sp.id_stacji = s.id_stacji " +
                "where sp.numer_stacji=? and p.id_przejazdu=?;");
        ps.setInt(2, idPrzejazdu);
        ps.setInt(1, nrStacji);
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        return resultSet.getString("s_nazwa");
    }

    private Details getDetails(int idSzczegolow) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select * from szczegoly_biletu where id_szczegolow=?");
        PreparedStatement ps2 = DB.connection.prepareStatement("select * from koszty_udogodnien where data_poczatkowa<=now() and data_koncowa>=now()");
        ps.setInt(1, idSzczegolow);
        ResultSet rs = ps.executeQuery();
        rs.next();
        ArrayList<Addition> additions = new ArrayList<>();
        ResultSet rs2 = ps2.executeQuery();
        while (rs2.next()) {
            if (rs2.getString("nazwa").equals("dodatkowy bagaż")) {
                additions.add(new Addition(new Addition(rs2.getString("nazwa"), rs2.getDouble("koszt_procentowy")), rs.getBoolean("dodatkowy_bagaz")));
            } else if (rs2.getString("nazwa").equals("przewóz zwierzęcia")) {
                additions.add(new Addition(new Addition(rs2.getString("nazwa"), rs2.getDouble("koszt_procentowy")), rs.getBoolean("zwierze")));
            } else {
                additions.add(new Addition(new Addition(rs2.getString("nazwa"), rs2.getDouble("koszt_procentowy")), rs.getBoolean("rower")));
            }
        }
        return new Details(additions);
    }

    private Timestamp getDepartureTime(int idPrzejazdu, int nrStacji) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select * from stations s " +
                        "where s.id_przejazdu=? " +
                        "order by s.czas_przyjazdu ", ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1, idPrzejazdu);
        ResultSet resultSet = ps.executeQuery();
        resultSet.absolute(nrStacji);
        return resultSet.getTimestamp("czas_odjazdu");
    }

    private Timestamp getArrivalTime(int idPrzejazdu, int nrStacji) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select * from stations s " +
                        "where s.id_przejazdu=? " +
                        "order by s.czas_przyjazdu ", ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1, idPrzejazdu);
        ResultSet resultSet = ps.executeQuery();
        resultSet.absolute(nrStacji);
        return resultSet.getTimestamp("czas_przyjazdu");
    }

    @Override
    public ArrayList<HistoryLongTermTicket> getLongTermTickets(String login) throws SQLException {
        ArrayList<HistoryLongTermTicket> tickets = new ArrayList<>();
        PreparedStatement ps = DB.connection.prepareStatement("select bo.id_podrozujacego,kl.imie,kl.nazwisko,kl.data_urodzenia,kl.email,kl.nr_telefonu,cbo.okres_waznosci,r.id_rabatu as r_id_rabatu,r.nazwa as r_nazwa, r.znizka as r_znizka,u.id_ulgi as u_id_ulgi,u.znizka as u_znizka, u.nazwa as u_znizka from konto ko join klienci kl on ko.id_klienta = kl.id_klienta " +
                "join zamowienia z on kl.id_klienta = z.id_klienta " +
                "join bilety_okresowe_zamowienia boz on z.id_zamowienia = boz.id_zamowienia " +
                "join bilety_okresowe bo on boz.id_bilety_okresowe_zamowienia = bo.id_bilety_okresowe_zamowienia " +
                "join cennik_biletow_okresowych cbo on bo.id_typ_biletu = cbo.id_typ_biletu " +
                "join przewoznicy p on cbo.id_przewoznika = p.id_przewoznika " +
                "left join ulgi u on boz.id_ulgi = u.id_ulgi " +
                "left join rabaty r on boz.id_rabatu = r.id_rabatu " +
                "where ko.login=?;");
        PreparedStatement getPodroznik = DB.connection.prepareStatement("select * from klienci kl where id_klienta=?");
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Discount discount = null;
            Voucher voucher = null;
            Person person;
            if (rs.getString("id_podrozujacego") == null) {
                person = new Person(rs.getString("imie"), rs.getString("nazwisko"), rs.getDate("data_urodzenia").toLocalDate(), rs.getString("email"), rs.getString("nr_telefonu"));
            } else {
                getPodroznik.setInt(1, rs.getInt("id_podrozujacego"));
                ResultSet klient = getPodroznik.executeQuery();
                klient.next();
                person = new Person(klient.getString("imie"), klient.getString("nazwisko"), klient.getDate("data_urodzenia").toLocalDate(), klient.getString("email"), klient.getString("nr_telefonu"));
            }

            String[] okres_waznosci = rs.getString("okres_waznosci").split(" ");

            if (rs.getString("r_id_rabatu") != null) {
                voucher = new Voucher(rs.getString("r_nazwa"), rs.getDouble("r_znizka"));
            }
            if (rs.getString("u_id_ulgi") != null) {
                discount = new Discount(rs.getString("u_nazwa"), rs.getDouble("u_znika"));
            }
            tickets.add(new HistoryLongTermTicket(rs.getDate("timestamp_od").toLocalDate(), new LongTermTicketType(Period.ofDays(Integer.parseInt(okres_waznosci[0])), new PricePLN(rs.getDouble("cena_bazowa")), rs.getString("nazwa_skrocony")), discount, voucher, rs.getInt("id_bilety_okresowe_zamowienia"), person));
        }
        return tickets;
    }
}
