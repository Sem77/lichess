package app.monitor.hashtablemaker;

import java.util.Hashtable;

public interface HashtableMergerInterface {

    /**
     * construit une table de hashage contenant les informations des tables de hashage d'une certaine année ou d'un certain mois
     */
    void buildHashtable();

    /**
     * méthode appelée par buildHashatble
     * permet de fusionner les informations des tables de hashage dest et h et les stocke dans dest
     * @param dest table de hashage de destination
     * @param h table de hashage à copier
     */
    void mergeHashtables(Hashtable dest, Hashtable h);
}
