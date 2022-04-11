package app.monitor.hashtablemaker.all;

import app.monitor.hashtablemaker.HashtableFinderAllInterface;
import app.monitor.hashtablemaker.HashtableMergerInterface;
import app.constant.Constants;

import java.io.*;
import java.util.*;

public class ShortestGamesAll implements HashtableMergerInterface, HashtableFinderAllInterface {
    public String hashtableYearName;
    public String hashTableNameAll;
    public String baseDirectory;

    public ShortestGamesAll() {
        this.hashtableYearName = Constants.SHORTEST_GAMES_OVER_A_YEAR;
        this.hashTableNameAll = Constants.SHORTEST_GAMES_ALL;
        baseDirectory = Constants.GAMES_DATA_DIRECTORY;
    }


    public void buildHashtable() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY;
        ArrayList<File> hashtablesPaths = findHashtablesByNameInYear(new File(baseDirectory), hashtableYearName);
        Hashtable<Integer, TreeSet<String>> hashtableYear = new Hashtable<>();
        for (File hashtablePath : hashtablesPaths) {
            try {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(hashtablePath));
                Hashtable<Integer, TreeSet<String>> hashtableMonth = (Hashtable<Integer, TreeSet<String>>) o.readObject();
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
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + Constants.SHORTEST_GAMES_ALL + "." + Constants.BINARY_EXTENSION));
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

    public void orderShortestGames() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY;
        File gamesDataDirectory = new File(Constants.GAMES_DATA_DIRECTORY);
        File hashtablePath = new File(gamesDataDirectory + File.separator + hashTableNameAll + "." + Constants.BINARY_EXTENSION);

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(hashtablePath));
            Hashtable<Integer, TreeSet<String>> hashtable = (Hashtable<Integer, TreeSet<String>>) ois.readObject();
            ois.close();

            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + Constants.ORDER_SHORTEST_GAMES + "." + Constants.BINARY_EXTENSION));
            shortestGames(hashtable, o);

            o.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeGamesWithNStrokes(Hashtable<Integer, TreeSet<String>> hashtable, int nStrokes, ObjectOutputStream out) throws IOException, ClassNotFoundException {

        TreeSet<String> gamesURLS = hashtable.get(nStrokes);

        if(gamesURLS != null) {
            for (String gameURL : gamesURLS) {
                try {
                    out.writeObject(gameURL);
                } catch(EOFException eofe) {}
            }
        }
    }

    private static void shortestGames(Hashtable<Integer, TreeSet<String>> hashtable, ObjectOutputStream out) {
        TreeSet<Integer> keys = new TreeSet<>(hashtable.keySet());
        for(Integer i : keys) {
            try {
                writeGamesWithNStrokes(hashtable, i, out);
            } catch (ClassNotFoundException cnfe) {
                System.out.println("Class not found");
            } catch (IOException ioe) {
            }
        }
    }

}
