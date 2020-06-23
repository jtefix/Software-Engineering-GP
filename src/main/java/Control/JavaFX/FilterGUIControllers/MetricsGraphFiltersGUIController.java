package Control.JavaFX.FilterGUIControllers;

import Control.JavaFX.MainController;
import Model.Filter;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.text.SimpleDateFormat;
import java.time.*;


public class MetricsGraphFiltersGUIController extends FiltersGUIController implements Initializable {

    private SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @FXML
    private JFXDatePicker dateFromPicker;

    @FXML
    private JFXDatePicker dateToPicker;

    @FXML
    private JFXTimePicker timeFromPicker;

    @FXML
    private JFXTimePicker timeToPicker;

    @FXML
    private RadioButton numOfPagesRadio;

    @FXML
    private RadioButton timeOnSiteRadio;

    @FXML
    private CheckMenuItem maleMenuItem;

    @FXML
    private CheckMenuItem femaleMenuItem;

    @FXML
    private CheckMenuItem age1MenuItem;

    @FXML
    private CheckMenuItem age2MenuItem;

    @FXML
    private CheckMenuItem age3MenuItem;

    @FXML
    private CheckMenuItem age4MenuItem;

    @FXML
    private CheckMenuItem age5MenuItem;

    @FXML
    private CheckMenuItem newsMenuItem;

    @FXML
    private CheckMenuItem shoppingMenuItem;

    @FXML
    private CheckMenuItem socialMediaMenuItem;

    @FXML
    private CheckMenuItem blogMenuItem;

    @FXML
    private CheckMenuItem hobbiesMenuItem;

    @FXML
    private CheckMenuItem travelMenuItem;

    @FXML
    private CheckMenuItem lowMenuItem;

    @FXML
    private CheckMenuItem mediumMenuItem;

    @FXML
    private CheckMenuItem highMenuItem;

    public MetricsGraphFiltersGUIController(){

        super();
        MainController.getInstance().setMetricsGraphFiltersGUIController(this);

    }

    public void setFilters(Filter filter) {


        dateFromPicker.setValue(LocalDate.parse("2015-01-01"));
        dateToPicker.setValue(LocalDate.parse("2015-01-02"));

        timeFromPicker.setValue(LocalTime.parse("12:00:00"));
        timeToPicker.setValue(LocalTime.parse("12:00:00"));

        numOfPagesRadio.setSelected(false);


        maleMenuItem.setSelected(false);
        femaleMenuItem.setSelected(false);
        age1MenuItem.setSelected(false);
        age2MenuItem.setSelected(false);
        age3MenuItem.setSelected(false);
        age4MenuItem.setSelected(false);
        age5MenuItem.setSelected(false);
        newsMenuItem.setSelected(false);
        shoppingMenuItem.setSelected(false);
        socialMediaMenuItem.setSelected(false);
        blogMenuItem.setSelected(false);
        hobbiesMenuItem.setSelected(false);
        travelMenuItem.setSelected(false);
        lowMenuItem.setSelected(false);
        mediumMenuItem.setSelected(false);
        highMenuItem.setSelected(false);

        LocalDate localDateFrom = Instant.ofEpochMilli(filter.getDateFrom().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateTo = Instant.ofEpochMilli(filter.getDateTo().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

        this.dateFromPicker.setValue(LocalDate.from(localDateFrom.atStartOfDay()));
        this.dateToPicker.setValue(LocalDate.from(localDateTo.atStartOfDay()));


        if(numOfPagesRadio.isSelected())
            numOfPagesRadio.setSelected(true);
        else
            timeOnSiteRadio.setSelected(true);

        for(String s: filter.getSelectedGenders()){
            switch (s){
                case "Male":
                    maleMenuItem.setSelected(true);
                    break;
                case "Female":
                    femaleMenuItem.setSelected(true);
                    break;
            }
        }


        for(String s: filter.getSelectedAges()){
            switch (s){
                case "<25":
                    age1MenuItem.setSelected(true);
                    break;
                case "25-34":
                    age2MenuItem.setSelected(true);
                    break;
                case "35-44":
                    age3MenuItem.setSelected(true);
                    break;
                case "45-54":
                    age4MenuItem.setSelected(true);
                    break;
                case ">54":
                    age5MenuItem.setSelected(true);
                    break;
            }
        }


        for(String s: filter.getSelectedContexts()){
            switch (s){
                case "News":
                    newsMenuItem.setSelected(true);
                    break;
                case "Shopping":
                    shoppingMenuItem.setSelected(true);
                    break;
                case "Social Media":
                    socialMediaMenuItem.setSelected(true);
                    break;
                case "Blog":
                    blogMenuItem.setSelected(true);
                    break;
                case "Hobbies":
                    hobbiesMenuItem.setSelected(true);
                    break;
                case "Travel":
                    travelMenuItem.setSelected(true);
                    break;
            }
        }


        for(String s: filter.getSelectedIncomes()){
            switch (s){
                case "High":
                    highMenuItem.setSelected(true);
                    break;
                case "Medium":
                    mediumMenuItem.setSelected(true);
                    break;
                case "Low":
                    lowMenuItem.setSelected(true);
                    break;
            }
        }


    }
}
