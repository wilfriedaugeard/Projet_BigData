const hashtag = require("../models/hashtag")
const loadService = require("../services/loader_service") 
/**
 * @namespace Route_convert
 */

let data = [] 
let request = null

/**
 * Manage roots about convert tweet page
 * @param {Object} app Express app
 * @param {Object} flag Flag instance
 * @memberof Route_convert
 */
async function init(app, flag) {
    app.get("/convert", async (req, res) => {
        res.render("pages/convert.ejs", {tweet: null, oldTweet:"", waiting: false} )
    })
    app.post("/convert", async (req, res) => {
        request = req.body.tweet
        res.render("pages/convert.ejs", {tweet: data[0], nbHashtag: data[1], oldTweet: request, waiting: true})
    })
    app.get("/convert/load", async (req, res) => {
        if(!flag.LOADED_FLAG){
            data = await loadService.load(hashtag.getTopKHashtag)
            flag.collectEnd()
        } 
        data = hashtag.convert(request)
        res.render("pages/convert.ejs", {tweet: data[0], nbHashtag: data[1], oldTweet: request, waiting: false})
    })
}


module.exports = {
    init
}