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

public class BuilderRDDUser {

    //User ayant tweeté ces triplets d'hashtag
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

    // User qui tweet le plus
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

    // User dont le pourcentage de tweet contenant les triplets sur le nombre total de tweet est le plus grand
    public static final JavaPairRDD<User, Long> influencer(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<User, Long> topUser = topUser(tweetRDD);

        JavaPairRDD<User, Long> tripletTweetByUser = userByTripletHashtags(tweetRDD)
                .flatMapPair(pair -> {
                    Set<Tuple2<User, Long>> list = new HashSet();
		   for(Iterator<User> it : pair._2.iterator(); it.hasNext();){
			User user = it.next();
			list.add(new Tuple2<User,Long>(user, new Long(1)));
		    }
                    return list.iterator();
                })
                .reduceByKey((a, b) -> a + b);

        return topUser.join(tripletTweetByUser)
                .mapToPair(item -> {
                    Tuple2<Long, Long> tuple = item._2;
                    return new Tuple2<User, Long>(item._1, (tuple._1 * 100) / tuple._2);
                })
                .mapToPair(item -> new Tuple2<Long,User>(item._2, item._1))
                .sortByKey(false)
                .mapToPair(item -> new Tuple2<User, Long>(item._2, item._1));
    }

    //Nombre total de RT que l'user à eu tous tweet confondu
    public static final JavaPairRDD<User, Long> userByTotalRT(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<User, Long> top = tweetRDD
                .mapToPair(t -> {
                    return new Tuple2<User, Long>(t.getUser(), new Long(t.getRetweetCount()));
                });
        return top
                .reduceByKey((a, b) -> a + b)
                .mapToPair(item -> new Tuple2<Long, User>(item._2, item._1))
                .sortByKey(false)
                .mapToPair(item -> new Tuple2<User, Long>(item._2, item._1));
    }

    class RtFollowersComparator implements Comparator<Tuple2<Long, Long>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(Tuple2<Long, Long> v1, Tuple2<Long, Long> v2) {
            //nb followers egales ou plus haut
            if (v1._1().compareTo(v2._1()) >= 0) {
                //nb de RT moyen plus bas
                return (v1._2().compareTo(v2._2()) < 0) ? 1 : -1;
            }
            //nb de followers moins haut
            return -1;

        }
    }

    //User avec beaucoup de followers et peu de RT
    //calcul le nombre moyen de RT par tweet
    //trie par nombre de followers (>) puis nombre de RT moyen (<)
    public static final JavaPairRDD<User, Tuple2<Long, Long>> fakeInfluenceur(JavaRDD<Tweet> tweetRDD) {
        JavaPairRDD<User, Long> userRTRDD = userByTotalRT(tweetRDD);
        JavaPairRDD<User, Long> ratioRdd = topUser(tweetRDD)
                .join(userRTRDD)
                .mapToPair(item -> {
                    Tuple2<Long, Long> tuple = item._2;
                    return new Tuple2<User, Long>(item._1, new Long(tuple._2 / tuple._1));
                });
        return ratioRdd
                .mapToPair(item -> new Tuple2<User, Tuple2<Long, Long>>(item._1, new Tuple2<Long, Long>(item._1.getFollowers(), item._2)))
                .mapToPair(item -> new Tuple2<Tuple2<Long, Long>, User>(item._2, item._1))
                .sortByKey(new RtFollowersComparator())
                .mapToPair(item -> new Tuple2<User, Tuple2<Long, Long>>(item._2, item._1));
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
