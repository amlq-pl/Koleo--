import org.junit.Test;
import pl.tcs.oopproject.postgresDatabaseIntegration.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class GetDirectConnectionsFunctionTest {
    @Test
    public void test1() throws SQLException {

        LocalDateTime startDate= LocalDateTime.parse("2024-06-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate= LocalDateTime.parse("2024-06-02 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        PreparedStatement ps = DB.connection.prepareStatement("select * from stations s " +
                "where s.czas_przyjazdu>=?::timestamp and s.czas_przyjazdu<=?::timestamp ;");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        ps.setString(1,formatter.format(startDate));
        ps.setString(2,formatter.format(endDate));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("czas_przyjazdu")+rs.getString("czas_odjazdu")+rs.getString("id_przejazdu"));
        }
    }
}