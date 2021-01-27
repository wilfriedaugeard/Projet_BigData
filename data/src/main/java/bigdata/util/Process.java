package bigdata.util;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

import scala.Tuple2;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

import bigdata.builder.*;
import bigdata.entities.User;
import bigdata.entities.Tweet;
import bigdata.entities.Hashtag;
import bigdata.entities.Triplet;
import bigdata.entities.IBigDataObject;
import bigdata.hbase.*;

import java.util.Iterator;

public class Process {
    private JavaSparkContext context;
    private JavaRDD<String> fileRDD;
    private JavaRDD<Tweet> tweetRDD;
    private JavaPairRDD<Hashtag, Integer> hashtagsRDD;
    private JavaRDD<User> userByHashtag;
    private long nbHashtagOccurence;
    private JavaRDD<Hashtag> hashtagByUser;
    private JavaPairRDD<String, Integer> nbTweetByLang;
    private JavaPairRDD<Triplet, List<User>> triplet;
    private Configuration hConf;


    public Process(String app_name, String pathFile) {
        SparkConf conf = new SparkConf().setAppName(app_name);
        this.context = new JavaSparkContext(conf);
        this.fileRDD = this.context.textFile(pathFile);
        this.tweetRDD = BuilderRDDTweet.getAllTweet(this.fileRDD);
        this.hConf = HBaseConfiguration.create();
    }

    public void close() {
        this.context.close();
    }

    public JavaPairRDD<String, Long> getTopHashtags(Boolean saveValues) throws Exception {

        JavaPairRDD<String, Long> rdd = BuilderRDDHashtags.topHastag(this.tweetRDD);

        if (saveValues) {
            String[] families = {String.class.getSimpleName(), Long.class.getSimpleName()};
            String[] columns = {"value", "count"};
            Save.apply(this.hConf, "top-hashtag", families, columns, rdd.take(Config.TOP_K));
        }
        return rdd;
    }

    public JavaPairRDD<String, Set<Tuple2<String, Long>>> getTopHashtagsByDay(Boolean saveValues) throws Exception {
        JavaPairRDD<String, Set<Tuple2<String, Long>>> rdd = BuilderRDDHashtags.topHastagByDay(this.tweetRDD);

        if (saveValues) {
            String[] families = {String.class.getSimpleName(), Set.class.getSimpleName()};
            String[] columns = {"value", "count"};

            JavaPairRDD<String, Set<String>> rdd2 = rdd.mapToPair(item -> {
                Set<Tuple2<String, Long>> set = item._2;
                Set<String> newSet = new HashSet<>();
                Iterator<Tuple2<String, Long>> iter = set.iterator();
                while (iter.hasNext()) {
                    newSet.add(GsonFactory.create().toJson(iter.next()));
                }
                return new Tuple2<String, Set<String>>(item._1, newSet);
            });
            Save.apply(this.hConf, "top-hashtag-by-day", families, columns, rdd2.take(Config.TOP_K));
        }
        return rdd;

    }

    public JavaPairRDD<User, Set<String>> getUserHashtags(Boolean saveValues) throws Exception {

        JavaPairRDD<User, Set<String>> rdd = BuilderRDDHashtags.userHashtags(this.tweetRDD);

        if (saveValues) {
            String[] families = {User.class.getSimpleName(), HashSet.class.getSimpleName()};
            String[] columns = {"user", "hashtags"};
            Save.apply(this.hConf, "user-hashtag", families, columns, InsertValues.convert(rdd));
        }
        return rdd;
    }


    public JavaPairRDD<Triplet, Long> getTripletHashtags(Boolean saveValues) throws Exception {
        JavaPairRDD<Triplet, Long> rdd = BuilderRDDHashtags.topTripletHashtag(this.tweetRDD);

        if (saveValues) {
            String[] families = {Triplet.class.getSimpleName(), Long.class.getSimpleName()};
            String[] columns = {"value", "count"};
            Save.apply(this.hConf, "triplet-hashtags", families, columns, rdd.take(Config.TOP_K));
        }
        return rdd;
    }

