package app.monitor.hashtablemaker.bymonth;

import app.constant.Constants;

import java.util.Hashtable;

public class FindGameWithLink extends HashtableMaker {
    public FindGameWithLink(String month, String year) {
        super(month, year, "app.threadworker.FindGameWithLinkThread", new Hashtable<String, String>(), Constants.GAME_LINK);
    }
}
