package bigdata.builder;

import java.util.Set;
import java.util.HashSet;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

import scala.Tuple2;
import bigdata.entities.User;
import bigdata.entities.Tweet;
import bigdata.entities.Triplet;


public class BuilderRDDUser {

    public static final JavaPairRDD<Triplet, Set<User>> userByTripletHashtags(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<Triplet, Set<User>> tuple = tweetRDD
                .filter(tweet -> tweet.getEntities().getHashtags().size() == 3)
                .flatMapToPair(tweet -> {
                    Triplet triplet = new Triplet();
                    Set<User> userList = new HashSet<User>();
                    Set<Tuple2<Triplet, Set<User>>> list = new HashSet<Tuple2<Triplet, Set<User>>>();
                    tweet.getEntities().getHashtags().forEach(h -> {
                        triplet.add(h);
                    });
                    userList.add(tweet.getUser());
                    list.add(new Tuple2<Triplet, Set<User>>(triplet, userList));
                    return list.iterator();
                })
                .reduceByKey((a, b) -> {
                    b.forEach(user -> a.add(user));
                    return a;
                });
        return tuple;
    }

    public static final JavaPairRDD<User, Long> topUser(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<User, Long> top = tweetRDD
                .mapToPair(t -> {
                    return new Tuple2<User, Long>(t.getUser(), new Long(1));
                });
        return top
                .reduceByKey((a, b) -> a + b)
                .mapToPair(item -> new Tuple2<Long, User>(item._2, item._1))
                .sortByKey(false)
                .mapToPair(item -> new Tuple2<User, Long>(item._2, item._1));
    }

    // public static final JavaPairRDD<User, Tuple2<User,Long>> user(JavaRDD<Tweet> tweetRDD){
    // 	JavaPairRDD<User,Tuple2<User,Long>> list = userByTripletHashTag(tweetRDD)
    // 		.flatMapToPair(pair ->{
    // 			List<Tuple2<User, Long>> userList = new LinkedList();
    // 			pair._2.forEach(u -> userList.add(new Tuple2<User, Long>(u, new Long(0))));
    // 			return userList.iterator();
    // 		})
    // 		.join(topUser(tweetRDD));
    // 	return list;
    // }
}
