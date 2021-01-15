const hbase  = require('hbase')
const path   = require('path')
const config = require(path.resolve('./models/hbase_config.js'))
const countryLanguage = require(path.resolve('./models/country.js'))
const client = hbase({ host: 'localhost', port: 8080 })



async function getHbaseValue(tableName, i, columnName) {
    return new Promise((resolve, reject) =>{
        try{
            hbase().table(tableName).row(i).get(columnName, (error, [value]) => {
                resolve(value.$) 
            }) 
        } catch(error){
            resolve(-1)
        } 
        
    })    
    
}


async function getTopKHashtag() {
    let ranking = []
    let hashtag, count
    for (let i = 0; i < config.K_MAX; i++) {
        hashtag = await getHbaseValue(config.TABLE_NAME_TOPK_HASHTAG, i.toString(), config.HASHTAG_VALUE)
        count = await getHbaseValue(config.TABLE_NAME_TOPK_HASHTAG, i.toString(), config.NB_VALUE)
        ranking.push([JSON.parse(hashtag).text, count])
    }
    return ranking
}


async function getTableLength(tableName){
    let n = 0
    const scanner = await client.table(tableName).scan({
        startRow: '0',
        maxVersions: 1
    })
    return new Promise(async (resolve, reject) =>{
        scanner.on('readable', async function(){
            while(data = scanner.read()){
                data = scanner.read()
                n++
            }
        })
        scanner.on('end', function (){
            resolve(n)
        })
    })
} 

async function getTopKTriplet() {
    let ranking = []
    let triplet = ''
    let n = await getTableLength(config.TABLE_NAME_TOPK_TRIPLET)
    let array, count;
    return new Promise(async (resolve, reject) => { 
        for (let i = 0; i < 1000; i++) {
            count = await getHbaseValue(config.TABLE_NAME_TOPK_TRIPLET, i.toString(), config.NB_VALUE)
            array = await getHbaseValue(config.TABLE_NAME_TOPK_TRIPLET, i.toString(), config.TRIPLET_VALUE)
            JSON.parse(array).triplet.forEach(element => {
                triplet+=', '+element.text
            })
            ranking.push([triplet.substring(1), count])
            triplet = ''
        }
        resolve(ranking) 
    }) 
}


async function country(){
    let ranking = []
    let fullName, lang, count, countryCode
    let n = await getTableLength(config.TABLE_NAME_TOPK_LANG)
    return new Promise(async (resolve, reject) => {
        for (let i = 0; i < n; i++) {
            count = await getHbaseValue(config.TABLE_NAME_TOPK_LANG, i.toString(), config.NB_VALUE)
            lang = await getHbaseValue(config.TABLE_NAME_TOPK_LANG, i.toString(), config.LANG_VALUE)
            fullName = await countryLanguage.getLanguageName(lang) 
            countryCode  = await countryLanguage.getCountry(lang)
            ranking.push([fullName, countryCode, count])
        }
        resolve(ranking) 
    }) 
} 

async function getTopKUserByTweet(){
    let ranking = []
    const scanner = await client.table(config.TABLE_NAME_TOPK_USER_BY_TWEET).scan({
        startRow: '0',
        maxVersions: 1
    })
    return new Promise((resolve, reject) => {
        scanner.on('readable', async function(){
            while(data = scanner.read()){
                console.log(data)
            }
            resolve(ranking.reverse()) 
        })
        
    }) 
} 

async function getTripletInfluencers(){
    let ranking = []
    let user_id, userName, nbTweet
    const scanner = await client.table(config.TABLE_NAME_TRIPLET_INFLUENCER).scan({
        startRow: '0',
        maxVersions: 1
    })
    return new Promise((resolve, reject) => {
        scanner.on('readable', async function(){
            while(data = scanner.read()){
                nbTweet = JSON.parse(data.$)
                data = scanner.read()
                user_id = JSON.parse(data.$).id_str
                userName = JSON.parse(data.$).name
                ranking.push([user_id, userName, nbTweet])
            }
            resolve(ranking) 
        })
        
    }) 
} 



module.exports = {
    getTopKHashtag,
    getTopKTriplet,
    getTopKUserByTweet,
    getTripletInfluencers,
    country
}