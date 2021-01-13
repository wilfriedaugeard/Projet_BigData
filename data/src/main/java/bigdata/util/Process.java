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

    // Tweets
    public JavaRDD<Tweet> getAllTweets() {

        return this.tweetRDD;
    }

    public JavaPairRDD<Hashtag, Long> getNbTweetByLang() throws Exception {

        JavaPairRDD<Hashtag, Long> rdd = BuilderRDDTweet.nbTweetByLang(this.tweetRDD);

        String[] families = {Hashtag.class.getSimpleName(), Long.class.getSimpleName()};
        String[] columns = {"language", "count"};
        Save.apply(this.hConf, "by-lang-tweet", families, columns, InsertValues.createFromJavaPairRDD(rdd, Optional.empty()));

        return rdd;
    }

    // Hashtags
    public JavaRDD<Hashtag> getAllHastags() {
        return BuilderRDDHashtags.getAllHastags(this.tweetRDD);
    }

    public JavaPairRDD<Hashtag, Long> getTopHashtags(int topK) throws Exception {

        JavaPairRDD<Hashtag, Long> rdd = BuilderRDDHashtags.topHastag(this.tweetRDD);

        String[] families = {Hashtag.class.getSimpleName(), Long.class.getSimpleName()};
        String[] columns = {"value", "count"};
        Save.apply(this.hConf, "top-hashtag", families, columns, InsertValues.createFromJavaPairRDD(rdd, Optional.of(topK)));

        return rdd;
    }

    public JavaPairRDD<User, Set<Hashtag>> getUserHashtags() throws Exception {

        JavaPairRDD<User, Set<Hashtag>> rdd = BuilderRDDHashtags.userHashtags(this.tweetRDD);

        String[] families = {User.class.getSimpleName(), HashSet.class.getSimpleName()};
        String[] columns = {"user", "hashtags"};
        Save.apply(this.hConf, "user-hashtag", families, columns, InsertValues.createFromJavaPairRDD(rdd, Optional.empty()));

        return rdd;
    }

    public JavaRDD<Triplet> getTripletHashtags() throws Exception {
        JavaRDD<Triplet> rdd = BuilderRDDHashtags.tripletHashtags(this.tweetRDD);

        String[] families = {Integer.class.getSimpleName(), Triplet.class.getSimpleName()};
        String[] columns = {"hashcode", "hashtags"};
        Save.apply(this.hConf, "triplet-hashtag", families, columns, InsertValues.createFromJavaRDD(rdd));

        return rdd;
    }

    public JavaPairRDD<Triplet, Long> getTopTripletHashtags(int topK) throws Exception {
        JavaPairRDD<Triplet, Long> rdd = BuilderRDDHashtags.topTriplet(this.tweetRDD);

        String[] families = {Triplet.class.getSimpleName(), Long.class.getSimpleName()};
        String[] columns = {"value", "count"};
        Save.apply(this.hConf, "top-triplet-hashtag", families, columns, InsertValues.createFromJavaPairRDD(rdd, Optional.of(topK)));

        return rdd;
    }

    // Users
    public JavaPairRDD<Triplet, Set<User>> getTripletHashtagsAndUsers() throws Exception {
        JavaPairRDD<Triplet, Set<User>> rdd = BuilderRDDUser.userByTripletHashtags(this.tweetRDD);

        String[] families = {Triplet.class.getSimpleName(), HashSet.class.getSimpleName()};
        String[] columns = {"value", "user"};
        Save.apply(this.hConf, "triplet-user", families, columns, InsertValues.createFromJavaPairRDD(rdd, Optional.empty()));

        return rdd;
    }

    public JavaPairRDD<User, Long> getTopUsers() throws Exception {
        JavaPairRDD<User, Long> rdd = BuilderRDDUser.topUser(this.tweetRDD);

        String[] families = {User.class.getSimpleName(), Long.class.getSimpleName()};
        String[] columns = {"user", "count"};
        Save.apply(this.hConf, "top-user", families, columns, InsertValues.createFromJavaPairRDD(rdd, Optional.empty()));

        return rdd;
    }

    // DISPLAYING
    public void displayResult(Object o, int k) {
        switch (o.getClass().getSimpleName()) {
            case "JavaRDD":
                displayResultJavaRDD((JavaRDD<IBigDataObject>) o, k);
                break;
            case "JavaPairRDD":
                try {
                    JavaPairRDD<IBigDataObject, Long> rdd = (JavaPairRDD<IBigDataObject, Long>) o;
                    displayResultJavaPairRDDInt(rdd, k);
                } catch (Exception e) {
                    try {
                        JavaPairRDD<IBigDataObject, Set<IBigDataObject>> rdd = (JavaPairRDD<IBigDataObject, Set<IBigDataObject>>) o;
                        displayResultJavaPairRDDSet(rdd, k);
                    } catch (Exception e2) {
                    }
                } finally {
                    break;
                }
            default:
                break;
        }
    }


    public void displayResultJavaRDD(JavaRDD<IBigDataObject> rdd, int k) {
        rdd.take(k).forEach(item -> System.out.println(item));
    }

    public void displayResultJavaPairRDDInt(JavaPairRDD<IBigDataObject, Long> rdd, int k) {
        rdd.take(k).forEach(item -> System.out.println(item));
    }

    public void displayResultJavaPairRDDSet(JavaPairRDD<IBigDataObject, Set<IBigDataObject>> rdd, int k) {
        rdd.take(k).forEach(item -> System.out.println(item));
    }

}
