package View;

import Control.JavaFX.ImportGUIController;
import Control.JavaFX.MainController;
import Model.Database;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

//import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MainPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    Tab importTab;

    private MainController mainController;

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/JavaFX/MainPage.fxml"));


        Scene scene = new Scene(root);

        stage.setTitle("Ad Auction Dashboard - SEG Team 7");
        stage.setScene(scene);

        stage.show();




    }
}
