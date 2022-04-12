package app.optimizer;

import app.model.Game;
import app.model.Player;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;

public class OptimizationAlgorithms {

    /**
     * Ajoute dans la table de hashage un joueur (clé) et les liens des parties qu'il a joué
     * @param game la partie d'échec de laquelle extraire les noms des participants
     * @param hashtable key: le nom d'utilisateur
     *                  value: liste des liens des parties du joueur
     */
    public synchronized static void gamesOfAPlayer(Game game, Hashtable<String, TreeSet<String>> hashtable) {

        String white = game.getWhitePlayer().getUsername(); // get the username of the player white
        if(!hashtable.containsKey(white)) {
            /**
             * si la table de hashage contient déjà le nom d'utilisateur
             * ajoute juste le lien du jeux à la partie existante
             */
            TreeSet<String> urlsGames = new TreeSet<>();
            urlsGames.add(game.getSite());
            hashtable.put(white, urlsGames);
        }
        else {
            /**
             * si non
             * crée une nouvelle liste
             */
            TreeSet<String> urlsGames = hashtable.get(white);
            urlsGames.add(game.getSite());
            hashtable.put(white, urlsGames);
        }

        /**
         * faire la même chose pour le joueur noir
         */
        String black = game.getBlackPlayer().getUsername(); // get the username of the player black
        if(!hashtable.containsKey(black)) {
            TreeSet<String> urlsGames = new TreeSet<>();
            urlsGames.add(game.getSite());
            hashtable.put(black, urlsGames);
        }
        else {
            TreeSet<String> urlsGames = hashtable.get(black);
            urlsGames.add(game.getSite());
            hashtable.put(black, urlsGames);
        }
    }


    /**
     * Ajoute dans la table de hashage un nombre et les liens des parties d'échec ayant ce nombre de coups
     * @param game la partie d'échec de laquelle extraire le nombre de coups
     * @param hashtable key: un entier
     *                  value: liste des liens des parties d'échec
     */
    public synchronized static void shortestGames(Game game, Hashtable<Integer, TreeSet<String>> hashtable) {
        int strokesNumber = game.getStrokesNumber();

        if(!hashtable.containsKey(strokesNumber)) {
            TreeSet<String> urlsGames = new TreeSet<>();
            urlsGames.add(game.getSite());
            hashtable.put(strokesNumber, urlsGames);
        }
        else {
            TreeSet<String> urlsGames = hashtable.get(strokesNumber);
            urlsGames.add(game.getSite());
            hashtable.put(strokesNumber, urlsGames);
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
            loser.addWinner(winner.getUsername());
            hashtable.put(loser.getUsername(), loser);
        }
        else {
            Player player = hashtable.get(loser.getUsername());
            player.increaseDefeats();
            player.addWinner(winner.getUsername());
            hashtable.put(loser.getUsername(), player);
        }
    }



    private static Player getPlayerByUsername(String playerUsername, Hashtable<String, Player> hashtable) {
        return hashtable.get(playerUsername);
    }



    public static void findGameWithLink(Game game, String pathFileContainingGame, Hashtable<String, String> hashtable) {
        hashtable.put(game.getSite(), pathFileContainingGame);
    }


    /**
     * parcours la liste de tous les joueurs de la table de hashage et calcule leurs page rank de manière itérative
     * @param hashtable
     */
    public static void pageRankCalculator(Hashtable<String, Player> hashtable) {
        double EPSILON = 0.000001;
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

    /**
     * calcule le page rank d'un joueur en fonction du page rank des joueurs qu'il a battu
     * @param playerUsername
     * @param hashtable
     */
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

    private static double calculEpsilon(Hashtable<String, Player> hashtable) {
        Set<String> keys = hashtable.keySet();
        double eps = 0.0;
        for(String key : keys) {
            Player player = getPlayerByUsername(key, hashtable);
            eps += player.getPageRank();
        }
        return eps;
    }


    public static void AuthorityAndHUBCalculator(Hashtable<String, Player> hashtable) {
        double EPSILON = 0.000001;
        Set<String> keys = hashtable.keySet();
        double oldEpsilon;
        double newEpsilon = 0;
        do {
            oldEpsilon = newEpsilon;
            for (String key : keys) {
                Player player = getPlayerByUsername(key, hashtable);
                authority(player, hashtable);
                hub(player, hashtable);
                normalize(player, hashtable);

                pageRank(key, hashtable);
            }
            newEpsilon = calculEpsilon(hashtable);
        } while(newEpsilon - oldEpsilon > EPSILON);
    }

    private static void authority(Player player, Hashtable<String, Player> hashtable) {
        ArrayList<Player> losersAgainst = new ArrayList<>();
        for(String loserAgainst : player.getLosersAgainst()) {
            Player loser = getPlayerByUsername(loserAgainst, hashtable);
            if(loser != null)
                losersAgainst.add(loser);
        }
        double authority = 0;
        for(Player loserAgainst : losersAgainst) {
            authority += loserAgainst.getHub();
        }
        player.setAuthority(authority);
    }

    private static void hub(Player player, Hashtable<String, Player> hashtable) {
        ArrayList<Player> winnersAgainst = new ArrayList<>();
        for(String winnerAgainst : player.getWinnersAgainst()) {
            Player winner = getPlayerByUsername(winnerAgainst, hashtable);
            if(winner != null)
                winnersAgainst.add(winner);
        }
        double hub = 0;
        for(Player winnerAgainst : winnersAgainst) {
            hub += winnerAgainst.getAuthority();
        }
        player.setHub(hub);
    }

    private static void normalize(Player player, Hashtable<String, Player> hashtable) {
        ArrayList<Player> playersAgainst = new ArrayList<>();
        for(String loserAgainst : player.getLosersAgainst()) {
            Player loser = getPlayerByUsername(loserAgainst, hashtable);
            if(loser != null)
                playersAgainst.add(loser);
        }
        for(String winnerAgainst : player.getWinnersAgainst()) {
            Player winner = getPlayerByUsername(winnerAgainst, hashtable);
            if(winner != null)
                playersAgainst.add(winner);
        }

        double denAuthority = 0;
        for(Player playerAgainst : playersAgainst) {
            denAuthority += playerAgainst.getAuthority() * playerAgainst.getAuthority();
        }
        denAuthority = Math.sqrt(denAuthority);

        double denHub = 0;
        for(Player playerAgainst : playersAgainst) {
            denHub += playerAgainst.getHub() * playerAgainst.getHub();
        }
        denHub = Math.sqrt(denHub);

        player.setAuthority(player.getAuthority() / denAuthority);
        player.setHub(player.getHub() / denHub);
    }
}
