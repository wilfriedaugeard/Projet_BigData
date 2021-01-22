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
     * @return the complet RDD
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
                .mapToPair(item -> new Tuple2<Hashtag, Long>(item._2, item._1));
    }

    /**
     * Private class to implement the comporator of userHashtags to sorted the rdd
     * by length of the hashtag list of each user
     */
    private static class HashtagComparator implements Comparator<Set<Hashtag>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(Set<Hashtag> v1, Set<Hashtag> v2) {
            return (v1.size() > v2.size())? 1:0;
        }
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
        return rdd
                .mapToPair(item -> new Tuple2<Tuple2<Set<Hashtag>, User>, Triplet>(item._2, item._1))
                .sortByKey(new HashtagComparator(), false, 1)
                .mapToPair(item -> new Tuple2<User, Set<Hashtag>>(item._2, item._1));
    }


    /**
     * Create the rdd containing for every triplet the number of tweets that used it
     *
     * @param tweetRDD
     * @return the complet rdd
     */
    public static final JavaPairRDD<Triplet, Long> topTripletHashtag(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<Triplet, Long> top = tweetRDD
                .filter(tweet -> tweet.getEntities().getHashtags().size() == 3)
                .mapToPair(tweet -> {
                    Triplet triplet = new Triplet();
                    tweet.getEntities().getHashtags().forEach(hashtag -> triplet.add(hashtag));
                    return new Tuple2<Triplet, Long>(triplet, new Long(1));
                });
        return top
                .reduceByKey((a, b) -> a + b)
                .mapToPair(item -> new Tuple2<Long, Triplet>(item._2, item._1))
                .sortByKey(false)
                .mapToPair(item -> new Tuple2<Triplet, Long>(item._2, item._1));
    }

}
