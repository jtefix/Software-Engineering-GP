package View;

import Control.Parser;
import Model.Database;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
public class ImportGUI {

//    private Stage stage;
//
//    private Database database;
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage stage) throws IOException {
//
//
//        this.stage= stage;
//
////        FXMLLoader loader = new FXMLLoader(getClass().getResource("/JavaFX/ImportGUI.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/JavaFX/ImportGUI.fxml"));
//
//
////        loadButton.setDisable(true);
//
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
//    }
//
//    private String clickCSVPath;
//    private String impressionCSVPath;
//    private String serverCSVPath;
//
//    @FXML
//    private ProgressBar progress ;
//
//    @FXML
//    private Button loadButton;
//
//    public void openClickCSV(ActionEvent actionEvent){
//        Desktop desktop = Desktop.getDesktop();
//
//        final FileChooser fileChooser = new FileChooser();
//        fileChooser.setInitialDirectory(new File("D:\\Users\\Naser Salameh\\OneDrive - University of Southampton\\Coding Workspaces\\Java\\Soton 2-2\\Software Engineering Group\\src\\main\\resources"));
//        File file = fileChooser.showOpenDialog(stage);
//
//        clickCSVPath = file.getAbsolutePath();
//        progress.setProgress(progress.getProgress()+.34);
//
//
//        if(progress.getProgress()==1.0)
//            loadButton.setDisable(false);
//    }
//
//    public void openImpressionCSV(ActionEvent actionEvent){
//        Desktop desktop = Desktop.getDesktop();
//
//        final FileChooser fileChooser = new FileChooser();
//        fileChooser.setInitialDirectory(new File("D:\\Users\\Naser Salameh\\OneDrive - University of Southampton\\Coding Workspaces\\Java\\Soton 2-2\\Software Engineering Group\\src\\main\\resources"));
//        File file = fileChooser.showOpenDialog(stage);
//
//        impressionCSVPath = file.getAbsolutePath();
//
//        progress.setProgress(progress.getProgress()+.33);
//
//        if(progress.getProgress()==1.0)
//            loadButton.setDisable(false);
//
//    }
//
//    public void openServerCSV(ActionEvent actionEvent){
//        Desktop desktop = Desktop.getDesktop();
//
//        final FileChooser fileChooser = new FileChooser();
//        fileChooser.setInitialDirectory(new File("D:\\Users\\Naser Salameh\\OneDrive - University of Southampton\\Coding Workspaces\\Java\\Soton 2-2\\Software Engineering Group\\src\\main\\resources"));
//        File file = fileChooser.showOpenDialog(stage);
//
//        serverCSVPath = file.getAbsolutePath();
//        progress.setProgress(progress.getProgress()+.33);
//
//        if(progress.getProgress()==1.0)
//            loadButton.setDisable(false);
//    }
//
//    public void loadFiles(ActionEvent actionEvent){
//
////        Database importDatabase = new Database("Import Temp");
//
//        this.database = new Database("Ad Auction");
//        Parser parser = new Parser();
//
//        System.out.println(clickCSVPath);
//        System.out.println(impressionCSVPath);
//        System.out.println(serverCSVPath);
//
//        database.setClicks(parser.parseClicks(clickCSVPath));
//        database.setImpressions(parser.parseImpressions(impressionCSVPath));
//        database.setServerLogs(parser.parseServerLogs(serverCSVPath));
//
//    }
//
//    public Database getDatabase() {
//        return database;
//    }
//
//    public void setDatabase(Database database) {
//        this.database = database;
//    }
}

