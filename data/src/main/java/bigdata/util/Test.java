package bigdata.util;

import java.io.FileWriter;
import java.io.File;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import bigdata.builder.*;
import bigdata.entities.Tweet;

public class Test {
    /**
     * Create the file with all execution times for the creation of each RDD
     *
     * @param fileName : the type of data used
     * @param fileRDD  : the RDD with the initial data
     * @throws Exception
     */
    public static <T,U> void apply(String fileName, JavaRDD<String> fileRDD) throws Exception {
        try {
            File file = new File("test-time-" + fileName + ".txt");
            if (!file.createNewFile()) {
                file.delete();
                file.createNewFile();
            }
            FileWriter timeFile = new FileWriter("test-time-" + fileName + ".txt");
            JavaRDD<Tweet> tweetRDD;
    	    JavaPairRDD<T, U> rdd;
    	    long startTime = 0;
            long endTime = 0;

            timeFile.write("******* Hashtag Builder  *******\n");

            timeFile.write(" - Top Hashtag : ");
            startTime = new Date().getTime();
            tweetRDD = BuilderRDDTweet.getAllTweet(fileRDD);
            rdd = (JavaPairRDD<T,U>) BuilderRDDHashtags.topHastag(tweetRDD);
            endTime = new Date().getTime();
            timeFile.write((endTime - startTime) + " ms\n");

            timeFile.write(" - Top Hashtag by day : ");
            startTime = new Date().getTime();
            tweetRDD = BuilderRDDTweet.getAllTweet(fileRDD);
            rdd =(JavaPairRDD<T,U>) BuilderRDDHashtags.topHastagByDay(tweetRDD);
            endTime = new Date().getTime();
            timeFile.write((endTime - startTime) + " ms\n");

            timeFile.write(" - User Hashtag : ");
            startTime = new Date().getTime();
            tweetRDD = BuilderRDDTweet.getAllTweet(fileRDD);
            rdd =(JavaPairRDD<T,U>) BuilderRDDHashtags.userHashtags(tweetRDD);
            endTime = new Date().getTime();
            timeFile.write((endTime - startTime) + " ms\n");

            timeFile.write(" - Top Triplet Hashtag : ");
            startTime = new Date().getTime();
            tweetRDD = BuilderRDDTweet.getAllTweet(fileRDD);
            rdd = (JavaPairRDD<T,U>) BuilderRDDHashtags.topTripletHashtag(tweetRDD);
            endTime = new Date().getTime();
            timeFile.write((endTime - startTime) + " ms\n");

            timeFile.write(" - Nb Hashtag by day : ");
            startTime = new Date().getTime();
            tweetRDD = BuilderRDDTweet.getAllTweet(fileRDD);
            rdd = (JavaPairRDD<T,U>) BuilderRDDHashtags.getNbHashtagByDay(tweetRDD);
            endTime = new Date().getTime();
            timeFile.write((endTime - startTime) + " ms\n");

/*            timeFile.write("\n\n ******* Tweet Builder  *******\n");

            timeFile.write(" - Nb Tweet by hashtag number : ");
            startTime = new Date().getTime();
            tweetRDD = BuilderRDDTweet.getAllTweet(fileRDD);
            rdd = (JavaPairRDD<T,U>) BuilderRDDTweet.tweetByHashtagNb(tweetRDD);
            endTime = new Date().getTime();
            timeFile.write((endTime - startTime) + " ms\n");

            timeFile.write(" - Nb Tweet by language: ");
            startTime = new Date().getTime();
            tweetRDD = BuilderRDDTweet.getAllTweet(fileRDD);
            rdd = (JavaPairRDD<T,U>) BuilderRDDTweet.nbTweetByLang(tweetRDD);
            endTime = new Date().getTime();
            timeFile.write((endTime - startTime) + " ms\n");

            timeFile.write(" - Nb Tweet by day : ");
            startTime = new Date().getTime();
            tweetRDD = BuilderRDDTweet.getAllTweet(fileRDD);
            rdd = (JavaPairRDD<T,U>) BuilderRDDTweet.getNbTweetByDay(tweetRDD);
            endTime = new Date().getTime();
            timeFile.write((endTime - startTime) + " ms\n");

            timeFile.write("\n\n ******* User Builder  *******\n");

            timeFile.write(" - Top Tweeting User : ");
            startTime = new Date().getTime();
            tweetRDD = BuilderRDDTweet.getAllTweet(fileRDD);
            rdd = (JavaPairRDD<T,U>) BuilderRDDUser.topUser(tweetRDD, "tweeting");
            endTime = new Date().getTime();
            timeFile.write((endTime - startTime) + " ms\n");

            timeFile.write(" - Top Followed User : ");
            startTime = new Date().getTime();
            tweetRDD = BuilderRDDTweet.getAllTweet(fileRDD);
            rdd = (JavaPairRDD<T,U>) BuilderRDDUser.topUser(tweetRDD, "followed");
            endTime = new Date().getTime();
            timeFile.write((endTime - startTime) + " ms\n");

            timeFile.write(" - Influencers : ");
            startTime = new Date().getTime();
            tweetRDD = BuilderRDDTweet.getAllTweet(fileRDD);
            rdd = (JavaPairRDD<T,U>) BuilderRDDUser.influencer(tweetRDD);
            endTime = new Date().getTime();
            timeFile.write((endTime - startTime) + " ms\n");
*/
           try{ timeFile.close();}catch(Exception e){System.exit(-1);}

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

}
