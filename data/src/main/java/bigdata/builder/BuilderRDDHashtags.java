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
import java.util.Iterator;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

public class BuilderRDDHashtags {

    /**
     * Create the RDD containing the ranking of the most used hashtags
     *
     * @param tweetRDD
     * @return the complet RDD
     */
    public final static JavaPairRDD<String, Long> topHastag(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<String, Long> tuple = tweetRDD.flatMapToPair(tweet -> {
            Set<Tuple2<String, Long>> list = new HashSet();
            tweet.getEntities().getHashtags().forEach(hashtag -> {
                list.add(new Tuple2<String, Long>(hashtag.getText().toLowerCase(), new Long(1)));
            });
            return list.iterator();
        });

        return tuple
                .reduceByKey((a, b) -> a + b)
                .mapToPair(item -> new Tuple2<Long, String>(item._2, item._1))
                .sortByKey(false)
                .mapToPair(item -> new Tuple2<String, Long>(item._2, item._1));
    }

    /**
     * Private class to implement the comporator of topHastagByDay to sorted the rdd
     * by length of the number total of appearance of the hashtag
     */
    private static class TopByDayHashtagComparator implements Comparator<Set<Tuple2<String, Long>>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(Set<Tuple2<String, Long>> v1, Set<Tuple2<String, Long>> v2) {
            Iterator<Tuple2<String, Long>> iter1 = v1.iterator();
            Iterator<Tuple2<String, Long>> iter2 = v2.iterator();

            long count1 = 0;
            long count2 = 0;

            while (iter1.hasNext()) {
                Tuple2<String, Long> val = iter1.next();
                count1 = count1 + val._2;
            }

            while (iter2.hasNext()) {
                Tuple2<String, Long> val = iter2.next();
                count2 = count2 + val._2;
            }

            return (new Long(count1)).compareTo(new Long(count2));
        }
    }

    /**
     * Create the RDD containing the classement of hashtag with the number of appearance each day
     *
     * @param tweetRDD
     * @return the complet RDD
     */
    public final static JavaPairRDD<String, Set<Tuple2<String, Long>>> topHastagByDay(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<String, Long> tuple = tweetRDD.flatMapToPair(tweet -> {
            Set<Tuple2<String, Long>> list = new HashSet();
            tweet.getEntities().getHashtags().forEach(hashtag -> {
                list.add(new Tuple2<String, Long>(hashtag.getText().toLowerCase() + "," + tweet.getCreated_at(), new Long(1)));
            });
            return list.iterator();
        });

        JavaPairRDD<String, Set<Tuple2<String, Long>>> top = tuple
                .reduceByKey((a, b) -> a + b)
                .mapToPair(item -> {
                    String[] hashDate = item._1.split(",");
                    Tuple2<String, Long> value = new Tuple2<String, Long>(hashDate[1], item._2);
                    Set<Tuple2<String, Long>> set = new HashSet();
                    set.add(value);
                    return new Tuple2<String, Set<Tuple2<String, Long>>>(hashDate[0], set);
                });

        return top
                .reduceByKey((a, b) -> {
                    a.addAll(b);
                    return a;
                })
                .mapToPair(item -> new Tuple2<Set<Tuple2<String, Long>>, String>(item._2, item._1))
                .sortByKey(new TopByDayHashtagComparator(), false, 1)
                .mapToPair(item -> new Tuple2<String, Set<Tuple2<String, Long>>>(item._2, item._1));
    }


    /**
     * Private class to implement the comporator of userHashtags to sorted the rdd
     * by length of the hashtag list of each user
     */
    private static class HashtagComparator implements Comparator<Set<String>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(Set<String> v1, Set<String> v2) {
            return (new Long(v1.size())).compareTo(new Long(v2.size()));
        }
    }

    /**
     * Create the RDD containing for each user the list of hashtags they have used
     *
     * @param tweetRDD
     * @return the rdd with all results
     */
    public static final JavaPairRDD<User, Set<String>> userHashtags(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<User, Set<String>> rdd = tweetRDD
                .mapToPair(tweet -> {
                    Set<String> hashtagSet = new HashSet<String>();
                    tweet.getEntities().getHashtags().forEach(hashtag -> {
                        hashtagSet.add(hashtag.getText().toLowerCase());
                    });
                    return new Tuple2<User, Set<String>>(tweet.getUser(), hashtagSet);
                })
                .filter(tuple -> !tuple._2.isEmpty())
                .reduceByKey((a, b) -> {
                    a.addAll(b);
                    return a;
                });
        return rdd
                .mapToPair(item -> new Tuple2<Set<String>, User>(item._2, item._1))
                .sortByKey(new HashtagComparator(), false, 1)
                .mapToPair(item -> new Tuple2<User, Set<String>>(item._2, item._1));
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


    /**
     * Create the RDD that contains for each day the number of hashtag founded
     *
     * @param tweetRDD
     * @return the complet RDD
     */
    public static final JavaPairRDD<String, Long> getNbHashtagByDay(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<String, Long> tuple = tweetRDD.mapToPair(tweet -> {
            return new Tuple2(tweet.getCreated_at(), new Long(tweet.getEntities().getHashtags().size()));
        });
        return tuple.reduceByKey((a, b) -> a + b);
    }

}
