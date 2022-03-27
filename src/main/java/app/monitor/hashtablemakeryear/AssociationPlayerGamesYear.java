package app.monitor.hashtablemakeryear;

import app.optimizer.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class AssociationPlayerGamesYear extends HashtableMakerYear {

    public AssociationPlayerGamesYear(String year) {
        super(year, Constants.A_PLAYER_GAME, Constants.A_PLAYER_GAME_OVER_A_YEAR);
    }

    public void buildHashtable(String year) {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY + File.separator + year;
        ArrayList<File> hashtablesPaths = findHashtablesByNameInMonth(new File(baseDirectory), hashTableName);
        Hashtable<String, ArrayList<String>> hashtableYear = new Hashtable<>();
        for (File hashtablePath : hashtablesPaths) {
            try {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(hashtablePath));
                Hashtable<String, ArrayList<String>> hashtableMonth = (Hashtable<String, ArrayList<String>>) o.readObject();
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
        Hashtable<String, ArrayList<String>> hastableDest = dest;
        Hashtable<String, ArrayList<String>> hashtableSrc = h;
        Set<String> keys = h.keySet();
        for (String key : keys) {
            ArrayList<String> n = hastableDest.get(key);
            ArrayList<String> m = hashtableSrc.get(key);
            if(n != null)
                dest.put(key, n.addAll(m));
            else
                dest.put(key, m);
        }
    }
}
