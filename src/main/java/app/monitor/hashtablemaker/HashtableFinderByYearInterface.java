package app.monitor.hashtablemaker;

import app.constant.Constants;

import java.io.File;
import java.util.ArrayList;

public interface HashtableFinderByYearInterface {
    /**
     * Parcours tous les mois d'une année spécifique à la recherche des tables de hashage ayant le nom "hashtableName"
     * et les regroupe dans une unique table de hashage
     * @param baseDirectory dossier d'une année et d'un mois dans lequel chercher la table de hashage
     * @param hashtableName nom de la table de hashage à charger et à exploiter
     * @return ArrayList des chemins menant vers ces tables de hashage
     */
    default ArrayList<File> findHashtablesByNameInMonth(File baseDirectory, String hashtableName) {
        ArrayList<File> hashtablesPath = new ArrayList<>();

        // liste les dossiers représentant les mois
        for(File monthDirectory : baseDirectory.listFiles()) {
            if(monthDirectory.isDirectory()) {

                // parcours chaque
                for(File fileInMonthDirectory : monthDirectory.listFiles()) {
                    if(fileInMonthDirectory.getName().equals("hashtables")) {

                        // cherche la table de hashage
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
