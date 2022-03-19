import exception.NoFileDataFoundException;
import model.Game;
import monitor.OptimizationWriter;
import optimizer.Constants;
import pgn.GameExtractorFromPgn;
import pgn.PgnGameExtractor;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String args[]) throws IOException, ClassNotFoundException, ParseException, NoFileDataFoundException {

        String pgnSrc = "/home/ubuntu/Downloads/lichess_db_standard_rated_2016-07.pgn";
        //String pgnSrc = "/home/ubuntu/Downloads/lichess_db_standard_rated_2013-01.pgn";
        //String pgnSrc = "/home/ubuntu/Downloads/t.pgn";

        OptimizationWriter ow = new OptimizationWriter();

        //ow.saveOptimizedGames(pgnSrc, "07", "2016", 4);

        ow.saveShortestGames("07", "2016", 4);

        //System.out.println(game);
    }
}
