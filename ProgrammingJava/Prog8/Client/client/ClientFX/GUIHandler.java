package ClientFX;
import ClientWork.DataSender;
import ClientWork.Main;
import Data.Coordinates;
import Data.Event;
import Data.Ticket;
import Data.TicketType;
import Exceptions.InvalidInputDataException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class GUIHandler {
    private static DataSender dataSender;
    private static Alert alert;

    public static DataSender getDataSender(){
        return dataSender;
    }

    public static void initDataSender(int port) {
        GUIHandler.dataSender = new DataSender(port);
    }
    public static AlertContainer authorize(String login, String password){
        alert = new Alert(Alert.AlertType.ERROR);
        try {
            boolean isConnected = dataSender.authorize(login,password,false);
            if(isConnected){
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText(Main.resourceBundle.getString("successAuthorization"));
            }else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText(Main.resourceBundle.getString("invalidData"));
            }
            return new AlertContainer(alert, isConnected);
        } catch (NoSuchElementException e) {
            alert.setHeaderText(Main.resourceBundle.getString("somethingIsWrong")+ e.getMessage());
            alert.setContentText(Main.resourceBundle.getString("repeatAttempt"));
            return new AlertContainer(alert, false);
        } catch (SocketException e) {
            alert.setHeaderText(Main.resourceBundle.getString("cannotConnect"));
            alert.setContentText(Main.resourceBundle.getString("repeatAttempt"));
            return new AlertContainer(alert, false);
        } catch (IllegalArgumentException ex) {
           alert.setHeaderText(Main.resourceBundle.getString("invalidPort"));
            alert.setContentText(Main.resourceBundle.getString("repeatAttempt"));
            return new AlertContainer(alert, false);
        } catch (Exception e) {
            alert.setHeaderText(Main.resourceBundle.getString("unknownError") + e);
            alert.setContentText(Main.resourceBundle.getString("repeatAttempt"));
            return new AlertContainer(alert, false);
        }

    }


    public static AlertContainer register(String login, String password){
        alert = new Alert(Alert.AlertType.ERROR);
        try {
            boolean isConnected = dataSender.authorize(login,password, true);
            if(isConnected){
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText(Main.resourceBundle.getString("successRegistration"));
            }else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText(Main.resourceBundle.getString("loginIsAlreadyExist"));
            }
            return new AlertContainer(alert, isConnected);
        }catch (NoSuchElementException e) {
            alert.setHeaderText(Main.resourceBundle.getString("somethingIsWrong") + e.getMessage());
            alert.setContentText(Main.resourceBundle.getString("repeatAttempt"));
            return new AlertContainer(alert, false);
        } catch (SocketException e) {
            alert.setHeaderText(Main.resourceBundle.getString("cannotConnect"));
            alert.setContentText(Main.resourceBundle.getString("repeatAttempt"));
            return new AlertContainer(alert, false);
        } catch (IllegalArgumentException ex) {
            alert.setHeaderText(Main.resourceBundle.getString("invalidPort"));
            alert.setContentText(Main.resourceBundle.getString("repeatAttempt"));
            return new AlertContainer(alert, false);
        } catch (Exception e) {
            alert.setHeaderText(Main.resourceBundle.getString("unknownError") + e);
            alert.setContentText(Main.resourceBundle.getString("repeatAttempt"));
            return new AlertContainer(alert, false);
        }
    }

    public static Alert refresh(){
        try {
            dataSender.sendUserData();
            return null;
        }catch (IOException e){
            if (e instanceof PortUnreachableException) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(Main.resourceBundle.getString("portIsUnreachable"));
                return alert;
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(Main.resourceBundle.getString("serverIsUnreachable"));
                return alert;
            }
        }catch (InterruptedException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public static Alert removeFromDatabase(int id){
        try {
            dataSender.removeFromDatabase(id);
            if(dataSender.getUserData().isSuccess()){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(Main.resourceBundle.getString("successRemovePart1")+" "+id+" "+Main.resourceBundle.getString("successRemovePart2"));
            }else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(Main.resourceBundle.getString("successRemovePart1")+" "+id+" "+Main.resourceBundle.getString("doesntExist"));

            }
            return alert;
        } catch (IOException e){
            if (e instanceof PortUnreachableException) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(Main.resourceBundle.getString("portIsUnreachable"));
                return alert;
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(Main.resourceBundle.getString("serverIsUnreachable"));
                return alert;
            }
        }catch (InterruptedException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public static Alert insertTicket(Ticket ticket, int key){
        try {
            dataSender.insertTicket(ticket,key);
            if(dataSender.getUserData().isSuccess()){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(Main.resourceBundle.getString("ticketWithKey")+" "+key+" "+Main.resourceBundle.getString("successAdd"));
            }else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(Main.resourceBundle.getString("ticketWithKey")+" "+key+" "+Main.resourceBundle.getString("isAlreadyExist"));

            }
            return alert;
        } catch (IOException e){
            if (e instanceof PortUnreachableException) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(Main.resourceBundle.getString("portIsUnreachable"));
                return alert;
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(Main.resourceBundle.getString("serverIsUnreachable"));
                return alert;
            }
        }catch (InterruptedException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public static void load(ActionEvent e, FXMLLoader loader) throws IOException {
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.show();
    }

    public static Alert updateTicket(Ticket ticket, long id) {
        try {
            dataSender.updateTicket(ticket, id);
            if(dataSender.getUserData().isSuccess()){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(Main.resourceBundle.getString("successRemovePart1")+" "+id+" "+Main.resourceBundle.getString("successUpdate"));
            }else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(Main.resourceBundle.getString("successRemovePart1")+" "+id+" "+Main.resourceBundle.getString("doesntExist"));

            }
            return alert;
        } catch (IOException e){
            if (e instanceof PortUnreachableException) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(Main.resourceBundle.getString("portIsUnreachable"));
                return alert;
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(Main.resourceBundle.getString("serverIsUnreachable"));
                return alert;
            }
        }catch (InterruptedException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public static Ticket getTicketFromStage(TextField nameField,
                                     TextField priceField,
                                     TextField cordXField,
                                     TextField cordYField,
                                     TextField eventNameField,
                                     TextField eventDescField,
                                     ChoiceBox<TicketType> typeField,
                                     CheckBox isRefundable
                                     ) throws InvalidInputDataException {
        String name;
        if (nameField.getText() != null && !nameField.getText().equals("")) {
            name = nameField.getText();
        } else {
            throw new InvalidInputDataException(Main.resourceBundle.getString("nameNullException"));
        }
        long price;
        if (priceField.getText() != null && !priceField.getText().equals("")) {
            try {
                price = Long.parseLong(priceField.getText());
                if(price <= 0) throw new InvalidInputDataException(Main.resourceBundle.getString("priceValueException"));
            }catch (NumberFormatException exception){
                throw new InvalidInputDataException(Main.resourceBundle.getString("priceTypeException"));
            }
        } else {
            throw new InvalidInputDataException(Main.resourceBundle.getString("priceNullException"));
        }
        float x;
        if (cordXField.getText() != null && !cordXField.getText().equals("")) {
            try {
                x = Float.parseFloat(cordXField.getText());
                if(x > 840 || x < 60) throw new InvalidInputDataException(Main.resourceBundle.getString("cordXValueException"));
            }catch (NumberFormatException ex){
                throw new InvalidInputDataException(Main.resourceBundle.getString("cordXTypeException"));
            }
        } else {
            throw new InvalidInputDataException(Main.resourceBundle.getString("cordXNullException"));
        }
        float y;
        if (cordYField.getText() != null && !cordYField.getText().equals("")) {
            try {
                y = Float.parseFloat(cordYField.getText());
                if(y > 396 || y < 60) throw new InvalidInputDataException(Main.resourceBundle.getString("cordYValueException"));
            }catch (NumberFormatException ex){
                throw new InvalidInputDataException(Main.resourceBundle.getString("cordYTypeException"));
            }
        } else {
            throw new InvalidInputDataException(Main.resourceBundle.getString("cordYNullException"));
        }
        String eventName;
        if (eventNameField.getText() != null && !eventNameField.getText().equals("")) {
            eventName = nameField.getText();
        } else {
            throw new InvalidInputDataException(Main.resourceBundle.getString("eventNameException"));
        }
        String eventDesc;
        if (eventDescField.getText() != null && !eventDescField.getText().equals("")) {
            eventDesc = nameField.getText();
        } else {
            eventDesc = null;
        }

        TicketType type = typeField.getValue();
        boolean refundable = isRefundable.isSelected();
        return new Ticket(name, price,new Coordinates(x, y),refundable,type,new Event(eventName, eventDesc));
    }

    public static void changeLanguage(ChoiceBox<String> languageBox, ActionEvent event,FXMLLoader loader){
        String language = languageBox.getValue();
        switch (language) {
            case "Русский" -> Main.setLocale("ru","RU");
            case "English" -> Main.setLocale("en","NZ");
            case "Dansk" -> Main.setLocale("da","DK");
            case "Norsk" -> Main.setLocale("nb","NO");
        }
        try {
            GUIHandler.load(event, loader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
