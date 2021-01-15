const TABLE_NAME_TOPK_HASHTAG       = 'augeard-tarmil-topk-hashtag'
const TABLE_NAME_TOPK_LANG          = 'augeard-tarmil-by-lang-tweet'
const TABLE_NAME_TOPK_USER_BY_TWEET = 'augeard-tarmil-top-user'
const TABLE_NAME_TRIPLET_INFLUENCER = 'augeard-tarmil-influencers-user'

const HASHTAG_VALUE = 'Hashtag:value'
const USER_VALUE    = 'User:user'
const TRIPLET_VALUE = 'Long:top-triplet-tweet'
const LANG_VALUE    = 'Hashtag:value' // temporaire, doit etre un String
const NB_VALUE      = 'Long:count'

const K_MAX = 1000



module.exports = {
    TABLE_NAME_TOPK_HASHTAG,
    TABLE_NAME_TOPK_LANG,
    TABLE_NAME_TOPK_USER_BY_TWEET,
    TABLE_NAME_TRIPLET_INFLUENCER,
    HASHTAG_VALUE,
    USER_VALUE,
    LANG_VALUE,
    NB_VALUE,
    K_MAX
}