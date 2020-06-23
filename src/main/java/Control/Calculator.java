package Control;

import Model.Click;
import Model.Database;
import Model.Impression;
import Model.ServerLog;

import java.util.*;


public class Calculator {

    private Database data;
    private ArrayList<Click> clicks;
    private ArrayList<Impression> impressions;
    private ArrayList<ServerLog> serverLogs;


    private int numOfImpressions_m;
    private int numOfClicks_m;
    private int numOfUniques_m;
    private int numOfBounces_m;
    private int timeOnPageBounces_m;
    private int numOfPageViewsBounces_m;
    private int numOfConversions_m;
    private double totalCost_m;
    private double clickThroughRate_m;
    private double costPerAcquisition_m;
    private double costPerClick_m;
    private double costPerThousandImpressions_m;
    private double bounceRate_m;

    public Calculator (Database data){
        this.data = data;
        this.clicks = data.getClicks();
        this.impressions = data.getImpressions();
        this.serverLogs = data.getServerLogs();
    }

    public Calculator (){
        this.data = new Database("Start");
        this.clicks = new ArrayList<Click>();
        this.impressions = new ArrayList<Impression>();
        this.serverLogs = new ArrayList<ServerLog>();
    }

    public Database getData(){
        return this.data;
    }

    public void setData (Database data){
        this.data = data;
        // setData updates everything, consider an update datasets method
        this.clicks = data.getClicks();
        this.impressions = data.getImpressions();
        this.serverLogs = data.getServerLogs();
    }

    public void calculateData(){
       numOfImpressions();
       numOfClicks();
       numOfUniques();
       numOfBounces(true);
       numOfBounces(false);
       numOfConversions();
       totalCost();
       clickThroughRate();
       costPerAcquisition();
       costPerClick();
       costPerThousandImpressions();
       bounceRate(true);
       bounceRate(false);
    }

    public int numOfImpressions(){
        int ret_v = this.impressions.size();
        this.numOfImpressions_m = ret_v;
        return ret_v;
    }

    public int numOfClicks(){
        int ret_v = this.clicks.size();
        this.numOfClicks_m = ret_v;
        return ret_v;
    }

    // number of unique users that click on an add
    public int numOfUniques(){
        Set<String> uniqueUserClicks = new HashSet<String>();
        for (Click c: this.clicks){
            // it's a good idea to use Strings for the user IDs
            uniqueUserClicks.add(c.getId());
        }

        int ret_v = uniqueUserClicks.size();
        this.numOfUniques_m = ret_v;
        return ret_v;
    }

    //true is for number of pages. false is for time on page, cld be changed to string
    public int numOfBounces(Boolean bounceType){

        int bounces =0;

        if(bounceType)
            bounces = numOfPageViewsBounces();
        else
            bounces = timeOnPageBounces();

        int ret_v = bounces;
        this.numOfBounces_m = ret_v;
        return ret_v;
    }


