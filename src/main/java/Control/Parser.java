package Control;

import Model.Click;
import Model.Database;
import Model.Impression;
import Model.ServerLog;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.IllegalFormatException;

public class Parser {

//    final String fileLocation = "src\\main\\resources\\";


    private static String COMMA_DELIMITER = ",";

    private SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public BufferedReader parseFile(String filePath) throws Exception {

       BufferedReader bufferedReader = null;
            File file = new File(filePath);
            if(!file.getName().endsWith(".csv"))
                throw new Exception("Wrong File Format Exception");

            bufferedReader = new BufferedReader(new FileReader(filePath));

        return bufferedReader;
    }

    public ArrayList<Click> parseClicks(String filePath) throws Exception {

        BufferedReader bufferedReader = parseFile(filePath);

        ArrayList<Click> parsedClicks = new ArrayList<Click>();
        try {
            bufferedReader.readLine();
            String line;

                while((line = bufferedReader.readLine()) != null) {
                    String[] values = line.split(COMMA_DELIMITER);

                    Click newClick = new Click(values[0],values[1],Double.valueOf(values[2]));
                    parsedClicks.add(newClick);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        return parsedClicks;
    }

    public ArrayList<Impression> parseImpressions(String filePath) throws Exception {

        BufferedReader bufferedReader = parseFile(filePath);

        ArrayList<Impression> parsedImpressions = new ArrayList<Impression>();

        String line;

            try {
                bufferedReader.readLine();
                while((line = bufferedReader.readLine()) != null) {
                    String[] values = line.split(COMMA_DELIMITER);

                    Impression newImpression =
                            new Impression(values[0],(values[1]),values[2],values[3],values[4],values[5],values[6]);
                    parsedImpressions.add(newImpression);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        return parsedImpressions;
    }

    public ArrayList<ServerLog> parseServerLogs(String filePath) throws Exception {

        BufferedReader bufferedReader = parseFile(filePath);

        ArrayList<ServerLog> parsedServerLogs = new ArrayList<ServerLog>();

        String line;

            try {
                bufferedReader.readLine();
                while((line = bufferedReader.readLine()) != null) {
                    String[] values = line.split(COMMA_DELIMITER);

                    boolean conversionCheck = false;

                    if(values[4].equals("No"))
                        conversionCheck = false;
                    else
                        conversionCheck = true;

                    ServerLog newServerLog = null;
                    if(( values[2].equals("n/a")))
                        newServerLog = new ServerLog(values[0],values[1],Integer.valueOf(values[3]),conversionCheck);
                    else
                        newServerLog  =
                            new ServerLog(values[0],values[1],values[2],Integer.valueOf(values[3]),conversionCheck);


                    parsedServerLogs.add(newServerLog);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return parsedServerLogs;
    }

}