    public JavaPairRDD<String, Long> getHashtagByTweet(Boolean saveValues) throws Exception {
        JavaPairRDD<String, Long> rdd = BuilderRDDTweet.tweetByHashtagNb(this.tweetRDD);

        if (saveValues) {
            String[] families = {String.class.getSimpleName(), Long.class.getSimpleName()};
            String[] columns = {"nb-hashtag", "count"};
            Save.apply(this.hConf, "tweet-by-hashtag-nb", families, columns, rdd.collect());
        }
        return rdd;
    }

    public JavaPairRDD<String, Long> getNbHashtagByDay(Boolean saveValues) throws Exception {

        JavaPairRDD<String, Long> rdd = BuilderRDDHashtags.getNbHashtagByDay(this.tweetRDD);

        if (saveValues) {
            String[] families = {String.class.getSimpleName(), Long.class.getSimpleName()};
            String[] columns = {"date", "count"};
            Save.apply(this.hConf, "hashtag-by-day", families, columns, rdd.collect());
        }
        return rdd;
    }

    public JavaPairRDD<String, Long> getNbTweetByLang(Boolean saveValues) throws Exception {

        JavaPairRDD<String, Long> rdd = BuilderRDDTweet.nbTweetByLang(this.tweetRDD);

        if (saveValues) {
            String[] families = {String.class.getSimpleName(), Long.class.getSimpleName()};
            String[] columns = {"language", "count"};
            Save.apply(this.hConf, "tweet-by-language", families, columns, rdd.collect());
        }
        return rdd;
    }

    public JavaPairRDD<String, Long> getNbTweetByDay(Boolean saveValues) throws Exception {

        JavaPairRDD<String, Long> rdd = BuilderRDDTweet.getNbTweetByDay(this.tweetRDD);

        if (saveValues) {
            String[] families = {String.class.getSimpleName(), Long.class.getSimpleName()};
            String[] columns = {"date", "count"};
            Save.apply(this.hConf, "tweet-by-day", families, columns, rdd.collect());
        }
        return rdd;
    }


    public JavaPairRDD<String, Long> getTopUsers(Boolean saveValues, String category) throws Exception {
        JavaPairRDD<String, Long> rdd = BuilderRDDUser.topUser(this.tweetRDD, category);
        if (saveValues) {
            String[] families = {String.class.getSimpleName(), Long.class.getSimpleName()};
            String[] columns = {"user", "count"};
            Save.apply(this.hConf, "top-" + category + "-user", families, columns, rdd.take(Config.TOP_K));
        }
        return rdd;
    }


    public JavaPairRDD<String, Long> getInfluencers(Boolean saveValues) throws Exception {
        JavaPairRDD<String, Long> rdd = BuilderRDDUser.influencer(this.tweetRDD);
        if (saveValues) {
            String[] families = {String.class.getSimpleName(), Long.class.getSimpleName()};
            String[] columns = {"user", "top-triplet-tweet"};
            Save.apply(this.hConf, "influencers", families, columns, rdd.take(Config.TOP_K));
        }
        return rdd;
    }

    public JavaPairRDD<String, Tuple2<Long, Long>> getFakeInfluencers(Boolean saveValues) throws Exception {
        JavaPairRDD<String, Tuple2<Long, Long>> rdd = BuilderRDDUser.fakeInfluencer(this.tweetRDD);
        if (saveValues) {
            String[] families = {String.class.getSimpleName(), Tuple2.class.getSimpleName()};
            String[] columns = {"user", "followers-averageRT"};
            Save.apply(this.hConf, "fake-influencers", families, columns, rdd.take(Config.TOP_K));
        }
        return rdd;

    }

    public void getTimes(String fileName) throws Exception {
        Test.apply(fileName, this.fileRDD);
    }


}
