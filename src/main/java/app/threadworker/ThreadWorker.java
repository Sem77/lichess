package app.threadworker;

import app.pgn.BinaryGameExtractor;

import java.util.Hashtable;

public class ThreadWorker extends Thread {
    public BinaryGameExtractor games;
    public Hashtable hashtable;

    public ThreadWorker(BinaryGameExtractor games, Hashtable hashtable) {
        this.games = games;
        this.hashtable = hashtable;
    }
}
