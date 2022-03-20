package optimizationapp.threadworker;

import optimizationapp.model.Game;
import optimizationapp.pgn.GameExtractorFromPgn;

import java.io.IOException;
import java.text.ParseException;

public class GameWriterThread extends Thread {
    private GameExtractorFromPgn gameExt;

    public GameWriterThread(GameExtractorFromPgn gameExt) {
        this.gameExt = gameExt;
    }

    /**
     * Thread to save binary version a games in a file
     * It continuously asks for the next game to PgnGameExtractor
     * when the string returned is not null, it creates a Game object and save it with the GameSaver object.
     */
    public void run() {
        GameExtractorFromPgn.GameGameSaver gameGameSaver = null;
        do {
            try {
                gameGameSaver = gameExt.getNextGame();
                if(gameGameSaver != null) {
                    Game game = GameExtractorFromPgn.gameStringToGameObject(gameGameSaver.game);
                    gameGameSaver.gameSaver.saveGame(game);
                }
            } catch (IOException ioe) {
                System.out.println("There was an error with the source file");
            } catch (ParseException pe) {
                System.out.println("Game format not recognized");
            }
        } while(gameGameSaver != null);
    }
}