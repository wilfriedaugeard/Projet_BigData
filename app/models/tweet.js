/* eslint-disable no-async-promise-executor */
const path   = require("path")
const config = require(path.resolve("./models/hbase_config.js"))
const hbase  = require(path.resolve("./models/hbase.js"))

let RANKING = "" 

/**
 * @namespace Model_tweet
 */
/**
 * Get number of tweet by day
 * @memberof Model_tweet 
 */
async function getNbTweetByDay() {
    let tmp = []  
    let date, count
    return new Promise(async (resolve, reject) => { 
        let n = await hbase.getTableLength(config.TABLE_NAME_TWEET_BY_DAY)
        for (let i = 0; i < n; i++) {
            date = await hbase.getHbaseValue(config.TABLE_NAME_TWEET_BY_DAY, i.toString(), config.DATE_VALUE)
            count = await hbase.getHbaseValue(config.TABLE_NAME_TWEET_BY_DAY, i.toString(), config.NB_VALUE)
            tmp.push([date.split(" ")[2], count])
        }
        RANKING = tmp.sort().map(v => v[1]).join()
        resolve(RANKING)
    })
}



function getRanking(){
    return RANKING
} 


module.exports = {
    getNbTweetByDay,
    getRanking
}