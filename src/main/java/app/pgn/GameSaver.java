package app.pgn;

import java.io.*;
import java.text.ParseException;

import app.model.Game;

public class GameSaver {
    private String EXTENSION = "dat";
    private File pathToBinaryFile;
    private ObjectOutputStream binaryFileToWrite;


    public GameSaver(File directoryToSave, int fileNumber) throws IOException {
        String fileName = "lichess_games_binary_" + fileNumber + "." + EXTENSION;
        this.pathToBinaryFile = new File(directoryToSave, fileName);
        this.binaryFileToWrite = new ObjectOutputStream(new FileOutputStream(pathToBinaryFile));
    }


    /**
     * Save a game in the file opened in the constructor
     * @param game
     * @throws ParseException
     * @throws IOException
     */
    public synchronized void saveGame(Game game) throws ParseException, IOException {
        binaryFileToWrite.writeObject(game);
    }

    public synchronized void closeStream() throws IOException {
        binaryFileToWrite.close();
    }
}
