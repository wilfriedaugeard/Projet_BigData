const path   = require('path')
const config = require(path.resolve('./models/hbase_config.js'))
const hbase  = require(path.resolve('./models/hbase.js'))


async function getTopKHashtag() {
    let ranking = []
    let hashtag, count
    for (let i = 0; i < config.K_MAX; i++) {
        hashtag = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_HASHTAG, i.toString(), config.HASHTAG_VALUE)
        count = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_HASHTAG, i.toString(), config.NB_VALUE)
        ranking.push([JSON.parse(hashtag).text, count])
    }
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


module.exports = {
    getTopKHashtag,
    getTopKTriplet,
    getHashtagInfo
}