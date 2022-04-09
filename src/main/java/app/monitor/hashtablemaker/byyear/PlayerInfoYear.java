package app.monitor.hashtablemaker.byyear;

import app.constant.Constants;
import app.model.Player;
import app.monitor.hashtablemaker.HashtableFinderByYearInterface;
import app.monitor.hashtablemaker.HashtableMergerInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class PlayerInfoYear implements HashtableMergerInterface, HashtableFinderByYearInterface {
    public String year;
    public String hashtableMonthName;
    public String hashTableYearName;
    public String baseDirectory;

    public PlayerInfoYear(String year) {
        this.year = year;
        this.hashtableMonthName = Constants.PLAYERS_INFO;
        this.hashTableYearName = Constants.PLAYERS_INFO_OVER_A_YEAR;
        baseDirectory = Constants.GAMES_DATA_DIRECTORY + File.separator + year;
    }

    @Override
    public void buildHashtable() {
        String baseDirectory = Constants.GAMES_DATA_DIRECTORY + File.separator + year;
        ArrayList<File> hashtablesPaths = findHashtablesByNameInMonth(new File(baseDirectory), hashtableMonthName);
        Hashtable<String, Player> hashtableYear = new Hashtable<>();
        for (File hashtablePath : hashtablesPaths) {
            try {
                ObjectInputStream o = new ObjectInputStream(new FileInputStream(hashtablePath));
                Hashtable<String, Player> hashtableMonth = (Hashtable<String, Player>) o.readObject();
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
}
