package bigdata;

import bigdata.util.Process;
import bigdata.util.Config;


public class TPSpark {

	public static final Integer TOP_K = 50;
	public static final String HASHTAG = "bbb20";

	public static void main(String[] args) {
		
		Process process = new Process(Config.APP_NAME, Config.FILE_PATH);
		
		// Exemple d'analyse des hashtags
		// a) 
		// process.computeTopHashtag();
		// process.displayTopKHashtag(TOP_K);
		
		// c)
		// System.out.print("Le #"+HASHTAG+" est utilisé "+process.getNbHashtagOccurence(HASHTAG)+" fois\n");

		// d)
		// process.getUserByHashtag(HASHTAG);
		// process.displayKUserByHashtag(10);

		process.close();

	}
	
}
