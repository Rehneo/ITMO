package ClientFX;
import ClientWork.Main;
import Data.Ticket;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


import javafx.scene.paint.Color;

import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VisualsController implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    private Button goToTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mapFromList();
        goToTable.setText(Main.resourceBundle.getString("goBackButton"));
    }

    public void goToTable(ActionEvent event) throws IOException {
        GUIHandler.refresh();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/t.fxml"));
        GUIHandler.load(event, loader);
    }

    public void refresh(Ticket t, int index)  {
        Alert alert = GUIHandler.refresh();
        Circle circle = (Circle) pane.getChildren().get(index);
        RotateTransition rotate = new RotateTransition(Duration.millis(2000), circle);
        rotate.setCycleCount(1);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.setAxis(Rotate.X_AXIS);
        TranslateTransition translate = new TranslateTransition(Duration.millis(2000), circle);
        translate.setToX(t.getCorX() - circle.getCenterX());
        translate.setToY(t.getCorY() - circle.getCenterY());
        ParallelTransition parallel = new ParallelTransition();
        parallel.getChildren().addAll(translate,rotate);
        parallel.setOnFinished(e -> {
            if (alert != null) {
                alert.show();
            }
            mapFromList();
        });
        parallel.play();
    }

    public void refresh(){
        Alert alert = GUIHandler.refresh();
        if(alert != null){
            alert.show();
        }
        mapFromList();
    }

    private void mapFromList() {
        pane.getChildren().clear();
        List<Ticket> tickets = List.of(GUIHandler.getDataSender().getUserData().getTickets());
        int i = 0;
        for(Ticket t:tickets){
            int[] color = t.getColor();
            Circle circle = new Circle(t.getCorX(), t.getCorY(), validSize(t.getPrice()),Color.rgb(color[0],color[1],color[2], 1.0));
            int finalI = i;
            circle.setOnMouseClicked(event -> {
                if(t.getOwner().equals(GUIHandler.getDataSender().getUserData().getLogin())){
                    new AlertBox(t, finalI).display();
                }else {
                    new AlertBox(t, finalI).displayToRead();
                }
            });
            pane.getChildren().add(circle);
            i ++;
        }
    }

    private double validSize(double x){
        x+=2;
        return ((80*Math.pow(x, 2)-80*Math.pow(x, 1.8)-1)/Math.pow(x,2));
    }
}
