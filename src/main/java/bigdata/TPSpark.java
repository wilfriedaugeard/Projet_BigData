package bigdata;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.*;
import org.apache.spark.util.StatCounter;
import com.google.gson.Gson;

import bigdata.entities.Tweet;
import bigdata.util.Builder;
import bigdata.util.Config;


public class TPSpark {

	
	public static void main(String[] args) {
		
		SparkConf conf = new SparkConf().setAppName(Config.APP_NAME);
		JavaSparkContext context = new JavaSparkContext(conf);

		JavaRDD<String> tweets = context.textFile(Config.FILE_PATH);
		final Gson gson = Builder.createGson();

		for(String tweet : tweets.take(10)){
			System.out.println(Builder.buildTweetFromJSON(tweet, gson).toString());
		} 
		
		context.close();

	}
	
}
