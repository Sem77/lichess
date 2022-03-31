package app.monitor.hashtablemakerMonth;

import app.optimizer.Constants;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.TreeSet;

public class AssociationPlayerGamesMonitor extends HashtableMaker {

    public AssociationPlayerGamesMonitor(String month, String year) {
        super(month, year, "app.threadworker.PlayerGamesWriterThread", new Hashtable<String, TreeSet<String>>(), Constants.A_PLAYER_GAME);
    }

}
