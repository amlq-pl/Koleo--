package pl.tcs.oopproject.model.station;

import java.time.LocalDateTime;

public record Station(String town, LocalDateTime departureTime, LocalDateTime arrivalTime) {
	@Override
	public String toString() {
		return "station: " + town + " arrives at " + departureTime.toString() + " departs at " + arrivalTime.toString();
	}
}
