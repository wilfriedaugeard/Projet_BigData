const TABLE_NAME_TOPK_HASHTAG       = 'augeard-tarmil-topk-hashtag'
const TABLE_NAME_TOPK_LANG          = 'augeard-tarmil-by-lang-tweet'
const TABLE_NAME_TOPK_USER_BY_TWEET = 'augeard-tarmil-top-user'
const TABLE_NAME_TOPK_TRIPLET       = 'augeard-tarmil-top-triplet-hashtag'
const TABLE_NAME_TRIPLET_INFLUENCER = 'augeard-tarmil-influencers-user'

const HASHTAG_VALUE = 'Hashtag:value'
const USER_VALUE    = 'User:user'
const TRIPLET_VALUE = 'Triplet:value'
const LANG_VALUE    = 'String:language'
const NB_VALUE      = 'Long:count'
const NB_TRIPLET    = 'Long:top-triplet-tweet'

const K_MAX = 1000



module.exports = {
    TABLE_NAME_TOPK_HASHTAG,
    TABLE_NAME_TOPK_TRIPLET,
    TABLE_NAME_TOPK_LANG,
    TABLE_NAME_TOPK_USER_BY_TWEET,
    TABLE_NAME_TRIPLET_INFLUENCER,
    HASHTAG_VALUE,
    TRIPLET_VALUE,
    USER_VALUE,
    LANG_VALUE,
    NB_VALUE,
    NB_TRIPLET,
    K_MAX
}