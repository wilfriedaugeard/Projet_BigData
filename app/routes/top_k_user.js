const user = require("../models/user")
/**
 * @namespace Route_topk_user 
 */

let data = null

async function load(){
    data = await user.getTripletInfluencers()
} 
/**
 * Manage roots about language top K
 * @param {Object} app Express app
 * @memberof Route_topk_user
 */
async function init(app) {
    load()
    app.get("/topk/users", async (req, res) => {
        let wait = (data == null)
        res.render("pages/top_k_user.ejs",{influencers: data, waiting: wait})
    })
    app.get("/topk/users/load", async (req, res) => {
        if(data == null) await load()
        res.render("pages/top_k_user.ejs",{influencers: data, waiting: false})
    })
}

module.exports = {
    init
}