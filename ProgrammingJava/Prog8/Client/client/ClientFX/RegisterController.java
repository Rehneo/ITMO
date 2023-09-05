package ClientFX;
import ClientWork.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button createUser;

    @FXML
    private Button authorize;
    @FXML
    private Label languageLabel;

    @FXML
    private ChoiceBox<String> languageBox;

    @FXML
    private PasswordField passwordField1;
    public void register(ActionEvent e) throws IOException {
        String login = loginField.getText();
        String password = passwordField.getText();
        String password1 = passwordField1.getText();
        if((login == null || password == null) || (login.equals("") || password.equals(""))){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(Main.resourceBundle.getString("nullLoginOrPassword"));
            alert.show();
        }else if(!password1.equals(password)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(Main.resourceBundle.getString("passwordsDontMatch"));
            alert.show();
        }else{
            AlertContainer alertContainer = GUIHandler.register(login, password);
            if ((alertContainer.alert().showAndWait().get() == ButtonType.OK) && alertContainer.isConnected()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/t.fxml"));
                GUIHandler.load(e, loader);
            }
        }
    }


    public void authorize(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
        GUIHandler.load(e, loader);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginField.setPromptText(Main.resourceBundle.getString("loginField"));
        passwordField.setPromptText(Main.resourceBundle.getString("passwordField"));
        authorize.setText(Main.resourceBundle.getString("authorizeButton"));
        createUser.setText(Main.resourceBundle.getString("createUser"));
        languageBox.getItems().addAll("Русский", "English","Dansk","Norsk");
        languageBox.setOnAction(this::changeLanguage);
        languageLabel.setText(Main.resourceBundle.getString("changeLanguage"));
        passwordField1.setPromptText(Main.resourceBundle.getString("repeatPassword"));
    }

    public void changeLanguage(ActionEvent event){
        String login = loginField.getText();
        String password = passwordField.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Register.fxml"));
        GUIHandler.changeLanguage(languageBox,event,loader);
        RegisterController controller = loader.getController();
        controller.setData(login,password);
    }

    public void setData(String login, String password){
        loginField.setText(login);
        passwordField.setText(password);
    }
}
