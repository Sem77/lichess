package app.monitor.hashtablemaker.bymonth;

import app.constants.Constants;
import app.pgn.GameExtractorFromPgn;
import app.threadworker.GameWriterThread;

import java.io.*;

/**
 * Class to write in binary files, hashtables or any data structure to optimize the research
 */
public class OptimizationWriter {

    /**
     * Method which monitor the saving of binary versions a games
     * it opens a pgn file, creates threads and launch them
     * the method creates a GameExtractorFromPgn object which takes a pgn file in parameter, and give it in parameter to the threads
     * it also creates a GameSaver object to save the binary version of the games, and give it in parameter to the threads
     * Each thread when running, call the getNextGame method of PgnGameExtractor which returns the next game to save
     * when this returns is not null, the thread call the saveGame of GameSaver to save the game in its binary version
     * @param pathToPgnFile path to the pgn files from which we read the games
     * @param month month in the year the games were played
     * @param year year the games were played
     * @param numberOfThreads number of thread to delegate
     */
    /**
     * On a privilégié cette méthode par rapport à la méthode seek() de RandomAccessFile pour des raisons de sécurité pour des questions de sécurité
     * l'inconvénient avec la méthode seek est que si un seul octet est modifié, toutes les données seront stockées dans la table de hashage seront erronées
     * avec notre méthode, si un petit fichier est erroné, cela ne casse pas complètement le système. On perd juste 20 parties (pas très grave)
     */
    public void saveOptimizedGames(String pathToPgnFile, String month, String year, int numberOfThreads) throws FileNotFoundException {

        File pgnFile = new File(pathToPgnFile);

        String pathToSave = Constants.GAMES_DATA_DIRECTORY + File.separator + year + File.separator + month;
        File directoryToSave = new File(pathToSave);
        if(!directoryToSave.exists()) {
            directoryToSave.mkdirs();
        }

        try {
            GameExtractorFromPgn gameExt = new GameExtractorFromPgn(pgnFile, directoryToSave);

            /////////////////////// Instanciation and launching of the threads ///////////////////////
            GameWriterThread[] gameWriterThreads = new GameWriterThread[numberOfThreads];
            for(int i=0; i<numberOfThreads; i++) {
                gameWriterThreads[i] = new GameWriterThread(gameExt);
            }

            for(int i=0; i<numberOfThreads; i++) {
                gameWriterThreads[i].start();
            }

            for(int i=0; i<numberOfThreads; i++) {
                gameWriterThreads[i].join();
            }
            //////////////////////////////////////////////////////////////////////////////////////////

            gameExt.closeStream(); // closing all streams opened in gameExt
            gameExt.gameSaver.closeStream();

        } catch(IOException ieo) {
            System.out.println("There was an error with the source file");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

}
