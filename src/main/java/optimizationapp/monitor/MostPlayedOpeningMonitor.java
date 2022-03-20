package optimizationapp.monitor;

import optimizationapp.optimizer.Constants;

import java.util.Hashtable;

public class MostPlayedOpeningMonitor extends HashtableMaker{

    public MostPlayedOpeningMonitor(String month, String year) {
        super(month, year, "optimizationapp.threadworker.MostPlayedOpeningWriterThread", new Hashtable<String, Integer>(), Constants.MOST_PLAYED_OPENING_GAMES);
    }
}
