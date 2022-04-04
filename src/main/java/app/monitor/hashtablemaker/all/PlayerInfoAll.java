package app.monitor.hashtablemaker.all;

import app.constants.Constants;
import app.model.OccurrenceString;
import app.model.Player;
import app.monitor.hashtablemaker.HashtableFinderAllInterface;
import app.monitor.hashtablemaker.HashtableMergerInterface;
import app.monitor.hashtablemaker.bymonth.OptimizationWriter;

import java.io.*;
import java.util.*;

public class PlayerInfoAll implements HashtableMergerInterface, HashtableFinderAllInterface {
    public String hashtableYearName;
    public String hashTableNameAll;
    public String baseDirectory;

    public PlayerInfoAll() {
        this.hashtableYearName = Constants.PLAYERS_INFO_OVER_A_YEAR;
        this.hashTableNameAll = Constants.PLAYERS_INFO_ALL;
        baseDirectory = Constants.GAMES_DATA_DIRECTORY;
    }


    @Override
    public void buildHashtable() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY;
        ArrayList<File> hashtablesPaths = findHashtablesByNameInYear(new File(baseDirectory), hashtableYearName);
        Hashtable<String, Player> hashtableYear = new Hashtable<>();
        for (File hashtablePath : hashtablesPaths) {
            try {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(hashtablePath));
                Hashtable<String, Player> hashtableMonth = (Hashtable<String, Player>) o.readObject();
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

        // Calcul du page rank de chaque joueur
        OptimizationWriter ow = new OptimizationWriter();
        ow.pageRankCalculator(hashtableYear);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + hashTableNameAll + "." + Constants.BINARY_EXTENSION));
            out.writeObject(hashtableYear);
            out.close();
        } catch (FileNotFoundException fnfe) {}
        catch (IOException ioe) {}

    }

    @Override
    public void mergeHashtables(Hashtable dest, Hashtable h) {
        Hashtable<String, Player> hashtableDest = dest;
        Hashtable<String, Player> hashtableSrc = h;
        Set<String> keys = hashtableSrc.keySet();
        for (String key : keys) {
            Player playerDest;
            if(hashtableDest.containsKey(key)) {
                playerDest = hashtableDest.get(key);
                Player playerSrc = hashtableSrc.get(key);
                playerDest.addLoser(playerSrc.getLosersAgainst());
                playerDest.increaseDefeats(playerSrc.getNbDefeats());
            }
            else {
                playerDest = hashtableSrc.get(key);
            }
            playerDest.setPageRank(1.0);
            hashtableDest.put(key, playerDest);
        }
    }

    public void saveBestPlayers() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY;
        File gamesDataDirectory = new File(Constants.GAMES_DATA_DIRECTORY);
        File hashtablePath = new File(gamesDataDirectory + File.separator + Constants.PLAYERS_INFO_ALL + "." + Constants.BINARY_EXTENSION); // list of the path of all hashtables

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(hashtablePath));
            Hashtable<String, Player> hashtable = (Hashtable<String, Player>) ois.readObject();
            ois.close();

            ArrayList<Player> players = new ArrayList<>();

            Set<String> keys = hashtable.keySet();
            for(String key : keys) {
                players.add(hashtable.get(key));
            }
            Collections.sort(players); // trie en fonction du page rank
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(baseDirectory + File.separator + Constants.ORDER_BEST_PLAYERS_ALL + "." + Constants.BINARY_EXTENSION));

            for(Player player : players) {
                oos.writeObject(player);
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
