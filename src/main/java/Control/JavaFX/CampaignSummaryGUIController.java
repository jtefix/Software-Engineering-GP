package Control.JavaFX;

import Control.Calculator;
import Control.FilteredCalculator;
import Control.PeriodicCalculator;
import Model.Database;
import Model.Filter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;

public class CampaignSummaryGUIController implements Initializable {

    private static CampaignSummaryGUIController instance;

    private Calculator calc;

    @FXML
    private VBox campaignSummaryVBox;

    @FXML
    private Label calculationsLabel;

    @FXML
    private JFXButton filtersButton;

    @FXML
    private JFXButton clearButton;

    private Database calculationsDatabase;

    public CampaignSummaryGUIController(){
        instance = this;
        MainController.getInstance().setCampaignSummaryGUIController(this);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        calculationsDatabase = MainController.getInstance().getCalculationsDatabase();
        MainController.getInstance().getvBoxes().add(campaignSummaryVBox);
        MainController.getInstance().getLabels().add(calculationsLabel);
        MainController.getInstance().getButtons().add(filtersButton);
        MainController.getInstance().getButtons().add(clearButton);
    }

    public Label getCalculationsLabel() {
        return calculationsLabel;
    }

    public void fillCalculationsLabel(Database database) throws ParseException {

        calculationsDatabase = database;

        String calculationsOutput =calculationsLabel.getText();
        calculationsOutput+= ("\nClicks: " + database.getClicks().size());
        calculationsOutput+= ("\nImpressions: " + database.getImpressions().size());
        calculationsOutput+= ("\nServer Logs: " + database.getServerLogs().size());

        DecimalFormat df = new DecimalFormat("###.###");

        boolean bounceType = false;
        try {
            bounceType = MainController.getInstance().getCampaignSummaryFilter().getBounceType();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calc = new Calculator(database);
        calculationsOutput+= ("\nNumber of Impressions: " + df.format(calc.numOfImpressions()));
        calculationsOutput+= ("\nNumber of Clicks: " + df.format(calc.numOfClicks()));
        calculationsOutput+= ("\nNumber of Uniques: " + df.format(calc.numOfUniques()));

        if(bounceType)
            calculationsOutput+= ("\nNumber of Page View Bounces: " + df.format(calc.numOfBounces(bounceType)));
        else
            calculationsOutput+= ("\nNumber of Time on Page Bounces: " + df.format(calc.numOfBounces(bounceType)));

        calculationsOutput+= ("\nNum of Conversions: " + df.format(calc.numOfConversions()));
        calculationsOutput+= ("\nTotal Cost: " + df.format(calc.totalCost()));
        calculationsOutput+= ("\nCTR (Click-Through-Rate): " + df.format(calc.clickThroughRate()));

        try {

            calculationsOutput+= ("\nCPA (Cost-Per-Acquisition): " + df.format(calc.costPerAcquisition()));
            calculationsOutput+= ("\nCPC (Cost-Per-Click): " + df.format(calc.costPerClick()));
            calculationsOutput+= ("\nCPM (Cost-Per-Thousand-Impressions): " + df.format(calc.costPerThousandImpressions()));

            if(bounceType)
                calculationsOutput+= ("\nBounce Rate (Num of Page View Bounces): " + df.format(calc.bounceRate(bounceType)));
            else
                calculationsOutput+= ("\nBounce Rate (Num of Time on Page Bounces): " + df.format(calc.bounceRate(bounceType)));

        } catch (IllegalArgumentException e){
            calculationsOutput+= ("Error in Calculations: " + e.getMessage());
            e.printStackTrace();
        }

        calculationsLabel.setText(calculationsOutput);
    }

    public void clearScreen(ActionEvent actionEvent) {
        calculationsLabel.setText("Screen Cleared.");
    }

    public void applyFilters(ActionEvent actionEvent){

        try {

            Filter filter = MainController.getInstance().getCampaignSummaryFilter();
            PeriodicCalculator periodicCalculator = new PeriodicCalculator(MainController.getInstance().getDatabase());
            Database periodicDatabase = periodicCalculator.getDatabaseWithinPeriod(filter.getDateFrom(),filter.getDateTo());

            FilteredCalculator filteredCalculator = new FilteredCalculator(periodicDatabase,filter);
            Database filteredDatabase = filteredCalculator.getFilteredDatabase();

            MainController.getInstance().setCalculationsDatabase(periodicDatabase);

            try{
                if(filter.getDateFrom().after(filter.getDateTo()))
                    throw new Exception("Error: Date From Cannot Be After Date To.");
                    //else if difference is less than an hour as that is lowest granularity
                else if( filter.getDateTo().getTime() - filter.getDateFrom().getTime() < 360000)
                    throw new Exception("Error: Difference must be greater than an Hour.");
                else{
                    calculationsLabel.setText("Calculations From Period (" +
                            MainController.getInstance().getCampaignSummaryFilter().getDateFrom() +
                            " To " + MainController.getInstance().getCampaignSummaryFilter().getDateTo() + "):");
                    fillCalculationsLabel(filteredDatabase);
                }
            }catch (Exception e){
                calculationsLabel.setText(e.getMessage());
            }

        } catch (ParseException e) {
            calculationsLabel.setText("Parse Error When Parsing Time, Error: " + e.getMessage());
        }


    }


}
