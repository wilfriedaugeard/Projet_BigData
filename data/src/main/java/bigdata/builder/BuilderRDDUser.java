package bigdata.builder;

import java.util.Iterator;
import java.util.Comparator;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

import scala.Tuple2;
import bigdata.entities.User;
import bigdata.entities.Tweet;
import bigdata.entities.Triplet;
import bigdata.entities.Hashtag;
import bigdata.util.Config;

public class BuilderRDDUser {

    /**
     * Create the RDD the sorted users by number of {tweet, followers or RT}
     *
     * @param tweetRDD
     * @return the complet RDD
     */
    public static final JavaPairRDD<String, Long> topUser(JavaRDD<Tweet> tweetRDD, String category) {
        JavaPairRDD<String, Long> top = tweetRDD
                .mapToPair(tweet -> {
                    if (category.equals("tweeting")) {
                        return new Tuple2<String, Long>(tweet.getUser().getUserInfo(), new Long(1));
                    } else if (category.equals("followed")) {
                        return new Tuple2<String, Long>(tweet.getUser().getUserInfo(), tweet.getUser().getFollowers());
                    } else {
                        return new Tuple2<String, Long>(tweet.getUser().getUserInfo(), tweet.getRetweet_count());
                    }
                });
        if (category.equals("followed")) {
            top = top.distinct();
        } else {
            top = top.reduceByKey((a, b) -> a + b);
        }
        return top
                .mapToPair(item -> new Tuple2<Long, String>(item._2, item._1))
                .sortByKey(false)
                .mapToPair(item -> new Tuple2<String, Long>(item._2, item._1));
    }

    /**
     * Create the RDD creating the list of influencers. The users that tweet the largest
     * number of tweet containing triplet of hashtags
     *
     * @param tweetRDD
     * @return the complet RDD
     */
    public static final JavaPairRDD<String, Long> influencer(JavaRDD<Tweet> tweetRDD) {

        JavaPairRDD<String, Long> influencer = tweetRDD
                .filter(tweet -> tweet.getEntities().getHashtags().size() == 3)
                .mapToPair(tweet -> {
                    return new Tuple2<String, Long>(tweet.getUser().getUserInfo(), new Long(1));
                });

        return influencer
                .reduceByKey((a, b) -> a + b)
                .mapToPair(item -> new Tuple2<Long, String>(item._2, item._1))
                .sortByKey(false)
                .mapToPair(item -> new Tuple2<String, Long>(item._2, item._1));
    }

    /**
     * Private class to implement the comporator of fakeInfluencer to sorted the rdd increasingly
     * by number of followers and then decreasingly by number of average RT by tweet
     */
    private static class RtFollowersComparator implements Comparator<Tuple2<Long, Long>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(Tuple2<Long, Long> v1, Tuple2<Long, Long> v2) {
            if (v1._2().compareTo(v2._2()) == 0) {
                return v1._1().compareTo(v2._1());
            }
            return v2._1().compareTo(v1._1());
        }
    }

    /**
     * Create the RDD of fake influencer. Users with the most followers and the less number of average RT
     *
     * @param tweetRDD
     * @return the complet RDD
     */
    public static final JavaPairRDD<String, Tuple2<Long, Long>> fakeInfluencer(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<User, Tuple2<Long, Long>> ratioRdd = tweetRDD
                .mapToPair(tweet -> {
                    return new Tuple2<User, Tuple2<Long, Long>>(
                            tweet.getUser(),
                            new Tuple2<Long, Long>(
                                    new Long(1),
                                    tweet.getRetweet_count()));
                })
                .reduceByKey((a, b) -> new Tuple2<Long, Long>(a._1 + b._1, a._2 + b._2));

        return ratioRdd
                .mapToPair(item -> new Tuple2<String, Tuple2<Long, Long>>(
                        item._1.getUserInfo(),
                        new Tuple2<Long, Long>(
                                item._1.getFollowers(),
                                (item._2._1 == 0) ? 0 : (item._2._2 / item._2._1))))

                .mapToPair(item -> new Tuple2<Tuple2<Long, Long>, String>(item._2, item._1))
                .sortByKey(new RtFollowersComparator(), false, 1)
                .mapToPair(item -> new Tuple2<String, Tuple2<Long, Long>>(item._2, item._1));
    }
}

