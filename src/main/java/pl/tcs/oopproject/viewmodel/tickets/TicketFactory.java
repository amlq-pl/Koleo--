package pl.tcs.oopproject.viewmodel.tickets;

import pl.tcs.oopproject.model.assignedSeat.TrainsAssignedSeats;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.ticket.Details;
import pl.tcs.oopproject.model.ticket.LongTermTicketType;
import pl.tcs.oopproject.model.ticket.LongTermTrainTicket;
import pl.tcs.oopproject.model.ticket.SingleJourneyTrainTicket;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.postgresDatabaseIntegration.CreateOrRefactor;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class TicketFactory {

    public ArrayList<LongTermTrainTicket> createLongTermTicket(LongTermTicketType ticket, Discount discount, Voucher voucher, LocalDate startDate, Person person) throws SQLException {
        CreateOrRefactor creator = new CreateOrRefactor();
        return creator.saveLongTermTicket(new ArrayList<>(Collections.singleton(startDate)), new ArrayList<>(Collections.singleton(discount)), new ArrayList<>(Collections.singleton(voucher)), ActiveUser.getActiveUser(), new ArrayList<>(Collections.singleton(ticket)), new ArrayList<>(Collections.singleton(person)));
    }

    public ArrayList<SingleJourneyTrainTicket> createSingleJourneyTicket(ArrayList<Discount> discount, ArrayList<Voucher> voucher, ArrayList<Details> details, ArrayList<TrainsAssignedSeats> seats, ArrayList<Person> person) throws SQLException, IllegalArgumentException {
        if (discount.size() != voucher.size() || voucher.size() != details.size() || details.size() != seats.size() || seats.size() != person.size())
            throw new IllegalArgumentException();
        CreateOrRefactor creator = new CreateOrRefactor();
        return creator.saveSingleJourneyTicket(person, discount, voucher, details, seats, ActiveUser.getActiveUser());
    }
}
