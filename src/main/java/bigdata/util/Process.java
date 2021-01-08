package bigdata.util;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.util.ToolRunner;

import scala.Tuple2;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.util.List;
import bigdata.entities.User;
import bigdata.util.Builder;
import bigdata.entities.Tweet;
import bigdata.entities.Hashtag;
import bigdata.entities.Triplet;
import bigdata.InitTable;
import bigdata.InsertTweet;

public class Process {
    private JavaSparkContext context;
    private JavaRDD<String> fileRDD;
    private JavaRDD<Tweet> tweetRDD;
    private JavaPairRDD<Hashtag, Integer> hashtagsRDD;
    private JavaRDD<User> userByHashtag;
    private long nbHashtagOccurence;
    private JavaRDD<Hashtag> hashtagByUser;
    private JavaPairRDD<String, Integer>  nbTweetByLang;
    private JavaPairRDD<Triplet, List<User>> triplet;
    private Configuration hConf;

    public Process(String app_name, String pathFile){
        SparkConf conf = new SparkConf().setAppName(app_name);
        this.context = new JavaSparkContext(conf);
        this.fileRDD = this.context.textFile(pathFile);

        this.hConf = HBaseConfiguration.create();
        ToolRunner.run(this.hConf, new InitTable(), null);

    } 

    private void getAllTweet(){
        this.tweetRDD = Builder.getAllTweet(this.fileRDD);
    } 

    public void displayNTweet(int n){
        
        BufferedWriter tweetfile = new BufferedWriter(new FileWriter("../tweet.txt"));
        getAllTweet();
        this.tweetRDD.take(n).forEach(item -> {System.out.println(item); tweetfile.write(item);});
        tweetfile.close();
        ToolRunnner.run(this.hconf, new InsertTweet(), "../tweet.txt");
    } 

    public void computeTopHashtag(){
        getAllTweet();
        this.hashtagsRDD = Builder.topHastag(this.tweetRDD);
    } 

    public void displayTopKHashtag(int k){
        this.hashtagsRDD.take(k).forEach(item -> System.out.println(item));
    } 

    public void getUserByHashtag(String hashtag){
        getAllTweet();
        this.userByHashtag = Builder.usersByHashtag(this.tweetRDD, hashtag);
    } 

    public void displayKUserByHashtag(int k){
        this.userByHashtag.take(k).forEach(item -> System.out.println(item));
    } 

    public long getNbHashtagOccurence(String hashtag){
        getAllTweet();
        Hashtag h = new Hashtag(hashtag);
        this.nbHashtagOccurence = Builder.getAllHastags(this.tweetRDD).filter(x -> x.equals(h)).count();
        return this.nbHashtagOccurence;
    } 

    public void getHashtagByUser(String id){
        getAllTweet();
        this.hashtagByUser = Builder.hashtagByUser(this.tweetRDD, id);
        this.hashtagByUser.collect().forEach(item -> System.out.println(item));
    } 

    public long getNbTweetByUser(String id){
        getAllTweet();
        return this.tweetRDD.filter(tweet -> tweet.getUser().getId().equals(id)).count();
    } 

    public void getNbTweetByLang(){
        getAllTweet();
        this.nbTweetByLang = Builder.nbTweetByLang(this.tweetRDD);
        this.nbTweetByLang.collect().forEach(item -> System.out.println(item));
    } 
    
    public void getTripletHashtag(){
        getAllTweet();
        this.triplet = Builder.userByTripletHashTag(this.tweetRDD);
    } 
    public void displayKTripletUsers(int k){
        this.triplet.take(k).forEach(item -> System.out.println(item));
    } 

    public void displayTripletWithMoreNUsers(int n){
        this.triplet.filter(t-> t._2.size()> n).collect().forEach(item -> System.out.println(item));
    } 

    public void tripletTopK(int k){
        getAllTweet();
        Builder.topTriplet(this.tweetRDD).take(k).forEach(item -> System.out.println(item));
    } 

    public void close(){
        this.context.close();
    } 

    
}
