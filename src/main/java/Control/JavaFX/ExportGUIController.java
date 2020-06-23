package Control.JavaFX;

import Control.Main;
import Control.Parser;
import Control.Writer;
import Model.Database;
import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionModel;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ExportGUIController implements Initializable {

    private ExportGUIController instance;

    private String exportPath;

    private Stage stage;

    @FXML
    private VBox exportVBox;

    @FXML
    private Label logLabel;

    @FXML
    private Label pathLabel;

    @FXML
    private Button exportButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button exportGraphicsButton;

    @FXML
    private JFXSpinner spinner;

    public ExportGUIController(){

        instance = this;
        MainController.getInstance().setExportGUIController(this);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainController.getInstance().getvBoxes().add(exportVBox);
        MainController.getInstance().getLabels().add(logLabel);
        MainController.getInstance().getLabels().add(pathLabel);
        MainController.getInstance().getButtons().add(exportButton);
        MainController.getInstance().getButtons().add(exportGraphicsButton);

        logLabel.setText("Select Output Location");

    }

    public void chooseExportLocation(ActionEvent actionEvent){
        Desktop desktop = Desktop.getDesktop();

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Export Path");

        if(! new File("src\\main\\resources").exists())
            try {
                throw new FileNotFoundException("File Not Found");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        else
            chooser.setInitialDirectory(new File("src\\main\\resources"));

        File selectedDirectory = chooser.showDialog(stage);

        exportPath = selectedDirectory.getAbsolutePath();

        logLabel.setText(logLabel.getText()+"\nSelect Export Data to export requested data in CSVs.\nSelect Export Graphs to export requested graphs in PNGs.");
        pathLabel.setText("Export Path: "+ exportPath);

        exportButton.setDisable(false);
        exportGraphicsButton.setDisable(false);

    }

    public void exportData(ActionEvent actionEvent){

        Timer timer = new Timer();
        TimerTask spinnerTask = new TimerTask() {
            @Override
            public void run() {
                spinner.setVisible(true);
            }
        };

        TimerTask exportTask = new TimerTask() {

            @Override
            public void run() {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Writer writer = new Writer();
                            writer.writeCalculation(exportPath);
                            logLabel.setText("Wrote Calculation CSVs");
                            progressBar.setProgress(.50);

                            writer.writeGraph(exportPath);
                            logLabel.setText(logLabel.getText()+"\nWrote Graph CSVs");
                            progressBar.setProgress(1.0);

                        } catch (IOException e) {
                            logLabel.setText(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
                spinner.setVisible(false);
            }
        };

        timer.schedule(spinnerTask,500l);
        timer.schedule(exportTask,1000l);


    }

    public void exportGraphs(ActionEvent actionEvent){
        saveChartAsPng();
        saveHistogramAsPng();


    }

    public void saveChartAsPng(){
        WritableImage image = MainController.getInstance().getLineChart().snapshot(new SnapshotParameters(),null);
        File imageFile = new File(exportPath+"\\Graphs.png");

        try {
            if (!imageFile.exists()) {
                imageFile.createNewFile();
            }

            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", imageFile);
            progressBar.setProgress(.5);
        } catch (IOException e) {
            logLabel.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveHistogramAsPng(){
        WritableImage image = MainController.getInstance().getBarChart().snapshot(new SnapshotParameters(),null);


        File imageFile = new File(exportPath+"\\ClickCostHistograms.png");
        try {
            if (!imageFile.exists()) {
                imageFile.createNewFile();
            }
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", imageFile);
            progressBar.setProgress(1);
        } catch (IOException e) {
            logLabel.setText(e.getMessage());
            e.printStackTrace();
        }
    }


//    public void printGraphics(){
//        PrinterJob job = PrinterJob.getPrinterJob();
//
//        class PrintGraphics implements Printable {
//            WritableImage image;
//            @Override
//            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
//                Graphics2D g2 = (Graphics2D) graphics;
//
//                int imgWidth = (int)image.getWidth();
//                int imgHeight = (int)image.getHeight();
//                double xRatio = (double) pageFormat.getImageableWidth() / (double) imgWidth;
//                double yRatio = (double) pageFormat.getImageableHeight() / (double) imgHeight;
//
//                g2.scale(xRatio, yRatio);
//
//                AffineTransform at = AffineTransform.getTranslateInstance(pageFormat.getImageableX(), pageFormat.getImageableY());
//
//                g2.drawRenderedImage(image,at);
//                return 0;
//            }
//        }
//
//    }


}
