package Control.JavaFX;

import Model.TableData;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GlossaryGUIController implements Initializable {

    private GlossaryGUIController instance;

    private ArrayList<TableData> termDefinitions;

    @FXML
    private VBox glossaryVBox;

    @FXML
    private TableView<TableData> tableview;

    @FXML
    private TableColumn term;

    @FXML
    private TableColumn definition;

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXButton clearButton;

    public GlossaryGUIController(){

        instance = this;

        termDefinitions = new ArrayList<>();

        termDefinitions.add(new TableData("Acquisition", "A conversion, or acquisition, occurs when a user clicks and then acts on an ad. The  " +
                "specific definition of an action depends on the campaign (e.g., buying a product, registering as a  " +
                "new customer or joining a mailing list)."));
        termDefinitions.add(new TableData("Bounce", "A user clicks on an ad, but then fails to interact with the website (typically detected  " +
                "when a user navigates away from the website after a short time, or when only a single page has  " +
                "been viewed)"));
        termDefinitions.add(new TableData("Bounce Rate", "The average number of bounces per click"));
        termDefinitions.add(new TableData("Campaign", "An effort by the marketing agency to gain exposure for a client’s website by  " +
                "participating in a range of ad auctions offered by different providers and networks. Bid amounts,  " +
                "keywords and other variables will be tailored to the client’s needs."));
        termDefinitions.add(new TableData("Click", "A click occurs when a user clicks on an ad that is shown to them"));
        termDefinitions.add(new TableData("Click Cost", "The cost of a particular click (usually determined through an auction process)."));
        termDefinitions.add(new TableData("Click-Through-Rate", "The average number of clicks per impression."));
        termDefinitions.add(new TableData("Conversion", "A conversion, or acquisition, occurs when a user clicks and then acts on an ad. The  " +
                "specific definition of an action depends on the campaign (e.g., buying a product, registering as a  " +
                "new customer or joining a mailing list)."));
        termDefinitions.add(new TableData("Conversion Rate", "The average number of conversions per click"));
        termDefinitions.add(new TableData("Cost-Per-Acquisition (CPA)", " The average amount of money spent on an advertising campaign  " +
                "for each acquisition (i.e., conversion)."));
        termDefinitions.add(new TableData("Cost-Per-Click (CPC)", "The average amount of money spent on an advertising campaign for each  " +
                "click."));
        termDefinitions.add(new TableData("Cost-Per-Thousand-Impressions (CPM)", "The average amount of money spent on an advertising  " +
                "campaign for every one thousand impressions."));
        termDefinitions.add(new TableData("Impression", "An impression occurs whenever an ad is shown to a user, regardless of whether  " +
                "they click on it."));
        termDefinitions.add(new TableData("Uniques", "The number of unique users that click on an ad during the course of a campaign"));
        termDefinitions.add(new TableData("Date", "Date and time of the click on the ad. Format:  " +
                "2015-01-20 16:12:47"));
        termDefinitions.add(new TableData("ID", "An ID to uniquely identify a particular user."));
        termDefinitions.add(new TableData("Gender", "Gender of user: Male / Female"));
        termDefinitions.add(new TableData("Age", "Age group of user: <25 / 25-34 / 35-44 / 45- " +
                "54 / >54"));
        termDefinitions.add(new TableData("Income", "Income of user: Low / Medium / High"));
        termDefinitions.add(new TableData("Context", "Context of ad: News / Shopping / Social  " +
                "Media / Blog / Hobbies / Travel"));
        termDefinitions.add(new TableData("Impression Cost", "Cost of this impression (in pence)."));
        termDefinitions.add(new TableData("Entry Date", "Date and time of arriving on the website.  " +
                "Format: 2015-01-20 16:12:47"));
        termDefinitions.add(new TableData("Exit Date", "Date and time of navigating away from the  " +
                "website. Format: 2015-01-20 16:29:30 " +
                "May be “n/a”"));
        termDefinitions.add(new TableData("Pages Viewed", "Number of pages user viewed during visit."));


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        MainController.getInstance().setGlossaryGUIController(this);
        MainController.getInstance().getvBoxes().add(glossaryVBox);
        MainController.getInstance().getTextFields().add(searchField);
        MainController.getInstance().getButtons().add(clearButton);


        term = new TableColumn("Term");
        term.setCellValueFactory(new PropertyValueFactory<>("term"));
        definition = new TableColumn("Definition");
        definition.setCellValueFactory(new PropertyValueFactory<>("definition"));

        tableview.getColumns().add(term);
        tableview.getColumns().add(definition);

        fillTable(termDefinitions);



    }

    private void fillTable(ArrayList<TableData> tableData){

        tableview.getItems().clear();


        for (TableData tb : tableData){
            tableview.getItems().add(tb);
        }

    }

    public void searchEvent(KeyEvent keyEvent) {

        ArrayList<TableData> searchMatches =  new ArrayList<>();

        for(TableData tb: termDefinitions){
            if(tb.getTerm().toLowerCase().contains(searchField.getText().toLowerCase()))
                searchMatches.add(tb);
        }

        fillTable(searchMatches);
    }



    public void clearSearch(ActionEvent actionEvent) {

        searchField.setText("");
        fillTable(termDefinitions);

    }
}
