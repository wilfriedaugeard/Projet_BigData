package bigdata;

import bigdata.util.Process;
import bigdata.util.Config;


public class ProjetTwitter {

    private static void usage(String error){
        Sytem.out.println("Usage: "+ error);
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {

        if(args.length != 3) {
            usage("wrong number of arguments");
        }
        String file;
        Switch(args[1]){
            case "small":
                file = Congig.SMALL_FILE_PATH;
                break;
            case "one":
                file = Config.ONE_FILE_PATH;
                break;
            case "all":
                file = Config.ALL_FILES_PATH;
                break
            default :
                usage("wrong file arguments ")
        }

        Process process = new Process(Config.APP_NAME, file);

        // EXEMPLE D'ANALYSE DES HASHTAGS

        // a) Permettre de récupérer pour un jour donné la liste des k hashtags les plus utilisés ainsi que leur nombre d’apparition (k entre 1 et 10000).
       // process.displayResult(process.getTopHashtags(TOP_K),10);

        // c) Permettre de récupérer le nombre d’apparition d’un hashtag donné.
        //process.displayResult(process.getTopHashtags(), 10);

        // d) Récupérer tous les utilisateurs qui ont utilisé un hashtag.
        //process.displayResult(process.getUserHashtags(TOP_K), 10);


        // EXEMPLE D'ANALYSE DES USERS

        // a) Permettre de récupérer pour un utilisateur la liste de ses hashtags sans doublon.
        // process.displayResult(process.getUserHashtags(), 10);

        // b) Permettre de savoir le nombre de tweet d’un utilisateur.
//         process.displayResult(process.getTopUsers(TOP_K), 10);

        // c) Nombre de tweet par pays ou par langue
       process.displayResult(process.getNbTweetByLang(), 100);


        // EXEMPLE D'ANALYSE DES INFLUENCEURS

        // a) Récupérer tous les triplets de hashtags ainsi que les utilisateurs qui les ont utilisés
        // process.displayResult(process.getTripletHashtagsAndUsers(), 100);

        // b) Donner les k triplets de hashtags les plus utilisés (k entre 1 et 1000)
        //process.displayResult(process.getTopTripletHashtags(TOP_K), TOP_K);

        // c) Trouver les influenceurs, c'est a dire les personnes avec le plus grand nombre de tweets dans les triplets que l'on a trouvé.
        //process.displayResult(process.getInfluencers(), 100);

        // d) Trouver les faux influencer, personnes avec beaucoup de followers dont les tweets ne sont jamais retweeté.
   //   process.displayResult(process.getFakeInfluencers(), 100);

        // g) Trouver les k users les plus Retweetés (k entre 1 et 1000)
//        process.displayResult(process.getUserRtCount(TOP_K), TOP_K);

        process.close();

    }

}
