package Control;

import Model.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class FilteredCalculator{

    private Database database;

    private Filter filter;

    ArrayList<String> selectedAges;
    ArrayList<String> selectedGenders;
    ArrayList<String> selectedContexts;
    ArrayList<String> selectedIncomes;
    boolean isUnique;


    public FilteredCalculator(Database database,Filter filter){

        this.database = database;

        this.filter = new Filter();

        selectedAges = filter.getSelectedAges();
        selectedGenders = filter.getSelectedGenders();
        selectedContexts = filter.getSelectedContexts();
        selectedIncomes = filter.getSelectedIncomes();
    }

    public Database getFilteredDatabase(){
        Database filteredDatabase = new Database("Filtered " + database.getCampaignName());

        HashMap<String,Impression> idImpressionMap = new HashMap<>();

        ArrayList<Impression> filteredImpressions = new ArrayList<>();

        for(Impression i: database.getImpressions()){


            if(selectedAges.contains(i.getAgeGroup()) &&
                selectedGenders.contains(i.getGender()) &&
                    selectedContexts.contains(i.getContext()) &&
                    selectedIncomes.contains(i.getIncome())
            ) {
                filteredImpressions.add(i);
                idImpressionMap.put(i.getId(),i);
            }
            }

        ArrayList<Click> filteredClicks = new ArrayList<>();
        for(Click c: database.getClicks()){
           if(idImpressionMap.containsKey(c.getId()))
               filteredClicks.add(c);
        }

        ArrayList<ServerLog> filteredServerLogs = new ArrayList<>();
        for(ServerLog s: database.getServerLogs()){
            if(idImpressionMap.containsKey(s.getId()))
                filteredServerLogs.add(s);
        }

        filteredDatabase.setClicks(filteredClicks);
        filteredDatabase.setImpressions(filteredImpressions);
        filteredDatabase.setServerLogs(filteredServerLogs);

        return filteredDatabase;
    }

}
