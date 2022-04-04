package app.monitor.hashtablemaker.all;

import app.model.OccurrenceString;
import app.monitor.hashtablemaker.HashtableFinderAllInterface;
import app.monitor.hashtablemaker.HashtableMergerInterface;
import app.constants.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Set;

public class MostPlayedOpeningAll implements HashtableMergerInterface, HashtableFinderAllInterface {
    public String hashtableYearName;
    public String hashTableNameAll;
    public String baseDirectory;

    public MostPlayedOpeningAll() {
        this.hashtableYearName = Constants.MOST_PLAYED_OPENING_GAMES_OVER_A_YEAR;
        this.hashTableNameAll = Constants.MOST_PLAYED_OPENING_GAMES_ALL;
        baseDirectory = Constants.GAMES_DATA_DIRECTORY;
    }


    public void buildHashtable() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY;
        ArrayList<File> hashtablesPaths = findHashtablesByNameInYear(new File(baseDirectory), hashtableYearName);
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
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + Constants.MOST_PLAYED_OPENING_GAMES_ALL + "." + Constants.BINARY_EXTENSION));
            out.writeObject(hashtableYear);
            out.close();
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

    /**
     * Ordonne dans un fichier les ouvertures les plus jouées sur une toutes les années avec le nombre de nombre de fois qu'elles ont été jouées
     * rangé par ordre décroissant
     * La méthode statique de Collections est utilisée pour trier
     */
    public void saveNMostPlayedOpening() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY;
        File gamesDataDirectory = new File(Constants.GAMES_DATA_DIRECTORY);
        File hashtablePath = new File(gamesDataDirectory + File.separator + Constants.MOST_PLAYED_OPENING_GAMES_ALL + "." + Constants.BINARY_EXTENSION); // list of the path of all hashtables

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(hashtablePath));
            Hashtable<String, Integer> hashtable = (Hashtable<String, Integer>) ois.readObject();
            ois.close();

            ArrayList<OccurrenceString> occurrenceStringArrayList = OccurrenceString.hashtableToOccurrenceString(hashtable);
            Collections.sort(occurrenceStringArrayList);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + Constants.ORDER_MOST_PLAYED_OPENING_GAMES_ALL + "." + Constants.BINARY_EXTENSION));

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
