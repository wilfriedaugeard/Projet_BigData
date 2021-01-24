const TABLE_NAME_TOPK_HASHTAG       = "augeard-tarmil-top-hashtag"
const TABLE_NAME_TOPK_LANG          = "augeard-tarmil-tweet-by-language"
const TABLE_NAME_TOPK_USER_BY_TWEET = "augeard-tarmil-top-user"
const TABLE_NAME_TOPK_TRIPLET       = "augeard-tarmil-triplet-hashtags"
const TABLE_NAME_TRIPLET_INFLUENCER = "augeard-tarmil-influencers"
const TABLE_NAME_TWEET_BY_DAY       = "augeard-tarmil-tweet-by-day"
const TABLE_NAME_REPARTITION_HASHTAGS = "augeard-tarmil-tweet-by-hashtag-nb"
const TABLE_NAME_TOPK_TWEETING_USER = "augeard-tarmil-top-tweeting-user"

const HASHTAG_VALUE = "String:value"
const USER_VALUE    = "String:user"
const TRIPLET_VALUE = "Triplet:value"
const LANG_VALUE    = "String:language"
const NB_VALUE      = "Long:count"
const NB_TRIPLET    = "Long:top-triplet-tweet"
const DATE_VALUE    = "String:date"
const REPARTITION_VALUE = "String:nb-hashtag"

const K_MAX = 1000



module.exports = {
    TABLE_NAME_TOPK_HASHTAG,
    TABLE_NAME_TOPK_TRIPLET,
    TABLE_NAME_TOPK_LANG,
    TABLE_NAME_TOPK_USER_BY_TWEET,
    TABLE_NAME_TRIPLET_INFLUENCER,
    TABLE_NAME_TWEET_BY_DAY,
    TABLE_NAME_REPARTITION_HASHTAGS,
    TABLE_NAME_TOPK_TWEETING_USER,
    HASHTAG_VALUE,
    TRIPLET_VALUE,
    USER_VALUE,
    LANG_VALUE,
    NB_VALUE,
    NB_TRIPLET,
    DATE_VALUE,
    REPARTITION_VALUE,
    K_MAX
}