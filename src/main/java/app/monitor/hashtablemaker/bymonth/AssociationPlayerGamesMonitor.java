package app.monitor.hashtablemaker.bymonth;

import app.constants.Constants;

import java.util.Hashtable;
import java.util.TreeSet;

public class AssociationPlayerGamesMonitor extends HashtableMaker {

    public AssociationPlayerGamesMonitor(String month, String year) {
        super(month, year, "app.threadworker.PlayerGamesWriterThread", new Hashtable<String, TreeSet<String>>(), Constants.A_PLAYER_GAME);
    }

}
