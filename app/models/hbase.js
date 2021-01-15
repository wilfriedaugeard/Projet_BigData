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



module.exports = {
    getTopKHashtag,
    country
}