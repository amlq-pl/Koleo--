package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.place.TrainsAssignedSeats;
import pl.tcs.oopproject.model.ticket.Details;
import pl.tcs.oopproject.model.ticket.LongTermTicketType;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface CreateOrRefactorTicket {
	// RETURN ID OF THE TICKET
	int saveLongTermTicket(LocalDateTime startDate, Discount discount, Voucher voucher, String login, LongTermTicketType ticketType);
	
	int saveSingleJourneyTicket(Discount discount, Voucher voucher, Details details, MultiStopRoute train, ArrayList<TrainsAssignedSeats> seats, String login);
	
	int returnTicket(int id);
}
