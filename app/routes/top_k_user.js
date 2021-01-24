const user = require("../models/user")
const loadService = require("../services/loader_service") 
/**
 * @namespace Route_topk_user 
 */

let dataInfluencer = null
let dataTweetMostly = null
/**
 * Manage roots about language top K
 * @param {Object} app Express app
 * @memberof Route_topk_user
 */
async function init(app) {
    app.get("/topk/users", async (req, res) => {
        let wait = (dataInfluencer == null ||dataTweetMostly==null )
        res.render("pages/top_k_user.ejs",{influencers: dataInfluencer, topTweet: dataTweetMostly, waiting: wait})
    })
    app.get("/topk/users/load", async (req, res) => {
        if(dataInfluencer == null) dataInfluencer = await loadService.load(user.getTripletInfluencers)
        if(dataTweetMostly == null) dataTweetMostly = await loadService.load(user.getTopTweetingUser)
        res.render("pages/top_k_user.ejs",{influencers: dataInfluencer, topTweet:dataTweetMostly,  waiting: false})
    })
}

module.exports = {
    init
}