package bigdata.builder;

import bigdata.util.GsonFactory;
import bigdata.entities.Tweet;

import scala.Tuple2;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

import bigdata.entities.Hashtag;

public class BuilderRDDTweet {

    public static final JavaRDD<Tweet> getAllTweet(JavaRDD<String> tweetsRDD) {
        JavaRDD<Tweet> tweets = tweetsRDD
                .map(line -> {
                    Tweet t = new Tweet();
                    try {
                        t = GsonFactory.create().fromJson(line, Tweet.class);
                    } catch (Exception e) {
                        //TODO: handle exception
                    } finally {
                        return t;
                    }

                })
                .filter(t -> t.isAvailable());
        return tweets;
    }

    public static final JavaPairRDD<Hashtag, Long> nbTweetByLang(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<Hashtag, Long> tuple = tweetRDD.mapToPair(t -> {
            return new Tuple2(new Hashtag(t.getLang().toLowerCase()), new Long(1));
        });
        return tuple.reduceByKey((a, b) -> a + b);
    }


}
