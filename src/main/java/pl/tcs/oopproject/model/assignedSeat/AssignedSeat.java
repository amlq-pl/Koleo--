package pl.tcs.oopproject.model.assignedSeat;

import pl.tcs.oopproject.model.carriage.Carriage;
import pl.tcs.oopproject.model.seat.Seat;

public record AssignedSeat(Carriage carriage, Seat seat) {
    public final static AssignedSeat TRAIN_WITHOUT_RESERVATION = new AssignedSeat(null, null);

    @Override
    public String toString() {
        return carriage.toString() + seat.toString();
    }
}
