package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.StreamHandler;

public class ServerLog {

    private SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Date entryDate;
    private String id;
    private Date exitDate;
    private int pagesViewed;
    private boolean conversion;


    public ServerLog(String entryDate, String id, String exitDate, int pagesViewed, boolean conversion){


        try {
            this.entryDate = formatter.parse(entryDate);
            this.exitDate = formatter.parse(exitDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.id = id;

        this.pagesViewed = pagesViewed;
        this.conversion = conversion;
    }

    public ServerLog(String entryDate,String id, Integer pagesViewed, boolean conversion) {

        this.id = id;

        try {
            this.entryDate = formatter.parse(entryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.pagesViewed = pagesViewed;
        this.conversion = conversion;

    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public int getPagesViewed() {
        return pagesViewed;
    }

    public void setPagesViewed(int pagesViewed) {
        this.pagesViewed = pagesViewed;
    }

    public boolean isConversion() {
        return conversion;
    }

    public void setConversion(boolean conversion) {
        this.conversion = conversion;
    }
}
