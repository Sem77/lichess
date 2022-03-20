package optimizationapp.monitor;

import optimizationapp.optimizer.Constants;

import java.util.Hashtable;

public class MostActivePlayerOverAMonth extends HashtableMaker {

    public MostActivePlayerOverAMonth(String month, String year) {
        super(month, year, "optimizationapp.threadworker.MostActivePlayersOverAMonthWriterThread", new Hashtable<String, Integer>(), Constants.MOST_ACTIVE_PLAYERS);
    }
}
