import org.junit.Test;

import pl.tcs.oopproject.postgresDatabaseIntegration.GetDirectConnectionsInTimeframe;
import pl.tcs.oopproject.model.connection.ScheduledTrain;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class GetDirectConnectionsFunctionTest {
    @Test
    public void test1() throws SQLException {

        LocalDateTime startDate = LocalDateTime.parse("2024-06-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse("2024-06-02 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        GetDirectConnectionsInTimeframe dc = new GetDirectConnectionsInTimeframe();
        ArrayList<ScheduledTrain> scheduledTrains = dc.getDirectConnectionsInTimeframe(startDate, endDate);
        for (ScheduledTrain scheduledTrain : scheduledTrains) {
            scheduledTrain.display();
        }
    }
}