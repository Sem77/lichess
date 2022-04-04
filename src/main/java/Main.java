import app.exception.NoFileDataFoundException;
import app.model.OccurrenceString;
import app.model.Player;
import app.monitor.hashtablemaker.all.*;
import app.monitor.hashtablemaker.bymonth.*;
import app.monitor.hashtablemaker.byyear.*;

import java.io.*;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.TreeSet;

public class Main {
    public static void main(String args[]) throws IOException, ClassNotFoundException, ParseException, NoFileDataFoundException, NoSuchMethodException {

        String pgn01 = "/home/ubuntu/IdeaProjects/database/data_src/lichess_db_standard_rated_2013-01.pgn";
        String pgn02 = "/home/ubuntu/IdeaProjects/database/data_src/lichess_db_standard_rated_2013-02.pgn";
        String pgn03 = "/home/ubuntu/IdeaProjects/database/data_src/lichess_db_standard_rated_2013-03.pgn";
        String pgn04 = "/home/ubuntu/IdeaProjects/database/data_src/lichess_db_standard_rated_2013-04.pgn";
        String pgn05 = "/home/ubuntu/IdeaProjects/database/data_src/lichess_db_standard_rated_2013-05.pgn";
        String pgn06 = "/home/ubuntu/IdeaProjects/database/data_src/lichess_db_standard_rated_2013-06.pgn";
        String pgn07 = "/home/ubuntu/IdeaProjects/database/data_src/lichess_db_standard_rated_2013-07.pgn";

        OptimizationWriter ow = new OptimizationWriter();
        //ow.saveOptimizedGames(pgn01, "01", "2013", 4);
        //ow.saveOptimizedGames(pgn02, "02", "2013", 4);
        //ow.saveOptimizedGames(pgn03, "03", "2013", 4);
        //ow.saveOptimizedGames(pgn04, "04", "2013", 4);
        //ow.saveOptimizedGames(pgn05, "05", "2013", 4);
        //ow.saveOptimizedGames(pgn06, "06", "2013", 4);
        //ow.saveOptimizedGames(pgn07, "07", "2013", 4);


        /*AssociationPlayerGamesMonitor apg = new AssociationPlayerGamesMonitor("02", "2013");
        MostActivePlayerMonitor mapom = new MostActivePlayerMonitor("02", "2013");
        MostPlayedOpeningMonitor mpom = new MostPlayedOpeningMonitor("02", "2013");
        ShortestGamesMonitor sgm = new ShortestGamesMonitor("02", "2013");
        PlayerInfoWriter piw = new PlayerInfoWriter("02", "2013");

        apg.buildHastable();
        mapom.buildHastable();
        mpom.buildHastable();
        sgm.buildHastable();
        piw.buildHastable();

        mpom.saveNMostPlayedOpening();
        mapom.saveNMostActivePlayers();*/


        /*AssociationPlayerGamesYear apgy = new AssociationPlayerGamesYear("2013");
        MostPlayedOpeningYear mpoy = new MostPlayedOpeningYear("2013");
        MostActivePlayerYear mapy = new MostActivePlayerYear("2013");
        ShortestGamesYear sgy = new ShortestGamesYear("2013");
        PlayerInfoYear piy = new PlayerInfoYear("2013");

        apgy.buildHashtable();
        mpoy.buildHashtable();
        mapy.buildHashtable();
        sgy.buildHashtable();
        piy.buildHashtable();

        mpoy.saveNMostPlayedOpening();
        mapy.saveNMostActivePlayers();*/



        /*MostPlayedOpeningAll mpoa = new MostPlayedOpeningAll();
        ShortestGamesAll sga = new ShortestGamesAll();
        AssociationPlayerGamesAll apga = new AssociationPlayerGamesAll();
        MostActivePlayerAll mapa = new MostActivePlayerAll();
        PlayerInfoAll pia = new PlayerInfoAll();

        mpoa.buildHashtable();
        sga.buildHashtable();
        apga.buildHashtable();
        mapa.buildHashtable();
        pia.buildHashtable();

        mpoa.saveNMostPlayedOpening();
        sga.saveFiveShortestGames();
        mapa.saveNMostActivePlayers();
        pia.saveBestPlayers();*/











        /*ow.pageRankCalculator("/home/ubuntu/IdeaProjects/database/data_dest/games_data/2013/02/hashtables/players_info_hashtable.dat");

        ObjectInputStream o = new ObjectInputStream(new FileInputStream("/home/ubuntu/IdeaProjects/database/data_dest/games_data/2013/02/hashtables/players_info_hashtable.dat"));

        try {
            Hashtable<String, Player> h = (Hashtable<String, Player>) o.readObject();
            System.out.println(h);
        } catch(EOFException eofe) {}
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        o.close();*/


    }
}
