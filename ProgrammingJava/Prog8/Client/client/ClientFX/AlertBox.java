package ClientFX;
import ClientWork.Main;
import Data.Ticket;
import Data.TicketType;
import Exceptions.InvalidInputDataException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class AlertBox {
    private Stage stage;
    public static FXMLLoader loader;
    private final Ticket ticket;

    private final int index;
    private final TextField nameField = new TextField();
    private final TextField priceField = new TextField();
    private final TextField cordXField = new TextField();
    private final TextField cordYField = new TextField();
    private final CheckBox isRefundable = new CheckBox();
    private final ChoiceBox<TicketType> typeField = new ChoiceBox<>();
    private final TextField eventNameField = new TextField();
    private final TextField eventDescField = new TextField();

    public AlertBox(Ticket ticket, int index){
        this.index = index;
        this.ticket = ticket;
        typeField.getItems().addAll(TicketType.values());
        nameField.setText(ticket.getName());
        priceField.setText(String.valueOf(ticket.getPrice()));
        cordXField.setText(String.valueOf(ticket.getCorX()));
        cordYField.setText(String.valueOf(ticket.getCorY()));
        typeField.setValue(ticket.getType());
        eventNameField.setText(ticket.getEventName());
        eventDescField.setText(ticket.getEventDescription());
        isRefundable.setSelected(ticket.isRefundable());
        isRefundable.setText(Main.resourceBundle.getString("refundable"));
    }

    public void display(){
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        Button closeButton = new Button("OK");
        Button updateButton = new Button(Main.resourceBundle.getString("updateTicket"));
        Button deleteButton = new Button(Main.resourceBundle.getString("removeButton"));
        deleteButton.setOnAction(e -> this.remove());
        updateButton.setOnAction(e -> this.update());
        closeButton.setOnAction(e -> stage.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new Label(Main.resourceBundle.getString("nameTicket")),
                nameField,
                new Label(Main.resourceBundle.getString("price")),
                priceField,
                new Label(Main.resourceBundle.getString("cordX")),
                cordXField,
                new Label(Main.resourceBundle.getString("cordY")),
                cordYField,
                isRefundable,
                new Label(Main.resourceBundle.getString("typeTicket")),
                typeField,
                new Label(Main.resourceBundle.getString("eventName")),
                eventNameField,
                new Label(Main.resourceBundle.getString("eventDesc")),
                eventDescField,
                closeButton,
                updateButton,
                deleteButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void displayToRead(){
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(Main.resourceBundle.getString("onlyRead"));
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> stage.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new Label(Main.resourceBundle.getString("nameTicket")),
                new Label(nameField.getText()),
                new Label(Main.resourceBundle.getString("price")),
                new Label(priceField.getText()),
                new Label(Main.resourceBundle.getString("cordX")),
                new Label(cordXField.getText()),
                new Label(Main.resourceBundle.getString("cordY")),
                new Label(cordYField.getText()),
                new Label(isRefundable.getText()+": "+ isRefundable.isSelected()),
                new Label(Main.resourceBundle.getString("typeTicket")),
                new Label(typeField.getSelectionModel().getSelectedItem().toString()),
                new Label(Main.resourceBundle.getString("eventName")),
                new Label(eventNameField.getText()),
                new Label(Main.resourceBundle.getString("eventDesc")),
                new Label(eventDescField.getText()),
                closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void remove(){
        Alert alert;
        if(ticket.getOwner().equals(GUIHandler.getDataSender().getUserData().getLogin())){
            alert = GUIHandler.removeFromDatabase(ticket.getId().intValue());
        }else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(Main.resourceBundle.getString("cannotRemove"));
        }
        VisualsController controller = loader.getController();
        controller.refresh();
        if(alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }
    public void update(){
        Alert alert;
        Ticket newTicket = null;
        try {
            if(ticket.getOwner().equals(GUIHandler.getDataSender().getUserData().getLogin())) {
                newTicket = getTicketFromStage();
                alert = GUIHandler.updateTicket(newTicket, ticket.getId());
                VisualsController controller = loader.getController();
                controller.refresh(newTicket,index);
                alert.show();
            }else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(Main.resourceBundle.getString("cannotUpdate"));
                alert.show();
            }
        } catch (InvalidInputDataException ex) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(ex.getMessage());
            alert.show();
        }
    }

    public static void setLoader(FXMLLoader loader){
        AlertBox.loader = loader;
    }
    public Ticket getTicketFromStage() throws InvalidInputDataException {
       return GUIHandler.getTicketFromStage(nameField,priceField,cordXField,cordYField,eventNameField,eventDescField,typeField,isRefundable);
    }
}
