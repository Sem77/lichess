package app.monitor.hashtablemakeryear;

import app.model.OccurrenceString;
import app.optimizer.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Set;

public class MostActivePlayerYear extends HashtableMakerYear {
    public MostActivePlayerYear(String year) {
        super(year, Constants.MOST_ACTIVE_PLAYERS, Constants.MOST_ACTIVE_PLAYERS_OVER_A_YEAR);
    }

    public void buildHashtable() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY + File.separator + year;
        ArrayList<File> hashtablesPaths = findHashtablesByNameInMonth(new File(baseDirectory), hashtableMonthName);
        Hashtable<String, Integer> hashtableYear = new Hashtable<>();
        for (File hashtablePath : hashtablesPaths) {
            try {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(hashtablePath));
                Hashtable<String, Integer> hashtableMonth = (Hashtable<String, Integer>) o.readObject();
                mergeHashtables(hashtableYear, hashtableMonth);
                o.close();
            } catch (FileNotFoundException fnfe) {
                System.out.println("An hashtable wasn't found");
            } catch (ClassNotFoundException cnfe) {
                System.out.println("Could not load an hashtable");
            } catch (IOException ioe) {
                System.out.println("There was an error with an hashtable");
            }
        }

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + Constants.MOST_ACTIVE_PLAYERS_OVER_A_YEAR + "." + Constants.BINARY_EXTENSION));
            out.writeObject(hashtableYear);
        } catch (FileNotFoundException fnfe) {

        } catch (IOException ioe) {

        }
    }

    public void mergeHashtables(Hashtable dest, Hashtable h) {
        Hashtable<String, Integer> hashtableDest = dest;
        Hashtable<String, Integer> hashtableSrc = h;
        Set<String> keys = hashtableSrc.keySet();
        for (String key : keys) {
            Integer n;
            Integer m = hashtableSrc.get(key);

            if(hashtableDest.containsKey(key)) {
                n = hashtableDest.get(key);
                m += n;
            }
            dest.put(key, m);
        }
    }

    public void saveNMostActivePlayers() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY + File.separator + year;
        File gamesDataDirectory = new File(baseDirectory);
        File hashtablePath = new File(gamesDataDirectory + File.separator + Constants.MOST_ACTIVE_PLAYERS_OVER_A_YEAR + "." + Constants.BINARY_EXTENSION);

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(hashtablePath));
            Hashtable<String, Integer> hashtable = (Hashtable<String, Integer>) ois.readObject();
            ois.close();

            ArrayList<OccurrenceString> occurrenceStringArrayList = OccurrenceString.hashtableToOccurrenceString(hashtable);
            Collections.sort(occurrenceStringArrayList);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + Constants.ORDER_MOST_ACTIVE_PLAYERS_OVER_A_YEAR + "." + Constants.BINARY_EXTENSION));

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
