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

    public JavaPairRDD<Hashtag, Long> getTopHashtags(Boolean saveValues) throws Exception {

        JavaPairRDD<Hashtag, Long> rdd = BuilderRDDHashtags.topHastag(this.tweetRDD);

        if (saveValues) {
            String[] families = {Hashtag.class.getSimpleName(), Long.class.getSimpleName()};
            String[] columns = {"value", "count"};
            Save.apply(this.hConf, "top-hashtag", families, columns, rdd);
        }
        return rdd;
    }


    public JavaPairRDD<User, Set<Hashtag>> getUserHashtags(Boolean saveValues) throws Exception {

        JavaPairRDD<User, Set<Hashtag>> rdd = BuilderRDDHashtags.userHashtags(this.tweetRDD);
        if (saveValues) {
            String[] families = {User.class.getSimpleName(), HashSet.class.getSimpleName()};
            String[] columns = {"user", "hashtags"};
            Save.apply(this.hConf, "user-hashtag", families, columns, rdd);
        }
        return rdd;
    }


    public JavaPairRDD<Triplet, Tuple2<Long, Set<User>>> getTripletHashtagsAndUsers(Boolean saveValues) throws Exception {
        JavaPairRDD<Triplet, Set<User>> rdd = BuilderRDDUser.userByTripletHashtags(this.tweetRDD);

        if (saveValues) {
            String[] families = {Triplet.class.getSimpleName(), Tuple2.class.getSimpleName()};
            String[] columns = {"value", "count-and-users-list"};
            Save.apply(this.hConf, "triplet-hashtags", families, columns, rdd);
        }
        return rdd;
    }

    public JavaPairRDD<String, Long> getHashtagByTweet(Boolean saveValues) throws Exception {
        JavaPairRDD<Long, String> rdd = BuilderRDDTweet.tweetByHashtagNb(this.tweetRDD);

        if (saveValues) {
            String[] families = {String.class.getSimpleName(), Long.class.getSimpleName()};
            String[] columns = {"nb-hashtag", "count"};
            Save.apply(this.hConf, "tweet-by-hashtag-nb", families, columns, rdd);
        }
        return rdd;
    }

    public JavaPairRDD<String, Long> getNbTweetByLang(Boolean saveValues) throws Exception {

        JavaPairRDD<String, Long> rdd = BuilderRDDTweet.nbTweetByLang(this.tweetRDD);

        if (saveValues) {
            String[] families = {String.class.getSimpleName(), Long.class.getSimpleName()};
            String[] columns = {"language", "count"};
            Save.apply(this.hConf, "tweet-by-language", families, columns, rdd);
        }
        return rdd;
    }

    public JavaPairRDD<String, Long> getNbTweetByDay(Boolean saveValues) throws Exception {

        JavaPairRDD<String, Long> rdd = BuilderRDDTweet.nbTweetByLang(this.tweetRDD);

        if (saveValues) {
            String[] families = {String.class.getSimpleName(), Long.class.getSimpleName()};
            String[] columns = {"date", "count"};
            Save.apply(this.hConf, "tweet-by-day", families, columns, rdd);
        }
        return rdd;
    }

    public JavaPairRDD<User, Long> getTopUsers(Boolean saveValues, String category) throws Exception {
        JavaPairRDD<User, Long> rdd = BuilderRDDUser.topUser(this.tweetRDD, category);
        if (saveValues) {
            String[] families = {User.class.getSimpleName(), Long.class.getSimpleName()};
            String[] columns = {"user", "count"};
            Save.apply(this.hConf, "top-" + category + "-user", families, columns, rdd);
        }
        return rdd;
    }


    public JavaPairRDD<User, Long> getInfluencers(Boolean saveValues) throws Exception {
        JavaPairRDD<User, Long> rdd = BuilderRDDUser.influencer(this.tweetRDD);
        if(saveValues) {
            String[] families = {User.class.getSimpleName(), Long.class.getSimpleName()};
            String[] columns = {"user", "top-triplet-tweet"};
            Save.apply(this.hConf, "influencers", families, columns, rdd);
        }
        return rdd;
    }

    public JavaPairRDD<User, Tuple2<Long, Long>> getFakeInfluencers(Boolean saveValues) throws Exception {
        JavaPairRDD<User, Tuple2<Long, Long>> rdd = BuilderRDDUser.fakeInfluencer(this.tweetRDD);
        if(saveValues) {
            String[] families = {User.class.getSimpleName(), Tuple2.class.getSimpleName()};
            String[] columns = {"user", "followers-averageRT"};
            Save.apply(this.hConf, "fake-influencers", families, columns, rdd);
        }
        return rdd;

    }


}
