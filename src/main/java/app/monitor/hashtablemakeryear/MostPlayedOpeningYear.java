package app.monitor.hashtablemakeryear;

import app.optimizer.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.CompletionService;

public class MostPlayedOpeningYear extends HashtableMakerYear{

    public MostPlayedOpeningYear(String year) {
        super(year, Constants.MOST_PLAYED_OPENING_GAMES, Constants.MOST_PLAYED_OPENING_GAMES_OVER_A_YEAR);
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
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + Constants.MOST_PLAYED_OPENING_GAMES_OVER_A_YEAR + "." + Constants.BINARY_EXTENSION));
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
}
