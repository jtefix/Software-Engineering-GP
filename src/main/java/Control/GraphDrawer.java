package Control;

import Control.JavaFX.MainController;
import Model.Database;
import Model.Filter;
import Model.Impression;
import javafx.scene.chart.XYChart;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.util.*;

public class GraphDrawer {

    private Database periodicDatabase;
    private Filter filter;

    public GraphDrawer(Database periodicDatabase){

        this.periodicDatabase = periodicDatabase;

        try {
            this.filter = MainController.getInstance().getMetricsGraphFilter();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    //for testing
    public GraphDrawer(Database periodicDatabase,Filter filter){

        this.periodicDatabase = periodicDatabase;

        this.filter = filter;


    }

    public XYChart.Series getChart(String granularity, String calculationType){

        int granularityValue = getGranularityValue(granularity);
        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName(calculationType + " Per " + granularity + " " + filter.getDateFrom() + " - " + filter.getDateTo() );

        for(HashMap.Entry entry: getDataPoints(granularityValue,calculationType).entrySet()){
            dataSeries.getData().add(new XYChart.Data( entry.getKey(), entry.getValue()));

        }

        return  dataSeries;
    }

    public HashMap<Integer,Double> getDataPoints(int granularityValue,String calculationType){

        HashMap dataPoints = new HashMap();

        int rangeField = 0;
        switch (granularityValue){
            case (3600000): rangeField = Calendar.HOUR_OF_DAY;
                break;
            case (86400000): rangeField = Calendar.DAY_OF_WEEK;
                break;
            case (604800000): rangeField = Calendar.WEEK_OF_YEAR;
                break;
        }

        Date dateFrom =filter.getDateFrom();
        Date dateTo = filter.getDateTo();
        long period = dateTo.getTime() - dateFrom.getTime();

        Date granularDateFrom = dateFrom;
        Date granularDateTo = dateFrom;

        Calendar calendar = Calendar.getInstance();
        for(int i=granularityValue; i<=period; i+=granularityValue){

            calendar.setTime(granularDateFrom);
            calendar.add(rangeField,1);
            granularDateTo = calendar.getTime();

            double calcResult = 0;

            try {
             calcResult = getCalculation(calculationType, granularDateFrom, granularDateTo);
            } catch (IllegalArgumentException e){
                e.printStackTrace();
            }
            dataPoints.put((i/granularityValue),calcResult);

            granularDateFrom.setTime(granularDateTo.getTime());
        }

        return dataPoints;
    }

    private double getCalculation(String calculationType, Date dateFrom, Date dateTo) {

        double calcValue = 0.0;

        PeriodicCalculator periodicCalculator = new PeriodicCalculator(periodicDatabase);
        Database granularDatabase = periodicCalculator.getDatabaseWithinPeriod(dateFrom,dateTo);

        Calculator calc = new Calculator(granularDatabase);
        boolean bounceType = true;
        try {
            bounceType = MainController.getInstance().getMetricsGraphFilter().getBounceType();
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
//        bounceType = filter.getBounceType();

        switch (calculationType){
            case "Number of Impressions":
                calcValue = calc.numOfImpressions();
                break;
            case "Number of Clicks":
                calcValue = calc.numOfClicks();
                break;
            case "Number of Uniques":
                calcValue = calc.numOfUniques();
                break;
            case "Number of Bounces":
                calcValue = calc.numOfBounces(bounceType);
                break;
            case "Number of Conversions":
                calcValue = calc.numOfConversions();
                break;
            case "Total Cost":
                calcValue = calc.totalCost();
                break;
            case "CTR: Click-Through-Rate":
                calcValue = calc.clickThroughRate();
                break;
            case "CPA: Cost-Per-Acquisition":
                calcValue = calc.costPerAcquisition(calc.totalCost(), calc.numOfConversions());
                break;
            case "CPC: Cost-Per-Click":
                calcValue = calc.costPerClick(calc.totalCost(), calc.numOfClicks());
                break;
            case "CPM: Cost-Per-Thousand-Impressions":
                calcValue = calc.costPerThousandImpressions(calc.totalCost(), calc.numOfImpressions());
                break;
            case "Bounce Rate":
                calcValue = calc.bounceRate(bounceType);
                break;
        }

        return calcValue;
    }


    private int getGranularityValue(String granularity) {

         int granularityValue = 0;
        //granularity is in milliseconds
        switch (granularity){
            case "Hour": granularityValue = 3600000;
                break;
            case "Day": granularityValue = 86400000;
                break;
            case "Week": granularityValue = 604800000;
                break;
        }

        return granularityValue;
    }


}
