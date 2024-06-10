package pl.tcs.oopproject.view.componentControllers.ticket;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.assignedSeat.AssignedSeat;
import pl.tcs.oopproject.model.assignedSeat.TrainsAssignedSeats;
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.ticket.Addition;
import pl.tcs.oopproject.model.ticket.Details;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.view.componentControllers.ticket.TicketItem;
import pl.tcs.oopproject.view.sceneControllers.TicketFormController;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class TicketItemContainer extends AnchorPane implements Initializable {
    private final ObservableList<TicketItem> ticketItems = FXCollections.observableArrayList();
    private final MultiStopRoute currentRoute;
    private final List<AssignedSeat> seatList;
    public Person person = (ActiveUser.getActiveUser() == null) ? Person.DEFAULT : ActiveUser.getPerson();
    private final TrainsAssignedSeats assignedSeats;
    @FXML
    private VBox TicketContainer;
    @FXML
    private ComboBox<Discount> DiscountComboBox;
    @FXML
    private ComboBox<Voucher> VoucherComboBox;
    @FXML
    private CheckBox BicycleCheck;
    @FXML
    private CheckBox AnimalCheck;
    @FXML
    private CheckBox LuggageCheck;
    @FXML
    private Label CostLabel;

    public TicketItemContainer(MultiStopRoute route, List<AssignedSeat> seatList, TrainsAssignedSeats assignedSeats) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/ticket-container.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        this.currentRoute = route;
        this.seatList = seatList;
        this.assignedSeats = assignedSeats;

        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int size = seatList.size();
        IntStream.range(0, size).forEach(c -> {
            TicketItem item = new TicketItem(currentRoute.route().get(c), seatList.get(c),
                    currentRoute.trains().get(c));
            TicketContainer.getChildren().addAll(item);
        });
    }

    public MultiStopRoute getConnection() {
        return currentRoute;
    }

    @FXML
    protected void DeleteButtonClick() {

    }

    @FXML
    protected void SetNewDataClick() {
        Stage newStage = new Stage();
        TicketFormController form = new TicketFormController(this);

        Scene scene = new Scene(form);
        newStage.setScene(scene);
        newStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DiscountComboBox.getItems().addAll(App.Discounts);
        DiscountComboBox.valueProperty().setValue(App.DEFAULT_DISCOUNT);
        VoucherComboBox.getItems().addAll(App.Vouchers);
        VoucherComboBox.valueProperty().setValue(App.DEFAULT_VOUCHER);

        BicycleCheck.allowIndeterminateProperty().setValue(false);
        AnimalCheck.allowIndeterminateProperty().setValue(false);
        LuggageCheck.allowIndeterminateProperty().setValue(false);

        try {
            CostLabel.textProperty().setValue(currentRoute.cost().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Details getDetails() {
        Addition animal = new Addition(App.Additions.get(0), AnimalCheck.isSelected());
        Addition bicycle = new Addition(App.Additions.get(1), BicycleCheck.isSelected());
        Addition luggage = new Addition(App.Additions.get(2), LuggageCheck.isSelected());

        return new Details(new ArrayList<>(List.of(bicycle, luggage, animal)));
    }

    public Discount getDiscount() {
        return DiscountComboBox.getValue();
    }

    public Voucher getVoucher() {
        return VoucherComboBox.getValue();
    }

    public TrainsAssignedSeats getAssignedSeats() {
        return assignedSeats;
    }

    public Person getPerson() {
        return person;
    }
}
