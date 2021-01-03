package bigdata;

import bigdata.util.Process;
import bigdata.util.Config;


public class TPSpark {

	public static final Integer TOP_K = 50;
	public static final String HASHTAG = "bbb20";
	public static final String USER_ID = "1870597914";

	public static void main(String[] args) {
		
		Process process = new Process(Config.APP_NAME, Config.FILE_PATH);
		
		// Exemple d'analyse des hashtags
		// a) Permettre de récupérer pour un jour donné la liste des khashtags les plus utilisés ainsi que leur nombre d’apparition (k entre 1 et 10000).
		// process.computeTopHashtag();
		// process.displayTopKHashtag(TOP_K);
		
		// c) Permettre de récupérer le nombre d’apparition d’un hashtag donné.
		// System.out.print("Le #"+HASHTAG+" est utilisé "+process.getNbHashtagOccurence(HASHTAG)+" fois\n");

		// d) Récupérer tous les utilisateurs qui ont utilisé un hashtag.
		// process.getUserByHashtag(HASHTAG);
		// process.displayKUserByHashtag(10);


		// Exemple d’analyse des users
		// a) Permettre de récupérer pour un utilisateur la liste de ses hashtags sans doublon.
		// process.getHashtagByUser(USER_ID);

		process.close();

	}
	
}
