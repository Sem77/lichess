package app.monitor.hashtablemaker.byyear;

import app.monitor.hashtablemaker.HashtableFinderByYearInterface;
import app.monitor.hashtablemaker.HashtableMergerInterface;
import app.constant.Constants;

import java.io.*;
import java.util.*;

public class ShortestGamesYear implements HashtableMergerInterface, HashtableFinderByYearInterface {
    public String year;
    public String hashtableMonthName;
    public String hashTableYearName;
    public String baseDirectory;

    public ShortestGamesYear(String year) {
        this.year = year;
        this.hashtableMonthName = Constants.SHORTEST_GAMES;
        this.hashTableYearName = Constants.SHORTEST_GAMES_OVER_A_YEAR;
        baseDirectory = Constants.GAMES_DATA_DIRECTORY + File.separator + year;
    }


    public void buildHashtable() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY + File.separator + year;
        ArrayList<File> hashtablesPaths = findHashtablesByNameInMonth(new File(baseDirectory), hashtableMonthName);
        Hashtable<Integer, TreeSet<String>> hashtableYear = new Hashtable<>();
        for (File hashtablePath : hashtablesPaths) {
            try {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(hashtablePath));
                Hashtable<Integer, TreeSet<String>> hashtableMonth = (Hashtable<Integer, TreeSet<String>>) o.readObject();
                mergeHashtables(hashtableYear, hashtableMonth);
                o.close();
            } catch (FileNotFoundException fnfe) {
                System.out.println("La table de hashage " + hashtableMonthName + " n'a pas été trouvée");
            } catch (ClassNotFoundException cnfe) {
                System.out.println("La table de hashage " + hashtableMonthName + " n'a pas pu être chargée");
            } catch (IOException ioe) {
                System.out.println("Erreur lors du chargement");
            }
        }

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + hashTableYearName + "." + Constants.BINARY_EXTENSION));
            out.writeObject(hashtableYear);
            out.close();
        } catch (FileNotFoundException fnfe) {

        } catch (IOException ioe) {

        }
    }

    public void mergeHashtables(Hashtable dest, Hashtable h) {
        Hashtable<Integer, TreeSet<String>> hashtableDest = dest;
        Hashtable<Integer, TreeSet<String>> hashtableSrc = h;
        Set<Integer> keys = hashtableSrc.keySet();
        for (Integer key : keys) {
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
