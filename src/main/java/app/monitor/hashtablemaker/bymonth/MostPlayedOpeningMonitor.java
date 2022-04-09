package app.monitor.hashtablemaker.bymonth;

import app.model.OccurrenceString;
import app.constant.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class MostPlayedOpeningMonitor extends HashtableMaker{

    public MostPlayedOpeningMonitor(String month, String year) {
        super(month, year, "app.threadworker.MostPlayedOpeningWriterThread", new Hashtable<String, Integer>(), Constants.MOST_PLAYED_OPENING_GAMES);
    }

    public void saveNMostPlayedOpening() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY + File.separator + year + File.separator + month + File.separator + "hashtables";
        File gamesDataDirectory = new File(baseDirectory);
        File hashtablePath = new File(gamesDataDirectory + File.separator + Constants.MOST_PLAYED_OPENING_GAMES + "." + Constants.BINARY_EXTENSION); // list of the path of all hashtables

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(hashtablePath));
            Hashtable<String, Integer> hashtable = (Hashtable<String, Integer>) ois.readObject();
            ois.close();

            ArrayList<OccurrenceString> occurrenceStringArrayList = OccurrenceString.hashtableToOccurrenceString(hashtable);
            Collections.sort(occurrenceStringArrayList);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + Constants.ORDER_MOST_PLAYED_OPENING_GAMES_OVER_A_MONTH + "." + Constants.BINARY_EXTENSION));

            for(OccurrenceString occurrenceString : occurrenceStringArrayList) {
                oos.writeObject(occurrenceString);
            }
            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
