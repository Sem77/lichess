import app.exception.NoFileDataFoundException;
import app.monitor.hashtablemaker.*;

import java.io.*;
import java.text.ParseException;

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


        AssociationPlayerGamesMonitor apg = new AssociationPlayerGamesMonitor("02", "2013");
        MostActivePlayerMonitor mapom = new MostActivePlayerMonitor("02", "2013");
        MostPlayedOpeningMonitor mpom = new MostPlayedOpeningMonitor("02", "2013");
        ShortestGamesMonitor sgm = new ShortestGamesMonitor("02", "2013");

        apg.buildHastable();
        mapom.buildHastable();
        mpom.buildHastable();
        sgm.buildHastable();

    }
}
