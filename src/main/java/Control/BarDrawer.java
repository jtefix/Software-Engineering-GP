package Control;

import Model.Click;
import javafx.scene.chart.XYChart;

import java.util.*;

public class BarDrawer {

    private ArrayList<Click> periodicClicks;
    private final int NUMBER_OF_CHARTS = 8;

    public BarDrawer(ArrayList<Click> periodicClicks){

        this.periodicClicks = periodicClicks;

    }

    public XYChart.Series getChart(){

        XYChart.Series dataSeries = new XYChart.Series();

        int[] dataSet = getDataSet();
//        for(HashMap.Entry entry: dataSet.entrySet()) {
//            XYChart.Data dataPoint = new XYChart.Data<>(entry.getKey(), entry.getValue());
//            dataSeries.getData().add(dataPoint);
//            categories.add((String) entry.getKey());
//        }

        dataSeries.getData().add(new XYChart.Data<>("0-2",dataSet[0]));
        dataSeries.getData().add(new XYChart.Data<>("2-4",dataSet[1]));
        dataSeries.getData().add(new XYChart.Data<>("4-6",dataSet[2]));
        dataSeries.getData().add(new XYChart.Data<>("6-8",dataSet[3]));
        dataSeries.getData().add(new XYChart.Data<>("8-10",dataSet[4]));
        dataSeries.getData().add(new XYChart.Data<>("10-12",dataSet[5]));
        dataSeries.getData().add(new XYChart.Data<>("12-14",dataSet[6]));
        dataSeries.getData().add(new XYChart.Data<>("14+",dataSet[7]));

        // fill this whenever you add new data to the chart

// after all data is added to the chart, sort the categories, set them manually and enable auto-ranging again (if needed)


        return  dataSeries;
    }

    public int[] getDataSet(){

        HashMap<String,Integer> groupedData = new HashMap<>();

        double costRange = 16;
        double[] costRanges = new double[NUMBER_OF_CHARTS];

        int index=0;
        int LOWEST_PRICE = 0;
        int HIGHEST_PRICE = 16;

        for(double i=LOWEST_PRICE;i<HIGHEST_PRICE;i+=costRange/NUMBER_OF_CHARTS){
            costRanges[index++] = i;
        }

        int[] numOfClicksWithinRange = new int[NUMBER_OF_CHARTS];
        for(int i: numOfClicksWithinRange)
            numOfClicksWithinRange[i]=0;

        for(Click c: periodicClicks){
            if(c.getCost()<costRanges[1])
                numOfClicksWithinRange[0]++;
            else if(c.getCost()<costRanges[2])
                numOfClicksWithinRange[1]++;
            else if(c.getCost()<costRanges[3])
                numOfClicksWithinRange[2]++;
            else if(c.getCost()<costRanges[4])
                numOfClicksWithinRange[3]++;
            else if(c.getCost()<costRanges[5])
                numOfClicksWithinRange[4]++;
            else if(c.getCost()<costRanges[6])
                numOfClicksWithinRange[5]++;
            else if(c.getCost()<costRanges[7])
                numOfClicksWithinRange[6]++;
            else
                numOfClicksWithinRange[7]++;

        }

        return numOfClicksWithinRange;
    }

}
