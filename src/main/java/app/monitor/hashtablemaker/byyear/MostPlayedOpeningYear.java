package app.monitor.hashtablemaker.byyear;

import app.model.OccurrenceString;
import app.monitor.hashtablemaker.HashtableFinderByYearInterface;
import app.monitor.hashtablemaker.HashtableMergerInterface;
import app.constants.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Set;

public class MostPlayedOpeningYear implements HashtableMergerInterface, HashtableFinderByYearInterface {
    public String year;
    public String hashtableMonthName;
    public String hashTableYearName;
    public String baseDirectory;

    public MostPlayedOpeningYear(String year) {
        this.year = year;
        this.hashtableMonthName = Constants.MOST_PLAYED_OPENING_GAMES;
        this.hashTableYearName = Constants.MOST_PLAYED_OPENING_GAMES_OVER_A_YEAR;
        baseDirectory = Constants.GAMES_DATA_DIRECTORY + File.separator + year;
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
                System.out.println("La table de hashage " + hashtableMonthName + " n'a pas été trouvée");
            } catch (ClassNotFoundException cnfe) {
                System.out.println("La table de hashage " + hashtableMonthName + " n'a pas pu être chargée");
            } catch (IOException ioe) {
                System.out.println("Erreur lors du chargement");
            }
        }

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + Constants.MOST_PLAYED_OPENING_GAMES_OVER_A_YEAR + "." + Constants.BINARY_EXTENSION));
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
     * Ordonne dans un fichier les ouvertures les plus jouées sur une certaine année avec le nombre de nombre de fois qu'elles ont été jouées
     * rangé par ordre décroissant
     * La méthode statique de Collections est utilisée pour trier
     */
    public void saveNMostPlayedOpening() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY + File.separator + year;
        File gamesDataDirectory = new File(baseDirectory);
        File hashtablePath = new File(gamesDataDirectory + File.separator + Constants.MOST_PLAYED_OPENING_GAMES_OVER_A_YEAR + "." + Constants.BINARY_EXTENSION);

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(hashtablePath));
            Hashtable<String, Integer> hashtable = (Hashtable<String, Integer>) ois.readObject();
            ois.close();

            ArrayList<OccurrenceString> occurrenceStringArrayList = OccurrenceString.hashtableToOccurrenceString(hashtable);
            Collections.sort(occurrenceStringArrayList);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + Constants.ORDER_MOST_PLAYED_OPENING_GAMES_OVER_A_YEAR + "." + Constants.BINARY_EXTENSION));

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
