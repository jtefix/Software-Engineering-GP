package Control.Swing;

import Control.Calculator;
import Model.Database;
import View.SwingMainPage;

import javax.swing.*;

public class SwingMainPageController {

    private Database database;
    private SwingMainPage swingMainPage;

    private Calculator calc;

    public SwingMainPageController(Database database, SwingMainPage swingMainPage){


        this.database = database;
        this.swingMainPage = swingMainPage;

        this.calc = new Calculator(database);

        ButtonGroup bg = new ButtonGroup();
        bg.add(swingMainPage.getNumberOfPagesViewedRadioButton());
        bg.add(swingMainPage.getTimeOnPageRadioButton());

        swingMainPage.getNumberOfPagesViewedRadioButton().setSelected(true);

        swingMainPage.getResultsPane().setText("Press Calculate to Calculate Results From Campaign: " + database.getCampaignName());

    }

    public void clickCalculateButton(){

        String resultScreen = "";
        boolean bounceType = swingMainPage.getNumberOfPagesViewedRadioButton().isSelected();

        resultScreen+= ("Clicks: " + database.getClicks().size());
        resultScreen+=  ("\nImpressions: " + database.getImpressions().size());
        resultScreen+=  ("\nServer Logs: " + database.getServerLogs().size());

        try {
            resultScreen+= "\n";
            resultScreen+= ("\nNum of Impressions: " + calc.numOfImpressions());
            resultScreen+= ("\nNum of Clicks: " + calc.numOfClicks());
            resultScreen+= ("\nNum of Uniques: " + calc.numOfUniques());
            resultScreen+= ("\nNum of Bounces: " + calc.numOfBounces(bounceType));
            resultScreen+= ("\nNum of Conversions: " + calc.numOfConversions());
            resultScreen+= ("\nTotal Cost: " + calc.totalCost());
            resultScreen+= ("\nCTR: " + calc.clickThroughRate());
            resultScreen+= ("\nCPA: " + calc.costPerAcquisition());
            resultScreen+= ("\nCPC: " + calc.costPerClick());
            resultScreen+= ("\nCPM: " + calc.costPerThousandImpressions());
            resultScreen+= ("\nBounce Rate: " + calc.bounceRate(bounceType));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            System.out.println("End Runtime");
        }

        swingMainPage.getResultsPane().setText(resultScreen);

    }

    public void clickClearButton(){

        swingMainPage.getNumberOfPagesViewedRadioButton().setSelected(true);

        swingMainPage.getResultsPane().setText("Cleared. \nPress Calculate to Calculate Results From Campaign: " + database.getCampaignName());

    }


}
