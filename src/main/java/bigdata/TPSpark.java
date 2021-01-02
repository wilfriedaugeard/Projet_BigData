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

		JavaRDD<String> fileRDD = context.textFile(Config.FILE_PATH);

		JavaRDD<Tweet> tweetRDD = Builder.getAllTweet(fileRDD);

		// JavaRDD<List<Hashtag>> hashtagsRDD = Builder.getAllHastags(tweetRDD);
		JavaPairRDD<Hashtag, Integer> hashtagsRDD = Builder.topHastag(tweetRDD);
		hashtagsRDD.take(10).forEach(item -> System.out.println(item));
		
		context.close();

	}
	
}
