package app.monitor.hashtablemakerMonth;

import app.optimizer.Constants;

import java.util.Hashtable;

public class MostActivePlayerMonitor extends HashtableMaker {

    public MostActivePlayerMonitor(String month, String year) {
        super(month, year, "app.threadworker.MostActivePlayersOverAMonthWriterThread", new Hashtable<String, Integer>(), Constants.MOST_ACTIVE_PLAYERS);
    }
}
