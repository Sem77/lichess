package app.monitor.hashtablemaker.byyear;

import app.monitor.hashtablemaker.HashtableFinderByYearInterface;
import app.monitor.hashtablemaker.HashtableMergerInterface;
import app.constants.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;

public class AssociationPlayerGamesYear implements HashtableMergerInterface, HashtableFinderByYearInterface {
    public String year;
    public String hashtableMonthName;
    public String hashTableYearName;
    public String baseDirectory;

    public AssociationPlayerGamesYear(String year) {
        this.year = year;
        this.hashtableMonthName = Constants.A_PLAYER_GAME;
        this.hashTableYearName = Constants.A_PLAYER_GAME_OVER_A_YEAR;
        baseDirectory = Constants.GAMES_DATA_DIRECTORY + File.separator + year;
    }


    public void buildHashtable() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY + File.separator + year;
        ArrayList<File> hashtablesPaths = findHashtablesByNameInMonth(new File(baseDirectory), hashtableMonthName);
        Hashtable<String, TreeSet<String>> hashtableYear = new Hashtable<>(); // table de hashage qui va contenir la fusion de toutes les autres tables de hashage
        for (File hashtablePath : hashtablesPaths) {
            try {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(hashtablePath));
                Hashtable<String, TreeSet<String>> hashtableMonth = (Hashtable<String, TreeSet<String>>) o.readObject();
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
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + Constants.A_PLAYER_GAME_OVER_A_YEAR + "." + Constants.BINARY_EXTENSION));
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
