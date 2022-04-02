package app.monitor.hashtablemaker.bymonth;

import app.constants.Constants;

import java.util.Hashtable;
import java.util.TreeSet;

public class ShortestGamesMonitor extends HashtableMaker{

    public ShortestGamesMonitor(String month, String year) {
        super(month, year, "app.threadworker.ShortestGamesWriterThread", new Hashtable<Integer, TreeSet<String>>(), Constants.SHORTEST_GAMES);
    }
}
