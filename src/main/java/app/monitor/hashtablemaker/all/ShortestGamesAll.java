package app.monitor.hashtablemaker.all;

import app.model.Game;
import app.monitor.hashtablemaker.HashtableFinderAllInterface;
import app.monitor.hashtablemaker.HashtableMergerInterface;
import app.constant.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;

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

    public void saveFiveShortestGames() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY;
        File gamesDataDirectory = new File(Constants.GAMES_DATA_DIRECTORY);
        File hashtablePath = new File(gamesDataDirectory + File.separator + Constants.SHORTEST_GAMES_ALL + "." + Constants.BINARY_EXTENSION); // list of the path of all hashtables

        ArrayList<Game> games = shortestGames(hashtablePath);

        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + hashTableNameAll + "." + Constants.BINARY_EXTENSION));
            for (Game game : games) {
                o.writeObject(game);
            }
            o.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Game> gamesWithNStrokes(File hashtablePath, int nStrokes) throws IOException, ClassNotFoundException {
        ObjectInputStream o = new ObjectInputStream(new FileInputStream(hashtablePath));

        Hashtable<Integer, TreeSet<String>> hashtable = (Hashtable<Integer, TreeSet<String>>) o.readObject();
        o.close();

        ArrayList<Game> games = new ArrayList<>();
        TreeSet<String> gameFiles = hashtable.get(nStrokes);

        if(gameFiles != null) {
            for (String gameFile : gameFiles) {
                ObjectInputStream a = new ObjectInputStream(new FileInputStream(gameFile));
                Game game;
                try {
                    do {
                        game = (Game) a.readObject();
                        if (game.getStrokesNumber() == nStrokes) {
                            games.add(game);
                        }
                    } while (game != null);
                } catch(EOFException eofe) {}
                a.close();
            }
        }
        return games;
    }

    private static ArrayList<Game> shortestGames(File hashtablePath) {
        ArrayList<Game> shortest = new ArrayList<>();
        for(int i=0; (i < 50) && (shortest.size() < 5); i++) {
            try {
                ArrayList<Game> g = gamesWithNStrokes(hashtablePath, i);
                for (int j = 0; (j < 5 - shortest.size()) && (j < g.size()); j++) {
                    shortest.add(g.get(j));
                }
            } catch(ClassNotFoundException cnfe) {System.out.println("Class not found");}
            catch(IOException ioe) {}
        }
        return shortest;
    }
}
