package Control;

import Model.Click;
import Model.Database;
import Model.Impression;
import Model.ServerLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PeriodicCalculator{

    private Database database;

    private Date dateFrom;
    private Date dateTo;

    private SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public PeriodicCalculator(Database database){

        this.database = database;

        this.dateFrom = new Date();
        this.dateTo = new Date();
    }

    public ArrayList<Click> getClicksWithinPeriod(Date startDate, Date endDate){


        ArrayList<Click> clicksWithinPeriod = new ArrayList<>();

        if(startDate.after(endDate))
            return clicksWithinPeriod;

        for(Click c: database.getClicks()){
            if(c.getDate().after(startDate) && c.getDate().before(endDate))
                clicksWithinPeriod.add(c);

        }

        return clicksWithinPeriod;
    }

    public ArrayList<Impression> getImpressionsWithinPeriod(Date startDate, Date endDate){



       ArrayList<Impression> impressionsWithinPeriod = new ArrayList<>();


        if(startDate.after(endDate))
            return impressionsWithinPeriod;

       for(Impression i: database.getImpressions()){
           if(i.getDate().after(startDate) && i.getDate().before(endDate))
               impressionsWithinPeriod.add(i);

       }

       return impressionsWithinPeriod;
    }

    public ArrayList<ServerLog> getServerLogsWithinPeriod(Date startDate, Date endDate){

        ArrayList<ServerLog> serverLogsWithinPeriod = new ArrayList<>();

        if(startDate.after(endDate))
            return serverLogsWithinPeriod;

        for(ServerLog s: database.getServerLogs()){
            if(s.getExitDate()!=null)
                if(s.getEntryDate().after(startDate) && s.getExitDate().before(endDate))
                    serverLogsWithinPeriod.add(s);
        }

        return serverLogsWithinPeriod;
    }

    public Database getDatabaseWithinPeriod(Date dateFrom, Date dateTo){

        Database databaseWithinPeriod = new Database(this.database.getCampaignName()+" Period " + dateFrom + " To " + dateTo);
        databaseWithinPeriod.setClicks(getClicksWithinPeriod(dateFrom, dateTo));
        databaseWithinPeriod.setImpressions(getImpressionsWithinPeriod(dateFrom, dateTo));
        databaseWithinPeriod.setServerLogs(getServerLogsWithinPeriod(dateFrom, dateTo));

        return databaseWithinPeriod;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}
