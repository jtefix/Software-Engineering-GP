package Control.Test;

import Control.*;
import Control.JavaFX.ImportGUIController;
import Control.JavaFX.MainController;
import Model.*;
import javafx.scene.chart.XYChart;
import org.junit.Before;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Test {

    private static final double DELTA = 1e-15;

    private Database database = new Database("Test");

    @Before
    public void init() throws Exception {
        Parser parser = new Parser();
        database.setClicks(parser.parseClicks("src/main/resources/click_log.csv"));

        database.setServerLogs(parser.parseServerLogs("src/main/resources/server_log.csv"));

        database.setImpressions(parser.parseImpressions("src/main/resources/impression_log.csv"));
    }

//    @org.junit.Test(expected = FileNotFoundException.class)
//    public void fileNotFoundTest(){ Parser parser= new Parser(); }
//
//    @org.junit.Test(expected =  Exception.class)
//    public void fileWrongTypeTest(){ Parser parser = new Parser("click_log.txt"); }

    //Testing Database class

    @org.junit.Test
    public void clickTest() {
        assertEquals(23923, database.getClicks().size());
    }

    @org.junit.Test
    public void serverLogTest() {
        assertEquals(23923, database.getServerLogs().size());
    }

    @org.junit.Test
    public void impressionsTest(){
        assertEquals(486104, database.getImpressions().size());
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Each table split into 2 partitions. (2015-01-01 - 2015-01-07) and (2015-01-07 - 2015-01-15)

    @org.junit.Test
    public void clickGetTest() {
        Click clickTest = new Click("2015-01-01 12:01:21", "8895519749317550080", 11.794442);
        assertEquals(clickTest.getDate(), database.getClicks().get(0).getDate());
        assertEquals(clickTest.getId(), database.getClicks().get(0).getId());
    }

    @org.junit.Test
    public void clickGetTest2() {
        Click clickTest = new Click("2015-01-14  06:49:50", "5859084121772149760", 12.520737);
        assertEquals(clickTest.getDate(), database.getClicks().get(23333).getDate());
        assertEquals(clickTest.getId(), database.getClicks().get(23333).getId());
    }

    @org.junit.Test
    public void serverLogGetTest1() {
        int impressionNum = 341;
        ServerLog serverLogTest = new ServerLog("2015-01-01 15:18:30", "6372401377795768320", "2015-01-01  15:22:41", 2, false);
        assertEquals(serverLogTest.getEntryDate(), database.getServerLogs().get(impressionNum).getEntryDate());
        assertEquals(serverLogTest.getId(), database.getServerLogs().get(impressionNum).getId());
        assertEquals(serverLogTest.getExitDate(), database.getServerLogs().get(impressionNum).getExitDate());
        assertEquals(serverLogTest.getPagesViewed(), database.getServerLogs().get(impressionNum).getPagesViewed());
        assertEquals(serverLogTest.isConversion(), database.getServerLogs().get(impressionNum).isConversion());
    }

    @org.junit.Test
    public void serverLogGetTest2() {
        int impressionNum = 14690;
        ServerLog serverLogTest = new ServerLog("2015-01-09 17:19:43", "1087356951602644992", "2015-01-09  17:20:58", 1, false);

        assertEquals(serverLogTest.getEntryDate(), database.getServerLogs().get(impressionNum).getEntryDate());
        assertEquals(serverLogTest.getId(), database.getServerLogs().get(impressionNum).getId());
        assertEquals(serverLogTest.getExitDate(), database.getServerLogs().get(impressionNum).getExitDate());
        assertEquals(serverLogTest.getPagesViewed(), database.getServerLogs().get(impressionNum).getPagesViewed());
        assertEquals(serverLogTest.isConversion(), database.getServerLogs().get(impressionNum).isConversion());
    }

    @org.junit.Test
    public void impressionGetTest1() {
        int impressionNum = 998;
        Impression impressionTest = new Impression("2015-01-01  12:28:09", "1394237900267662336", "Female", "35-44", "Medium", "News", "0.001627");

        assertEquals(impressionTest.getDate(), database.getImpressions().get(impressionNum).getDate());
        assertEquals(impressionTest.getId(), database.getImpressions().get(impressionNum).getId());
        assertEquals(impressionTest.getGender(), database.getImpressions().get(impressionNum).getGender());
        assertEquals(impressionTest.getAgeGroup(), database.getImpressions().get(impressionNum).getAgeGroup());
        assertEquals(impressionTest.getIncome(), database.getImpressions().get(impressionNum).getIncome());
        assertEquals(impressionTest.getContext(), database.getImpressions().get(impressionNum).getContext());
        assertEquals(impressionTest.getCost(), database.getImpressions().get(impressionNum).getCost());
    }

    @org.junit.Test
    public void impressionGetTest2() {
        int impressionNum = 299629;
        Impression impressionTest = new Impression("2015-01-09  18:01:19", "6276544492062608384", "Male", ">54", "Low", "Shopping", "0.000000");

        assertEquals(impressionTest.getDate(), database.getImpressions().get(impressionNum).getDate());
        assertEquals(impressionTest.getId(), database.getImpressions().get(impressionNum).getId());
        assertEquals(impressionTest.getGender(), database.getImpressions().get(impressionNum).getGender());
        assertEquals(impressionTest.getAgeGroup(), database.getImpressions().get(impressionNum).getAgeGroup());
        assertEquals(impressionTest.getIncome(), database.getImpressions().get(impressionNum).getIncome());
        assertEquals(impressionTest.getContext(), database.getImpressions().get(impressionNum).getContext());
        assertEquals(impressionTest.getCost(), database.getImpressions().get(impressionNum).getCost());
    }

    @org.junit.Test
    public void serverLogGetExceptTest() {
        int serverLogNum = 10;
        assertEquals(null, database.getServerLogs().get(serverLogNum).getExitDate());
    }

    //Tests for empty values in the given csv file
//    @org.junit.Test (expected = ArrayIndexOutOfBoundsException.class)
//    public void serverLogGetEmptyEdgeTest() {
//    	Parser parser = new Parser("EdgeCaseTests.csv");
//    	Database testDatabase = new Database("Edge Tester");
//        testDatabase.setServerLogs(parser.parseServerLogs());
//        testDatabase.getServerLogs().get(9).getId();
//    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Testing Calculator class

    @org.junit.Test
    public void numOfImpressionsTest() {
        Calculator calculator = new Calculator(database);
        assertEquals(486104, calculator.numOfImpressions());
    }

    @org.junit.Test
    public void numOfUniquesTest() {
        Calculator calculator = new Calculator(database);
        assertEquals(23806, calculator.numOfUniques());
    }

    @org.junit.Test
    public void numOfClicksTest() {
        Calculator calculator = new Calculator(database);
        assertEquals(23923, calculator.numOfClicks());
    }

    @org.junit.Test
    public void numOfBouncesOnTimeTest() {
        Calculator calculator = new Calculator(database);
        assertEquals(2371, calculator.numOfBounces(false));
    }

    @org.junit.Test
    public void numOfBouncesOnViewTest() {
        Calculator calculator = new Calculator(database);
        assertEquals(8665, calculator.numOfBounces(true));
    }

    @org.junit.Test
    public void numOfConversionsTest() {
        Calculator calculator = new Calculator(database);
        assertEquals(2026, calculator.numOfConversions());
    }

    @org.junit.Test
    public void conversionsViewerTest() {
        Calculator calculator = new Calculator(database);
        assertEquals(2026, calculator.numOfConversions());
    }

    @org.junit.Test
    public void bounceViewerTest() {
        Calculator calculator = new Calculator(database);
        assertEquals(2026, calculator.numOfConversions());
    }

    @org.junit.Test
    public void totalCostTest() {
        Calculator calculator = new Calculator(database);
        assertEquals(117610.866, calculator.totalCost(), 0.001);
    }

    //Failed

    @org.junit.Test
    public void clickThroughRateTest() {
        Calculator calculator = new Calculator(database);
        assertEquals(0.049213748498263744, calculator.clickThroughRate(), DELTA);
    }

    @org.junit.Test
    public void costPerAcquisitionTest() {
        Calculator calculator = new Calculator(database);
        calculator.totalCost();
        calculator.numOfConversions();
        assertEquals(58.05077281589375, calculator.costPerAcquisition(), DELTA);
    }

    @org.junit.Test (expected = IllegalArgumentException.class)
    public void costPerAcquisitionEdgeTest() {
        Calculator calculator = new Calculator(database);
        calculator.setNumOfConversions_m(0);
        calculator.totalCost();
        calculator.costPerAcquisition();
    }

    @org.junit.Test
    public void costPerThousandImpTest() {
        Calculator calculator = new Calculator(database);
        calculator.numOfImpressions();
        calculator.totalCost();
        assertEquals(241.99766610082457, calculator.costPerThousandImpressions(), 1e-10);
    }

    @org.junit.Test (expected = IllegalArgumentException.class)
    public void costPerThousandImpEdgeTest() {
        Calculator calculator = new Calculator(database);
        calculator.setNumOfImpressions_m(0);
        calculator.totalCost();
        calculator.costPerThousandImpressions();
    }

    @org.junit.Test
    public void costPerClickTest() {
        Calculator calculator = new Calculator(database);
        assertEquals(0.09910964343936797, calculator.bounceRate(false), DELTA);
    }

    @org.junit.Test
    public void bounceRatePageViewTest() {
        Calculator calculator = new Calculator(database);
        assertEquals(0.36220373698950803, calculator.bounceRate(true), DELTA);
    }

    @org.junit.Test
    public void bounceRatePageTimeTest() {
        Calculator calculator = new Calculator(database);
        assertEquals(0.09910964343936797, calculator.bounceRate(false), DELTA);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @org.junit.Test
    public void impSetGetTest() {
        Calculator calculator = new Calculator(database);
        calculator.setNumOfImpressions_m(50);
        assertEquals(50, calculator.getNumOfImpressions_m());
    }

    @org.junit.Test
    public void clickSetGetTest() {
        Calculator calculator = new Calculator(database);
        calculator.setNumOfClicks_m(500);
        assertEquals(500, calculator.getNumOfClicks_m());
    }

    @org.junit.Test
    public void costPerClickSetGetTest() {
        Calculator calculator = new Calculator(database);
        calculator.setCostPerClick_m(0.13);
        assertEquals(0.13, calculator.getCostPerClick_m(), 0.01);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Tesing PeriodicCalculator class

    public ArrayList periodicCalcTestData(int option, String fDate, String sDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date firstDate = sdf.parse(fDate);
        Date secondDate = sdf.parse(sDate);

        PeriodicCalculator calculator = new PeriodicCalculator(database);

        ArrayList<Click> clickArray = calculator.getClicksWithinPeriod(firstDate, secondDate);
        ArrayList<Impression> impressionArray = calculator.getImpressionsWithinPeriod(firstDate, secondDate);
        ArrayList<ServerLog> serverlogArray = calculator.getServerLogsWithinPeriod(firstDate, secondDate);

        switch(option){
            case 1 :
                return clickArray;
            case 2 :
                return impressionArray;
            case 3 :
                return serverlogArray;
        }
        return null;
    }

    //Each table split into 2 partitions. (2015-01-01 - 2015-01-07) and (2015-01-07 - 2015-01-15)

    @org.junit.Test
    public void periodicClickTest() throws ParseException {
        assertEquals(6102, periodicCalcTestData(1, "2015-01-01 00:00:00", "2015-01-05 00:00:00").size());
    }

    @org.junit.Test
    public void periodicClick2Test() throws ParseException {
        assertEquals(3937, periodicCalcTestData(1, "2015-01-08 12:20:29", "2015-01-10 13:45:32").size());
    }

    @org.junit.Test
    public void periodicImpressionTest() throws ParseException {
        assertEquals(128408, periodicCalcTestData(2, "2015-01-01 15:24:00", "2015-01-05 12:00:32").size());
    }

    @org.junit.Test
    public void periodicImpression2Test() throws ParseException {
        assertEquals(175665, periodicCalcTestData(2, "2015-01-10 00:20:29", "2015-01-14 13:45:32").size());
    }

    @org.junit.Test
    public void periodicServerLogTest() throws ParseException {
        assertEquals(4932, periodicCalcTestData(3, "2015-01-04 12:20:29", "2015-01-07 13:45:32").size());
    }

    @org.junit.Test
    public void periodicServerLog2Test() throws ParseException {
        assertEquals(3128, periodicCalcTestData(3, "2015-01-09 08:20:29", "2015-01-10 23:45:32").size());
    }

    @org.junit.Test
    public void periodicUptoDateClickTest() throws ParseException {
        ArrayList<Click> clickData = periodicCalcTestData(1, "2015-01-01 00:00:00", "2015-01-05 00:00:00");
        Click finalClick = clickData.get(clickData.size()-1);
        assertEquals("Sun Jan 04 23:58:34 GMT 2015", finalClick.getDate().toString());
    }

    @org.junit.Test
    public void periodicClickDefectTest() throws ParseException {
        assertTrue(periodicCalcTestData(1, "2015-01-04 12:50:40", "2015-01-01 15:45:55").isEmpty());
    }

    @org.junit.Test
    public void periodicTimeClickDefectTest() throws ParseException {
        assertTrue(periodicCalcTestData(1, "2015-01-01 12:50:40", "2015-01-01 10:45:55").isEmpty());
    }

    @org.junit.Test
    public void periodicImpressionsDefectTest() throws ParseException {
        assertTrue(periodicCalcTestData(2, "2015-01-04 00:50:40", "2015-01-01 15:45:55").isEmpty());
    }

    @org.junit.Test
    public void periodicTimeImpressionsDefectTest() throws ParseException {
        assertTrue(periodicCalcTestData(2, "2015-01-01 12:50:40", "2015-01-01 10:45:55").isEmpty());
    }

    @org.junit.Test
    public void periodicServerLogDefectTest() throws ParseException {
        assertTrue(periodicCalcTestData(3, "2015-01-04 16:09:09", "2015-01-01 00:45:05").isEmpty());
    }

    @org.junit.Test
    public void periodicTimeServerLogDefectTest() throws ParseException {
        assertTrue(periodicCalcTestData(3, "2015-01-01 12:50:40", "2015-01-01 10:45:55").isEmpty());
    }

    @org.junit.Test
    public void periodicClickEdge1Test1() throws ParseException {
        assertEquals(1079, periodicCalcTestData(1, "2015-01-01 00:00:00", "2015-01-02 00:00:00").size());
    }

    @org.junit.Test
    public void periodicClickEdge1Test2() throws ParseException {
        assertEquals(1833, periodicCalcTestData(1, "2015-01-06 00:00:00", "2015-01-07 00:00:00").size());
    }

    @org.junit.Test
    public void periodicClickEdge2Test1() throws ParseException {
        assertEquals(1857, periodicCalcTestData(1, "2015-01-07 00:00:00", "2015-01-08 00:00:00").size());
    }

    @org.junit.Test
    public void periodicClickEdge2Test2() throws ParseException {
        assertEquals(726, periodicCalcTestData(1, "2015-01-14 00:00:00", "2015-01-15 00:00:00").size());
    }

    @org.junit.Test
    public void periodicImpressionsEdge1Test1() throws ParseException {
        assertEquals(22049, periodicCalcTestData(2, "2015-01-01 00:00:00", "2015-01-02 00:00:00").size());
    }

    @org.junit.Test
    public void periodicImpressionsEdge1Test2() throws ParseException {
        assertEquals(37379, periodicCalcTestData(2, "2015-01-06 00:00:00", "2015-01-07 00:00:00").size());
    }

    @org.junit.Test
    public void periodicImpressionsEdge2Test1() throws ParseException {
        assertEquals(37958, periodicCalcTestData(2, "2015-01-07 00:00:00", "2015-01-08 00:00:00").size());
    }

    @org.junit.Test
    public void periodicImpressionsEdge2Test2() throws ParseException {
        assertEquals(14135, periodicCalcTestData(2, "2015-01-14 00:00:00", "2015-01-15 00:00:00").size());
    }

    @org.junit.Test
    public void periodicServerLogEdge1Test1() throws ParseException {
        assertEquals(988, periodicCalcTestData(3, "2015-01-01 00:00:00", "2015-01-02 00:00:00").size());
    }

    @org.junit.Test
    public void periodicServerLogEdge1Test2() throws ParseException {
        assertEquals(1660, periodicCalcTestData(3, "2015-01-06 00:00:00", "2015-01-07 00:00:00").size());
    }

    @org.junit.Test
    public void periodicServerLogEdge2Test1() throws ParseException {
        assertEquals(1670, periodicCalcTestData(3, "2015-01-07 00:00:00", "2015-01-08 00:00:00").size());
    }

    @org.junit.Test
    public void periodicServerLogEdge2Test2() throws ParseException {
        assertEquals(667, periodicCalcTestData(3, "2015-01-14 00:00:00", "2015-01-15 00:00:00").size());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Testing BarDrawer class

    @org.junit.Test
    public void barDrawerCountTest() throws ParseException {
        BarDrawer bd = new BarDrawer(periodicCalcTestData(1,"2015-01-02 00:00:00","2015-01-07 00:00:00"));
        int[] countArray = bd.getDataSet();
        assertEquals(4380, countArray[0]);
        assertEquals(0, countArray[1]);
        assertEquals(404, countArray[2]);
        assertEquals(864, countArray[3]);
        assertEquals(873, countArray[4]);
        assertEquals(855, countArray[5]);
        assertEquals(821, countArray[6]);
        assertEquals(406, countArray[7]);
    }

    @org.junit.Test
    public void barDrawerCountDefectTest() throws ParseException {
        BarDrawer bd = new BarDrawer(periodicCalcTestData(1,"2015-01-07 00:00:00","2015-01-01 00:00:00"));
        int[] countArray = bd.getDataSet();
        System.out.println(countArray.length);
        for(int i = 0; i<8;i++){
            assertTrue(countArray[i] == 0);
        }
    }

    @org.junit.Test
    public void barDrawerChartTest() throws ParseException {
        BarDrawer bd = new BarDrawer(periodicCalcTestData(1,"2015-01-02 00:00:00","2015-01-07 00:00:00"));
        XYChart.Series countArray = bd.getChart();

        XYChart.Series testSeries = new XYChart.Series();

        testSeries.getData().add(new XYChart.Data<>("0-2",4380));
        testSeries.getData().add(new XYChart.Data<>("2-4",0));
        testSeries.getData().add(new XYChart.Data<>("4-6",404));
        testSeries.getData().add(new XYChart.Data<>("6-8",864));
        testSeries.getData().add(new XYChart.Data<>("8-10",873));
        testSeries.getData().add(new XYChart.Data<>("10-12",855));
        testSeries.getData().add(new XYChart.Data<>("12-14",821));
        testSeries.getData().add(new XYChart.Data<>("14+",406));

        for(int i=0; i<8; i++){
            assertEquals(testSeries.getData().get(i).toString(), countArray.getData().get(i).toString());
        }
    }

    @org.junit.Test
    public void barDrawerChartDefectTest() throws ParseException {
        BarDrawer bd = new BarDrawer(periodicCalcTestData(1,"2015-01-06 00:00:00","2001-01-01 00:00:00"));
        XYChart.Series countArray = bd.getChart();

        for(int i=0; i<8;i++){
            if(i==7)
                assertEquals("Data[14+,0,null]", countArray.getData().get(7).toString());
            else {
                int labler = i;
                String compare = String.format("Data[%d-%d,0,null]", labler * 2, (labler * 2) + 2);
                assertEquals(compare, countArray.getData().get(i).toString());
            }
        }

    }

    public Filter filterMain(){
        setDemogs();
        Filter filter = new Filter();
        filter.setSelectedContexts(contexts);
        filter.setSelectedAges(ages);
        filter.setSelectedIncomes(incomes);
        filter.setSelectedGenders(genders);
        return filter;
    }

    public Database filteredDB(Filter filter){
        FilteredCalculator databaseFiltered = new FilteredCalculator(database, filter);
        return databaseFiltered.getFilteredDatabase();
    }

    //Testing Filter class and FilterCalculator

    private ArrayList<String> ages;
    private ArrayList<String> incomes;
    private ArrayList<String> contexts;
    private ArrayList<String> genders;

    public void setDemogs(){
        contexts = new ArrayList<String>();
        ages = new ArrayList<String>();
        genders = new ArrayList<String>();
        incomes = new ArrayList<String>();
        ages.add("<25");
        ages.add("25-34");
        ages.add("35-44");
        ages.add("45-54");
        ages.add(">54");
        incomes.add("High");
        incomes.add("Medium");
        incomes.add("Low");
        genders.add("Male");
        genders.add("Female");
        contexts.add("News");
        contexts.add("Blog");
        contexts.add("Shopping");
        contexts.add("Social Media");
    }

    public Filter filterAdder(int option, String content){
        Filter filter = filterMain();
        ArrayList<String> contentList = new ArrayList<String>();
        contentList.add(content);
        switch(option){
            case 1:
                filter.setSelectedContexts(contentList);
                return filter;
            case 2:
                filter.setSelectedIncomes(contentList);
                return filter;
            case 3:
                filter.setSelectedAges(contentList);
                return filter;
            case 4:
                filter.setSelectedGenders(contentList);
                return filter;
        }
        return null;
    }

    private Calculator filterCalc(Database database){
        Calculator calc = new Calculator(database);
        return calc;
    }

    @org.junit.Test
    public void filterImpressionsByContextNewsGetTest(){
        assertEquals(139170, filteredDB(filterAdder(1, "News")).getImpressions().size());
    }

    @org.junit.Test
    public void filterCTRByContextNewsGetTest(){
        assertEquals(0.04825752676582597, filterCalc(filteredDB(filterAdder(1, "News"))).clickThroughRate(), DELTA);
    }


    @org.junit.Test(expected = IllegalArgumentException.class)
    public void filterCPAByContextNewsGetTest(){
        filterCalc(filteredDB(filterAdder(1, "News"))).costPerAcquisition();
    }

    @org.junit.Test
    public void filterCPMByContextNewsGetTest(){
        Database database = filteredDB(filterAdder(1, "News"));
        Calculator calculator = new Calculator(database);
        calculator.totalCost();
        calculator.numOfImpressions();
        assertEquals(231.79012405755373, calculator.costPerThousandImpressions(), DELTA);
    }

    @org.junit.Test
    public void filterBounceTimeByContextNewsGetTest(){
        assertEquals(0.3572066706372841, filterCalc(filteredDB(filterAdder(1, "News"))).bounceRate(true), DELTA);
    }

    @org.junit.Test
    public void filterBounceViewByContextNewsGetTest(){
        assertEquals(0.0958904109589041, filterCalc(filteredDB(filterAdder(1, "News"))).bounceRate(false), DELTA);
    }

    @org.junit.Test
    public void filterTotalCostByContextNewsGetTest(){
        assertEquals(32218.827243999967, filterCalc(filteredDB(filterAdder(1, "News"))).totalCost(), DELTA);
    }

    @org.junit.Test
    public void filterUniquesByContextNewsGetTest(){
        assertEquals(6682, filterCalc(filteredDB(filterAdder(1, "News"))).numOfUniques());
    }

    @org.junit.Test
    public void filterConversionsByContextNewsGetTest(){
        assertEquals(563, filterCalc(filteredDB(filterAdder(1, "News"))).numOfConversions());
    }

    @org.junit.Test
    public void filterImpressionsByContextShoppingGetTest(){
        assertEquals(139256, filterCalc(filteredDB(filterAdder(1, "Shopping"))).numOfImpressions());
    }

    @org.junit.Test
    public void filterCTRByContextShoppingGetTest(){
        assertEquals(0.047732234158671796, filterCalc(filteredDB(filterAdder(1, "Shopping"))).clickThroughRate(), DELTA);
    }

    @org.junit.Test
    public void filterTotalCostByContextShoppingGetTest(){
        assertEquals(32358.15628499998, filterCalc(filteredDB(filterAdder(1, "Shopping"))).totalCost(), DELTA);
    }

    @org.junit.Test
    public void filterImpressionsByContextBlogGetTest(){
        assertEquals(69583, filteredDB(filterAdder(1, "Blog")).getImpressions().size());
    }

    @org.junit.Test
    public void filterClickCostByContextBlogGetTest(){
        Database database = filteredDB(filterAdder(1, "Blog"));
        Calculator calculator = new Calculator(database);
        calculator.totalCost();
        calculator.numOfClicks();
        assertEquals(4.9805881372837675, calculator.costPerClick(), DELTA);
    }

    @org.junit.Test
    public void filterBounceViewByContextBlogGetTest(){
        assertEquals(0.09900625690099374, filterCalc(filteredDB(filterAdder(1, "Blog"))).bounceRate(false), DELTA);
    }

    @org.junit.Test
    public void filterUniquesByContextBlogGetTest(){
        assertEquals(2699, filterCalc(filteredDB(filterAdder(1, "Blog"))).numOfUniques());
    }

    @org.junit.Test
    public void filterImpressionsByContextSocialMediaGetTest(){
        assertEquals(138095, filteredDB(filterAdder(1, "Social Media")).getImpressions().size());
    }

    @org.junit.Test
    public void filterTotalCostByContextSocialMediaGetTest(){
        assertEquals(39501.62422700002, filterCalc(filteredDB(filterAdder(1, "Social Media"))).totalCost(), DELTA);
    }

    @org.junit.Test
    public void filterCPMByContextSocialMediaGetTest(){
        Database database = filteredDB(filterAdder(1, "Social Media"));
        Calculator calculator = new Calculator(database);
        calculator.totalCost();
        calculator.numOfImpressions();
        assertEquals(286.24365381884076, calculator.costPerThousandImpressions(), DELTA);
    }

    @org.junit.Test
    public void filterImpressionsByIncomeGetTest(){
        assertEquals(97106, filteredDB(filterAdder(2, "High")).getImpressions().size());
    }

    @org.junit.Test
    public void filterCTRByContextIncomeGetTest(){
        assertEquals(0.03136733631156302, filterCalc(filteredDB(filterAdder(2, "Low"))).clickThroughRate(), DELTA);
    }

    @org.junit.Test
    public void filterConvByContextIncomeGetTest(){
        assertEquals(1244, filterCalc(filteredDB(filterAdder(2, "Medium"))).numOfConversions());
    }

    @org.junit.Test
    public void filterCPAByContextIncomeGetTest(){
        Database database = filteredDB(filterAdder(2, "Medium"));
        Calculator calculator = new Calculator(database);
        calculator.totalCost();
        calculator.numOfConversions();
        assertEquals(54.9158848609326, calculator.costPerAcquisition(), DELTA);
    }

    @org.junit.Test
    public void filterImpressionsByAge1GetTest(){
        assertEquals(60972, filteredDB(filterAdder(3, ">54")).getImpressions().size());
    }

    @org.junit.Test
    public void filterClickByContextAge1GetTest(){
        assertEquals(7031, filterCalc(filteredDB(filterAdder(3, "25-34"))).numOfClicks());
    }

    @org.junit.Test
    public void filterCPCByContextAge1GetTest(){
        Database database = filteredDB(filterAdder(3, "25-34"));
        Calculator calculator = new Calculator(database);
        calculator.totalCost();
        calculator.numOfClicks();
        assertEquals(4.962649332669604, calculator.costPerClick(), DELTA);
    }

    @org.junit.Test
    public void filterImpressionsByAge2GetTest(){
        assertEquals(121774, filteredDB(filterAdder(3, "35-44")).getImpressions().size());
    }

    @org.junit.Test
    public void filterBounceTimeByContextAge2GetTest(){
        assertEquals(0.3701205804210096, filterCalc(filteredDB(filterAdder(3, "45-54"))).bounceRate(true), DELTA);
    }

    @org.junit.Test
    public void filterImpressionsByMaleGetTest(){
        assertEquals(161469, filteredDB(filterAdder(4, "Male")).getImpressions().size());
    }

    @org.junit.Test
    public void filterCTRByContextMaleGetTest(){
        assertEquals(0.049470796251912134, filterCalc(filteredDB(filterAdder(4, "Male"))).clickThroughRate(), DELTA);
    }

    @org.junit.Test
    public void filterConversionsByContextMaleGetTest(){
        assertEquals(674, filterCalc(filteredDB(filterAdder(4, "Male"))).numOfConversions());
    }

    @org.junit.Test
    public void filterImpressionsByFemaleGetTest(){
        assertEquals(324635, filteredDB(filterAdder(4, "Female")).getImpressions().size());
    }

    @org.junit.Test
    public void filterTotalCostByContextFemaleGetTest(){
        assertEquals(78443.56686100048, filterCalc(filteredDB(filterAdder(4, "Female"))).totalCost(), DELTA);
    }

    @org.junit.Test
    public void filterCPAByContextFemaleGetTest(){
        Database database = filteredDB(filterAdder(4, "Female"));
        Calculator calculator = new Calculator(database);
        calculator.totalCost();
        calculator.numOfConversions();
        assertEquals(58.02038969008911, calculator.costPerAcquisition(), DELTA);
    }

    @org.junit.Test
    public void filterImpressionsByComboGetTest(){
        ArrayList<String> genders = new ArrayList<String>();
        ArrayList<String> incomes = new ArrayList<String>();
        genders.add("Female");
        incomes.add("High");
        Filter filter = filterMain();
        filter.setSelectedGenders(genders);
        filter.setSelectedIncomes(incomes);
        assertEquals(64790, filteredDB(filter).getImpressions().size());
    }

    @org.junit.Test
    public void filterCTRByContextComboGetTest(){
        ArrayList<String> genders = new ArrayList<String>();
        ArrayList<String> ages = new ArrayList<String>();
        genders.add("Female");
        genders.add("Male");
        ages.add("<25");
        Filter filter = filterMain();
        filter.setSelectedGenders(genders);
        filter.setSelectedAges(ages);
        assertEquals(0.030757341576506954, filterCalc(filteredDB(filter)).clickThroughRate(), DELTA);
    }

    @org.junit.Test
    public void filterBounceViewByContextComboGetTest(){
        ArrayList<String> contexts = new ArrayList<String>();
        ArrayList<String> ages = new ArrayList<String>();
        ArrayList<String> genders = new ArrayList<String>();
        contexts.add("Blog");
        genders.add("Male");
        ages.add("<25");
        ages.add("25-34");
        Filter filter = filterMain();
        filter.setSelectedGenders(genders);
        filter.setSelectedContexts(contexts);
        filter.setSelectedAges(ages);
        System.out.println(filterCalc(filteredDB(filter)).bounceRate(false));
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Testing GraphDrawer class

//    @org.junit.Test
//    public void graphDrawerTest() throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        Date dateFrom = sdf.parse("2015-01-02");
//        Date dateTo = sdf.parse("2015-01-05");
//
//        Filter filter = new Filter();
//        filter.setDateFrom(dateFrom);
//        filter.setDateTo(dateTo);
//        filter.setBounceType(true);
//
//        PeriodicCalculator periodicCalculator = new PeriodicCalculator(database);
//        Database periodicDatabase = periodicCalculator.getDatabaseWithinPeriod(dateFrom,dateTo);
//
////        FilteredCalculator filteredCalculator = new FilteredCalculator(periodicDatabase,filter);
////        Database filteredDatabase = filteredCalculator.getFilteredDatabase();
//
//        GraphDrawer gd = new GraphDrawer(periodicDatabase,filter);
//        Double result = gd.getDataPoints(3600000, "Number of Impressions").get(1);
//        assertEquals(java.util.Optional.of(320.0),java.util.Optional.of(result));
//    }

    //What is this testing?
//    @org.junit.Test
//    public void graphDrawerEdgeTest() throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        Date dateFrom = sdf.parse("2015-01-02");
//        Date dateTo = sdf.parse("2015-01-05");
//
//        Filter filter = new Filter();
//        filter.setDateFrom(dateFrom);
//        filter.setDateTo(dateTo);
//        filter.setBounceType(true);
//
//        PeriodicCalculator periodicCalculator = new PeriodicCalculator(database);
//        Database periodicDatabase = periodicCalculator.getDatabaseWithinPeriod(sdf.parse("2015-01-02"),sdf.parse("2015-01-05"));
//
////        FilteredCalculator filteredCalculator = new FilteredCalculator(periodicDatabase,filter);
////        Database filteredDatabase = filteredCalculator.getFilteredDatabase();
//
//        GraphDrawer gd = new GraphDrawer(periodicDatabase,filter);
//        System.out.println(gd.getDataPoints(3600000, "Number of Impressions"));
//        assertEquals(null, gd.getDataPoints(3600000, "Number of Impressions"));
//    }


}

