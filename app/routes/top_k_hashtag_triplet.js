const hashtag = require("../models/hashtag")
const loadService = require("../services/loader_service") 
/**
 * @namespace Route_topk_hashtag_triplet 
 */
let data = null

/**
 * Manage roots about hashtags triplet top K
 * @param {Object} app Express app
 * @param {Object} flag Flag instance 
 * @memberof Route_topk_hashtag_triplet
 */
async function init(app) {
    app.get("/topk/hashtag_triplet", async (req, res) => {
        let wait = (data == null)
        res.render("pages/top_k_hashtag_triplet.ejs", {triplet: data, waiting: wait})
    })
    app.get("/topk/hashtag_triplet/load", async (req, res) => {
        if(data == null) data = await loadService.load(hashtag.getTopKTriplet)
        res.render("pages/top_k_hashtag_triplet.ejs", {triplet: data, waiting: false})
    })
}

module.exports = {
    init
}