/* eslint-disable no-async-promise-executor */
/* eslint-disable camelcase */
const path   = require("path")
const config = require(path.resolve("./models/hbase_config.js"))
const hbase  = require(path.resolve("./models/hbase.js"))



async function getTopKUserByTweet(){
    let ranking = []
} 

async function getTripletInfluencers(){
    let ranking = []
    let user_id, userName, nbTweet, user
    let n = await hbase.getTableLength(config.TABLE_NAME_TRIPLET_INFLUENCER)
    return new Promise(async (resolve, reject) => {
        for (let i = 0; i < 1000; i++) {
            nbTweet = await hbase.getHbaseValue(config.TABLE_NAME_TRIPLET_INFLUENCER, i.toString(), config.NB_TRIPLET)
            user = await hbase.getHbaseValue(config.TABLE_NAME_TRIPLET_INFLUENCER, i.toString(), config.USER_VALUE)
            user_id = JSON.parse(user).id_str
            userName = JSON.parse(user).name
            ranking.push([user_id, userName, nbTweet])
        }
        resolve(ranking) 
    }) 
} 


module.exports = {
    getTopKUserByTweet,
    getTripletInfluencers
} 