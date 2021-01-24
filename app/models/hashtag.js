/* eslint-disable no-async-promise-executor */
const path   = require("path")
const config = require(path.resolve("./models/hbase_config.js"))
const hbase  = require(path.resolve("./models/hbase.js"))

let HASHTAG_LIST = [] 
let RANKING = [] 

/**
 * @namespace Model_hashtag
 */
/**
 * Get top k hashtags
 * @memberof Model_hashtag 
 */
async function getTopKHashtag() {
    HASHTAG_LIST = [] 
    let ranking = []
    let hashtag, count
    return new Promise(async (resolve, reject) => { 
        for (let i = 0; i < config.K_MAX; i++) {
            hashtag = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_HASHTAG, i.toString(), config.HASHTAG_VALUE)
            count = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_HASHTAG, i.toString(), config.NB_VALUE)
            ranking.push([hashtag, count])
            HASHTAG_LIST.push(hashtag)
        }
        RANKING = ranking
        resolve(ranking)
    })
}

/**
 * Get top k triplet hashtags
 * @memberof Model_hashtag 
 */
async function getTopKTriplet() {
    let ranking = []
    let triplet = ""
    let n = await hbase.getTableLength(config.TABLE_NAME_TOPK_TRIPLET)
    let array, count
    return new Promise(async (resolve, reject) => { 
        for (let i = 0; i < 1000; i++) {
            count = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_TRIPLET, i.toString(), config.NB_VALUE)
            array = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_TRIPLET, i.toString(), config.TRIPLET_VALUE)
            JSON.parse(array).triplet.forEach(element => {
                triplet+=", "+element.text
            })
            ranking.push([triplet.substring(1), count])
            triplet = ""
        }
        resolve(ranking) 
    }) 
}

/**
 * Get hashtag info (name with nb)
 * @param {string} hashtagName hashtag name
 * @memberof Model_hashtag 
 */
function getHashtagInfo(hashtagName){
    let list = []  
    let regex = hbase.buildRegex(hashtagName)
    regex += ".*"
    HASHTAG_LIST.filter(function(word, index){
        if(word.match(regex)){
            if(list.length > 30) return false
            list.push(RANKING[index])
            return true
        }else{
            return false
        } 
    })
    return list

} 

/**
 * Convert famous word of a tweet on hashtag
 * @param {string} tweet tweet to convert
 * @memberof Model_hashtag 
 */
function convert(tweet){
    let splittedTweet = tweet.split(/[\s\n\r]+/)
    let nbHashtag = [] 
    nbHashtag = nbHashtag.fill(null, 0,splittedTweet.length)
    for(let i=0; i<splittedTweet.length; i++){
        HASHTAG_LIST.filter(function(word, index){
            let regex = hbase.buildRegex(splittedTweet[i])
            if(word.match(regex)){
                if(HASHTAG_LIST[index].length == splittedTweet[i].length){
                    splittedTweet[i] = "#"+splittedTweet[i]  
                    nbHashtag[i] = RANKING[index][1]
                } 
                return true
            }else{
                return false
            } 
        })
    } 
    return [splittedTweet, nbHashtag] 
} 


/**
 * Get repartition of hashtags
 * @memberof Model_hashtag 
 */
function getHashtagRepartition(){
    let name = []
    let countArray = []  
    let rep, count
    return new Promise(async (resolve, reject) => { 
        let n = await hbase.getTableLength(config.TABLE_NAME_REPARTITION_HASHTAGS)
        for (let i = 0; i < n; i++) {
            rep = await hbase.getHbaseValue(config.TABLE_NAME_REPARTITION_HASHTAGS, i.toString(), config.REPARTITION_VALUE)
            count = await hbase.getHbaseValue(config.TABLE_NAME_REPARTITION_HASHTAGS, i.toString(), config.NB_VALUE)
            name.push(rep)
            countArray.push(count)
        }
        resolve([name.join(), countArray.join()])
    })
} 


function getRanking(){
    return RANKING
} 


module.exports = {
    getTopKHashtag,
    getTopKTriplet,
    getHashtagInfo,
    getHashtagRepartition,
    convert,
    getRanking
}