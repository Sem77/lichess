package app.pgn;

import app.model.Game;

import java.io.*;
import java.util.List;

public class BinaryGameExtractor {
    private List<File> gamesFiles;
    private File currentFile;
    private int indexCurrentFile;
    private ObjectInputStream games;
    private boolean endOfFile = false;

    /**
     * @param gamesFiles list of files to create an hashtable from
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public BinaryGameExtractor(List<File> gamesFiles) throws IOException, ClassNotFoundException {
        this.gamesFiles = gamesFiles;
        indexCurrentFile = 0;
        currentFile = gamesFiles.get(indexCurrentFile);
        games = new ObjectInputStream(new FileInputStream(currentFile));
    }

    /**
     * synchronized because many threads will try to call it
     * this method read a game file and return a GameGameFile object to the thread asking
     * @return GameGameFile which contains a game and the filepath the games is from
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public synchronized GameGameFile getNextGame() throws ClassNotFoundException, IOException {
        try {
            Game game = (Game) games.readObject();
            return new GameGameFile(game, currentFile.getAbsolutePath());
        } catch(EOFException eofe) {
            endOfFile = true;
        }

        /**
         * if we are at the end of the file,
         * calls getNextGames function which returns the next file from gamesFile
         */
        if(endOfFile) {
            games = getNextGames();
            if(games != null) {
                Game game = (Game) games.readObject();
                return new GameGameFile(game, currentFile.getAbsolutePath());
            }
        }

        // if we read the files from gamesFile
        // returns null
        return null;
    }

    /**
     * @return ObjectInputStream a new gameFile from the list of gamesFile
     *         null if there are no more gameFile in the list of gamesFile
     */
    private ObjectInputStream getNextGames() {
        try {
            indexCurrentFile++;
            if (indexCurrentFile < gamesFiles.size()) {
                currentFile = gamesFiles.get(indexCurrentFile);
                games = new ObjectInputStream(new FileInputStream(currentFile));
                return games;
            }
        } catch(IOException fnfe) {
            fnfe.printStackTrace();
        }
        return null;
    }

    // TODO
    // mettre tous les ObjectOutputStream games dans un tableau et les fermer à la fin
    public void closeStream() throws IOException {
        games.close();
    }


    public class GameGameFile {
        public Game game;
        public String pathGameFile;

        public GameGameFile(Game game, String pathGameFile) {
            this.game = game;
            this.pathGameFile = pathGameFile;
        }
    }
}
