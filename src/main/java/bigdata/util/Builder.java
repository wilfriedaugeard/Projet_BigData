package bigdata.util;


import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import org.apache.spark.api.java.JavaRDD;
import com.google.gson.Gson;

import bigdata.entities.Tweet;
import bigdata.entities.Hashtag;

public class Builder {

	public static final JavaRDD<Tweet> getAllTweet(JavaRDD<String> tweetsRDD){
		JavaRDD<Tweet> tweets = tweetsRDD
			.map(line ->{
				Tweet t = GsonFactory.create().fromJson(line, Tweet.class);
				return t;
			})
			.filter(t -> t.isAvailable());
		return tweets;
	} 

	public static final JavaRDD<List<Hashtag>> getAllHastags(JavaRDD<Tweet> tweetRDD){
		JavaRDD<List<Hashtag>> hashtagRDD = tweetRDD
			.map(tweet -> {
				return tweet.getEntities().getHashtags();
			})
			.filter(hashtag -> !hashtag.isEmpty());
		return hashtagRDD;
	} 

	
}
