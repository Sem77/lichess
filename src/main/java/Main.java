import app.exception.NoFileDataFoundException;
import app.monitor.*;

import java.io.*;
import java.text.ParseException;

public class Main {
    public static void main(String args[]) throws IOException, ClassNotFoundException, ParseException, NoFileDataFoundException, NoSuchMethodException {

        //String pgnSrc = "/home/ubuntu/Downloads/lichess_db_standard_rated_2016-07.pgn";
        String pgnSrc = "/home/ubuntu/Downloads/lichess_db_standard_rated_2013-01 (1).pgn";
        //String pgnSrc = "/home/ubuntu/Downloads/t.pgn";

        OptimizationWriter ow = new OptimizationWriter();
        //ow.saveOptimizedGames(pgnSrc, "01", "2013", 4);

        AssociationPlayerGamesMonitor apg = new AssociationPlayerGamesMonitor("01", "2013");
        MostActivePlayerOverAMonth mapom = new MostActivePlayerOverAMonth("01", "2013");
        MostPlayedOpeningMonitor mpom = new MostPlayedOpeningMonitor("01", "2013");
        ShortestGamesMonitor sgm = new ShortestGamesMonitor("01", "2013");

        apg.buildHastable();
        mapom.buildHastable();
        mpom.buildHastable();
        sgm.buildHastable();
    }
}
