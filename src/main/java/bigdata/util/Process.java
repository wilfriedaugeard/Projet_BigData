package bigdata.util;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

import bigdata.entities.User;
import bigdata.util.Builder;
import bigdata.entities.Tweet;
import bigdata.entities.Hashtag;

public class Process {
    private JavaSparkContext context;
    private JavaRDD<String> fileRDD;
    private JavaRDD<Tweet> tweetRDD;
    private JavaPairRDD<Hashtag, Integer> hashtagsRDD;
    private JavaRDD<User> userByHashtag;
    private long nbHashtagOccurence;

    public Process(String app_name, String pathFile){
        SparkConf conf = new SparkConf().setAppName(app_name);
        this.context = new JavaSparkContext(conf);
        this.fileRDD = this.context.textFile(pathFile);
    } 

    private void getAllTweet(){
        this.tweetRDD = Builder.getAllTweet(this.fileRDD);
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

    
    

    public void close(){
        this.context.close();
    } 

    
}
