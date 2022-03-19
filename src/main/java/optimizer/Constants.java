package optimizer;

import java.util.regex.Pattern;

public class Constants {

    // Regular expressions string
    public static final String REGEX_EVENT = "\\[Event \".+\"\\]";
    public static final String REGEX_SITE = "\\[Site \".+\"\\]";
    public static final String REGEX_WHITE = "\\[White \".+\"\\]";
    public static final String REGEX_BLACK = "\\[Black \".+\"\\]";
    public static final String REGEX_RESULT = "\\[Result \".+\"\\]";
    public static final String REGEX_UTC_DATE = "\\[UTCDate \".+\"\\]";
    public static final String REGEX_UTC_TIME = "\\[UTCTime \".+\"\\]";
    public static final String REGEX_WHITE_ELO = "\\[WhiteElo \".+\"\\]";
    public static final String REGEX_BLACK_ELO = "\\[BlackElo \".+\"\\]";
    public static final String REGEX_WHITE_RATING_DIFF = "\\[WhiteRatingDiff \".+\"\\]";
    public static final String REGEX_BLACK_RATING_DIFF = "\\[BlackRatingDiff \".+\"\\]";
    public static final String REGEX_ECO = "\\[ECO \".+\"\\]";
    public static final String REGEX_OPENING = "\\[Opening \".+\"\\]";
    public static final String REGEX_TIME_CONTROL = "\\[TimeControl \".+\"\\]";
    public static final String REGEX_TERMINATION = "\\[Termination \".+\"\\]\\" + "n\\" + "n.+";
    public static final String REGEX_STROKES = "\\" + "n\\" + "n.+[01(1/2)]";

    // Regular expressions compiled
    public static final Pattern[] regExCompiled = {
            Pattern.compile(REGEX_EVENT),
            Pattern.compile(REGEX_SITE),
            Pattern.compile(REGEX_WHITE),
            Pattern.compile(REGEX_BLACK),
            Pattern.compile(REGEX_RESULT),
            Pattern.compile(REGEX_UTC_DATE),
            Pattern.compile(REGEX_UTC_TIME),
            Pattern.compile(REGEX_WHITE_ELO),
            Pattern.compile(REGEX_BLACK_ELO),
            Pattern.compile(REGEX_WHITE_RATING_DIFF),
            Pattern.compile(REGEX_BLACK_RATING_DIFF),
            Pattern.compile(REGEX_ECO),
            Pattern.compile(REGEX_OPENING),
            Pattern.compile(REGEX_TIME_CONTROL),
            Pattern.compile(REGEX_TERMINATION),
            Pattern.compile(REGEX_STROKES),
    };


    public static final int NUMBER_OF_GAMES_PER_FILE = 20;

    public static final String ROOT_DATA = "/home/ubuntu/IdeaProjects/lichess";
    public static final String GAMES_DATA_DIRECTORY = ROOT_DATA + "/data_dest/games_data";
    public static final String GAMES_HASTABLES = ROOT_DATA + "/data_dest/hashtables";
    public static final String A_PLAYER_S_GAMES = ROOT_DATA + GAMES_HASTABLES + "/a_player_games"; // directory where hashtables where we find the games of a player
}
