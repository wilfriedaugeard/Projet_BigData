package bigdata.util;


import java.util.List;
import java.util.ArrayList;
import org.apache.spark.api.java.JavaRDD;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import bigdata.entities.Tweet;
import bigdata.entities.Hashtag;

public class Builder {
    public static final Tweet buildTweetFromJSON(String tweet, Gson gson){
		return gson.fromJson(tweet, Tweet.class);
	} 

	public static final Gson createGson(){
		return new GsonBuilder().setPrettyPrinting().create();
	} 

	public static final List<Tweet> buildNTweet(JavaRDD<String> tweetsRDD, Integer n){
		final Gson gson = Builder.createGson();
		List<Tweet> tweetList = new ArrayList<Tweet>();
		for(String tweet : tweetsRDD.take(n)){
			Tweet t = Builder.buildTweetFromJSON(tweet, gson);
			if(t.isAvailable()) tweetList.add(t);
		} 
		return tweetList;
	} 

	public static final List<Hashtag> getAllHastags(List<Tweet> tweetList){
		List<Hashtag> hashtagList = new ArrayList<Hashtag>();
		for(Tweet tweet : tweetList){
			for(Hashtag hashtag: tweet.getEntities().getHashtags()){
				hashtagList.add(hashtag);
			} 	
		} 
		return hashtagList;
	} 

	
}
