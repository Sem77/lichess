import app.constant.Constants;
import app.exception.NoFileDataFoundException;
import app.model.Game;
import app.monitor.hashtablemaker.all.*;
import app.monitor.hashtablemaker.bymonth.*;
import app.monitor.hashtablemaker.byyear.*;
import app.pgn.GameExtractorFromPgn;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]) throws IOException, ClassNotFoundException, ParseException, NoFileDataFoundException, NoSuchMethodException {

        String pgn01 = "/home/ubuntu/IdeaProjects/database/data_src/lichess_db_standard_rated_2013-01.pgn";
        String pgn02 = "/home/ubuntu/IdeaProjects/database/data_src/lichess_db_standard_rated_2013-02.pgn";
        String pgn03 = "/home/ubuntu/IdeaProjects/database/data_src/lichess_db_standard_rated_2013-03.pgn";
        String pgn04 = "/home/ubuntu/IdeaProjects/database/data_src/lichess_db_standard_rated_2013-04.pgn";
        String pgn05 = "/home/ubuntu/IdeaProjects/database/data_src/lichess_db_standard_rated_2013-05.pgn";
        String pgn06 = "/home/ubuntu/IdeaProjects/database/data_src/lichess_db_standard_rated_2013-06.pgn";
        String pgn07 = "/home/ubuntu/IdeaProjects/database/data_src/lichess_db_standard_rated_2013-07.pgn";

        //ArrayList<String> pgnPaths = new ArrayList<>();
        //pgnPaths.add(pgn01); pgnPaths.add(pgn02); pgnPaths.add(pgn03); pgnPaths.add(pgn04); pgnPaths.add(pgn05); pgnPaths.add(pgn06); pgnPaths.add(pgn07);

        OptimizationWriter ow = new OptimizationWriter();
        //ow.saveOptimizedGames(pgn01, "01", "2013", 4);
        //ow.saveOptimizedGames(pgn02, "02", "2013", 4);
        //ow.saveOptimizedGames(pgn03, "03", "2013", 4);
        //ow.saveOptimizedGames(pgn04, "04", "2013", 4);
        //ow.saveOptimizedGames(pgn05, "05", "2013", 4);
        //ow.saveOptimizedGames(pgn06, "06", "2013", 4);
        //ow.saveOptimizedGames(pgn07, "07", "2013", 4);


        AssociationPlayerGamesMonitor apg = new AssociationPlayerGamesMonitor("03", "2014");
        MostActivePlayerMonitor mapom = new MostActivePlayerMonitor("02", "2013");
        MostPlayedOpeningMonitor mpom = new MostPlayedOpeningMonitor("02", "2013");
        ShortestGamesMonitor sgm = new ShortestGamesMonitor("02", "2013");
        PlayerInfoWriter piw = new PlayerInfoWriter("02", "2013");
        FindGameWithLink fgwl = new FindGameWithLink("02", "2013");


        //apg.buildHastable();
        //mapom.buildHastable();
        //mpom.buildHastable();
        //sgm.buildHastable();
        //piw.buildHastable();
        //fgwl.buildHastable();

        //mpom.saveNMostPlayedOpening();
        //mapom.saveNMostActivePlayers();


        AssociationPlayerGamesYear apgy = new AssociationPlayerGamesYear("2013");
        MostPlayedOpeningYear mpoy = new MostPlayedOpeningYear("2013");
        MostActivePlayerYear mapy = new MostActivePlayerYear("2013");
        ShortestGamesYear sgy = new ShortestGamesYear("2013");
        PlayerInfoYear piy = new PlayerInfoYear("2013");
        FindGameWithLinkYear fgwly = new FindGameWithLinkYear("2013");

        //apgy.buildHashtable();
        //mpoy.buildHashtable();
        //mapy.buildHashtable();
        //sgy.buildHashtable();
        //piy.buildHashtable();
        //fgwly.buildHashtable();

        //mpoy.saveNMostPlayedOpening();
        //mapy.saveNMostActivePlayers();



        MostPlayedOpeningAll mpoa = new MostPlayedOpeningAll();
        ShortestGamesAll sga = new ShortestGamesAll();
        AssociationPlayerGamesAll apga = new AssociationPlayerGamesAll();
        MostActivePlayerAll mapa = new MostActivePlayerAll();
        PlayerInfoAll pia = new PlayerInfoAll();
        FindGameWithLinkAll fgwla = new FindGameWithLinkAll();


        //mpoa.buildHashtable();
        //sga.buildHashtable();
        //apga.buildHashtable();
        //mapa.buildHashtable();
        //pia.buildHashtable();
        //fgwla.buildHashtable();

        //mpoa.saveNMostPlayedOpening();
        //sga.saveFiveShortestGames();
        //mapa.saveNMostActivePlayers();
        //pia.saveBestPlayers();








        //buildHashtablesGamesOfAPlayer();
        //buildHashtablesMostPlayedOpening();
        //buildHashtablesMostActivePlayers();
        //buildHashtablesShortestGames();
        //buildHashtablesPlayerInfo();
        buildHashtablesToFindGameWithLink();


        /*ow.pageRankCalculator("/home/ubuntu/IdeaProjects/database/data_dest/games_data/2013/02/hashtables/players_info_hashtable.dat");

        ObjectInputStream o = new ObjectInputStream(new FileInputStream("/home/ubuntu/IdeaProjects/database/data_dest/games_data/2013/02/hashtables/players_info_hashtable.dat"));

        try {
            Hashtable<String, Player> h = (Hashtable<String, Player>) o.readObject();
            System.out.println(h);
        } catch(EOFException eofe) {}
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        o.close();*/

        /*ObjectInputStream o = new ObjectInputStream(new FileInputStream("/home/ubuntu/IdeaProjects/database/data_dest/games_data/2013/game_link_over_a_year_hashtable.dat"));

            Hashtable<String, String> h = (Hashtable<String, String>) o.readObject();
            System.out.println(h.get("https://lichess.org/k2udryk3"));*/


        //buildBinaryGames("/home/ubuntu/IdeaProjects/database/data_src/games_pgn");
        //extract1000FirstGames("/home/ubuntu/IdeaProjects/database/data_src", "lichess_db_standard_rated_2014-02.pgn", "/home/ubuntu/IdeaProjects/database/data_src/games_pgn/2014/02");

    }

    static void buildBinaryGames(String basePath) {

        OptimizationWriter ow = new OptimizationWriter();
        File basePathFile = new File(basePath);

        for(File yearDir : basePathFile.listFiles()) {
            if(yearDir.isDirectory()) {
                String year = yearDir.getName();

                for(File monthDir : yearDir.listFiles()) {
                    if(monthDir.isDirectory()) {
                        String month = monthDir.getName();

                        for(File pgnFile : monthDir.listFiles()) {
                            if(pgnFile.getName().endsWith(".pgn")) {
                                try {
                                    ow.saveOptimizedGames(pgnFile.getAbsolutePath(), month, year, 4);
                                } catch(FileNotFoundException fnfe) {
                                    System.out.println("\033[0;31mLe fichier " + pgnFile.getAbsolutePath() + " n'a pas été trouvé\033[0m");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    static void buildHashtablesGamesOfAPlayer() {
        File basePathFile = new File(Constants.GAMES_DATA_DIRECTORY);

        // Construction des tables de hashage pour chacun des mois
        for(File yearDir : basePathFile.listFiles()) {
            if(yearDir.isDirectory()) {
                String year = yearDir.getName();

                for(File monthDir : yearDir.listFiles()) {
                    if(monthDir.isDirectory()) {
                        String month = monthDir.getName();

                        try {
                            AssociationPlayerGamesMonitor apg = new AssociationPlayerGamesMonitor(month, year);
                            apg.buildHastable();
                        } catch(NoFileDataFoundException | NoSuchMethodException nfdfe) {
                            System.out.println("Aucun fichier binaire de jeux n'a été trouvé pour le mois " + month + " de l'année " + year);
                        }
                    }
                }
            }
        }

        // Construction des tables de hashage pour chacunes des années
        for(File yearDir : basePathFile.listFiles()) {
            if(yearDir.isDirectory()) {
                String year = yearDir.getName();

                AssociationPlayerGamesYear apgy = new AssociationPlayerGamesYear(year);
                apgy.buildHashtable();
            }
        }

        // Construction des tables de hashage pour tout
        AssociationPlayerGamesAll apga = new AssociationPlayerGamesAll();
        apga.buildHashtable();
    }

    static void buildHashtablesMostPlayedOpening() {
        File basePathFile = new File(Constants.GAMES_DATA_DIRECTORY);

        // Construction des tables de hashage pour chacun des mois
        for(File yearDir : basePathFile.listFiles()) {
            if(yearDir.isDirectory()) {
                String year = yearDir.getName();

                for(File monthDir : yearDir.listFiles()) {
                    if(monthDir.isDirectory()) {
                        String month = monthDir.getName();

                        try {
                            MostPlayedOpeningMonitor mpom = new MostPlayedOpeningMonitor(month, year);
                            mpom.buildHastable();
                            mpom.saveNMostPlayedOpening();
                        } catch(NoFileDataFoundException | NoSuchMethodException nfdfe) {
                            System.out.println("Aucun fichier binaire de jeux n'a été trouvé pour le mois " + month + " de l'année " + year);
                        }
                    }
                }
            }
        }

        // Construction des tables de hashage pour chacunes des années
        for(File yearDir : basePathFile.listFiles()) {
            if(yearDir.isDirectory()) {
                String year = yearDir.getName();

                MostPlayedOpeningYear mpoy = new MostPlayedOpeningYear(year);
                mpoy.buildHashtable();
                mpoy.saveNMostPlayedOpening();
            }
        }

        // Construction des tables de hashage pour tout
        MostPlayedOpeningAll mpoa = new MostPlayedOpeningAll();
        mpoa.buildHashtable();
        mpoa.saveNMostPlayedOpening();
    }

    static void buildHashtablesMostActivePlayers() {
        File basePathFile = new File(Constants.GAMES_DATA_DIRECTORY);

        // Construction des tables de hashage pour chacun des mois
        for(File yearDir : basePathFile.listFiles()) {
            if(yearDir.isDirectory()) {
                String year = yearDir.getName();

                for(File monthDir : yearDir.listFiles()) {
                    if(monthDir.isDirectory()) {
                        String month = monthDir.getName();

                        try {
                            MostActivePlayerMonitor mapom = new MostActivePlayerMonitor(month, year);
                            mapom.buildHastable();
                            mapom.saveNMostActivePlayers();
                        } catch(NoFileDataFoundException | NoSuchMethodException nfdfe) {
                            System.out.println("Aucun fichier binaire de jeux n'a été trouvé pour le mois " + month + " de l'année " + year);
                        }
                    }
                }
            }
        }

        // Construction des tables de hashage pour chacunes des années
        for(File yearDir : basePathFile.listFiles()) {
            if(yearDir.isDirectory()) {
                String year = yearDir.getName();

                MostActivePlayerYear mapy = new MostActivePlayerYear(year);
                mapy.buildHashtable();
                mapy.saveNMostActivePlayers();
            }
        }

        // Construction des tables de hashage pour tout
        MostActivePlayerAll mapa = new MostActivePlayerAll();
        mapa.buildHashtable();
        mapa.saveNMostActivePlayers();
    }

    static void buildHashtablesShortestGames() {
        File basePathFile = new File(Constants.GAMES_DATA_DIRECTORY);

        // Construction des tables de hashage pour chacun des mois
        for(File yearDir : basePathFile.listFiles()) {
            if(yearDir.isDirectory()) {
                String year = yearDir.getName();

                for(File monthDir : yearDir.listFiles()) {
                    if(monthDir.isDirectory()) {
                        String month = monthDir.getName();

                        try {
                            ShortestGamesMonitor sgm = new ShortestGamesMonitor(month, year);
                            sgm.buildHastable();
                        } catch(NoFileDataFoundException | NoSuchMethodException nfdfe) {
                            System.out.println("Aucun fichier binaire de jeux n'a été trouvé pour le mois " + month + " de l'année " + year);
                        }
                    }
                }
            }
        }

        // Construction des tables de hashage pour chacunes des années
        for(File yearDir : basePathFile.listFiles()) {
            if(yearDir.isDirectory()) {
                String year = yearDir.getName();

                ShortestGamesYear sgy = new ShortestGamesYear(year);
                sgy.buildHashtable();
            }
        }

        // Construction des tables de hashage pour tout
        ShortestGamesAll sga = new ShortestGamesAll();
        sga.buildHashtable();
        sga.saveFiveShortestGames();
    }

    static void buildHashtablesPlayerInfo() {
        File basePathFile = new File(Constants.GAMES_DATA_DIRECTORY);

        // Construction des tables de hashage pour chacun des mois
        for(File yearDir : basePathFile.listFiles()) {
            if(yearDir.isDirectory()) {
                String year = yearDir.getName();

                for(File monthDir : yearDir.listFiles()) {
                    if(monthDir.isDirectory()) {
                        String month = monthDir.getName();

                        try {
                            PlayerInfoWriter piw = new PlayerInfoWriter(month, year);
                            piw.buildHastable();
                        } catch(NoFileDataFoundException | NoSuchMethodException nfdfe) {
                            System.out.println("Aucun fichier binaire de jeux n'a été trouvé pour le mois " + month + " de l'année " + year);
                        }
                    }
                }
            }
        }

        // Construction des tables de hashage pour chacunes des années
        for(File yearDir : basePathFile.listFiles()) {
            if(yearDir.isDirectory()) {
                String year = yearDir.getName();

                PlayerInfoYear piy = new PlayerInfoYear(year);
                piy.buildHashtable();
            }
        }

        // Construction des tables de hashage pour tout
        PlayerInfoAll pia = new PlayerInfoAll();
        pia.buildHashtable();
        pia.saveBestPlayers();
    }

    static void buildHashtablesToFindGameWithLink() {
        File basePathFile = new File(Constants.GAMES_DATA_DIRECTORY);

        // Construction des tables de hashage pour chacun des mois
        for(File yearDir : basePathFile.listFiles()) {
            if(yearDir.isDirectory()) {
                String year = yearDir.getName();

                for(File monthDir : yearDir.listFiles()) {
                    if(monthDir.isDirectory()) {
                        String month = monthDir.getName();

                        try {
                            FindGameWithLink fgwl = new FindGameWithLink(month, year);
                            fgwl.buildHastable();
                        } catch(NoFileDataFoundException | NoSuchMethodException nfdfe) {
                            System.out.println("Aucun fichier binaire de jeux n'a été trouvé pour le mois " + month + " de l'année " + year);
                        }
                    }
                }
            }
        }

        // Construction des tables de hashage pour chacunes des années
        for(File yearDir : basePathFile.listFiles()) {
            if(yearDir.isDirectory()) {
                String year = yearDir.getName();

                FindGameWithLinkYear fgwly = new FindGameWithLinkYear(year);
                fgwly.buildHashtable();
            }
        }

        // Construction des tables de hashage pour tout
        FindGameWithLinkAll fgwla = new FindGameWithLinkAll();
        fgwla.buildHashtable();
    }



    static void extract1000FirstGames(String path, String fileName, String pathToSave) {
        try {
            PrintWriter f = new PrintWriter(pathToSave + File.separator + fileName);
            GameExtractorFromPgn gefp = new GameExtractorFromPgn(new File(path + File.separator + fileName), null);
            int n = 1;
            String game = gefp.findNextGame();
            while (n <= 2000 && !game.equals("")) {
                f.println(game);
                game = gefp.findNextGame();
                n++;
            }
            f.close();
        } catch(IOException ioe) {

        }
    }
}
