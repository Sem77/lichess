package optimizationapp.threadworker;

import optimizationapp.optimizer.OptimizationAlgorithms;
import optimizationapp.pgn.BinaryGameExtractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class ShortestGamesWriterThread extends ThreadWorker {
    public ShortestGamesWriterThread(BinaryGameExtractor games, Hashtable<Integer, ArrayList<String>> hashtable) {
        super(games, hashtable);
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
                ieo.printStackTrace();
            } catch (NullPointerException npe) {
                GameGameFileNull = true;
            }
        } while(!GameGameFileNull);
    }
}
