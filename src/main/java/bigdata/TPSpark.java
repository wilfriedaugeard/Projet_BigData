package bigdata;

// import org.apache.spark.SparkConf;
// import org.apache.spark.api.java.*;
import bigdata.util.Process;
import bigdata.util.Config;


public class TPSpark {

	public static void main(String[] args) {
		
		Process process = new Process(Config.APP_NAME, Config.FILE_PATH);
		
		process.computeTopHashtag();
		process.displayTopKHashtag(50);

		process.close();

	}
	
}
