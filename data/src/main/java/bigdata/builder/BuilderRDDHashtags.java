package bigdata.builder;

import bigdata.entities.User;
import bigdata.entities.Tweet;
import bigdata.entities.Hashtag;
import bigdata.entities.Triplet;
import bigdata.util.Config;


import java.util.Comparator;
import java.io.Serializable;

import scala.Tuple2;

import java.util.Set;
import java.util.List;
import java.util.HashSet;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

public class BuilderRDDHashtags {

    /**
     * Create the RDD containing the ranking of the most used hashtags
     *
     * @param tweetRDD
     * @return the rdd with the TOP_K values
     */
    public final static JavaPairRDD<Hashtag, Long> topHastag(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<Hashtag, Long> tuple = tweetRDD.flatMapToPair(t -> {
            Set<Tuple2<Hashtag, Long>> list = new HashSet();
            t.getEntities().getHashtags().forEach(h -> {
                list.add(new Tuple2<Hashtag, Long>(h, new Long(1)));
            });
            return list.iterator();
        });

        return tuple
                .reduceByKey((a, b) -> a + b)
                .mapToPair(item -> new Tuple2<Long, Hashtag>(item._2, item._1))
                .sortByKey(false)
                .mapToPair(item -> new Tuple2<Hashtag, Long>(item._2, item._1))
                .take(Config.TOP_K);
    }

    /**
     * Create the RDD containing for each user the list of hashtags they have used
     *
     * @param tweetRDD
     * @return the rdd with all results
     */
    public static final JavaPairRDD<User, Set<Hashtag>> userHashtags(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<User, Set<Hashtag>> rdd = tweetRDD
                .mapToPair(tweet -> {
                    Set<Hashtag> hashtagSet = new HashSet<Hashtag>();
                    tweet.getEntities().getHashtags().forEach(h -> {
                        hashtagSet.add(h);
                    });
                    return new Tuple2<User, Set<Hashtag>>(tweet.getUser(), hashtagSet);
                })
                .filter(tuple -> !tuple._2.isEmpty())
                .reduceByKey((a, b) -> {
                    a.addAll(b);
                    return a;
                });
        return rdd;
    }

    /**
     * Private class to implement the comporator of userByTripletHashtags to sorted the rdd
     * by number of tweet using this triplet
     */
    private static class TripletComparator implements Comparator<Tuple2<Long, Set<User>>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(Tuple2<Long, Long> v1, Tuple2<Long, Set<User>> v2) {
            return v1._1.compareTo(v2._1);
        }
    }

    /**
     * Create the rdd containing for every triplet the number of tweets that used it and the list of users
     * that tweeted them
     *
     * @param tweetRDD
     * @return the complet rdd
     */
    public static final JavaPairRDD<Triplet, Tuple2<Long, Set<User>>> userByTripletHashtags(JavaRDD<Tweet> tweetRDD) {
        JavaRDD<Triplet> triplet = tweetRDD
                .filter(tweet -> tweet.getEntities().getHashtags().size() == 3)
                .mapToPair(tweet -> {
                    Triplet triplet = new Triplet();
                    tweet.getEntities().getHashtags().forEach(item -> triplet.add(item));
                    Set<User> user = new HashSet();
                    user.add(tweet.getUser());
                    return new Tuple2<Triplet, Tuple2<Long, Set<User>>>(
                            triplet,
                            new Tuple2<Long, Set<User>>(
                                    new Long(1),
                                    user.iterator())
                    );
                });

        return triplet
                .reduceByKey((a, b) -> new Tuple2<Long, Set<User>>(a._1 + b._1, a._2.addAll(b._2)))
                .mapToPair(item -> new Tuple2<Long, Triplet>(item._2, item._1))
                .sortByKey(new TripletComparator(), false, 1)
                .mapToPair(item -> new Tuple2<Triplet, Long>(item._2, item._1));

    }
}
