package pl.tcs.oopproject.view.componentControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.assignedSeat.AssignedSeat;
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.view.sceneControllers.TicketFormController;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class TicketItemContainer extends AnchorPane implements Initializable {
    private final ObservableList<TicketItem> ticketItems = FXCollections.observableArrayList();
    private final MultiStopRoute currentRoute;
    private final List<AssignedSeat> seatList;
    private final Person person = ActiveUser.getPerson();
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

    public TicketItemContainer(MultiStopRoute route, List<AssignedSeat> seatList) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/ticket-container.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        this.currentRoute = route;
        this.seatList = seatList;

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
        FXMLLoader loader = new FXMLLoader(App.class.getResource("scenes/ticket-form.fxml"));

        Parent p = null;

        try {
            p = loader.load();
            TicketFormController controller = loader.getController();
            controller.setPerson(person);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(p);
        newStage.setScene(scene);
        newStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DiscountComboBox.getItems().addAll(App.Discounts);
        VoucherComboBox.getItems().addAll(App.Vouchers);

        BicycleCheck.allowIndeterminateProperty().setValue(false);
        AnimalCheck.allowIndeterminateProperty().setValue(false);
        LuggageCheck.allowIndeterminateProperty().setValue(false);
    }

    public void getDetails() {
//        Addition animal = null;
//        Addition bicycle = null;
//        Addition luggage = null;
//
//        return new Details(new ArrayList<Addition>(List.of(bicycle, luggage, animal)));
    }
}
