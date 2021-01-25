package bigdata.util;

import java.io.FileWriter;
import java.io.File;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

import bigdata.builder.*;
import bigdata.entities.Tweet;

public class Test {
    private JavaRDD<Tweet> tweetRDD;
    private JavaPairRDD<T, U> rdd;
    private long startTime;
    private long endTime;


    /**
     * Create the file with all execution times for the creation of each RDD
     *
     * @param fileName : the type of data used
     * @param fileRDD  : the RDD with the initial data
     * @throws Exception
     */
    public void apply(String fileName, JavaRDD<String> fileRDD) throws Exception {
        try {
            File file = new File("test-time-" + fileName + ".txt");
            if (!file.createNewFile()) {
                file.delete();
                file.createNewFile();
            }
            FileWriter timeFile = new FileWriter("test-time-" + fileName + ".txt");

            timeFile.write("******* Hashtag Builder  *******\n");

            timeFile.write(" - Top Hashtag : ");
            this.startTime = new Date().getTime();
            this.tweetRDD = BuilderRDDTweet.getAllTweet(this.fileRDD);
            this.rdd = BuilderRDDHashtags.topHastag(this.tweetRDD);
            this.endTime = new Date().getTime();
            timeFile.write((this.endTime - this.startTime) + " ms\n");

            timeFile.write(" - Top Hashtag by day : ");
            this.startTime = new Date().getTime();
            this.tweetRDD = BuilderRDDTweet.getAllTweet(this.fileRDD);
            this.rdd = BuilderRDDHashtags.topHastagByDay(this.tweetRDD);
            this.endTime = new Date().getTime();
            timeFile.write((this.endTime - this.startTime) + " ms\n");

            timeFile.write(" - User Hashtag : ");
            this.startTime = new Date().getTime();
            this.tweetRDD = BuilderRDDTweet.getAllTweet(this.fileRDD);
            this.rdd = BuilderRDDHashtags.userHashtags(this.tweetRDD);
            this.endTime = new Date().getTime();
            timeFile.write((this.endTime - this.startTime) + " ms\n");

            timeFile.write(" - Top Triplet Hashtag : ");
            this.startTime = new Date().getTime();
            this.tweetRDD = BuilderRDDTweet.getAllTweet(this.fileRDD);
            this.rdd = BuilderRDDHashtags.topTripletHashtag(this.tweetRDD);
            this.endTime = new Date().getTime();
            timeFile.write((this.endTime - this.startTime) + " ms\n");

            timeFile.write(" - Nb Hashtag by day : ");
            this.startTime = new Date().getTime();
            this.tweetRDD = BuilderRDDTweet.getAllTweet(this.fileRDD);
            this.rdd = BuilderRDDHashtags.getNbHashtagByDay(this.tweetRDD);
            this.endTime = new Date().getTime();
            timeFile.write((this.endTime - this.startTime) + " ms\n");

            timeFile.write("\n\n ******* Tweet Builder  *******\n");

            timeFile.write(" - Nb Tweet by hashtag number : ");
            this.startTime = new Date().getTime();
            this.tweetRDD = BuilderRDDTweet.getAllTweet(this.fileRDD);
            this.rdd = BuilderRDDTweet.tweetByHashtagNb(this.tweetRDD);
            this.endTime = new Date().getTime();
            timeFile.write((this.endTime - this.startTime) + " ms\n");

            timeFile.write(" - Nb Tweet by language: ");
            this.startTime = new Date().getTime();
            this.tweetRDD = BuilderRDDTweet.getAllTweet(this.fileRDD);
            this.rdd = BuilderRDDTweet.nbTweetByLang(this.tweetRDD);
            this.endTime = new Date().getTime();
            timeFile.write((this.endTime - this.startTime) + " ms\n");

            timeFile.write(" - Nb Tweet by day : ");
            this.startTime = new Date().getTime();
            this.tweetRDD = BuilderRDDTweet.getAllTweet(this.fileRDD);
            this.rdd = BuilderRDDTweet.getNbTweetByDay(this.tweetRDD);
            this.endTime = new Date().getTime();
            timeFile.write((this.endTime - this.startTime) + " ms\n");

            timeFile.write("\n\n ******* User Builder  *******\n");

            timeFile.write(" - Top Tweeting User : ");
            this.startTime = new Date().getTime();
            this.tweetRDD = BuilderRDDTweet.getAllTweet(this.fileRDD);
            this.rdd = BuilderRDDUser.topUser(this.tweetRDD, "tweeting");
            this.endTime = new Date().getTime();
            timeFile.write((this.endTime - this.startTime) + " ms\n");

            timeFile.write(" - Top Followed User : ");
            this.startTime = new Date().getTime();
            this.tweetRDD = BuilderRDDTweet.getAllTweet(this.fileRDD);
            this.rdd = BuilderRDDUser.topUser(this.tweetRDD, "followed");
            this.endTime = new Date().getTime();
            timeFile.write((this.endTime - this.startTime) + " ms\n");

            timeFile.write(" - Influencers : ");
            this.startTime = new Date().getTime();
            this.tweetRDD = BuilderRDDTweet.getAllTweet(this.fileRDD);
            this.rdd = BuilderRDDUser.influencers(this.tweetRDD);
            this.endTime = new Date().getTime();
            timeFile.write((this.endTime - this.startTime) + " ms\n");

            timeFile.close()

        } catch (Exception e) {
            eprintStackTrace();
            System.exit(-1);
        }

    }

}
