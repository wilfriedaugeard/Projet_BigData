package bigdata;

import bigdata.util.Process;
import bigdata.util.Config;


public class TPSpark {

    public static final Integer TOP_K = 10000;
    public static final Integer ALL = Integer.MAX_VALUE;
    public static final String HASHTAG = "ATINfourSB19";
    public static final String USER_ID = "1870597914";

    public static void main(String[] args) throws Exception {
        Process process = new Process(Config.APP_NAME, Config.ALL_FILES_PATH);

        // EXEMPLE D'ANALYSE DES HASHTAGS

        // a) Permettre de récupérer pour un jour donné la liste des k hashtags les plus utilisés ainsi que leur nombre d’apparition (k entre 1 et 10000).
 	process.displayResult(process.getTopHashtags(TOP_K), TOP_K);

        // c) Permettre de récupérer le nombre d’apparition d’un hashtag donné.
        //process.displayResult(process.getTopHashtags(), 10);

        // d) Récupérer tous les utilisateurs qui ont utilisé un hashtag.
        // process.displayResult(process.getUserHashtags(), 10);


        // EXEMPLE D'ANALYSE DES USERS

        // a) Permettre de récupérer pour un utilisateur la liste de ses hashtags sans doublon.
        // process.displayResult(process.getUserHashtags(), 10);

        // b) Permettre de savoir le nombre de tweet d’un utilisateur.
       // process.displayResult(process.getTopUsers(), 10);

        // c) Nombre de tweet par pays ou par langue
         //process.displayResult(process.getNbTweetByLang(), 10);


        // EXEMPLE D'ANALYSE DES INFLUENCEURS

        // a) Récupérer tous les triplets de hashtags ainsi que les utilisateurs qui les ont utilisés
        // process.displayResult(process.getTripletHashtagsAndUsers(), 10);

        // b) Donner les k triplets de hashtags les plus utilisés (k entre 1 et 1000)
         //process.displayResult(process.getTopTripletHashtags(TOP_K), TOP_K);

        // c) Trouver les influenceurs, c'est a dire les personnes avec le plus grand nombre de tweets dans les triplets que l'on a trouvé.
        // DOING


        process.close();

    }

}
