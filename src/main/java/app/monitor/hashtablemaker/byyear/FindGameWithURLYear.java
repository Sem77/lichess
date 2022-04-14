package app.monitor.hashtablemaker.byyear;

import app.constant.Constants;
import app.monitor.hashtablemaker.HashtableFinderByYearInterface;
import app.monitor.hashtablemaker.HashtableMergerInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class FindGameWithURLYear implements HashtableFinderByYearInterface, HashtableMergerInterface {
    public String year;
    public String hashtableMonthName;
    public String hashTableYearName;
    public String baseDirectory;

    public FindGameWithURLYear(String year) {
        this.year = year;
        this.hashtableMonthName = Constants.GAME_LINK;
        this.hashTableYearName = Constants.GAME_LINK_OVER_A_YEAR;
        baseDirectory = Constants.GAMES_DATA_DIRECTORY + File.separator + year;
    }

    public void buildHashtable() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY + File.separator + year;
        ArrayList<File> hashtablesPaths = findHashtablesByNameInMonth(new File(baseDirectory), hashtableMonthName);
        Hashtable<String, String> hashtableYear = new Hashtable<>();
        for (File hashtablePath : hashtablesPaths) {
            try {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(hashtablePath));
                Hashtable<String, String> hashtableMonth = (Hashtable<String, String>) o.readObject();
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
        Hashtable<String, String> hashtableDest = dest;
        Hashtable<String, String> hashtableSrc = h;
        Set<String> keys = hashtableSrc.keySet();
        for (String key : keys) {
            hashtableDest.put(key, hashtableSrc.get(key));
        }
    }
}
