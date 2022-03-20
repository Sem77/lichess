package threadworker;

import optimizer.OptimizationAlgorithms;
import pgn.BinaryGameExtractor;

import java.io.IOException;
import java.util.Hashtable;

public class MostActivePlayersOverAMonthWriterThread extends ThreadWorker {

    public MostActivePlayersOverAMonthWriterThread(BinaryGameExtractor games, Hashtable<String, Integer> hashtable) {
        super(games, hashtable);
    }


    public void run() {
        BinaryGameExtractor.GameGameFile gameGameFile;
        boolean GameGameFileNull = false;
        do {
            try {
                gameGameFile = games.getNextGame();
                if(gameGameFile != null) {
                    OptimizationAlgorithms.mostActivePlayer(gameGameFile.game.getWhitePlayer(), hashtable);
                    OptimizationAlgorithms.mostActivePlayer(gameGameFile.game.getBlackPlayer(), hashtable);
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
