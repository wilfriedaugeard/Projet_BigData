/* eslint-disable no-async-promise-executor */
/* eslint-disable camelcase */
const path   = require("path")
const config = require(path.resolve("./models/hbase_config.js"))
const hbase  = require(path.resolve("./models/hbase.js"))

/**
 * @namespace Model_user
 */
/**
 * Get top k of users who use mostly triplet hashtags 
 * @memberof Model_user 
 */
async function getTripletInfluencers(){
    let ranking = []
    let nbTweet, user
    let n = await hbase.getTableLength(config.TABLE_NAME_TRIPLET_INFLUENCER)
    return new Promise(async (resolve, reject) => {
        for (let i = 0; i < n; i++) {
            nbTweet = await hbase.getHbaseValue(config.TABLE_NAME_TRIPLET_INFLUENCER, i.toString(), config.NB_TRIPLET)
            user = await hbase.getHbaseValue(config.TABLE_NAME_TRIPLET_INFLUENCER, i.toString(), config.USER_VALUE)
            user = JSON.parse(user)
            ranking.push([user._1, user._2, nbTweet])
        }
        resolve(ranking) 
    }) 
} 

/**
 * Get top k of users who is mostly followed
 * @memberof Model_user 
 */
async function getTopFollowedUser(){
    let ranking = []
    let nbFollowers, user
    let n = await hbase.getTableLength(config.TABLE_NAME_TOPK_FOLLOWED_USER)
    return new Promise(async (resolve, reject) => {
        for (let i = 0; i < n; i++) {
            nbFollowers = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_FOLLOWED_USER, i.toString(), config.NB_VALUE)
            user = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_FOLLOWED_USER, i.toString(), config.USER_VALUE)
            user = JSON.parse(user)
            ranking.push([user._1, user._2, nbFollowers])
        }
        resolve(ranking) 
    }) 
} 



/**
 * Get top k of users who tweet mostly
 * @memberof Model_user 
 */
async function getTopTweetingUser(){
    let ranking = []
    let nbTweet, user
    let n = await hbase.getTableLength(config.TABLE_NAME_TOPK_TWEETING_USER)
    return new Promise(async (resolve, reject) => {
        for (let i = 0; i < n; i++) {
            nbTweet = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_TWEETING_USER, i.toString(), config.NB_VALUE)
            user = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_TWEETING_USER, i.toString(), config.USER_VALUE)
            user = JSON.parse(user)
            ranking.push([user._1, user._2, nbTweet])
        }
        console.log(ranking)
        resolve(ranking) 
    }) 
} 

/**
 * Get top k of users who is mostly retweeted
 * @memberof Model_user 
 */
async function getTopRetweetedUser(){
    let ranking = []
    let nbTweet, user
    let n = await hbase.getTableLength(config.TABLE_NAME_TOPK_RT_USER)
    return new Promise(async (resolve, reject) => {
        for (let i = 0; i < n; i++) {
            nbTweet = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_RT_USER, i.toString(), config.NB_VALUE)
            user = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_RT_USER, i.toString(), config.USER_VALUE)
            user = JSON.parse(user)
            ranking.push([user._1, user._2, nbTweet])
        }
        resolve(ranking) 
    }) 
} 

/**
 * Get user info by id
 * @param {string} user_id user id
 * @memberof Model_user 
 */
async function getInfo(user_id){ 
    let pos = await hbase.getElement(config.TABLE_NAME_USER_INFO, user_id.toString())
    if(pos.length == 0 ) return null
    for(let i=0; i<pos.length; i++){
        let hashtagList = await hbase.getHbaseValue(config.TABLE_NAME_USER_INFO, pos[i], config.HASHTAG_LIST)
        let user = await hbase.getHbaseValue(config.TABLE_NAME_USER_INFO, pos[i], config.USER_DETAILS)
        hashtagList = hashtagList.split("")
        hashtagList.shift()
        hashtagList.pop()
        hashtagList = hashtagList.join("")
        if(user_id == JSON.parse(user).id_str) return [JSON.parse(user), hashtagList.split(", ").map(v => "#"+v).join(", ")] 
    } 
    return null
} 


module.exports = {
    getTripletInfluencers,
    getTopFollowedUser,
    getTopTweetingUser,
    getTopRetweetedUser,
    getInfo
} 