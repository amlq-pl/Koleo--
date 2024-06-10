package pl.tcs.oopproject.model.seat;

public record Seat(SeatType seatType, int number) {

    @Override
    public String toString() {
        return seatType.toString() + number;
    }
}
