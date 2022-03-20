package optimizationapp.pgn;

import optimizationapp.model.Game;
import optimizationapp.optimizer.Constants;

import java.io.*;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameExtractorFromPgn {
    private BufferedReader pgnFile;
    private String gameString;
    private File directoryToSave;
    public GameSaver gameSaver;
    private int fileNumber;
    private int maxGamePerFile = 0;
    private int n = 0;

    public GameExtractorFromPgn(File pgnFilePath, File directoryToSave) throws IOException {
        pgnFile = new BufferedReader(new InputStreamReader(new FileInputStream(pgnFilePath)));
        fileNumber = 0;

        this.directoryToSave = directoryToSave;
        gameSaver = new GameSaver(directoryToSave, fileNumber);
    }


    /**
     * build a Game object from a String
     * generally extracted from a pgn file
     * @param gameString
     * @return Game object
     * @throws ParseException
     */
    public static Game gameStringToGameObject(String gameString) throws ParseException {
        String[] out = new String[Constants.regExCompiled.length];

		/*String[] regExs = {
			"\\[Event \".+\"\\]", // regExEvent
			"\\[Site \".+\"\\]", // regExSite
			"\\[White \".+\"\\]", // regExWhite
			"\\[Black \".+\"\\]", // regExBlack
			"\\[Result \".+\"\\]", // regExResult
			"\\[UTCDate \".+\"\\]", // regExUTCDate
			"\\[UTCTime \".+\"\\]", // regExUTCTime
			"\\[WhiteElo \".+\"\\]", // regExWhiteElo
			"\\[BlackElo \".+\"\\]", // regExBlackElo
			"\\[WhiteRatingDiff \".+\"\\]", // regExWhiteRatingDiff
			"\\[BlackRatingDiff \".+\"\\]", // regExBlackRatingDiff
			"\\[ECO \".+\"\\]", // regExECO
			"\\[Opening \".+\"\\]", // regExOpening
			"\\[TimeControl \".+\"\\]", // regExTimeControl
			"\\[Termination \".+\"\\]", // regExTermination
			"\\" + "n\\" + "n.+[01(1/2)]" // regExSteps
		};*/

        String regExValue = "\".+\"";
        Pattern regExValueCompiled = Pattern.compile(regExValue);

        Matcher[] matchers = new Matcher[Constants.regExCompiled.length];
        for(int i=0; i<Constants.regExCompiled.length; i++) {
            matchers[i] = Constants.regExCompiled[i].matcher(gameString);
        }

        Matcher m;

        for(int i=0; i<Constants.regExCompiled.length-1; i++) {
            if(matchers[i].find()) {
                m = regExValueCompiled.matcher(matchers[i].group());
                m.find();
                String info = m.group();
                out[i] = (info.substring(1, info.length() - 1)); // remove <"><">
            }
            else {
                out[i] = null;
            }
        }

        try {
            matchers[Constants.regExCompiled.length - 1].find();
            out[Constants.regExCompiled.length - 1] = (matchers[Constants.regExCompiled.length - 1].group().substring(2));
        } catch(IllegalStateException ise) {
            return null;
        }

        Game game = null;

        try {
            game = new Game(out[0], out[1], out[2], out[3], out[4], out[5], out[6],
                    out[7], out[8], out[9], out[10], out[11], out[12], out[13], out[14], out[15]);
        } catch(IndexOutOfBoundsException iobe) {
            System.out.println(gameString);
        }

        return game;
    }


    /**
     * find the next chess game in the file
     * @return the game String found
     * @throws IOException
     */
    public synchronized String findNextGame() throws IOException {
        gameString = "";
        String line;
        int nbEmptyLine = 0;
        do {
            line = pgnFile.readLine();
            if(line == null)
                return "";
            if(line.compareTo("") == 0)
                nbEmptyLine++;
            gameString += line + "\n";
        } while(nbEmptyLine < 2);
        gameString = gameString.substring(0, gameString.length()-1); // remove the last <"\n">

        return gameString;
    }

    /**
     * @return the next game found in the file to the thread asking
     */
    public synchronized GameGameSaver getNextGame() throws IOException {
        String game = findNextGame();
        if(game.compareTo("") != 0) {
            if (maxGamePerFile >= Constants.NUMBER_OF_GAMES_PER_FILE) {
                maxGamePerFile = 0;
                fileNumber++;
                //gameSaver.closeStream();
                // TODO
                // stocker les gameSaver dans un tableau et les fermer après
                gameSaver = new GameSaver(directoryToSave, fileNumber);
            }
            maxGamePerFile++;
            return new GameGameSaver(game, gameSaver);
        }
        return null;
    }

    public void closeStream() throws IOException {
        pgnFile.close();
    }


    public class GameGameSaver {
        public String game;
        public GameSaver gameSaver;

        public GameGameSaver(String game, GameSaver gameSaver) {
            this.game = game;
            this.gameSaver = gameSaver;
        }
    }
}
