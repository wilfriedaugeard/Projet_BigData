const user = require("../models/user")
const loadService = require("../services/loader_service") 
/**
 * @namespace Route_topk_user 
 */

let data = null

/**
 * Manage roots about language top K
 * @param {Object} app Express app
 * @memberof Route_topk_user
 */
async function init(app) {
    data = await loadService.load(user.getTripletInfluencers)
    app.get("/topk/users", async (req, res) => {
        let wait = (data == null)
        res.render("pages/top_k_user.ejs",{influencers: data, waiting: wait})
    })
    app.get("/topk/users/load", async (req, res) => {
        if(data == null) data = await loadService.load(user.getTripletInfluencers)
        res.render("pages/top_k_user.ejs",{influencers: data, waiting: false})
    })
}

module.exports = {
    init
}