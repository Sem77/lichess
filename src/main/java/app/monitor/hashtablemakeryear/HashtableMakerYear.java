package app.monitor.hashtablemakeryear;

import app.optimizer.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

public abstract class HashtableMakerYear {
    public String year;
    public String hashtableMonthName;
    public String hashTableYearName;
    public String baseDirectory;

    public HashtableMakerYear(String year, String hashtableMonthName, String hashTableYearName) {
        this.year = year;
        this.hashtableMonthName = hashtableMonthName;
        this.hashTableYearName = hashTableYearName;
        baseDirectory = Constants.GAMES_DATA_DIRECTORY + File.separator + year;
    }


    public abstract void buildHashtable();


    public abstract void mergeHashtables(Hashtable dest, Hashtable h);


    private ArrayList<File> findHashtablesByNameInYear(File baseDirectory, String hashtableName) {
        ArrayList<File> hashtablesPath = new ArrayList<>();

        // browse by year
        for(File yearDirectory : baseDirectory.listFiles()) {
            if(yearDirectory.isDirectory()) {

                // browse by month
                for(File monthDirectory : yearDirectory.listFiles()) {
                    if(monthDirectory.isDirectory()) {

                        // browse to find hashtables directory
                        for(File fileInMonthDirectory : monthDirectory.listFiles()) {
                            if(fileInMonthDirectory.getName().equals("hashtables")) {

                                // browse to find the right hashtable
                                for(File fileInHashtableDir : fileInMonthDirectory.listFiles()) {
                                    if(fileInHashtableDir.getName().equals(hashtableName + "." + Constants.BINARY_EXTENSION)) {
                                        hashtablesPath.add(fileInHashtableDir);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if(hashtablesPath.size() > 0)
            return hashtablesPath;
        else
            return null;
    }


    public ArrayList<File> findHashtablesByNameInMonth(File baseDirectory, String hashtableName) {
        ArrayList<File> hashtablesPath = new ArrayList<>();

        // browse by month
        for(File monthDirectory : baseDirectory.listFiles()) {
            if(monthDirectory.isDirectory()) {

                // browse to find hashtables directory
                for(File fileInMonthDirectory : monthDirectory.listFiles()) {
                    if(fileInMonthDirectory.getName().equals("hashtables")) {

                        // browse to find the right hashtable
                        for(File fileInHashtableDir : fileInMonthDirectory.listFiles()) {
                            if(fileInHashtableDir.getName().equals(hashtableName + "." + Constants.BINARY_EXTENSION)) {
                                hashtablesPath.add(fileInHashtableDir);
                            }
                        }
                    }
                }
            }

        }

        if(hashtablesPath.size() > 0)
            return hashtablesPath;
        else
            return null;
    }
}
