package app.threadworker;

import app.optimizer.OptimizationAlgorithms;
import app.pgn.BinaryGameExtractor;

import java.io.IOException;
import java.util.Hashtable;

public class FindGameWithURLThread extends ThreadWorker {
    public FindGameWithURLThread(BinaryGameExtractor games, Hashtable hashtable) {
        super(games, hashtable);
    }


    public void run() {
        BinaryGameExtractor.GameGameFile gameGameFile;
        boolean GameGameFileNull = false;
        do {
            try {
                gameGameFile = games.getNextGame();
                if(gameGameFile != null) {
                    OptimizationAlgorithms.findGameWithURL(gameGameFile.game, gameGameFile.pathGameFile, hashtable);
                    OptimizationAlgorithms.findGameWithURL(gameGameFile.game, gameGameFile.pathGameFile, hashtable);
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
