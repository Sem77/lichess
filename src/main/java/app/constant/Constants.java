package app.constant;

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

    public static final String GAME_LINK = "game_link_hashtable";
    public static final String GAME_LINK_OVER_A_YEAR = "game_link_over_a_year_hashtable";
    public static final String GAME_LINK_ALL = "game_link_all_hashtable";

    public static final String A_PLAYER_GAME = "a_player_game_hastable";
    public static final String A_PLAYER_GAME_OVER_A_YEAR = "a_player_game_over_a_year_hastable";
    public static final String A_PLAYER_GAME_ALL = "a_player_game_all_hastable";

    public static final String ORDER_SHORTEST_GAMES = "shortest_games_in_order";
    public static final String SHORTEST_GAMES = "shortest_games_hashtable";
    public static final String SHORTEST_GAMES_OVER_A_YEAR = "shortest_games_over_a_year_hashtable";
    public static final String SHORTEST_GAMES_ALL = "shortest_games_all_hashtable";

    public static final String MOST_PLAYED_OPENING_GAMES = "most_played_opening_games_hashtable";
    public static final String MOST_PLAYED_OPENING_GAMES_OVER_A_YEAR = "most_played_opening_games_over_a_year_hashtable";
    public static final String MOST_PLAYED_OPENING_GAMES_ALL = "most_played_opening_games_all_hashtable";
    public static final String ORDER_MOST_PLAYED_OPENING_GAMES_ALL = "most_played_opening_games_in_order_all";
    public static final String ORDER_MOST_PLAYED_OPENING_GAMES_OVER_A_YEAR = "most_played_opening_games_in_order_over_a_year";
    public static final String ORDER_MOST_PLAYED_OPENING_GAMES_OVER_A_MONTH = "most_played_opening_games_in_order_over_a_month";

    public static final String MOST_ACTIVE_PLAYERS = "most_active_players_hashtable";
    public static final String MOST_ACTIVE_PLAYERS_OVER_A_YEAR = "most_active_players_over_a_year_hashtable";
    public static final String MOST_ACTIVE_PLAYERS_ALL = "most_active_players_all_hashtable";
    public static final String ORDER_MOST_ACTIVE_PLAYERS_ALL = "most_active_players_in_order_all";
    public static final String ORDER_MOST_ACTIVE_PLAYERS_OVER_A_YEAR = "most_active_players_in_order_over_a_year";
    public static final String ORDER_MOST_ACTIVE_PLAYERS_OVER_A_MONTH = "most_active_players_in_order_over_a_month";

    public static final String PLAYERS_INFO = "players_info_hashtable";
    public static final String PLAYERS_INFO_OVER_A_YEAR = "players_info_over_a_year";
    public static final String PLAYERS_INFO_ALL = "players_info_all";
    public static final String ORDER_BEST_PLAYERS_ALL = "best_players_in_order_all";
    public static final String ORDER_BEST_PLAYERS_OVER_A_YEAR = "best_players_in_order_over_a_year";

    public static final int THREADS_DELEGATED = 4;
    public static final int NUMBER_OF_GAMES_PER_FILE = 20;
    public static final String BINARY_EXTENSION = "dat";
    public static final String ROOT_DATA = "/home/ubuntu/IdeaProjects/database";
    public static final String GAMES_DATA_DIRECTORY = ROOT_DATA + "/data_dest/games_data";
    public static final String GAMES_HASTABLES = ROOT_DATA + "/data_dest/hashtables";
}
