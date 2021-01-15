package bigdata;

import bigdata.util.Process;
import bigdata.util.Config;
import bigdata.util.Display;


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
                Display.displayResult(process.getTopHashtags(saveValues), Config.DISPLAY);
                break;
            case "user-hashtag":
                Display.displayResult(process.getUserHashtags(saveValues), Config.DISPLAY);
                break;
            case "triplet-hashtag":
                Display.displayResult(process.getTripletHashtagsAndUsers(saveValues), Config.DISPLAY);
                break;
            case "tweet-by-hashtag-nb":
                Display.displayResult(process.getHashtagByTweet(saveValues), Config.DISPLAY);
                break;
            case "tweet-by-language":
                Display.displayResult(process.getNbTweetByLang(saveValues), Config.DISPLAY);
                break;
            case "tweet-by-day":
                Display.displayResult(process.getNbTweetByDay(saveValues), Config.DISPLAY);
                break;
            case "top-followed-user":
                Display.displayResult(process.getTopUsers(saveValues, "followed"), Config.DISPLAY);
                break;
            case "top-retweeted-user":
                Display.displayResult(process.getTopUsers(saveValues, "retweeted"), Config.DISPLAY);
                break;
            case "top-tweeting-user":
                Display.displayResult(process.getTopUsers(saveValues, "tweeting"), Config.DISPLAY);
                break;
            case "influencers":
                Display.displayResult(process.getInfluencers(saveValues), Config.DISPLAY);
                break;
            case "fake-influencers":
                Display.displayResult(process.getFakeInfluencers(saveValues), Config.DISPLAY);
                break;
            default:
                process.close();
                usage("wrong function argument");
        }

        process.close();

    }

}
