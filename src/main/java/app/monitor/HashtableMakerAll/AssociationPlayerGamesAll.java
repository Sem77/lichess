package app.monitor.HashtableMakerAll;

import app.optimizer.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class AssociationPlayerGamesAll extends HashtableMakerAll {
    public AssociationPlayerGamesAll() {
        super(Constants.A_PLAYER_GAME_OVER_A_YEAR, Constants.A_PLAYER_GAME_ALL);
    }

    public void buildHashtable() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY;
        ArrayList<File> hashtablesPaths = findHashtablesByNameInYear(new File(baseDirectory), hashtableYearName);
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
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + Constants.A_PLAYER_GAME_OVER_A_YEAR + "." + Constants.BINARY_EXTENSION));
            out.writeObject(hashtableYear);
        } catch (FileNotFoundException fnfe) {

        } catch (IOException ioe) {

        }
    }


    public void mergeHashtables(Hashtable dest, Hashtable h) {
        Hashtable<String, ArrayList<String>> hashtableDest = dest;
        Hashtable<String, ArrayList<String>> hashtableSrc = h;
        Set<String> keys = hashtableSrc.keySet();
        for (String key : keys) {
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
