package Control.JavaFX.FilterGUIControllers;

import Control.JavaFX.MainController;
import javafx.fxml.Initializable;

public class CampaignSummaryFiltersGUIController extends FiltersGUIController implements Initializable {

    public CampaignSummaryFiltersGUIController(){
        super();
        MainController.getInstance().setCampaignSummaryFiltersGUIController(this);
    }



}
