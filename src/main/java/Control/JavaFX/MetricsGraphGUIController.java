package Control.JavaFX;

import Control.Calculator;
import Control.FilteredCalculator;
import Control.GraphDrawer;
import Control.JavaFX.FilterGUIControllers.MetricsGraphFiltersGUIController;
import Control.PeriodicCalculator;
import Model.Database;
import Model.Filter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class MetricsGraphGUIController implements Initializable {

    private MetricsGraphGUIController instance;

    @FXML
    private VBox metricsGraphVBox;

    @FXML
    private JFXComboBox<String> calculationCombo;

    @FXML
    private JFXButton filterButton;

    @FXML
    private JFXButton clearButton;

    @FXML
    private JFXComboBox granularityCombo;

    @FXML
    private LineChart lineChart;

    @FXML
    private NumberAxis timeAxis;

    @FXML
    private TextField graphNameField;

    @FXML
    private Label selectedGraphLabel;

    @FXML
    private Label infoLabel;

    @FXML
    private JFXSpinner spinner;

    private HashMap<XYChart.Series,Filter> graphsMap;

    private Date timeAxisStart;
    private Date timeAxisEnd;

    private Database graphDatabase;


    public MetricsGraphGUIController(){

        instance = this;
        MainController.getInstance().setMetricsGraphGUIController(this);

        graphDatabase = MainController.getInstance().getGraphDatabase();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MainController.getInstance().getvBoxes().add(metricsGraphVBox);
        MainController.getInstance().getButtons().add(clearButton);
        MainController.getInstance().getButtons().add(filterButton);
        MainController.getInstance().getTextFields().add(graphNameField);
        MainController.getInstance().getComboBoxes().add(calculationCombo);
        MainController.getInstance().getComboBoxes().add(granularityCombo);
        MainController.getInstance().getLabels().add(selectedGraphLabel);
        MainController.getInstance().getLabels().add(infoLabel);

        calculationCombo.getItems().addAll(
                "Number of Impressions",
                "Number of Clicks",
                "Number of Uniques",
                "Number of Bounces",
                "Number of Conversions",
                "Total Cost",
                "CTR: Click-Through-Rate",
                "CPA: Cost-Per-Acquisition",
                "CPC: Cost-Per-Click",
                "CPM: Cost-Per-Thousand-Impressions",
                "Bounce Rate");

        calculationCombo.setValue("Number of Impressions");

        granularityCombo.getItems().addAll(
                "Hour",
                "Day",
                "Week");

        granularityCombo.setValue("Hour");

        graphsMap = new HashMap<>();
        lineChart.setAnimated(false);


    }

    public void drawGraph(){


            Timer timer = new Timer();
            TimerTask spinnerTask = new TimerTask() {
                @Override
                public void run() {
                    spinner.setVisible(true);
                }
            };

            TimerTask graphTask = new TimerTask() {
                @Override
                public void run() {

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String granularity = (String) granularityCombo.getValue();

                                Filter filter = MainController.getInstance().getMetricsGraphFilter();

                                PeriodicCalculator periodicCalculator = new PeriodicCalculator(MainController.getInstance().getDatabase());
                                Database periodicDatabase = periodicCalculator.getDatabaseWithinPeriod(filter.getDateFrom(), filter.getDateTo());

                                FilteredCalculator filteredCalculator = new FilteredCalculator(periodicDatabase, filter);
                                Database filteredDatabase = filteredCalculator.getFilteredDatabase();

                                MainController.getInstance().setGraphDatabase(filteredDatabase);

                                if (timeAxisStart == null) {
                                    timeAxisStart = filter.getDateFrom();
                                }
                                if (filter.getDateFrom().after(timeAxisStart)) {
                                    timeAxisStart = filter.getDateFrom();

                                }

                                timeAxis.setTickLabelFormatter(new StringConverter<>() {

                                    @Override
                                    public String toString(Number number) {

                                        int rangeField = 0;

                                        switch (granularity) {
                                            case "Hour":
                                                rangeField = Calendar.HOUR_OF_DAY;
                                                break;
                                            case "Day":
                                                rangeField = Calendar.DAY_OF_WEEK;
                                                break;
                                            case "Week":
                                                rangeField = Calendar.WEEK_OF_MONTH;
                                                break;

                                        }

                                        Calendar calendar = Calendar.getInstance();

                                        calendar.setTime(timeAxisStart);
                                        calendar.add(rangeField, number.intValue());
                                        return calendar.getTime().toString();
                                    }

                                    @Override
                                    public Number fromString(String s) {
                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        try {
                                            return formatter.parse(s).getTime();
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }
                                });

                                graphDatabase = filteredDatabase;

                                GraphDrawer graphDrawer = new GraphDrawer(filteredDatabase);

                                XYChart.Series<Number, Number> dataSeries = graphDrawer.getChart(granularity, calculationCombo.getValue());
//            dataSeries.setName(graphNameField.getText());
                                int rangeField = 0;
                                switch (granularity) {
                                    case ("Hour"):
                                        rangeField = 3600000;
                                        break;
                                    case ("Day"):
                                        rangeField = 86400000;
                                        break;
                                    case ("Week"):
                                        rangeField = 604800000;
                                        break;
                                }

                                Number offset = (filter.getDateFrom().getTime() - timeAxisStart.getTime()) / rangeField;
                                for (XYChart.Data data : dataSeries.getData()) {
                                    Number oldValue = (Number) data.getXValue();
                                    data.setXValue(oldValue.intValue() + offset.intValue());
                                }

                                dataSeries.setName(graphNameField.getText());
                                graphsMap.put(dataSeries, filter);

                                EventHandler<MouseEvent> graphClicked = new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent mouseEvent) {
                                        selectedGraphLabel.setText("Selected Graph: " + dataSeries.getName());
                                        MainController.getInstance().setMetricsFiltersOptions(graphsMap.get(dataSeries));
                                    }


                                };


                                lineChart.getData().add(dataSeries);
                                dataSeries.getNode().setOnMouseClicked(graphClicked);

                                spinner.setVisible(false);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    });
                }
            };

            timer.schedule(spinnerTask,500l);
            timer.schedule(graphTask,1000l);



    }



    public void clearGraphs(ActionEvent actionEvent){

        lineChart.getData().clear();
//        timeAxisStart = null;

    }

    public LineChart getLineChart() {
        return lineChart;
    }

}