package monitor;

import exception.NoFileDataFoundException;
import optimizer.Constants;
import pgn.BinaryGameExtractor;
import pgn.GameExtractorFromPgn;
import threadworker.GameWriterThread;
import threadworker.MostPlayedOpeningWriterThread;
import threadworker.PlayerGamesWriterThread;
import threadworker.ShortestGamesWriterThread;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

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


    /**
     * Method which monitor the creation of the hashtable which matches a player to the games he played
     *
     * @param month month in the year the games were played
     * @param year year the games were played
     * @param numberOfThreads to delegate
     * @throws NoFileDataFoundException
     */
    public void saveAssociationPlayerGames(String month, String year, int numberOfThreads) throws NoFileDataFoundException {
        String pathToRead = Constants.GAMES_DATA_DIRECTORY + File.separator + year + File.separator + month;
        File directoryToRead = new File(pathToRead); // Directory where the gameFiles are stored

        // if the directory doesn't exist, throw a NoFileDataFoundException and quit
        if(!directoryToRead.exists()) {
            throw new NoFileDataFoundException("There is no data saved for the year or the month specified");
        }

        ArrayList<File> gamesFiles = new ArrayList<>(Arrays.asList(directoryToRead.listFiles())); // list of all the files that are in directoryToRead

        // removing the directories from the gamesFiles and keeping only the files
        for(int i=0; i<gamesFiles.size(); i++) {
            if(!gamesFiles.get(i).isFile())
                gamesFiles.remove(i);
        }

        // if there is no file found, throw a NoFileDataFoundException and quit
        if(gamesFiles.isEmpty()) {
            throw new NoFileDataFoundException("No file data found in the year and month specified");
        }

        try {
            BinaryGameExtractor gamesExtractor = new BinaryGameExtractor(gamesFiles);

            File hashTableDirectory = new File(directoryToRead, File.separator + "hashtables");

            if(!hashTableDirectory.exists()) {
                hashTableDirectory.mkdirs();
            }

            File hashTableFile = new File(hashTableDirectory, "a_player_game_hashtable.dat");
            Hashtable<String, ArrayList<String>> a_player_game_hashtable = new Hashtable<>();

            /*if(hashTableFile.exists()) {
                ObjectInputStream hashTableInputStream = new ObjectInputStream(new FileInputStream(hashTableFile));
                a_player_game_hashtable = (Hashtable<String, ArrayList<String>>) hashTableInputStream.readObject();
                hashTableInputStream.close();
            }
            else {
                a_player_game_hashtable = new Hashtable<>();
            }*/

            /////////////////////// Instanciation and launching of the threads ///////////////////////
            PlayerGamesWriterThread[] playerGamesWriterThreads = new PlayerGamesWriterThread[numberOfThreads];
            for(int i=0; i<numberOfThreads; i++) {
                playerGamesWriterThreads[i] = new PlayerGamesWriterThread(gamesExtractor, a_player_game_hashtable);
            }

            for(int i=0; i<numberOfThreads; i++) {
                playerGamesWriterThreads[i].start();
            }

            for(int i=0; i<numberOfThreads; i++) {
                playerGamesWriterThreads[i].join();
            }
            /////////////////////////////////////////////////////////////////////////////////////////

            //gamesExtractor.closeStream();

            // Save the hashtable in a file
            ObjectOutputStream hashTableOutputStream = new ObjectOutputStream(new FileOutputStream(hashTableFile));
            hashTableOutputStream.writeObject(a_player_game_hashtable);
            hashTableOutputStream.close();

        } catch (FileNotFoundException fnfe) {
            System.out.println("Source file not found");
        } catch (IOException ioe) {
            System.out.println("There was an error with the file");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class not found");
        } catch(InterruptedException ie) {
            ie.printStackTrace();
        }
    }


    public void saveShortestGames(String month, String year, int numberOfThreads) throws NoFileDataFoundException {
        String pathToRead = Constants.GAMES_DATA_DIRECTORY + File.separator + year + File.separator + month;
        File directoryToRead = new File(pathToRead); // Directory where the gameFiles are stored

        // if the directory doesn't exist, throw a NoFileDataFoundException and quit
        if(!directoryToRead.exists()) {
            throw new NoFileDataFoundException("There is no data saved for the year or the month specified");
        }

        ArrayList<File> gamesFiles = new ArrayList<>(Arrays.asList(directoryToRead.listFiles())); // list of all the files that are in directoryToRead

        // removing the directories from the gamesFiles list and keeping only the files
        for(int i=0; i<gamesFiles.size(); i++) {
            if(!gamesFiles.get(i).isFile())
                gamesFiles.remove(i);
        }

        // if there is no file found, throw a NoFileDataFoundException and quit
        if(gamesFiles.isEmpty()) {
            throw new NoFileDataFoundException("No file data found in the year and month specified");
        }

        try {
            BinaryGameExtractor gamesExtractor = new BinaryGameExtractor(gamesFiles);

            File hashTableDirectory = new File(directoryToRead, File.separator + "hashtables");

            if(!hashTableDirectory.exists()) {
                hashTableDirectory.mkdirs();
            }

            File hashTableFile = new File(hashTableDirectory, "shortest_games_hashtable.dat");
            Hashtable<Integer, ArrayList<String>> shortest_games_hashtable = new Hashtable<>();

            // Creation of the threads and launching
            ShortestGamesWriterThread[] shortestGamesWriterThread = new ShortestGamesWriterThread[numberOfThreads];
            for(int i=0; i<numberOfThreads; i++) {
                shortestGamesWriterThread[i] = new ShortestGamesWriterThread(gamesExtractor, shortest_games_hashtable);
            }

            for(int i=0; i<numberOfThreads; i++) {
                shortestGamesWriterThread[i].start();
            }

            for(int i=0; i<numberOfThreads; i++) {
                shortestGamesWriterThread[i].join();
            }

            //gamesExtractor.closeStream();

            // Save the hashtable in a file
            ObjectOutputStream hashTableOutputStream = new ObjectOutputStream(new FileOutputStream(hashTableFile));
            hashTableOutputStream.writeObject(shortest_games_hashtable);
            hashTableOutputStream.close();

        } catch (FileNotFoundException fnfe) {
            System.out.println("Source file not found");
        } catch (IOException ioe) {
            System.out.println("There was an error with the file");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class not found");
        } catch(InterruptedException ie) {
            ie.printStackTrace();
        }
    }


    public void saveMostPlayedOpening(String month, String year, int numberOfThreads) throws NoFileDataFoundException {
        String pathToRead = Constants.GAMES_DATA_DIRECTORY + File.separator + year + File.separator + month;
        File directoryToRead = new File(pathToRead); // Directory where the gameFiles are stored

        // if the directory doesn't exist, throw a NoFileDataFoundException and quit
        if(!directoryToRead.exists()) {
            throw new NoFileDataFoundException("There is no data saved for the year or the month specified");
        }

        ArrayList<File> gamesFiles = new ArrayList<>(Arrays.asList(directoryToRead.listFiles())); // list of all the files that are in directoryToRead

        // removing the directories from the gamesFiles and keeping only the files
        for(int i=0; i<gamesFiles.size(); i++) {
            if(!gamesFiles.get(i).isFile())
                gamesFiles.remove(i);
        }

        // if there is no file found, throw a NoFileDataFoundException and quit
        if(gamesFiles.isEmpty()) {
            throw new NoFileDataFoundException("No file data found in the year and month specified");
        }

        try {
            BinaryGameExtractor gamesExtractor = new BinaryGameExtractor(gamesFiles);

            File hashTableDirectory = new File(directoryToRead, File.separator + "hashtables");

            if(!hashTableDirectory.exists()) {
                hashTableDirectory.mkdirs();
            }

            File hashTableFile = new File(hashTableDirectory, "most_played_opening_games_hashtable.dat");
            Hashtable<String, Integer> most_played_opening_games_hashtable = new Hashtable<>();

            // Creation of the threads and launching
            MostPlayedOpeningWriterThread[] mostPlayedOpeningWriterThreads = new MostPlayedOpeningWriterThread[numberOfThreads];
            for(int i=0; i<numberOfThreads; i++) {
                mostPlayedOpeningWriterThreads[i] = new MostPlayedOpeningWriterThread(gamesExtractor, most_played_opening_games_hashtable);
            }

            for(int i=0; i<numberOfThreads; i++) {
                mostPlayedOpeningWriterThreads[i].start();
            }

            for(int i=0; i<numberOfThreads; i++) {
                mostPlayedOpeningWriterThreads[i].join();
            }

            //gamesExtractor.closeStream();

            // Save the hashtable in a file
            ObjectOutputStream hashTableOutputStream = new ObjectOutputStream(new FileOutputStream(hashTableFile));
            hashTableOutputStream.writeObject(most_played_opening_games_hashtable);
            hashTableOutputStream.close();

        } catch (FileNotFoundException fnfe) {
            System.out.println("Source file not found");
        } catch (IOException ioe) {
            System.out.println("There was an error with the file");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class not found");
        } catch(InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
