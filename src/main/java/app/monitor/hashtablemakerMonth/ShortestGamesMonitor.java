package app.monitor.hashtablemakerMonth;

import app.optimizer.Constants;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.TreeSet;

public class ShortestGamesMonitor extends HashtableMaker{

    public ShortestGamesMonitor(String month, String year) {
        super(month, year, "app.threadworker.ShortestGamesWriterThread", new Hashtable<Integer, TreeSet<String>>(), Constants.SHORTEST_GAMES);
    }
}
