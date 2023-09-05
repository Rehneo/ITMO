package ClientFX;
import ClientWork.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class AuthorizeController implements Initializable {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button sign;

    @FXML
    private Button register;


    @FXML
    private Label languageLabel;

    @FXML
    private ChoiceBox<String> languageBox;


    public void authorize(ActionEvent e) throws IOException {
        String login = loginField.getText();
        String password = passwordField.getText();
        if((login == null || password == null) || (login.equals("") || password.equals(""))){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(Main.resourceBundle.getString("nullLoginOrPassword"));
            alert.show();
        }else {
            AlertContainer alertContainer = GUIHandler.authorize(login, password);
            if ((alertContainer.alert().showAndWait().get() == ButtonType.OK) && alertContainer.isConnected()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/t.fxml"));
                GUIHandler.load(e, loader);
            }
        }
    }


    public void register(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Register.fxml"));
        GUIHandler.load(e,loader);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginField.setPromptText(Main.resourceBundle.getString("loginField"));
        passwordField.setPromptText(Main.resourceBundle.getString("passwordField"));
        sign.setText(Main.resourceBundle.getString("signButton"));
        register.setText(Main.resourceBundle.getString("registerButton"));
        languageBox.getItems().addAll("Русский", "English","Dansk","Norsk");
        languageBox.setOnAction(this::changeLanguage);
        languageLabel.setText(Main.resourceBundle.getString("changeLanguage"));
        sign.setOnMouseEntered(this::changeColor);
    }

    private void changeColor(MouseEvent mouseEvent) {
    }

    public void changeLanguage(ActionEvent event){
        String login = loginField.getText();
        String password = passwordField.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
        GUIHandler.changeLanguage(languageBox,event,loader);
        loginField.setText(login);
        passwordField.setText(password);
        AuthorizeController controller = loader.getController();
        controller.setData(login,password);


    }



    public void setData(String login, String password){
        loginField.setText(login);
        passwordField.setText(password);
    }

}
