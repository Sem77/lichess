package app.monitor.hashtablemaker.bymonth;

import app.constant.Constants;

import java.util.Hashtable;

public class FindGameWithURL extends HashtableMaker {
    public FindGameWithURL(String month, String year) {
        super(month, year, "app.threadworker.FindGameWithURLThread", new Hashtable<String, String>(), Constants.GAME_LINK);
    }
}
