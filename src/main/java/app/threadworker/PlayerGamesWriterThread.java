package app.threadworker;

import app.optimizer.OptimizationAlgorithms;
import app.pgn.BinaryGameExtractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.TreeSet;

public class PlayerGamesWriterThread extends ThreadWorker {

    public PlayerGamesWriterThread(BinaryGameExtractor games, Hashtable<String, TreeSet<String>> hashtable) {
        super(games, hashtable);
    }


    public void run() {
        BinaryGameExtractor.GameGameFile gameGameFile;
        boolean GameGameFileNull = false;
        do {
            try {
                gameGameFile = games.getNextGame();
                if(gameGameFile != null) {
                    OptimizationAlgorithms.gamesOfAPlayer(gameGameFile.game, hashtable);
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
