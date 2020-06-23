package Control.JavaFX;

import Control.JavaFX.FilterGUIControllers.CampaignSummaryFiltersGUIController;
import Control.JavaFX.FilterGUIControllers.FiltersGUIController;
import Control.JavaFX.FilterGUIControllers.MetricsGraphFiltersGUIController;

import Model.Database;
import Model.Filter;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static MainController instance;

    private Database database;
    private Filter campaignSummaryFilter;
    private Filter metricsGraphFilter;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab importTab;

    @FXML
    private Tab campaignSummaryTab;

    @FXML
    private Tab metricGraphsTab;

    @FXML
    private Tab histogramTab;

    @FXML
    private Tab glossaryTab;

    @FXML
    private Tab exportTab;

    @FXML
    private Tab appearanceTab;

    //Inject Controllers
    private ImportGUIController importGUIController;
    private CampaignSummaryGUIController campaignSummaryGUIController;
    private CampaignSummaryFiltersGUIController campaignSummaryFiltersGUIController;
    private MetricsGraphGUIController metricsGraphGUIController;
    private MetricsGraphFiltersGUIController metricsGraphFiltersGUIController;
    private HistogramGUIController histogramGUIController;
    private GlossaryGUIController glossaryGUIController;
    private ExportGUIController exportGUIController;
    private AppearanceGUIController appearanceGUIController;

    //VBoxes
    private ArrayList<VBox> vBoxes;

    //Buttons
    private ArrayList<Button> buttons;

    //Labels
    private ArrayList<Label> labels;

    //CheckBoxes
    private ArrayList<JFXComboBox> comboBoxes;

    //TextFields
    private ArrayList<TextField> textFields;

    //TextFields
    private ArrayList<RadioButton> radioButtons;

    private Database calculationsDatabase;
    private Database graphDatabase;
    private Database histogramDatabase;

    public MainController(){
        instance = this;
        histogramDatabase = database;

        vBoxes = new ArrayList<>();
        buttons = new ArrayList<>();
        labels = new ArrayList<>();
        comboBoxes = new ArrayList<>();
        textFields = new ArrayList<>();
        radioButtons = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.database = new Database("Ad Auction");

        this.campaignSummaryFilter = new Filter();
        try {
            campaignSummaryFilter.setDateFrom(campaignSummaryFiltersGUIController.getDateFrom());
            campaignSummaryFilter.setDateTo(campaignSummaryFiltersGUIController.getDateTo());
            campaignSummaryFilter.setBounceType(campaignSummaryFiltersGUIController.getBounceType());
            campaignSummaryFilter.setSelectedAges(campaignSummaryFiltersGUIController.getAges());
            campaignSummaryFilter.setSelectedGenders(campaignSummaryFiltersGUIController.getGenders());
            campaignSummaryFilter.setSelectedContexts(campaignSummaryFiltersGUIController.getContexts());
            campaignSummaryFilter.setSelectedIncomes(campaignSummaryFiltersGUIController.getIncomes());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.metricsGraphFilter = new Filter();
        try {
            metricsGraphFilter.setDateFrom(metricsGraphFiltersGUIController.getDateFrom());
            metricsGraphFilter.setDateTo(metricsGraphFiltersGUIController.getDateTo());
            metricsGraphFilter.setBounceType(metricsGraphFiltersGUIController.getBounceType());
            metricsGraphFilter.setSelectedAges(metricsGraphFiltersGUIController.getAges());
            metricsGraphFilter.setSelectedGenders(metricsGraphFiltersGUIController.getGenders());
            metricsGraphFilter.setSelectedContexts(metricsGraphFiltersGUIController.getContexts());
            metricsGraphFilter.setSelectedIncomes(metricsGraphFiltersGUIController.getIncomes());
//

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }




    //needs to create a new filter each time it is called based on environment change.
    public Filter getMetricsGraphFilter() throws ParseException {

        Filter metricsGraphFilter = new Filter();
        metricsGraphFilter.setDateFrom(metricsGraphFiltersGUIController.getDateFrom());
        metricsGraphFilter.setDateTo(metricsGraphFiltersGUIController.getDateTo());
        metricsGraphFilter.setBounceType(metricsGraphFiltersGUIController.getBounceType());
        metricsGraphFilter.setSelectedAges(metricsGraphFiltersGUIController.getAges());
        metricsGraphFilter.setSelectedGenders(metricsGraphFiltersGUIController.getGenders());
        metricsGraphFilter.setSelectedContexts(metricsGraphFiltersGUIController.getContexts());
        metricsGraphFilter.setSelectedIncomes(metricsGraphFiltersGUIController.getIncomes());

        return metricsGraphFilter;
    }

    public Filter getCampaignSummaryFilter() throws ParseException {

        Filter campaignSummaryFilter= new Filter();
        campaignSummaryFilter.setDateFrom(campaignSummaryFiltersGUIController.getDateFrom());
        campaignSummaryFilter.setDateTo(campaignSummaryFiltersGUIController.getDateTo());
        campaignSummaryFilter.setBounceType(campaignSummaryFiltersGUIController.getBounceType());
        campaignSummaryFilter.setSelectedAges(campaignSummaryFiltersGUIController.getAges());
        campaignSummaryFilter.setSelectedGenders(campaignSummaryFiltersGUIController.getGenders());
        campaignSummaryFilter.setSelectedContexts(campaignSummaryFiltersGUIController.getContexts());
        campaignSummaryFilter.setSelectedIncomes(campaignSummaryFiltersGUIController.getIncomes());

        return campaignSummaryFilter;
    }


    public ArrayList<VBox> getvBoxes() {
        return vBoxes;
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public ArrayList<JFXComboBox> getComboBoxes() {
        return comboBoxes;
    }

    public ArrayList<RadioButton> getRadioButtons() { return radioButtons; }


    public ArrayList<TextField> getTextFields() {
        return textFields;
    }

    public static MainController getInstance() {
        return instance;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void setMetricsFiltersOptions(Filter filter) {
        metricsGraphFiltersGUIController.setFilters(filter);
    }

    public void enableCampaignSummaryTab(){
        campaignSummaryTab.setDisable(false);
    }

    public void enableMetricGraphsTab(){
        metricGraphsTab.setDisable(false);
    }

    public void enableHistogramTab(){
        histogramTab.setDisable(false);
    }

    public void enableGlossaryTab(){
        glossaryTab.setDisable(false);
    }

    public void enableExportTab(){
        exportTab.setDisable(false);
    }

    public void enableAppearanceTab(){
        appearanceTab.setDisable(false);
    }

    public ImportGUIController getImportGUIController() {
        return importGUIController;
    }

    public void setImportGUIController(ImportGUIController importGUIController) {
        this.importGUIController = importGUIController;
    }

    public CampaignSummaryGUIController getCampaignSummaryGUIController() {
        return campaignSummaryGUIController;
    }

    public void setCampaignSummaryGUIController(CampaignSummaryGUIController campaignSummaryGUIController) {
        this.campaignSummaryGUIController = campaignSummaryGUIController;
    }

    public void setCampaignSummaryFiltersGUIController(CampaignSummaryFiltersGUIController campaignSummaryFiltersGUIController) {
        this.campaignSummaryFiltersGUIController = campaignSummaryFiltersGUIController;
    }

    public void setMetricsGraphGUIController(MetricsGraphGUIController metricsGraphGUIController) {
        this.metricsGraphGUIController = metricsGraphGUIController;
    }

    public void setMetricsGraphFiltersGUIController(MetricsGraphFiltersGUIController metricsGraphFiltersGUIController) {
        this.metricsGraphFiltersGUIController = metricsGraphFiltersGUIController;
    }

    public void setHistogramGUIController(HistogramGUIController histogramGUIController) {
        this.histogramGUIController = histogramGUIController;
    }

    public void setExportGUIController(ExportGUIController exportGUIController) {
        this.exportGUIController = exportGUIController;
    }

    public void setAppearanceGUIController(AppearanceGUIController appearanceGUIController) {
        this.appearanceGUIController = appearanceGUIController;
    }

    public Database getCalculationsDatabase() {
        return calculationsDatabase;
    }

    public void setCalculationsDatabase(Database calculationsDatabase) {
        this.calculationsDatabase = calculationsDatabase;
    }

    public Database getGraphDatabase() {
        return graphDatabase;
    }

    public void setGraphDatabase(Database graphDatabase) {
        this.graphDatabase = graphDatabase;
    }

    public Database getHistogramDatabase() {
        return histogramDatabase;
    }

    public void setHistogramDatabase(Database histogramDatabase) {
        this.histogramDatabase = histogramDatabase;
    }

    public Tab getImportTab() {
        return importTab;
    }

    public Tab getCampaignSummaryTab() {
        return campaignSummaryTab;
    }

    public Tab getMetricGraphsTab() {
        return metricGraphsTab;
    }

    public TabPane getTabPane() {
        return tabPane;
    }


    public void setGlossaryGUIController(GlossaryGUIController glossaryGUIController) {
        this.glossaryGUIController = glossaryGUIController;
    }


    public LineChart getLineChart() {
        return metricsGraphGUIController.getLineChart();
    }

    public BarChart getBarChart() {
        return histogramGUIController.getBarChart();
    }


}
