package Control.JavaFX;

import Control.Main;
import Control.Parser;
import Model.Database;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionModel;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ImportGUIController implements Initializable {

    private static ImportGUIController instance;

    private String clickCSVPath;
    private String impressionCSVPath;
    private String serverCSVPath;

    private Stage stage;

    private Database database;

    @FXML
    private VBox importVBox;

    @FXML
    private ProgressBar progress ;

    @FXML
    private Button loadButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button clicksButton;

    @FXML
    private Button serverLogsButton;

    @FXML
    private Button impressionsButton;

    @FXML
    private Label clicksLabel;

    @FXML
    private Label impressionsLabel;

    @FXML
    private Label serverLogsLabel;


    @FXML
    private Label logLabel;

    @FXML
    private JFXSpinner spinner;

    public ImportGUIController(){
        instance = this;
        MainController.getInstance().setImportGUIController(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MainController.getInstance().getvBoxes().add(importVBox);
        MainController.getInstance().getLabels().add(logLabel);
        MainController.getInstance().getLabels().add(clicksLabel);
        MainController.getInstance().getLabels().add(impressionsLabel);
        MainController.getInstance().getLabels().add(serverLogsLabel);
        MainController.getInstance().getButtons().add(clearButton);
        MainController.getInstance().getButtons().add(loadButton);
        MainController.getInstance().getButtons().add(clicksButton);
        MainController.getInstance().getButtons().add(impressionsButton);
        MainController.getInstance().getButtons().add(serverLogsButton);


    }

    public void openClickCSV(ActionEvent actionEvent){
        Desktop desktop = Desktop.getDesktop();

        final FileChooser fileChooser = new FileChooser();
        try{
            if(! new File("src\\main\\resources").exists())
                throw new FileNotFoundException("File Not Found");
            else
                fileChooser.setInitialDirectory(new File("src\\main\\resources"));
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }        File file = fileChooser.showOpenDialog(stage);

        clickCSVPath = file.getAbsolutePath();

        try{
            if(!clickCSVPath.contains("click"))
                throw new Exception("File is not Click Logs CSV. Cannot Load...");
            else{
                clearButton.setDisable(false);
                progress.setProgress(progress.getProgress()+.34);
                logLabel.setText(logLabel.getText()+"\nLoaded Clicks...");

                if(progress.getProgress()==1.0) {
                    logLabel.setText(logLabel.getText()+"\nReady to Load Files...");
                    loadButton.setDisable(false);
                }
            }
        } catch (Exception e){
            logLabel.setText(logLabel.getText()+"\nCould not Load Click Logs. Error: " + e.getMessage());
            if(progress.getProgress()==1.0) {
                progress.setProgress(progress.getProgress()-.33);
                loadButton.setDisable(true);
            }
            e.printStackTrace();
        }


    }

    public void openImpressionCSV(ActionEvent actionEvent){
        Desktop desktop = Desktop.getDesktop();

        final FileChooser fileChooser = new FileChooser();

        try{
            if(! new File("src\\main\\resources").exists())
                throw new FileNotFoundException("File Not Found");
            else
                fileChooser.setInitialDirectory(new File("src\\main\\resources"));
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        File file = fileChooser.showOpenDialog(stage);

        impressionCSVPath = file.getAbsolutePath();

        try{
            if(!impressionCSVPath.contains("impression"))
                throw new Exception("File is not Impression Logs CSV. Cannot Load...");
            else{
                clearButton.setDisable(false);
                progress.setProgress(progress.getProgress()+.33);
                logLabel.setText(logLabel.getText()+"\nLoaded Impressions...");

                if(progress.getProgress()==1.0) {
                    logLabel.setText(logLabel.getText()+"\nReady to Load Files...");
                    loadButton.setDisable(false);
                }
            }
        } catch (Exception e) {
            logLabel.setText(logLabel.getText()+"\nCould not Load Impression Logs. Error: " + e.getMessage());
            if(progress.getProgress()==1.0) {
                progress.setProgress(progress.getProgress()-.33);
                loadButton.setDisable(true);
            }
            e.printStackTrace();
        }


    }

    public void openServerCSV(ActionEvent actionEvent){
        Desktop desktop = Desktop.getDesktop();

        final FileChooser fileChooser = new FileChooser();
        try{
            if(! new File("src\\main\\resources").exists())
                throw new FileNotFoundException("File Not Found");
            else
                fileChooser.setInitialDirectory(new File("src\\main\\resources"));
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }        File file = fileChooser.showOpenDialog(stage);

        serverCSVPath = file.getAbsolutePath();
        try{
            if(!serverCSVPath.contains("server"))
                throw new Exception("File is not Server Logs CSV. Cannot Load...");
            else{
                clearButton.setDisable(false);
                progress.setProgress(progress.getProgress()+.33);
                logLabel.setText(logLabel.getText()+"\nLoaded Server Logs...");
                if(progress.getProgress()==1.0) {
                    logLabel.setText(logLabel.getText()+"\nReady to Load Files...");
                    loadButton.setDisable(false);
                }
            }
        } catch (Exception e){
            logLabel.setText(logLabel.getText()+"\nCould not Load Server Logs. Error: " + e.getMessage());
            if(progress.getProgress()==1.0) {
                progress.setProgress(progress.getProgress()-.33);
                loadButton.setDisable(true);
            }
            e.printStackTrace();
        }


    }

    public void loadFiles(ActionEvent actionEvent){

        Timer timer = new Timer();
        TimerTask spinnerTask = new TimerTask() {
            @Override
            public void run() {
                spinner.setVisible(true);
            }
        };

        TimerTask loadTask = new TimerTask() {
            @Override
            public void run() {
            Parser parser = new Parser();
            database = MainController.getInstance().getDatabase();
            try {
                database.setClicks(parser.parseClicks(clickCSVPath));
                database.setImpressions(parser.parseImpressions(impressionCSVPath));
                database.setServerLogs(parser.parseServerLogs(serverCSVPath));

                Database calcDatabase = new Database("CalcData");
                calcDatabase.setClicks(database.getClicks());
                calcDatabase.setImpressions(database.getImpressions());
                calcDatabase.setServerLogs(database.getServerLogs());

                Database graphDatabase = new Database("GraphData");
                calcDatabase.setClicks(database.getClicks());
                calcDatabase.setImpressions(database.getImpressions());
                calcDatabase.setServerLogs(database.getServerLogs());

                MainController.getInstance().setCalculationsDatabase(calcDatabase);
                MainController.getInstance().setGraphDatabase(graphDatabase);


                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            logLabel.setText(logLabel.getText()+"\nLoaded Files Succesfully!");

                            MainController.getInstance().getCampaignSummaryGUIController().fillCalculationsLabel(database);

                            MainController.getInstance().enableCampaignSummaryTab();
                            MainController.getInstance().enableMetricGraphsTab();
                            MainController.getInstance().enableHistogramTab();
                            MainController.getInstance().enableGlossaryTab();
                            MainController.getInstance().enableExportTab();
                            MainController.getInstance().enableAppearanceTab();

                            SelectionModel selectionModel = MainController.getInstance().getTabPane().getSelectionModel();
                            selectionModel.select(1);
                        }
                        catch (Exception e1){
                            logLabel.setText("Error in Loading Files: " + e1.getMessage());
                            e1.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                logLabel.setText("Error in Loading Files: " + e.getMessage());
                e.printStackTrace();
            }
            spinner.setVisible(false);
            }
        };
        timer.schedule(spinnerTask,500l);
        timer.schedule(loadTask,1000l);


    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }



    public void clearSelection(ActionEvent actionEvent) {

        clickCSVPath = "";
        impressionCSVPath = "";
        serverCSVPath = "";

        logLabel.setText("Cleared Selection...");
        progress.setProgress(0.0);
        loadButton.setDisable(true);
        clearButton.setDisable(true);
    }
}
