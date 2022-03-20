package monitor;

import optimizer.Constants;

import java.util.ArrayList;
import java.util.Hashtable;

public class ShortestGamesMonitor extends HashtableMaker{

    public ShortestGamesMonitor(String month, String year) {
        super(month, year, "threadworker.ShortestGamesWriterThread", new Hashtable<Integer, ArrayList<String>>(), Constants.SHORTEST_GAMES);
    }
}
