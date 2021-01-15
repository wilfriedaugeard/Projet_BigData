const path   = require('path')
const config = require(path.resolve('./models/hbase_config.js'))
const hbase  = require(path.resolve('./models/hbase.js'))
const countryLanguage = require(path.resolve('./models/countryProcess.js'))


async function getTopKLang(){
    let ranking = []
    let fullName, lang, count, countryCode
    let n = await hbase.getTableLength(config.TABLE_NAME_TOPK_LANG)
    return new Promise(async (resolve, reject) => {
        for (let i = 0; i < n; i++) {
            count = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_LANG, i.toString(), config.NB_VALUE)
            lang = await hbase.getHbaseValue(config.TABLE_NAME_TOPK_LANG, i.toString(), config.LANG_VALUE)
            fullName = await countryLanguage.getLanguageName(lang) 
            countryCode  = await countryLanguage.getCountry(lang)
            ranking.push([fullName, countryCode, count])
        }
        resolve(ranking) 
    }) 
} 


module.exports ={
    getTopKLang
} 