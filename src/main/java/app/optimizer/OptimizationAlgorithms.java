package app.optimizer;

import app.model.Game;
import app.model.Player;

import java.util.ArrayList;
import java.util.Hashtable;

public class OptimizationAlgorithms {

    /**
     * Adds in the hashtable a player and the file containing the game he played
     * @param game the game to analyse and extract the usernames of the players
     * @param pathFileContainingGame path of the file containing the game
     * @param hashtable key: the username of a player
     *                  value: list of filepath we can find the games that the player played
     */
    public synchronized static void gamesOfAPlayer(Game game, String pathFileContainingGame, Hashtable<String, ArrayList<String>> hashtable) {

        String white = game.getWhitePlayer().getUsername(); // get the username of the player white
        if(!hashtable.containsKey(white)) {
            /**
             * if hashtable already contains the username,
             * just add pathFileContainingGame to the existing list
             */
            ArrayList<String> filesContainingGame = new ArrayList<>();
            filesContainingGame.add(pathFileContainingGame);
            hashtable.put(white, filesContainingGame);
        }
        else {
            /**
             * if not,
             * add the username and add pathFileContainingGame a new list
             */
            ArrayList<String > filesContainingGame = hashtable.get(white);
            filesContainingGame.add(pathFileContainingGame);
            hashtable.put(white, filesContainingGame);
        }

        /**
         * Do the same for the black player
         */
        String black = game.getBlackPlayer().getUsername(); // get the username of the player black
        if(!hashtable.containsKey(black)) {
            ArrayList<String> filesContainingGame = new ArrayList<>();
            filesContainingGame.add(pathFileContainingGame);
            hashtable.put(black, filesContainingGame);
        }
        else {
            ArrayList<String > filesContainingGame = hashtable.get(black);
            filesContainingGame.add(pathFileContainingGame);
            hashtable.put(black, filesContainingGame);
        }
    }


    public synchronized static void shortestGames(Game game, String pathFileContainingGame, Hashtable<Integer, ArrayList<String>> hashtable) {
        int strokesNumber = game.getStrokesNumber();

        if(!hashtable.containsKey(strokesNumber)) {
            ArrayList<String> filesContainingGame = new ArrayList<>();
            filesContainingGame.add(pathFileContainingGame);
            hashtable.put(strokesNumber, filesContainingGame);
        }
        else {
            ArrayList<String> filesContainingGame = hashtable.get(strokesNumber);
            filesContainingGame.add(pathFileContainingGame);
            hashtable.put(strokesNumber, filesContainingGame);
        }
    }


    public synchronized static void mostPlayedOpening(Game game, Hashtable<String, Integer> hashtable) {
        String opening = game.getOpening();
        Integer nbOccOpening;
        if((nbOccOpening = hashtable.get(opening)) != null)
            hashtable.put(opening, nbOccOpening+1);
        else
            hashtable.put(opening, 1);
    }


    public synchronized static void mostActivePlayer(Player player, Hashtable<String, Integer> hashtable) {
        String playerUsername = player.getUsername();
        Integer nbGamesPlayed;
        if((nbGamesPlayed = hashtable.get(playerUsername)) != null)
            hashtable.put(playerUsername, nbGamesPlayed+1);
        else
            hashtable.put(playerUsername, 1);
    }
}
