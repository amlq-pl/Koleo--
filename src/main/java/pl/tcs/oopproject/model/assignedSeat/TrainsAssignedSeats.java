package pl.tcs.oopproject.model.assignedSeat;

import pl.tcs.oopproject.model.connection.MultiStopRoute;

import java.util.List;

public class TrainsAssignedSeats {
    private MultiStopRoute connection;
    private List<AssignedSeat> seats;

    public TrainsAssignedSeats(MultiStopRoute connection, List<AssignedSeat> seats) {
        this.connection = connection;
        this.seats = seats;
    }

    public List<AssignedSeat> seatList() {
        return seats;
    }

    public void seat(List<AssignedSeat> seats) {
        this.seats = seats;
    }

    public MultiStopRoute getConnection() {
        return connection;
    }

    public void setConnection(MultiStopRoute connection) {
        this.connection = connection;
    }

}
