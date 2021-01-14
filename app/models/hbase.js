const hbase  = require('hbase')
const path   = require('path')
const config = require(path.resolve('./models/hbase_config.js'))
const client = hbase({ host: 'localhost', port: 8080 })



async function getHbaseValue(tableName, i, columnName) {
    return new Promise((resolve, reject) =>{
        hbase().table(tableName).row(i).get(columnName, (error, [value]) => {
            resolve(JSON.parse(value.$))
        })
    })
}


async function getTopKHashtag() {
    let ranking = []
    for (let i = 0; i < config.K_MAX; i++) {
        hashtag = await getHbaseValue(config.TABLE_NAME_TOPK_HASHTAG, i.toString(), config.HASHTAG_VALUE)
        count = await getHbaseValue(config.TABLE_NAME_TOPK_HASHTAG, i.toString(), config.NB_VALUE)
        ranking.push([hashtag, count])
    }
    return ranking
}



module.exports = {
    getTopKHashtag
}