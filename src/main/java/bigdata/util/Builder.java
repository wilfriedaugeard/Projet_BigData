package bigdata.util;

import scala.Tuple2;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.Serializable;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;
import com.google.gson.Gson;

import bigdata.entities.User;
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

	public static final JavaRDD<Hashtag> getAllHastags(JavaRDD<Tweet> tweetRDD){
		JavaRDD<Hashtag> hashtagRDD = tweetRDD
			.flatMap(tweet -> {
				List<Hashtag> list = new LinkedList();
				tweet.getEntities().getHashtags().forEach(h -> {
					list.add(h);
				});
				return list.iterator();
			});
		return hashtagRDD;
	} 


	public final static JavaPairRDD<Hashtag, Integer> topHastag(JavaRDD<Tweet> tweetRDD){
		JavaPairRDD<Hashtag, Integer> tuple = tweetRDD.flatMapToPair(t -> {
			List<Tuple2<Hashtag, Integer>> list = new LinkedList();
			t.getEntities().getHashtags().forEach(h ->{
				list.add(new Tuple2<Hashtag, Integer>(h, 1));
			});
			return list.iterator();
		});
		return tuple
			.reduceByKey((a, b) -> a+b)
			.mapToPair(item -> new Tuple2<Integer, Hashtag>(item._2, item._1))
			.sortByKey(false)
			.mapToPair(item -> new Tuple2<Hashtag, Integer>(item._2, item._1));	
	} 
	

	public static final JavaRDD<User> usersByHashtag(JavaRDD<Tweet> tweetRDD, String hashtag){
		Hashtag h = new Hashtag(hashtag);
		JavaRDD<User> userList = tweetRDD
			.filter(tweet -> (tweet.getEntities().getHashtags().contains(h)))
			.map(tweet -> tweet.getUser());
		return userList;
	} 

	public static final JavaRDD<Hashtag> hashtagByUser(JavaRDD<Tweet> tweetRDD, String userId){
		JavaRDD<Hashtag> hashtagRDD = tweetRDD
			.filter(tweet -> tweet.getUser().getId().equals(userId))
			.flatMap(tweet -> {
				List<Hashtag> list = new LinkedList();
				tweet.getEntities().getHashtags().forEach(h -> {
					list.add(h);
				});
				return list.iterator();
			})
			.distinct();
		return hashtagRDD;
	} 

	
}
