package app.monitor.hashtablemaker.all;

import app.monitor.hashtablemaker.HashtableFinderAllInterface;
import app.monitor.hashtablemaker.HashtableMergerInterface;
import app.constant.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;

public class AssociationPlayerGamesAll implements HashtableMergerInterface, HashtableFinderAllInterface {
    public String hashtableYearName;
    public String hashTableNameAll;
    public String baseDirectory;

    public AssociationPlayerGamesAll() {
        this.hashtableYearName = Constants.A_PLAYER_GAME_OVER_A_YEAR;
        this.hashTableNameAll = Constants.A_PLAYER_GAME_ALL;
        baseDirectory = Constants.GAMES_DATA_DIRECTORY;
    }

    public void buildHashtable() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY;
        ArrayList<File> hashtablesPaths = findHashtablesByNameInYear(new File(baseDirectory), hashtableYearName);
        Hashtable<String, TreeSet<String>> hashtableYear = new Hashtable<>();
        for (File hashtablePath : hashtablesPaths) {
            try {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(hashtablePath));
                Hashtable<String, TreeSet<String>> hashtableMonth = (Hashtable<String, TreeSet<String>>) o.readObject();
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
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + hashTableNameAll + "." + Constants.BINARY_EXTENSION));
            out.writeObject(hashtableYear);
            out.close();
        } catch (FileNotFoundException fnfe) {

        } catch (IOException ioe) {

        }
    }


    public void mergeHashtables(Hashtable dest, Hashtable h) {
        Hashtable<String, TreeSet<String>> hashtableDest = dest;
        Hashtable<String, TreeSet<String>> hashtableSrc = h;
        Set<String> keys = hashtableSrc.keySet();
        for (String key : keys) {
            TreeSet<String> n;
            TreeSet<String> m = hashtableSrc.get(key);

            if(hashtableDest.containsKey(key)) {
                n = hashtableDest.get(key);
                m.addAll(n);
            }
            dest.put(key, m);
        }
    }
}
