package bigdata.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import bigdata.entities.Tweet;


public class Builder {
    public static final Tweet buildTweetFromJSON(String tweet, Gson gson){
		return gson.fromJson(tweet, Tweet.class);
	} 

	public static final Gson createGson(){
		return new GsonBuilder().setPrettyPrinting().create();
	} 

	
}
