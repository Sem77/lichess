package app.optimizer;

import app.model.Game;
import app.model.Player;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;

public class OptimizationAlgorithms {

    /**
     * Ajoute dans la table de hashage un joueur (clé) et le chemin vers le fichier contenant le(s) jeu(x) qu'il a joué
     * @param game la partie d'échec de laquelle extraire les noms des participants
     * @param pathFileContainingGame chemin vers le fichier de la partie d'échec
     * @param hashtable key: le nom d'utilisateur
     *                  value: liste les chemins vers les fichiers contenant le(s) jeux du joueur
     */
    public synchronized static void gamesOfAPlayer(Game game, String pathFileContainingGame, Hashtable<String, TreeSet<String>> hashtable) {

        String white = game.getWhitePlayer().getUsername(); // get the username of the player white
        if(!hashtable.containsKey(white)) {
            /**
             * if hashtable already contains the username,
             * just add pathFileContainingGame to the existing list
             */
            TreeSet<String> filesContainingGame = new TreeSet<>();
            filesContainingGame.add(pathFileContainingGame);
            hashtable.put(white, filesContainingGame);
        }
        else {
            /**
             * if not,
             * add the username and add pathFileContainingGame a new list
             */
            TreeSet<String> filesContainingGame = hashtable.get(white);
            filesContainingGame.add(pathFileContainingGame);
            hashtable.put(white, filesContainingGame);
        }

        /**
         * Do the same for the black player
         */
        String black = game.getBlackPlayer().getUsername(); // get the username of the player black
        if(!hashtable.containsKey(black)) {
            TreeSet<String> filesContainingGame = new TreeSet<>();
            filesContainingGame.add(pathFileContainingGame);
            hashtable.put(black, filesContainingGame);
        }
        else {
            TreeSet<String> filesContainingGame = hashtable.get(black);
            filesContainingGame.add(pathFileContainingGame);
            hashtable.put(black, filesContainingGame);
        }
    }


    /**
     * Ajoute dans la table de hashage un nombre et la liste des chemins vers les parties d'échec ayant ce nombre de coups
     * @param game la partie d'échec de laquelle extraire le nombre de coups
     * @param pathFileContainingGame chemin vers le fichier de la partie d'échec
     * @param hashtable key: un entier
     *                  value: liste les chemins vers les parties d'échec
     */
    public synchronized static void shortestGames(Game game, String pathFileContainingGame, Hashtable<Integer, TreeSet<String>> hashtable) {
        int strokesNumber = game.getStrokesNumber();

        if(!hashtable.containsKey(strokesNumber)) {
            TreeSet<String> filesContainingGame = new TreeSet<>();
            filesContainingGame.add(pathFileContainingGame);
            hashtable.put(strokesNumber, filesContainingGame);
        }
        else {
            TreeSet<String> filesContainingGame = hashtable.get(strokesNumber);
            filesContainingGame.add(pathFileContainingGame);
            hashtable.put(strokesNumber, filesContainingGame);
        }
    }


    /**
     * Ajoute dans la table de hashage une ouverture et le nombre de fois qu'elle a été utilisée
     * @param game la partie d'échec de laquelle extraire l'ouverture
     * @param hashtable key: une ouverture
     *                  value: le nombre de fois qu'elle a été utilisée
     */
    public synchronized static void mostPlayedOpening(Game game, Hashtable<String, Integer> hashtable) {
        String opening = game.getOpening();
        Integer nbOccOpening;
        if((nbOccOpening = hashtable.get(opening)) != null)
            hashtable.put(opening, nbOccOpening+1);
        else
            hashtable.put(opening, 1);
    }


    /**
     * Ajoute dans la table de hashage un joueur et le nombre de parties que celui-ci a joué
     * @param player le joueur concerné
     * @param hashtable key: le nom d'utilisateur
     *                  value: le nombre de parties d'échec jouées
     */
    public synchronized static void mostActivePlayer(Player player, Hashtable<String, Integer> hashtable) {
        String playerUsername = player.getUsername();
        Integer nbGamesPlayed;
        if((nbGamesPlayed = hashtable.get(playerUsername)) != null)
            hashtable.put(playerUsername, nbGamesPlayed+1);
        else
            hashtable.put(playerUsername, 1);
    }


    public synchronized static void playerInfo(Game game, Hashtable<String, Player> hashtable) {
        Player winner = game.getWinner();
        Player loser = game.getLoser();

        // Traitement pour le gagnant
        if(!hashtable.containsKey(winner.getUsername())) {
            winner.addLoser(loser.getUsername());
            hashtable.put(winner.getUsername(), winner);
        }
        else {
            Player player = hashtable.get(winner.getUsername());
            player.addLoser(loser.getUsername());
            hashtable.put(winner.getUsername(), player);
        }

        // Traitement pour le perdant
        if(!hashtable.containsKey(loser.getUsername())) {
            loser.increaseDefeats();
            hashtable.put(loser.getUsername(), loser);
        }
        else {
            Player player = hashtable.get(loser.getUsername());
            player.increaseDefeats();
            hashtable.put(loser.getUsername(), player);
        }
    }


    public static void pageRankCalculator(Hashtable<String, Player> hashtable) {
        double EPSILON = 0.001;
        Set<String> keys = hashtable.keySet();
        double oldEpsilon;
        double newEpsilon = 0;
        do {
            oldEpsilon = newEpsilon;
            for (String key : keys) {
                pageRank(key, hashtable);
            }
            newEpsilon = calculEpsilon(hashtable);
        } while(newEpsilon - oldEpsilon > EPSILON);
    }


    private static void pageRank(String playerUsername, Hashtable<String, Player> hashtable) {
        double d = 0.85;

        Player player = getPlayerByUsername(playerUsername, hashtable);

        ArrayList<Player> losersAgainst = new ArrayList<>();
        for(String loserAgainst : player.getLosersAgainst()) {
            Player loser = getPlayerByUsername(loserAgainst, hashtable);
            if(loser != null)
                losersAgainst.add(loser);
        }
        double sum = 0;
        for(Player loser : losersAgainst) {
            sum += loser.getPageRank() / loser.getNbDefeats();
        }

        player.setPageRank(1-d + sum*d);
    }

    private static Player getPlayerByUsername(String playerUsername, Hashtable<String, Player> hashtable) {
        return hashtable.get(playerUsername);
    }

    private static double calculEpsilon(Hashtable<String, Player> hashtable) {
        Set<String> keys = hashtable.keySet();
        double eps = 0.0;
        for(String key : keys) {
            Player player = getPlayerByUsername(key, hashtable);
            eps += player.getPageRank();
        }
        return eps;
    }


    public static void findGameWithLink(Game game, String pathFileContainingGame, Hashtable<String, String> hashtable) {
        hashtable.put(game.getSite(), pathFileContainingGame);
    }
}
