package app.monitor.hashtablemaker;

import app.constant.Constants;

import java.io.File;
import java.util.ArrayList;

public interface HashtableFinderAllInterface {

    default ArrayList<File> findHashtablesByNameInYear(File baseDirectory, String hashtableName) {
        ArrayList<File> hashtablesPath = new ArrayList<>();

        // browse by year
        for(File yearDirectory : baseDirectory.listFiles()) {
            if(yearDirectory.isDirectory()) {

                // browse to find the right hashtable
                for(File fileInHashtableDir : yearDirectory.listFiles()) {
                    if(fileInHashtableDir.getName().equals(hashtableName + "." + Constants.BINARY_EXTENSION)) {
                        hashtablesPath.add(fileInHashtableDir);
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
