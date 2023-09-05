package ClientWork;
import ClientFX.GUIHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    public static final FXMLLoader loader = new FXMLLoader();

    public static ResourceBundle resourceBundle;

    public static void main(String[] args){
        if (args.length == 0) {
            launch(args);
            App app = new App(Integer.parseInt("7777"));
            try {
                app.start();
            } catch (NumberFormatException ex) {
                System.out.println("Значение порта должно быть целочисленным.");
            }
        }
    }


    @Override
    public void init() throws IOException {
        GUIHandler.initDataSender(7777);
        GUIHandler.getDataSender().connect();
        loader.setLocation(getClass().getResource("/Main.fxml"));
        resourceBundle = ResourceBundle.getBundle("Locale", new Locale("ru","RU"));
        loader.setResources(resourceBundle);
    }


    @Override
    public void start(Stage stage) throws IOException{
        stage.setResizable(false);
        stage.setTitle("Client");
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }


    public static void setLocale(String language, String country){
        resourceBundle = ResourceBundle.getBundle("Locale", new Locale(language,country));
    }

}
