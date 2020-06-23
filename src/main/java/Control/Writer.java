package Control;

import Control.JavaFX.MainController;
import Model.Click;
import Model.Database;
import Model.Impression;
import Model.ServerLog;

import javax.xml.crypto.Data;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    private BufferedWriter bufferedWriter;

    public Writer() throws IOException {

    }

    public void writeCalculation(String exportPath) throws IOException {
        Database calculationsDatabase = MainController.getInstance().getCalculationsDatabase();

        exportCSVs(exportPath,"calculation",calculationsDatabase);
    }

    public void writeGraph(String exportPath) throws IOException {
        Database graphDatabase = MainController.getInstance().getGraphDatabase();

        exportCSVs(exportPath,"graph",graphDatabase);
    }


    private void exportCSVs(String exportPath,String fileName, Database database) throws IOException {

        File clickCSV = new File(exportPath+"\\"+fileName+"_click_log.csv");
        File impressionCSV = new File(exportPath +"\\"+fileName+ "_impression_log.csv");
        File serverLogCSV = new File(exportPath +"\\"+fileName+ "_server_log.csv");

        if (!clickCSV.exists()) {
            clickCSV.createNewFile();
        }
        if (!impressionCSV.exists()) {
            clickCSV.createNewFile();
        }
        if (!serverLogCSV.exists()) {
            clickCSV.createNewFile();
        }

        bufferedWriter = new BufferedWriter(new FileWriter(clickCSV));
        String calculationsClickOutput = getClickOutput(database);
        bufferedWriter.write(calculationsClickOutput);
        bufferedWriter.close();

        if(!fileName.equals("histogram")){
            bufferedWriter = new BufferedWriter(new FileWriter(impressionCSV));
            String impressionOutput = getImpressionOutput(database);
            bufferedWriter.write(impressionOutput);
            bufferedWriter.close();

            bufferedWriter = new BufferedWriter(new FileWriter(serverLogCSV));
            String serverOutput = getServerOutput(database);
            bufferedWriter.write(serverOutput);
            bufferedWriter.close();
        }
    }

    private String getClickOutput(Database database) {
        String output = "Date,ID,Click Cost\n";

        for(Click c: database.getClicks()){
            output+=c.getDate()+","+c.getId()+","+c.getCost()+"\n";
        }

        return output;
    }


    private String getImpressionOutput(Database database) {
        String output = "Date,ID,Gender,Age,Income,Context,Impression Cost\n";

        for(Impression i: database.getImpressions()){
            output+=i.getDate()+","+i.getId()+","+i.getGender()+","+
                    i.getAgeGroup()+","+i.getIncome()+","+i.getContext()+
                            ","+i.getCost()+"\n";
        }

        return output;
    }


    private String getServerOutput(Database database) {
        String output = "Date,ID,Click Cost\n";

        for(ServerLog s: database.getServerLogs()){
            String conversion = "";
            if(s.isConversion())
                conversion = "Yes";
            else
                conversion = "No";

            output+=s.getEntryDate()+","+s.getId()+","+s.getExitDate()+"," +
                    s.getPagesViewed()+","+conversion+"\n";
        }

        return output;
    }

}
