import app.constant.Constants;
import app.exception.NoFileDataFoundException;
import app.monitor.hashtablemaker.all.*;
import app.monitor.hashtablemaker.bymonth.*;
import app.monitor.hashtablemaker.byyear.*;
import app.pgn.GameExtractorFromPgn;

import java.io.*;
import java.text.ParseException;

public class Main {
    public static void main(String args[]) throws IOException, ClassNotFoundException, ParseException, NoFileDataFoundException, NoSuchMethodException {

        buildBinaryGames(Constants.ROOT_DATA + File.separator + "data_src");
        //buildHashtablesGamesOfAPlayer();
        //buildHashtablesMostPlayedOpening();
        //buildHashtablesMostActivePlayers();
        //buildHashtablesShortestGames();
        //buildHashtablesPlayerInfo();
        //buildHashtablesToFindGameWithLink();

        //extract1000FirstGames("/home/ubuntu/IdeaProjects/database/data_src", "lichess_db_standard_rated_2014-05.pgn", "/home/ubuntu/IdeaProjects/database/data_src/games_pgn/2014/05");
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
        sga.orderShortestGames();
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
        pia.saveBestPlayersAccordingPR();
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
