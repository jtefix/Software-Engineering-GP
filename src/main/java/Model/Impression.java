package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Impression {

    private SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Date date;
    private String id;
    // 0 for male, 1 for female
    private String gender;

    //0 for <25, 1 for 25-34, 2 for 25-54, 3 for >54
    private String ageGroup;

    //0 for low, 1 for medium, 2 for high
    private String income;

    //0 for Blog, 1 for News, 2 for Shopping, 3 for Social Media
    private String context;

    private String cost;

    public Impression(String date, String id, String gender, String ageGroup, String income, String context, String cost){

        try {
            this.date = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.id = id;
        this.gender = gender;
        this.ageGroup = ageGroup;
        this.income = income;
        this.context = context;
        this.cost = cost;

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
