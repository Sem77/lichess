package app.threadworker;

import app.model.Game;
import app.pgn.GameExtractorFromPgn;

import java.io.IOException;
import java.text.ParseException;

public class GameWriterThread extends Thread {
    private GameExtractorFromPgn gameExt;

    public GameWriterThread(GameExtractorFromPgn gameExt) {
        this.gameExt = gameExt;
    }

    /**
     * Thread qui partage les parties d'échec dans plusieurs fichiers binaires
     * Il appelle la méthode getNextGame de gameExt qui lui retourne un objet de type GameGameSaver
     * cet objet contient une partie d'échec et un gameSaver qui permettra d'enregistrer la partie sur le disque
     * Lorsque le GameGameSaver retourné n'est pas null, il crée un objet de type Game avec la méthode statique gameStringToGameObject de GameExtractorFromPgn
     * puis enregistre la partie avec la méthode saveGame de gameSaver
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