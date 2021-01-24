/* eslint-disable no-async-promise-executor */
/* eslint-disable camelcase */
const path   = require("path")
const config = require(path.resolve("./models/hbase_config.js"))
const hbase  = require(path.resolve("./models/hbase.js"))

/**
 * @namespace Model_user
 */


async function getTopKUserByTweet(){
    let ranking = []
} 
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
        resolve(ranking) 
    }) 
} 



module.exports = {
    getTopKUserByTweet,
    getTripletInfluencers,
    getTopFollowedUser,
    getTopTweetingUser
} 