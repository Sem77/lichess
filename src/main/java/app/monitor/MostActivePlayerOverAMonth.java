package app.monitor;

import app.optimizer.Constants;

import java.util.Hashtable;

public class MostActivePlayerOverAMonth extends HashtableMaker {

    public MostActivePlayerOverAMonth(String month, String year) {
        super(month, year, "app.threadworker.MostActivePlayersOverAMonthWriterThread", new Hashtable<String, Integer>(), Constants.MOST_ACTIVE_PLAYERS);
    }
}
