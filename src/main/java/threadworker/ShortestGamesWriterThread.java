package threadworker;

import optimizer.OptimizationAlgorithms;
import pgn.BinaryGameExtractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class ShortestGamesWriterThread extends Thread {
    private BinaryGameExtractor games;
    private Hashtable<Integer, ArrayList<String>> hashtable;

    public ShortestGamesWriterThread(BinaryGameExtractor games, Hashtable<Integer, ArrayList<String>> hashtable) {
        this.games = games;
        this.hashtable = hashtable;
    }

    public void run() {
        BinaryGameExtractor.GameGameFile gameGameFile;
        boolean GameGameFileNull = false;
        do {
            try {
                gameGameFile = games.getNextGame();
                if(gameGameFile != null) {
                    OptimizationAlgorithms.shortestGames(gameGameFile.game, gameGameFile.pathGameFile, hashtable);
                }
            } catch(ClassNotFoundException cnfe) {
                System.out.println("Class not found");
            } catch(IOException ieo) {
                //System.out.println("There was a error with the file");
                ieo.printStackTrace();
            } catch (NullPointerException npe) {
                GameGameFileNull = true;
            }
        } while(!GameGameFileNull);
    }
}
