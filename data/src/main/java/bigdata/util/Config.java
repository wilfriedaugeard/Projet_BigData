package bigdata.util;


public class Config {
    public static final String APP_NAME = "TP Spark";
    public static final String ONE_FILE_PATH = "/raw_data/tweet_01_03_2020.nljson";
    public static final String SMALL_FILE_PATH = "/raw_data/tweet_01_03_2020_first10000.nljson";
    public static final String[] FILES_LIST = {
            "/raw_data/tweet_01_03_2020.nljson",
            "/raw_data/tweet_02_03_2020.nljson",
            "/raw_data/tweet_03_03_2020.nljson",
            "/raw_data/tweet_04_03_2020.nljson",
            "/raw_data/tweet_05_03_2020.nljson",
            "/raw_data/tweet_06_03_2020.nljson",
            "/raw_data/tweet_07_03_2020.nljson",
            "/raw_data/tweet_08_03_2020.nljson",
            "/raw_data/tweet_09_03_2020.nljson",
            "/raw_data/tweet_10_03_2020.nljson",
            "/raw_data/tweet_11_03_2020.nljson",
            "/raw_data/tweet_12_03_2020.nljson",
            "/raw_data/tweet_13_03_2020.nljson",
            "/raw_data/tweet_14_03_2020.nljson",
            "/raw_data/tweet_15_03_2020.nljson",
            "/raw_data/tweet_16_03_2020.nljson",
            "/raw_data/tweet_17_03_2020.nljson",
            "/raw_data/tweet_18_03_2020.nljson",
            "/raw_data/tweet_19_03_2020.nljson",
            "/raw_data/tweet_20_03_2020.nljson",
            "/raw_data/tweet_21_03_2020.nljson",
    };

    public static String concatFiles() {
        String files = "";
        for (String f : FILES_LIST) {
            files += f + ",";
        }
        files = files.substring(0, files.length() - 1); // remove last ','
        return files;
    }

    public static final String ALL_FILES_PATH = concatFiles();


}
