const hashtag = require("../models/hashtag")
const loadService = require("../services/loader_service") 
/**
 * @namespace Route_hashtag
 */
let data = [] 
let request = null

/**
 * Manage roots about search hashtags page
 * @param {Object} app Express app
 * @param {Object} flag Flag instance
 * @memberof Route_hashtag
 */
async function init(app, flag) {
  
    app.get("/hashtag", async (req, res) => {
        res.render("pages/hashtag.ejs", {list: null, waiting: false} )
    })
    app.post("/hashtag", async (req, res) => {
        request = req.body.hashtag
        res.render("pages/hashtag.ejs", {list: null, word:request, waiting: true} )
    })
    app.get("/hashtag/load", async (req, res) => {
        if(!flag.isActivated()){
            data = await loadService.load(hashtag.getTopKHashtag)
            flag.collectEnd(data)
        } 
        data = hashtag.getHashtagInfo(request)
        res.render("pages/hashtag.ejs", {list: data, word:request, waiting: false} )
    })
}

module.exports = {
    init
}