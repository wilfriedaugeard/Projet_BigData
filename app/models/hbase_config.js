const TABLE_NAME_TOPK_HASHTAG       = "augeard-tarmil-top-hashtag"
const TABLE_NAME_TOPK_LANG          = "augeard-tarmil-by-lang-tweet"
const TABLE_NAME_TOPK_USER_BY_TWEET = "augeard-tarmil-top-user"
const TABLE_NAME_TOPK_TRIPLET       = "augeard-tarmil-top-triplet-hashtag"
const TABLE_NAME_TRIPLET_INFLUENCER = "augeard-tarmil-influencers-user"
const TABLE_NAME_TWEET_BY_DAY       = "augeard-tarmil-tweet-by-day"
const TABLE_NAME_REPARTITION_HASHTAGS = "augeard-tarmil-tweet-by-hashtag-nb"

const HASHTAG_VALUE = "Hashtag:value"
const USER_VALUE    = "User:user"
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