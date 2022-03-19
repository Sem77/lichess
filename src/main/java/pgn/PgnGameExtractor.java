package pgn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import model.Game;
import optimizer.Constants;


public class PgnGameExtractor {
    // regular expression matching a whole game
    private String regEx = "\\[Event \".+\"\\]\\" +
            "n\\[Site \".+\"\\]\\" +
            "n\\[White \".+\"\\]\\" +
            "n\\[Black \".+\"\\]\\" +
            "n\\[Result \".+\"\\]\\" +
            "n\\[UTCDate \".+\"\\]\\" +
            "n\\[UTCTime \".+\"\\]\\" +
            "n\\[WhiteElo \".+\"\\]\\" +
            "n\\[BlackElo \".+\"\\]\\" +
            "n\\[WhiteRatingDiff \".+\"\\]\\" +
            "n\\[BlackRatingDiff \".+\"\\]\\" +
            "n\\[ECO \".+\"\\]\\" +
            "n\\[Opening \".+\"\\]\\" +
            "n\\[TimeControl \".+\"\\]\\" +
            "n\\[Termination \".+\"\\]\\" + "n\\" + "n.+";
    Pattern gameRegEx = Pattern.compile(regEx);

    private File directoryToSave;
    private FileInputStream pgnFile;
    public GameSaver gameSaver;
    private int fileNumber;
    private int maxGamePerFile = 0;
    // getting strings matching a game
    private Matcher gameRegExMatcher;

    public PgnGameExtractor(String pathToPgnFile) throws IOException {
        this.pgnFile = new FileInputStream(pathToPgnFile);
        this.gameRegExMatcher = gameRegEx.matcher(fromFile(pgnFile));
        pgnFile.close();
    }


    public PgnGameExtractor(String pathToPgnFile, File directoryToSave) throws IOException {
        this.pgnFile = new FileInputStream(pathToPgnFile);
        this.gameRegExMatcher = gameRegEx.matcher(fromFile(pgnFile));
        pgnFile.close();

        fileNumber = 0;
        this.directoryToSave = directoryToSave;
        gameSaver = new GameSaver(directoryToSave, fileNumber);
    }


    /** Convert a file to a CharSequence*/
    private CharSequence fromFile(FileInputStream fis) throws IOException {
        FileChannel fc = fis.getChannel();

        // Create a read-only CharBuffer on the file
        ByteBuffer bbuf = fc.map(FileChannel.MapMode.READ_ONLY, 0, (int) fc.size());
        CharBuffer cbuf = Charset.forName("8859_1").newDecoder().decode(bbuf);
        return cbuf;
    }


    /**
     * @brief Extraction every games from a pgn file
     * @return ArrayList<String> containing the games
     * @throws IOException
     */
    /*
    public ArrayList<String> extractGamesStringFromFile() throws IOException {
        FileInputStream pgnFile = new FileInputStream(pathToPgnFile); // Loading input file (pgn file)

        Pattern gameRegEx = Pattern.compile(regEx);

        // getting strings matching a game
        Matcher gameRegExMatcher = gameRegEx.matcher(fromFile(pgnFile));

        pgnFile.close();

        ArrayList<String> out = new ArrayList<>();

        // storing each game in array
        while(gameRegExMatcher.find())
            out.add(gameRegExMatcher.group());

        return out;
    }*/


    /*
    public ArrayList<Game> extractGamesFromFile() throws IOException {
        FileInputStream pgnFile = new FileInputStream(pathToPgnFile); // Loading input file (pgn file)

        Pattern gameRegEx = Pattern.compile(regEx);

        // getting strings matching a game
        Matcher gameRegExMatcher = gameRegEx.matcher(fromFile(pgnFile));

        pgnFile.close();

        ArrayList<Game> out = new ArrayList<>();

        // storing each game in an array
        while(gameRegExMatcher.find()) {
            try {
                out.add(extractGameInfo(gameRegExMatcher.group()));
            } catch(ParseException pe) {
                pe.printStackTrace();
            }
        }
        return out;
    }*/


    public static Game extractGameInfo(String gameString) throws ParseException {

        ArrayList<String> out = new ArrayList<String>();

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

        String regExGame = "\\" + "n\\" + "n.+[01(1/2)]";
        Pattern regExStrokesCompiled = Pattern.compile(regExGame);

        Matcher m = regExValueCompiled.matcher(gameString);
        while(m.find()) {
            String info = m.group();
            out.add(info.substring(1, info.length()-1)); // remove <"><">
        }

        m = regExStrokesCompiled.matcher(gameString);
        while(m.find())
            out.add(m.group().substring(2));

        Game game;

        if(out.size() == 16) {
            game = new Game(out.get(0), out.get(1), out.get(2), out.get(3), out.get(4), out.get(5), out.get(6),
                    out.get(7), out.get(8), out.get(9), out.get(10), out.get(11), out.get(12), out.get(13), out.get(14), out.get(15));
        }
        // In case there are not whiteRatingDiff and blackRatingDiff
        // some games can miss them
        else {
            game = new Game(out.get(0), out.get(1), out.get(2), out.get(3), out.get(4), out.get(5), out.get(6),
                    out.get(7), out.get(8), out.get(9), out.get(10), out.get(11), out.get(12), out.get(13));
        }

        return game;
    }


    /**
     * @return the next game found in the file
    */
    public synchronized GameGameSaver getNextGame() throws IOException {
        if(gameRegExMatcher.find()) {
            if(maxGamePerFile >= Constants.NUMBER_OF_GAMES_PER_FILE) {
                maxGamePerFile = 0;
                fileNumber++;
                //gameSaver.closeStream();
                //TODO
                // stocker les gameSaver dans un tableau et les fermer après
                gameSaver = new GameSaver(directoryToSave, fileNumber);
            }
            maxGamePerFile++;
            return new GameGameSaver(gameRegExMatcher.group(), gameSaver);
        }
        return null;
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
