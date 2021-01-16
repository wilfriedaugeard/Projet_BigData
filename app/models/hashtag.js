const path   = require('path')
const config = require(path.resolve('./models/hbase_config.js'))
const hbase  = require(path.resolve('./models/hbase.js'))

let HASHTAG_LIST = [] 
let RANKING = [] 

async function getTopKHashtag() {
    HASHTAG_LIST = [] 
    let ranking = []
    let hashtag, count
    for (let i = 0; i < config.K_MAX; i++) {
        hashtag = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_HASHTAG, i.toString(), config.HASHTAG_VALUE)
        count = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_HASHTAG, i.toString(), config.NB_VALUE)
        ranking.push([JSON.parse(hashtag).text, count])
        HASHTAG_LIST.push(JSON.parse(hashtag).text)
    }
    RANKING = ranking
    return ranking
}


async function getTopKTriplet() {
    let ranking = []
    let triplet = ''
    let n = await hbase.getTableLength(config.TABLE_NAME_TOPK_TRIPLET)
    let array, count;
    return new Promise(async (resolve, reject) => { 
        for (let i = 0; i < 1000; i++) {
            count = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_TRIPLET, i.toString(), config.NB_VALUE)
            array = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_TRIPLET, i.toString(), config.TRIPLET_VALUE)
            JSON.parse(array).triplet.forEach(element => {
                triplet+=', '+element.text
            })
            ranking.push([triplet.substring(1), count])
            triplet = ''
        }
        resolve(ranking) 
    }) 
}


async function getHashtagInfo(hashtagName){
    return new Promise(async (resolve, reject) => { 
        let ranking = []
        let hashtag, count
        const data = await hbase.getElement(config.TABLE_NAME_TOPK_HASHTAG, hashtagName)
        let n = (data.length < 100) ? data.length : 100
        for (let i=0; i<n; i++){
            hashtag = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_HASHTAG, data[i], config.HASHTAG_VALUE)
            count = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_HASHTAG, data[i], config.NB_VALUE)
            ranking.push([JSON.parse(hashtag).text,count])
        } 
        resolve(ranking) 
    }) 
} 


function convert(tweet){
    let splittedTweet = tweet.split(/[\s\n\r]+/)
    let nbHashtag = [] 
    nbHashtag = nbHashtag.fill(null, 0,splittedTweet.length)
    for(let i=0; i<splittedTweet.length; i++){
        HASHTAG_LIST.filter(function(word, index){
            let regex = hbase.buildRegex(splittedTweet[i])
            if(word.match(regex)){
                if(HASHTAG_LIST[index].length == splittedTweet[i].length){
                    splittedTweet[i] = '#'+splittedTweet[i]  
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


module.exports = {
    getTopKHashtag,
    getTopKTriplet,
    getHashtagInfo,
    convert
}