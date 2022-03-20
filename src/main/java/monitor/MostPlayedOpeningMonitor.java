package monitor;

import optimizer.Constants;

import java.util.Hashtable;

public class MostPlayedOpeningMonitor extends HashtableMaker{

    public MostPlayedOpeningMonitor(String month, String year) {
        super(month, year, "threadworker.MostPlayedOpeningWriterThread", new Hashtable<String, Integer>(), Constants.MOST_PLAYED_OPENING_GAMES);
    }
}
