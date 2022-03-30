package app.monitor.hashtablemakerMonth;

import app.exception.NoFileDataFoundException;
import app.optimizer.Constants;
import app.pgn.BinaryGameExtractor;
import app.threadworker.ThreadWorker;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public abstract class HashtableMaker {
    public String month;
    public String year;
    public String hashTableName;
    public String threadWorkerName;
    public Hashtable hashtable;

    public HashtableMaker(String month, String year, String threadWorkerName, Hashtable hashtable, String hashTableName) {
        this.month = month;
        this.year = year;
        this.hashTableName = hashTableName;
        this.threadWorkerName = threadWorkerName;
        this.hashtable = hashtable;
    }

    /**
     * build the hashtable
     * @throws NoFileDataFoundException
     * @throws NoSuchMethodException
     */
    public void buildHastable() throws NoFileDataFoundException, NoSuchMethodException {
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

            File hashTableFile = new File(hashTableDirectory, hashTableName + "." + Constants.BINARY_EXTENSION);

            /////////////////////// Instanciation and launching of the threads ///////////////////////
            ArrayList<ThreadWorker> threadWorkers = new ArrayList<>();
            for(int i=0; i<Constants.THREADS_DELEGATED; i++) {
                Class<?> threadWorker = Class.forName(threadWorkerName);
                Constructor<?> constructor = threadWorker.getConstructor(BinaryGameExtractor.class, Hashtable.class);
                threadWorkers.add((ThreadWorker) constructor.newInstance(gamesExtractor, hashtable));
            }

            for(int i=0; i<Constants.THREADS_DELEGATED; i++) {
                threadWorkers.get(i).start();
            }

            for(int i=0; i<Constants.THREADS_DELEGATED; i++) {
                threadWorkers.get(i).join();
            }
            /////////////////////////////////////////////////////////////////////////////////////////

            //gamesExtractor.closeStream();

            // Save the hashtable in a file
            ObjectOutputStream hashTableOutputStream = new ObjectOutputStream(new FileOutputStream(hashTableFile));
            hashTableOutputStream.writeObject(hashtable);
            hashTableOutputStream.close();

        } /*catch (FileNotFoundException fnfe) {
            System.out.println("Source file not found");
        }*/ catch (IOException ioe) {
            System.out.println("There was an error with the file");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class not found");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } catch(InstantiationException ie) {
            System.out.println("Problème lors de l'instanciation des threadWorker");
        } catch (InvocationTargetException ite) {
            System.out.println("Problème lors de l'instanciation des threadWorker");
        } catch (IllegalAccessException iae) {
            System.out.println("Problème lors de l'instanciation des threadWorker");
        }
    }
}
