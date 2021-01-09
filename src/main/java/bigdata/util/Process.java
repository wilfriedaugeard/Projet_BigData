package bigdata.util;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.util.ToolRunner;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import scala.Tuple2;
import java.util.List;
import java.util.Set;

import bigdata.entities.User;
import bigdata.builder.*;
import bigdata.entities.Tweet;
import bigdata.entities.Hashtag;
import bigdata.entities.Triplet;
import bigdata.InitHashtagTable;
import bigdata.InsertHashtag;
import bigdata.entities.IBigDataObject;

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
    Configuration hConf;
    FileWriter saveFile;

    public Process(String app_name, String pathFile) throws IOException{
        SparkConf conf = new SparkConf().setAppName(app_name);
        this.context = new JavaSparkContext(conf);
        this.fileRDD = this.context.textFile(pathFile);
        this.tweetRDD = BuilderRDDTweet.getAllTweet(this.fileRDD);
            
        this.hConf = HBaseConfiguration.create();
        try {
            File file = new File("saveFile.txt");
        if(!file.createNewFile()){
            file.delete();
            file.createNewFile();
        }
        this.saveFile = new FileWriter("saveFile.txt");
        }catch(IOException ioe) {}
        
    } 

    public void close() throws IOException{
        this.context.close();
    } 

    // Tweets
    public JavaRDD<Tweet> getAllTweets(){
        return this.tweetRDD;
    } 
    public JavaPairRDD<Hashtag, Long> getNbTweetByLang(){
        return BuilderRDDTweet.nbTweetByLang(this.tweetRDD);
    } 

    // Hashtags
    public JavaRDD<Hashtag> getAllHastags(){
        return BuilderRDDHashtags.getAllHastags(this.tweetRDD);
    } 
    public JavaPairRDD<Hashtag, Long> getTopHashtags(){
        return BuilderRDDHashtags.topHastag(this.tweetRDD);
    }
    public JavaPairRDD<User, Set<Hashtag>> getUserHashtags(){
        return BuilderRDDHashtags.userHashtags(this.tweetRDD);
    }  
    public JavaRDD<Triplet> getTripletHashtags(){
        return BuilderRDDHashtags.tripletHashtags(this.tweetRDD);
    } 
    public JavaPairRDD<Triplet, Long> getTopTripletHashtags(){
        return BuilderRDDHashtags.topTriplet(this.tweetRDD);
    } 

    // Users
    public JavaPairRDD<Triplet, Set<User>> getTripletHashtagsAndUsers(){
        return BuilderRDDUser.userByTripletHashtags(this.tweetRDD);
    } 
    public JavaPairRDD<User, Long> getTopUsers(){
        return BuilderRDDUser.topUser(this.tweetRDD);
    } 

    // DISPLAYING
    public void displayResult(Object o, int k){
        switch(o.getClass().getSimpleName()){
            case "JavaRDD":
                displayResultJavaRDD((JavaRDD<IBigDataObject>) o, k);
                break;
            case "JavaPairRDD":
                try{
                    JavaPairRDD<IBigDataObject, Long> rdd = (JavaPairRDD<IBigDataObject, Long>) o;
                    displayResultJavaPairRDDInt(rdd, k);
                }catch(Exception e){
                    try{
                        JavaPairRDD<IBigDataObject, Set<IBigDataObject>> rdd = (JavaPairRDD<IBigDataObject, Set<IBigDataObject>>) o;
                        displayResultJavaPairRDDSet(rdd, k);
                    }catch(Exception e2){}  
                }finally{
                    break;
                } 
            default:
                break;
        } 
    } 

    public void displayResultJavaRDD(JavaRDD<IBigDataObject> rdd, int k){
        rdd.take(k).forEach(item -> System.out.println(item));
    } 


    public void displayResultJavaPairRDDInt(JavaPairRDD<IBigDataObject, Long> rdd, int k) throws IOException, Exception{
        try{
            rdd.take(k).forEach(item -> { 
                System.out.println(item); 
		        try{
                    String line = item.toString();
                    line = line.replace("({\n\"text\"; ","");
                    line = line.replace("}","");
                    line = line.replace(")","");
                    System.out.println("\n\n"+line + "\n");
                    this.saveFile.write(item.toString());
                }catch(Exception e){}
            });

	        this.saveFile.close();
            ToolRunner.run(this.hConf, new InitHashtagTable(),null);
            ToolRunner.run(this.hConf, new InsertHashtag(), null);

        }catch(IOException ioe) {}
    } 


    public void displayResultJavaPairRDDSet(JavaPairRDD<IBigDataObject, Set<IBigDataObject>> rdd, int k){
        rdd.take(k).forEach(item -> System.out.println(item));
    } 

}
