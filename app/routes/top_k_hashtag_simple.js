const hashtag = require("../models/hashtag")
const loadService = require("../services/loader_service") 
/**
 * @namespace Route_topk_hashtag 
 */

let data = null

/**
 * Manage roots about hashtags top K
 * @param {Object} app Express app
 * @param {Object} flag Flag instance 
 * @memberof Route_topk_hashtag
 */
async function init(app, flag) {
    app.get("/topk/hashtag_simple", async (req, res) => {
        let wait = (!flag.isActivated())
        if(!wait){
            data = flag.getData()
        } 
        res.render("pages/top_k_hashtag_simple.ejs", {object: data, waiting: wait})
    })
    app.get("/topk/hashtag_simple/load", async (req, res) => {
        if(!flag.isActivated()){
            data = await loadService.load(hashtag.getTopKHashtag)
            flag.collectEnd(data)
        } 
        data = flag.getData()
        res.render("pages/top_k_hashtag_simple.ejs", {object: data, waiting: false})
    }) 
}

module.exports = {
    init
}