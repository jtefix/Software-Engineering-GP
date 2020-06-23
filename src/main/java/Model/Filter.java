package Model;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Filter {

    private Date dateFrom;
    private Date dateTo;

    private boolean bounceType;

    private ArrayList<String> selectedAges;
    private ArrayList<String> selectedGenders;
    private ArrayList<String> selectedContexts;
    private ArrayList<String> selectedIncomes;

    public Filter(){

        dateFrom = new Date();
        dateTo = new Date();

        bounceType = true;

        selectedAges = new ArrayList<>();
        selectedGenders = new ArrayList<>();
        selectedContexts = new ArrayList<>();
        selectedIncomes = new ArrayList<>();

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

    public boolean getBounceType() {
        return bounceType;
    }

    public void setBounceType(boolean bounceType) {
        this.bounceType = bounceType;
    }

    public ArrayList<String> getSelectedAges() {
        return selectedAges;
    }

    public void setSelectedAges(ArrayList<String> selectedAges) {
        this.selectedAges = selectedAges;
    }

    public ArrayList<String> getSelectedGenders() {
        return selectedGenders;
    }

    public void setSelectedGenders(ArrayList<String> selectedGenders) {
        this.selectedGenders = selectedGenders;
    }

    public ArrayList<String> getSelectedContexts() {
        return selectedContexts;
    }

    public void setSelectedContexts(ArrayList<String> selectedContexts) {
        this.selectedContexts = selectedContexts;
    }

    public ArrayList<String> getSelectedIncomes() {
        return selectedIncomes;
    }

    public void setSelectedIncomes(ArrayList<String> selectedIncomes) {
        this.selectedIncomes = selectedIncomes;
    }

}
