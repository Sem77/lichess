package app.monitor.HashtableMakerAll;

import app.model.Game;
import app.optimizer.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class ShortestGamesAll extends HashtableMakerAll {
    public ShortestGamesAll() {
        super(Constants.SHORTEST_GAMES_OVER_A_YEAR, Constants.SHORTEST_GAMES_ALL);
    }

    public void buildHashtable() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY;
        ArrayList<File> hashtablesPaths = findHashtablesByNameInYear(new File(baseDirectory), hashtableYearName);
        Hashtable<Integer, ArrayList<String>> hashtableYear = new Hashtable<>();
        for (File hashtablePath : hashtablesPaths) {
            try {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(hashtablePath));
                Hashtable<Integer, ArrayList<String>> hashtableMonth = (Hashtable<Integer, ArrayList<String>>) o.readObject();
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
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + Constants.SHORTEST_GAMES_OVER_A_YEAR + "." + Constants.BINARY_EXTENSION));
            out.writeObject(hashtableYear);
        } catch (FileNotFoundException fnfe) {

        } catch (IOException ioe) {

        }
    }

    public void mergeHashtables(Hashtable dest, Hashtable h) {
        Hashtable<Integer, ArrayList<String>> hashtableDest = dest;
        Hashtable<Integer, ArrayList<String>> hashtableSrc = h;
        Set<Integer> keys = hashtableSrc.keySet();
        for (Integer key : keys) {
            ArrayList<String> n;
            ArrayList<String> m = hashtableSrc.get(key);

            if(hashtableDest.containsKey(key)) {
                n = hashtableDest.get(key);
                m.addAll(n);
            }
            dest.put(key, m);
        }
    }
}
