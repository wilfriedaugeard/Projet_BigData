const hashtag = require("../models/hashtag")
/**
 * @namespace Route_topk_hashtag 
 */

let data = null

async function load(){
    data = await hashtag.getTopKHashtag()
} 
/**
 * Manage roots about hashtags top K
 * @param {Object} app Express app
 * @param {Object} flag Flag instance 
 * @memberof Route_topk_hashtag
 */
async function init(app, flag) {
    app.get("/topk/hashtag_simple", async (req, res) => {
        let wait = (!flag.LOADED_FLAG)
        if(!wait){
            data = hashtag.getRanking()
        } 
        res.render("pages/top_k_hashtag_simple.ejs", {object: data, waiting: wait})
    })
    app.get("/topk/hashtag_simple/load", async (req, res) => {
        if(!flag.LOADED_FLAG){
            await load()
            flag.collectEnd()
        } 
        data = hashtag.getRanking()
        res.render("pages/top_k_hashtag_simple.ejs", {object: data, waiting: false})
    }) 
}

module.exports = {
    init
}