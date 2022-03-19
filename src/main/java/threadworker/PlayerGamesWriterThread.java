package threadworker;

import optimizer.OptimizationAlgorithms;
import pgn.BinaryGameExtractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class PlayerGamesWriterThread extends Thread {
    private BinaryGameExtractor games;
    private Hashtable<String, ArrayList<String>> hashtable;

    public PlayerGamesWriterThread(BinaryGameExtractor games, Hashtable<String, ArrayList<String>> hashtable) {
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
                    OptimizationAlgorithms.gamesOfAPlayer(gameGameFile.game, gameGameFile.pathGameFile, hashtable);
                }
            } catch(ClassNotFoundException cnfe) {
                System.out.println("Class not found");
            } catch(IOException ieo) {
                System.out.println("There was a error with the file");
            } catch (NullPointerException npe) {
                GameGameFileNull = true;
            }
        } while(!GameGameFileNull);

    }
}
