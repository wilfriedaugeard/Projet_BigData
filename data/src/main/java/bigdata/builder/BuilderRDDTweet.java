package bigdata.builder;

import bigdata.util.GsonFactory;
import bigdata.entities.Tweet;

import scala.Tuple2;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

import bigdata.entities.Hashtag;

public class BuilderRDDTweet {

    /**
     * Create the RDD containing all the tweets
     *
     * @param tweetsRDD
     * @return the complete RDD
     */
    public static final JavaRDD<Tweet> getAllTweet(JavaRDD<String> tweetsRDD) {
        return tweetsRDD
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
    }

    /**
     * Create the RDD that contains for each value of hashtags contained in a tweet the number of tweets founded
     *
     * @param tweetJavaRDD
     * @return the complet RDD of 4 rows
     */
    public static final JavaPairRDD<String, Long> tweetByHashtagNb(JavaRDD<Tweet> tweetJavaRDD) {
        JavaPairRDD<String, Long> tuple = tweetJavaRDD.mapToPair(tweet -> {
            int nbHashtag = tweet.getEntities().getNbHashtags();
            String range = "";
            if (nbHashtag == 0) {
                range = "0";
            } else if (1 <= nbHashtag && nbHashtag <= 3) {
                range = "[1-3]";
            } else if (4 <= nbHashtag && nbHashtag <= 7) {
                range = "[4-7]";
            } else {
                range = "8+";
            }
            return new Tuple2<String, Long>(range, new Long(1));
        });

        return tuple.reduceByKey((a, b) -> a + b);
    }

    /**
     * Create the RDD that contains for each languages the number of tweets founded
     *
     * @param tweetRDD
     * @return the complet RDD
     */
    public static final JavaPairRDD<String, Long> nbTweetByLang(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<String, Long> tuple = tweetRDD.mapToPair(tweet -> {
            return new Tuple2(tweet.getLang().toLowerCase(), new Long(1));
        });
        return tuple.reduceByKey((a, b) -> a + b)
                .mapToPair(item -> new Tuple2<Long, String>(item._2, item._1))
                .sortByKey(false)
                .mapToPair(item -> new Tuple2<String, Long>(item._2, item._1));
    }

    /**
     * Create the RDD that contains for each day the number of tweets founded
     *
     * @param tweetRDD
     * @return the complet RDD
     */
    public static final JavaPairRDD<String, Long> getNbTweetByDay(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<String, Long> tuple = tweetRDD.mapToPair(tweet -> {
            return new Tuple2(tweet.getCreated_at(), new Long(1));
        });
        return tuple.reduceByKey((a, b) -> a + b);
    }

}
