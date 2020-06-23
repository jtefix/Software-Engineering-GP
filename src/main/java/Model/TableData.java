package Model;

public class TableData{
    String term;
    String definition;

    public TableData(String term,String definition){
        this.term = term;
        this.definition = definition;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }

}