package Model;

import java.util.ArrayList;
import java.util.Calendar;

public class Database {

    private String campaignName;

    private ArrayList<Click> clicks;
    private ArrayList<Impression> impressions;
    private ArrayList<ServerLog> serverLogs;

    public Database(String campaignName) {
        this.campaignName = campaignName;

        this.clicks = new ArrayList<Click>();
        this.impressions = new ArrayList<Impression>();
        this.serverLogs = new ArrayList<ServerLog>();
    }


    public boolean addClick(Click click){

        for(Click c: clicks)
            if(click.equals(c)){
                System.out.println("Click already within array");
                return false;
            }

        return true;
    }

    public boolean addImpression(Impression impression){

        for(Impression i: impressions)
            if(impression.equals(i)){
                System.out.println("Impression already within array");
                return false;
            }

        return true;
    }

    public boolean addServerLog(ServerLog serverLog){

        for(ServerLog s: serverLogs)
            if(serverLog.equals(s)){
                System.out.println("ServerLog already within array");
                return false;
            }

        return true;
    }

    public ArrayList<Click> getClicks() {
        return clicks;
    }

    public void setClicks(ArrayList<Click> clicks) {
        this.clicks = clicks;
    }

    public ArrayList<Impression> getImpressions() {
        return impressions;
    }

    public void setImpressions(ArrayList<Impression> impressions) {
        this.impressions = impressions;
    }

    public ArrayList<ServerLog> getServerLogs() {
        return serverLogs;
    }

    public void setServerLogs(ArrayList<ServerLog> serverLogs) {
        this.serverLogs = serverLogs;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }
}
