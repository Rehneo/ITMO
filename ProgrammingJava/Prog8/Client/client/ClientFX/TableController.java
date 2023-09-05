package ClientFX;
import ClientWork.Main;
import Data.Ticket;
import Data.TicketType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;

import java.io.IOException;

import java.net.URL;

import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.event.ActionEvent;

public class TableController implements Initializable {

    private Scene scene;

    private Stage stage;

    @FXML
    private TableView<Ticket> table;
    @FXML
    private TableColumn<Ticket, Long> id;
    @FXML
    private TableColumn<Ticket, String> name;
    @FXML
    private TableColumn<Ticket, Float> corX;

    @FXML
    private TableColumn<Ticket, Float> corY;
    @FXML
    private TableColumn<Ticket, LocalDateTime> creationDate;
    @FXML
    private TableColumn<Ticket, Long> price;
    @FXML
    private TableColumn<Ticket,Boolean> refundable;
    @FXML
    private TableColumn<Ticket, TicketType> type;
    @FXML
    private TableColumn<Ticket, Long> eventId;

    @FXML
    private TableColumn<Ticket, String> eventName;
    @FXML
    private TableColumn<Ticket, String> owner;

    @FXML
    private Button delete;
    @FXML
    private Button add;
    @FXML
    private Button update;
    @FXML
    private Button map;

    private final ScheduledExecutorService refresher = Executors.newSingleThreadScheduledExecutor();
    @FXML
    private Label languageLabel;

    @FXML
    private ChoiceBox<String> languageBox;

    @FXML
    private Label userLabel;

    @FXML
    private Button changeUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        corX.setCellValueFactory(new PropertyValueFactory<>("CorX"));
        corX.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        corY.setCellValueFactory(new PropertyValueFactory<>("CorY"));
        corY.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        creationDate.setCellValueFactory(new PropertyValueFactory<>("CreationDate"));
        price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        refundable.setCellValueFactory(new PropertyValueFactory<>("Refundable"));
        type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        eventId.setCellValueFactory(new PropertyValueFactory<>("EventId"));
        eventName.setCellValueFactory(new PropertyValueFactory<>("EventName"));
        owner.setCellValueFactory(new PropertyValueFactory<>("Owner"));
        ObservableList<Ticket> observableList = FXCollections.observableArrayList(GUIHandler.getDataSender().getUserData().getTickets());
        table.setItems(observableList);
        delete.setText(Main.resourceBundle.getString("removeButton"));
        add.setText(Main.resourceBundle.getString("addTableButton"));
        update.setText(Main.resourceBundle.getString("updateTicket"));
        map.setText(Main.resourceBundle.getString("map"));
        languageBox.getItems().addAll("Русский", "English","Dansk","Norsk");
        languageBox.setOnAction(this::changeLanguage);
        languageLabel.setText(Main.resourceBundle.getString("changeLanguage"));
        userLabel.setText(GUIHandler.getDataSender().getUserData().getLogin());
        changeUser.setText(Main.resourceBundle.getString("changeUser"));
        refresher.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> refreshTable());
            }
        }, 0, 6, TimeUnit.SECONDS);
        Platform.setImplicitExit(false);
    }


    public void refreshTable() {
        Ticket ticket = table.getSelectionModel().getSelectedItem();
        int index = table.getSelectionModel().getSelectedIndex();
        Alert alert = GUIHandler.refresh();
        if(alert != null){
            alert.show();
        }
        ObservableList<Ticket> list = FXCollections.observableArrayList(GUIHandler.getDataSender().getUserData().getTickets());
        if(ticket != null){
            Ticket prev = null;
            for(Ticket t: list){
                if(ticket.getId().equals(t.getId())){
                    prev = t;
                }
            }
            TableColumn<Ticket, ?> sortColumn = null;
            TableColumn.SortType st = null;
            if(table.getSortOrder().size()!=0){
                sortColumn = table.getSortOrder().get(0);
                st = sortColumn.getSortType();
            }
            table.setItems(list);
            if (sortColumn!=null) {
                table.getSortOrder().add(sortColumn);
                sortColumn.setSortType(st);
                sortColumn.setSortable(true);
            }
            if(prev != null){
                if(table.getSelectionModel().getSelectedIndex() == -1){
                    table.getSelectionModel().select(index);
                }
            }
        }
        table.refresh();
    }

    public void removeFromTable(){
        refresher.shutdown();
        if(table.getSelectionModel().getSelectedItem() != null){
            refresher.shutdown();
            Ticket ticket = table.getSelectionModel().getSelectedItem();
            if(ticket.getOwner().equals(GUIHandler.getDataSender().getUserData().getLogin())){
                Alert alert = GUIHandler.removeFromDatabase(ticket.getId().intValue());
                table.setItems(FXCollections.observableArrayList(GUIHandler.getDataSender().getUserData().getTickets()));
                alert.show();
                table.refresh();
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(Main.resourceBundle.getString("cannotRemove"));
                alert.show();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(Main.resourceBundle.getString("nothingIsSelected"));
            alert.show();
        }
    }

    public void goToInsert(ActionEvent event) throws IOException {
        refresher.shutdown();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Insert.fxml"));
        GUIHandler.load(event, loader);
    }

    public void goToUpdate(ActionEvent event) throws IOException {
        if(table.getSelectionModel().getSelectedItem() != null){
            if(table.getSelectionModel().getSelectedItem().getOwner().equals(GUIHandler.getDataSender().getUserData().getLogin())) {
                refresher.shutdown();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Insert.fxml"));
                Parent root = loader.load();
                InsertController controller = loader.getController();
                controller.displayTicket(table.getSelectionModel().getSelectedItem());
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(Main.resourceBundle.getString("cannotUpdate"));
                alert.show();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(Main.resourceBundle.getString("nothingIsSelected"));
            alert.show();
        }
    }

    public void goToVisuals(ActionEvent event) throws IOException{
        refresher.shutdown();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Visuals.fxml"));
        Parent root = loader.load();
        AlertBox.setLoader(loader);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void changeLanguage(ActionEvent e){
        refresher.shutdown();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/t.fxml"));
        GUIHandler.changeLanguage(languageBox,e,loader);
    }

    public void changeUser(ActionEvent e) throws IOException {
        refresher.shutdown();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
        GUIHandler.load(e,loader);
    }
}
