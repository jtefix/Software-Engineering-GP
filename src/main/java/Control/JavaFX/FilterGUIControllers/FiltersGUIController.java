package Control.JavaFX.FilterGUIControllers;

import Control.JavaFX.MainController;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class FiltersGUIController implements Initializable {

    private FiltersGUIController instance;
    private SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @FXML
    private Label dateFromLabel;

    @FXML
    private Label dateToLabel;

    @FXML
    private Label timeFromLabel;

    @FXML
    private Label timeToLabel;

    @FXML
    private Label bounceTypeLabel;


    @FXML
    private JFXDatePicker dateFromPicker;

    @FXML
    private JFXDatePicker dateToPicker;

    @FXML
    private JFXTimePicker timeFromPicker;

    @FXML
    private JFXTimePicker timeToPicker;

    @FXML
    private JFXRadioButton numOfPagesRadio;

    @FXML
    private JFXRadioButton timeOnSiteRadio;

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

    public FiltersGUIController(){
        instance = this;


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dateFromPicker.setValue(LocalDate.parse("2015-01-01"));
        dateToPicker.setValue(LocalDate.parse("2015-01-02"));

        timeFromPicker.setValue(LocalTime.parse("12:00:00"));
        timeToPicker.setValue(LocalTime.parse("12:00:00"));

        numOfPagesRadio.setSelected(true);


        maleMenuItem.setSelected(true);
        femaleMenuItem.setSelected(true);
        age1MenuItem.setSelected(true);
        age2MenuItem.setSelected(true);
        age3MenuItem.setSelected(true);
        age4MenuItem.setSelected(true);
        age5MenuItem.setSelected(true);
        newsMenuItem.setSelected(true);
        shoppingMenuItem.setSelected(true);
        socialMediaMenuItem.setSelected(true);
        blogMenuItem.setSelected(true);
        hobbiesMenuItem.setSelected(true);
        travelMenuItem.setSelected(true);
        lowMenuItem.setSelected(true);
        mediumMenuItem.setSelected(true);
        highMenuItem.setSelected(true);

        MainController.getInstance().getLabels().add(dateFromLabel);
        MainController.getInstance().getLabels().add(dateToLabel);
        MainController.getInstance().getLabels().add(timeFromLabel);
        MainController.getInstance().getLabels().add(timeToLabel);
        MainController.getInstance().getLabels().add(bounceTypeLabel);
        MainController.getInstance().getRadioButtons().add(numOfPagesRadio);
        MainController.getInstance().getRadioButtons().add(timeOnSiteRadio);


    }

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

    public ArrayList<String> getGenders(){
        ArrayList<String> selectedGenders = new ArrayList<>();

        if(maleMenuItem.isSelected())
            selectedGenders.add("Male");

        if(femaleMenuItem.isSelected())
            selectedGenders.add("Female");

        return selectedGenders;
    }


    public ArrayList<String> getAges(){
        ArrayList<String> selectedAges = new ArrayList<>();

        if(age1MenuItem.isSelected())
            selectedAges.add("<25");

        if(age2MenuItem.isSelected())
            selectedAges.add("25-34");

        if(age3MenuItem.isSelected())
            selectedAges.add("35-44");

        if(age4MenuItem.isSelected())
            selectedAges.add("45-54");

        if(age5MenuItem.isSelected())
            selectedAges.add(">54");

        return selectedAges;
    }


    public ArrayList<String> getContexts(){
        ArrayList<String> selectedContexts = new ArrayList<>();

        if(newsMenuItem.isSelected())
            selectedContexts.add("News");

        if(shoppingMenuItem.isSelected())
            selectedContexts.add("Shopping");

        if(socialMediaMenuItem.isSelected())
            selectedContexts.add("Social Media");

        if(blogMenuItem.isSelected())
            selectedContexts.add("Blog");

        if(hobbiesMenuItem.isSelected())
            selectedContexts.add("Hobbies");

        if(travelMenuItem.isSelected())
            selectedContexts.add("Travel");

        return selectedContexts;
    }


    public ArrayList<String> getIncomes(){
        ArrayList<String> selectedIncomes = new ArrayList<>();

        if(highMenuItem.isSelected())
            selectedIncomes.add("High");

        if(mediumMenuItem.isSelected())
            selectedIncomes.add("Medium");

        if(lowMenuItem.isSelected())
            selectedIncomes.add("Low");

        return selectedIncomes;
    }


    public boolean getBounceType(){
        return (numOfPagesRadio.isSelected());
    }


}
