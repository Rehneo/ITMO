package ClientFX;
import ClientWork.Main;
import Data.Ticket;
import Data.TicketType;
import Exceptions.InvalidInputDataException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InsertController implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField cordXField;
    @FXML
    private TextField cordYField;
    @FXML
    private CheckBox isRefundable;
    @FXML
    private ChoiceBox<TicketType> typeField;
    @FXML
    private TextField eventNameField;
    @FXML
    private TextField eventDescField;

    @FXML
    private TextField keyField;

    @FXML
    private Button button;

    @FXML
    private Button goBack;
    @FXML
    private Label label;

    @FXML
    private Label upLabel;

    @FXML
    private Label goBackLabel;

    private long id;

    public void insert() {
        try {
            int key;
            if (keyField.getText() != null && !keyField.getText().equals("")) {
                try {
                    key = Integer.parseInt(keyField.getText());
                }catch (NumberFormatException ex){
                    throw new InvalidInputDataException(Main.resourceBundle.getString("keyValueException"));
                }
            } else {
                throw new InvalidInputDataException(Main.resourceBundle.getString("keyNullException"));
            }
            Ticket ticket = getTicketFromStage();
            Alert alert = GUIHandler.insertTicket(ticket, key);
            alert.show();
        }catch (InvalidInputDataException ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(ex.getMessage());
            alert.show();
        }
    }

    public void update(ActionEvent e){
        try {
            Ticket ticket = getTicketFromStage();
            Alert alert = GUIHandler.updateTicket(ticket, id);
            alert.show();
        } catch (InvalidInputDataException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(ex.getMessage());
            alert.show();
        }

    }

    public Ticket getTicketFromStage() throws InvalidInputDataException {
        return GUIHandler.getTicketFromStage(nameField,priceField,cordXField,cordYField,eventNameField,eventDescField,typeField,isRefundable);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeField.getItems().addAll(TicketType.values());
        keyField.setVisible(true);
        nameField.setPromptText(Main.resourceBundle.getString("nameTicket"));
        keyField.setPromptText(Main.resourceBundle.getString("key"));
        priceField.setPromptText(Main.resourceBundle.getString("price"));
        cordYField.setPromptText(Main.resourceBundle.getString("cordY"));
        cordXField.setPromptText(Main.resourceBundle.getString("cordX"));
        isRefundable.setText(Main.resourceBundle.getString("refundable"));
        eventNameField.setPromptText(Main.resourceBundle.getString("eventName"));
        eventDescField.setPromptText(Main.resourceBundle.getString("eventDesc"));
        label.setText(Main.resourceBundle.getString("addButton"));
        upLabel.setText(Main.resourceBundle.getString("insertLabel"));
        goBackLabel.setText(Main.resourceBundle.getString("goBackButton"));

    }

    public void goToTable(ActionEvent event) throws IOException {
        GUIHandler.refresh();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/t.fxml"));
        GUIHandler.load(event, loader);
    }

    public void displayTicket(Ticket ticket){
        nameField.setText(ticket.getName());
        priceField.setText(String.valueOf(ticket.getPrice()));
        cordXField.setText(String.valueOf(ticket.getCorX()));
        cordYField.setText(String.valueOf(ticket.getCorY()));
        typeField.setValue(ticket.getType());
        eventNameField.setText(ticket.getEventName());
        eventDescField.setText(ticket.getEventDescription());
        isRefundable.setSelected(ticket.isRefundable());
        keyField.setVisible(false);
        id = ticket.getId();
        button.setOnAction(this::update);
        label.setText(Main.resourceBundle.getString("updateTicket"));
    }
}
