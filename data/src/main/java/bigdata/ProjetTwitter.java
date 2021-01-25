package bigdata;

import bigdata.util.Process;
import bigdata.util.Config;
import bigdata.util.DisplayResult;


public class ProjetTwitter {

    private static void usage(String error) {
        System.out.println("Usage: " + error);
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {

        if (args.length != 3) {
            usage("wrong number of arguments");
        }

        String file = "";
        switch (args[1]) {
            case "small":
                file = Config.SMALL_FILE_PATH;
                break;
            case "one":
                file = Config.ONE_FILE_PATH;
                break;
            case "all":
                file = Config.ALL_FILES_PATH;
                break;
            default:
                usage("wrong file arguments ");
                break;
        }

        Process process = new Process(Config.APP_NAME, file);

        Boolean saveValues = (args[2].equals("true")) ? true : false;

        switch (args[0]) {
            case "top-hashtag":
                DisplayResult.displayResult(process.getTopHashtags(saveValues), Config.DISPLAY);
                break;
            case "top-hashtag-by-day":
                DisplayResult.displayResult(process.getTopHashtagsByDay(saveValues), Config.DISPLAY);
                break;
            case "user-hashtag":
                DisplayResult.displayResult(process.getUserHashtags(saveValues), Config.DISPLAY);
                break;
            case "triplet-hashtag":
                DisplayResult.displayResult(process.getTripletHashtags(saveValues), Config.DISPLAY);
                break;
            case "hashtag-by-day":
                DisplayResult.displayResult(process.getNbHashtagByDay(saveValues), Config.DISPLAY);
                break;
            case "tweet-by-hashtag-nb":
                DisplayResult.displayResult(process.getHashtagByTweet(saveValues), Config.DISPLAY);
                break;
            case "tweet-by-language":
                DisplayResult.displayResult(process.getNbTweetByLang(saveValues), Config.DISPLAY);
                break;
            case "tweet-by-day":
                DisplayResult.displayResult(process.getNbTweetByDay(saveValues), Config.DISPLAY);
                break;
            case "top-followed-user":
                DisplayResult.displayResult(process.getTopUsers(saveValues, "followed"), Config.DISPLAY);
                break;
            case "top-retweeted-user":
                DisplayResult.displayResult(process.getTopUsers(saveValues, "retweeted"), Config.DISPLAY);
                break;
            case "top-tweeting-user":
                DisplayResult.displayResult(process.getTopUsers(saveValues, "tweeting"), Config.DISPLAY);
                break;
            case "influencers":
                DisplayResult.displayResult(process.getInfluencers(saveValues), Config.DISPLAY);
                break;
            case "fake-influencers":
                DisplayResult.displayResult(process.getFakeInfluencers(saveValues), Config.DISPLAY);
                break;
            case "time-test":
                process.getTimes(args[1]);
                break;
            default:
                process.close();
                usage("wrong function argument");
        }

        process.close();

    }

}
