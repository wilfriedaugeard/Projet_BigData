package bigdata;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.*;
import org.apache.spark.util.StatCounter;
import com.google.gson.Gson;
import java.util.List;

import bigdata.entities.Tweet;
import bigdata.entities.Hashtag;
import bigdata.util.Builder;
import bigdata.util.Config;


public class TPSpark {

	public static void main(String[] args) {
		
		SparkConf conf = new SparkConf().setAppName(Config.APP_NAME);
		JavaSparkContext context = new JavaSparkContext(conf);

		JavaRDD<String> tweetsRDD = context.textFile(Config.FILE_PATH);
		List<Tweet> allTweet = Builder.buildNTweet(tweetsRDD, 50);
		int i = 0;
		for(Tweet tweet: allTweet){
			System.out.println(i+" "+tweet.getEntities().getHashtags());
			i++;
		} 

		JavaRDD<Hashtag> hashtagsRDD = context.parallelize(Builder.getAllHastags(allTweet));
		System.out.println(hashtagsRDD.count());
		
		context.close();

	}
	
}
