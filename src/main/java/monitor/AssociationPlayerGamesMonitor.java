package monitor;

import optimizer.Constants;

import java.util.ArrayList;
import java.util.Hashtable;

public class AssociationPlayerGamesMonitor extends HashtableMaker {

    public AssociationPlayerGamesMonitor(String month, String year) {
        super(month, year, "threadworker.PlayerGamesWriterThread", new Hashtable<String, ArrayList<String>>(), Constants.A_PLAYER_GAME);
    }

}
