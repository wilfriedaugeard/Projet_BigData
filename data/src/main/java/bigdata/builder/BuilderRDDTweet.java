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

    public static final JavaPairRDD<String, Long> nbTweetByLang(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<String, Long> tuple = tweetRDD.mapToPair(t -> {
            return new Tuple2(t.getLang().toLowerCase(), new Long(1));
        });
        return tuple.reduceByKey((a, b) -> a + b)
                .mapToPair(item -> new Tuple2<Long, String>(item._2, item._1))
                .sortByKey(false)
                .mapToPair(item -> new Tuple2<String, Long>(item._2, item._1));
    }


}
