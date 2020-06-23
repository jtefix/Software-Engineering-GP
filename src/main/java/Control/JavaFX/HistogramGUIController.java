package Control.JavaFX;

import Control.BarDrawer;
import Control.PeriodicCalculator;
import Model.Click;

import Model.Database;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.*;

public class HistogramGUIController implements Initializable {

    private HistogramGUIController instance;

    private SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @FXML
    private VBox histogramVBox;

    @FXML
    private Label dateFromLabel;

    @FXML
    private Label dateToLabel;

    @FXML
    private Label timeFromLabel;

    @FXML
    private Label timeToLabel;

    @FXML
    private Label numOfClicksLabel;

    @FXML
    private JFXButton histogramButton;

    @FXML
    private JFXButton clearButton;

    @FXML
    private BarChart barChart;

    @FXML
    private JFXDatePicker dateFromPicker;

    @FXML
    private JFXDatePicker dateToPicker;

    @FXML
    private JFXTimePicker timeFromPicker;

    @FXML
    private JFXTimePicker timeToPicker;

    @FXML
    private CategoryAxis rangesAxis;

    public HistogramGUIController(){
        instance = this;
        MainController.getInstance().setHistogramGUIController(this);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MainController.getInstance().getvBoxes().add(histogramVBox);
        MainController.getInstance().getLabels().add(dateFromLabel);
        MainController.getInstance().getLabels().add(dateToLabel);
        MainController.getInstance().getLabels().add(timeFromLabel);
        MainController.getInstance().getLabels().add(timeToLabel);
        MainController.getInstance().getLabels().add(numOfClicksLabel);
        MainController.getInstance().getButtons().add(clearButton);
        MainController.getInstance().getButtons().add(histogramButton);

        dateFromPicker.setValue(LocalDate.parse("2015-01-01"));
        dateToPicker.setValue(LocalDate.parse("2015-01-02"));

        timeFromPicker.setValue(LocalTime.parse("12:00:00"));
        timeToPicker.setValue(LocalTime.parse("12:00:00"));

        barChart.setAnimated(false);

    }

    public void drawChart(){

        clearChart();
        rangesAxis = new CategoryAxis();

        try {
            Date dateFrom = getDateFrom();
            Date dateTo = getDateTo();

            PeriodicCalculator periodicCalculator = new PeriodicCalculator(MainController.getInstance().getDatabase());
            MainController.getInstance().setHistogramDatabase(periodicCalculator.getDatabaseWithinPeriod(dateFrom,dateTo));
            ArrayList<Click> periodicClicks = periodicCalculator.getClicksWithinPeriod(dateFrom,dateTo);

            BarDrawer barDrawer = new BarDrawer(periodicClicks);
            XYChart.Series<String,Number> dataSeries = barDrawer.getChart();
            dataSeries.setName("Click Costs");

            barChart.getData().add(dataSeries);

            for (final XYChart.Data<String, Number> data : dataSeries.getData()) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(data.getYValue().toString());
                Tooltip.install(data.getNode(), tooltip);
            }




//
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void clearChart(){
        barChart.getData().clear();
    };

    public Date getDateFrom() throws ParseException {



        Date dateFrom = java.sql.Date.valueOf(dateFromPicker.getValue());
        dateFrom.setTime(dateFrom.getTime() + timeFromPicker.getValue().getLong(ChronoField.MILLI_OF_DAY));

        return dateFrom;
    }

    public Date getDateTo() throws ParseException {


        Date dateTo = java.sql.Date.valueOf(dateToPicker.getValue());
        dateTo.setTime(dateTo.getTime() + timeToPicker.getValue().getLong(ChronoField.MILLI_OF_DAY));
        return dateTo;
    }

    public BarChart getBarChart() {
        return barChart;
    }
}
