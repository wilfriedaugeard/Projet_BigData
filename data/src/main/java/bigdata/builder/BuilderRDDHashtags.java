package bigdata.builder;

import bigdata.entities.User;
import bigdata.entities.Tweet;
import bigdata.entities.Hashtag;
import bigdata.entities.Triplet;

import scala.Tuple2;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.LinkedList;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

public class BuilderRDDHashtags {

    public static final JavaRDD<Hashtag> getAllHastags(JavaRDD<Tweet> tweetRDD) {
        JavaRDD<Hashtag> hashtagRDD = tweetRDD
                .flatMap(tweet -> {
                    Set<Hashtag> list = new HashSet();
                    tweet.getEntities().getHashtags().forEach(h -> {
                        list.add(h);
                    });
                    return list.iterator();
                });
        return hashtagRDD;
    }

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

    public static final JavaPairRDD<User, Set<Hashtag>> userHashtags(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<User, Set<Hashtag>> list = tweetRDD
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
        return list;
    }

    public static final JavaRDD<Triplet> tripletHashtags(JavaRDD<Tweet> tweetRDD) {
        JavaRDD<Triplet> triplet = tweetRDD
                .filter(tweet -> tweet.getEntities().getHashtags().size() == 3)
                .map(tweet -> {
                    Triplet t = new Triplet();
                    tweet.getEntities().getHashtags().forEach(h -> t.add(h));
                    return t;
                });
        return triplet;
    }

    public static final JavaPairRDD<Triplet, Long> topTriplet(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<Triplet, Long> top = tripletHashtags(tweetRDD)
                .mapToPair(t -> {
                    return new Tuple2<Triplet, Long>(t, new Long(1));

                });
        return top
                .reduceByKey((a, b) -> a + b)
                .mapToPair(item -> new Tuple2<Long, Triplet>(item._2, item._1))
                .sortByKey(false)
                .mapToPair(item -> new Tuple2<Triplet, Long>(item._2, item._1));
    }

}
