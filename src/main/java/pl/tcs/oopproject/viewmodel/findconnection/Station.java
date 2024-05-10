package pl.tcs.oopproject.viewmodel.findconnection;

import java.time.LocalDateTime;

public interface Station {
	public String getTown();
	public LocalDateTime getArrivalDate();
	public LocalDateTime getDepartureDate();
}
