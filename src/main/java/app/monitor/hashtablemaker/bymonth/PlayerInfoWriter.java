package app.monitor.hashtablemaker.bymonth;

import app.constant.Constants;
import app.model.Player;

import java.util.Hashtable;

public class PlayerInfoWriter extends HashtableMaker {

    public PlayerInfoWriter(String month, String year) {
        super(month, year, "app.threadworker.PlayerInfoWriterThread", new Hashtable<String, Player>(), Constants.PLAYERS_INFO);
    }
}
