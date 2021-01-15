const hbase  = require('hbase')
const { resolve } = require('path')
const path   = require('path')
const config = require(path.resolve('./models/hbase_config.js'))
const countryLanguage = require(path.resolve('./models/country.js'))
const client = hbase({ host: 'localhost', port: 8080 })



async function getHbaseValue(tableName, i, columnName) {
    return new Promise((resolve, reject) =>{
        try{
                hbase().table(tableName).row(i).get(columnName, (error, [value]) => {
                    resolve(JSON.parse(value.$))
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
        ranking.push([hashtag, count])
    }
    return ranking
}

async function country(){
    let ranking = []
    let fullName, country, count, countryCode
    const scanner = await client.table(config.TABLE_NAME_TOPK_LANG).scan({
        startRow: '0',
        maxVersions: 1
    })
    return new Promise((resolve, reject) => {
        scanner.on('readable', async function(){
            while(data = scanner.read()){
                country = JSON.parse(data.$).text
                data = scanner.read()
                count = JSON.parse(data.$)
                fullName = await countryLanguage.getLanguageName(country) 
                countryCode  = await countryLanguage.getCountry(country)
                ranking.push([fullName, countryCode, count])
            }
            resolve(ranking.reverse()) 
        })
        
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
    getTopKUserByTweet,
    getTripletInfluencers,
    country
}