    private int timeOnPageBounces(){

        int bounces = 0;

        for(ServerLog l: this.serverLogs){
            // calculate difference in entry-exit time

            if( !(l.getEntryDate() == null || l.getExitDate() == null)) {
                Date entryDate = l.getEntryDate();
                Date exitDate = l.getExitDate();

                long diff = -1;

                try {
                    diff = exitDate.getTime() - entryDate.getTime();
                    int diffsec = (int) (diff / (1000));

                    if(diffsec < 5)
                        bounces++;

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

        int ret_v = bounces;
        this.timeOnPageBounces_m = ret_v;
        return ret_v;
    }


    private int numOfPageViewsBounces(){
        int bounces = 0;
        for(ServerLog l: this.serverLogs){
            if(l.getPagesViewed() == 1){
                bounces++;
            }
        }

        int ret_v = bounces;
        this.numOfPageViewsBounces_m = ret_v;
        return ret_v;
    }



    public int numOfConversions(){
        int conversions = 0;
        for(ServerLog l: this.serverLogs){
            if(l.isConversion()) conversions++;
        }

        int ret_v = conversions;
        this.numOfConversions_m = ret_v;
        return ret_v;
    }

    // total cost in pence
    // should be rounded probably
    public double totalCost(){
        double totalCost = 0.0;
        for (Click c: this.clicks){
           totalCost += c.getCost();
        }

        double ret_v = totalCost;
        this.totalCost_m = ret_v;
        return ret_v;
    }


    // average number of clicks per impression
    // CTR
//    public double clickThroughRate(){
//        HashMap<String, Integer> impClickMap = new HashMap<String, Integer>();
//        for(Impression imp : this.impressions){
//            impClickMap.put(imp.getId(), 0);
//        }
//
//        for(Click c : this.clicks){
//            if( c != null || impClickMap.containsKey(c.getId()))
//                impClickMap.put(c.getId(), impClickMap.get(c.getId()) + 1);
//        }
//
//        double sum = 0;
//        for(Integer i: impClickMap.values()){
//            sum += i;
//        }
//        double ret_v = 0;
//        if(impClickMap.size() == 0)
//            throw new IllegalArgumentException("Cannot Calculate CTR: No Clicks");
//        else {
//            ret_v = sum / impClickMap.size();
//
//        }
//        this.clickThroughRate_m = ret_v;
//        return ret_v;
//    }

    public double clickThroughRate(){

        double ret_v =0.0;

        if(impressions.size() == 0)
            throw new IllegalArgumentException("Error: No Impressions, Cannot Calculate CTR.");
        else{
            ret_v = Double.valueOf(clicks.size())/impressions.size();
        }
        this.clickThroughRate_m = ret_v;
        return ret_v;
    }

    // average money spent on add campaign per conversion
    // CPA
    public double costPerAcquisition() throws IllegalArgumentException{

        double cpa = 0.0;
        if(numOfConversions_m == 0.0)
            throw new IllegalArgumentException("Number of Conversions is 0. Cannot Calculate CPA");
        else
            cpa = totalCost_m / numOfConversions_m;

        return cpa;
    }

    public double costPerAcquisition(double totalCost, double numberOfConversions) throws IllegalArgumentException{

        double cpa = 0.0;
        if(numberOfConversions == 0.0)
            throw new IllegalArgumentException("Number of Conversions is 0. Cannot Calculate CPA");
        else
            cpa = totalCost / numberOfConversions;

        return cpa;
    }


    // average money spent on add campaign per click
    // CPC
    public double costPerClick() throws IllegalArgumentException{

        double cpc = 0.0;

        if(numOfClicks_m == 0.0)
            throw new IllegalArgumentException("Number of Clicks is 0. Cannot Calculate CPC");
        else
            cpc = totalCost_m / numOfClicks_m;

        return cpc;
    }

    public double costPerClick(double totalCost ,double numberOfClicks) throws IllegalArgumentException{

        double cpc = 0.0;

        if(numberOfClicks == 0.0)
            throw new IllegalArgumentException("Number of Clicks is 0. Cannot Calculate CPC");
        else
            cpc = totalCost / numberOfClicks;

        return cpc;
    }

    // average money spent on add campaign per 1000 impressions
    // CPM
    public double costPerThousandImpressions() throws IllegalArgumentException{

        double cpm = 0.0;

        if(numOfImpressions_m == 0.0)
            throw new IllegalArgumentException("Number of Impressions is 0. Cannot Calculate CPM");
        else
            cpm = totalCost_m / (numOfImpressions_m / 1000);

        return cpm;
    }

    public double costPerThousandImpressions(double totalCost ,double numberOfImpressions) throws IllegalArgumentException{

        double cpm = 0.0;

        if(numberOfImpressions == 0.0)
            throw new IllegalArgumentException("Number of Impressions is 0. Cannot Calculate CPM");
        else
            cpm = totalCost / (numberOfImpressions / 1000);

        return cpm;
    }

    // average number of bounces per click
    public double bounceRate(boolean bounceType) throws IllegalArgumentException{

        double numOfBounces = 0.0;
        double bounceRate = 0.0;
        numOfBounces = numOfBounces(bounceType);

        if(numOfClicks() == 0.0)
            throw new IllegalArgumentException("Number of Clicks is 0. Cannot Calculate Bounce Rate");
        else
            bounceRate = numOfBounces / numOfClicks();

        return bounceRate;
    }

    public int getNumOfImpressions_m() {
        return numOfImpressions_m;
    }

    public void setNumOfImpressions_m(int numOfImpressions_m) {
        this.numOfImpressions_m = numOfImpressions_m;
    }

    public int getNumOfClicks_m() {
        return numOfClicks_m;
    }

    public void setNumOfClicks_m(int numOfClicks_m) {
        this.numOfClicks_m = numOfClicks_m;
    }

    public int getNumOfUniques_m() {
        return numOfUniques_m;
    }

    public void setNumOfUniques_m(int numOfUniques_m) {
        this.numOfUniques_m = numOfUniques_m;
    }

    public int getNumOfBounces_m() {
        return numOfBounces_m;
    }

    public void setNumOfBounces_m(int numOfBounces_m) {
        this.numOfBounces_m = numOfBounces_m;
    }

    public int getNumOfConversions_m() {
        return numOfConversions_m;
    }

    public void setNumOfConversions_m(int numOfConversions_m) {
        this.numOfConversions_m = numOfConversions_m;
    }

    public double getTotalCost_m() {
        return totalCost_m;
    }

    public void setTotalCost_m(double totalCost_m) {
        this.totalCost_m = totalCost_m;
    }

    public double getClickThroughRate_m() {
        return clickThroughRate_m;
    }

    public void setClickThroughRate_m(double clickThroughRate_m) {
        this.clickThroughRate_m = clickThroughRate_m;
    }

    public double getCostPerAcquisition_m() {
        return costPerAcquisition_m;
    }

    public void setCostPerAcquisition_m(double costPerAcquisition_m) {
        this.costPerAcquisition_m = costPerAcquisition_m;
    }

    public double getCostPerClick_m() {
        return costPerClick_m;
    }

    public void setCostPerClick_m(double costPerClick_m) {
        this.costPerClick_m = costPerClick_m;
    }

    public double getCostPerThousandImpressions_m() {
        return costPerThousandImpressions_m;
    }

    public void setCostPerThousandImpressions_m(double costPerThousandImpressions_m) {
        this.costPerThousandImpressions_m = costPerThousandImpressions_m;
    }

    public double getBounceRate_m() {
        return bounceRate_m;
    }

    public void setBounceRate_m(double bounceRate_m) {
        this.bounceRate_m = bounceRate_m;
    }
}
