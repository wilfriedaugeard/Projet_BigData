const lang = require("../models/lang")
/**
 * @namespace Route_topk_lang 
 */

let data = null

async function load(){
    data = await lang.getTopKLang()
} 
/**
 * Manage roots about language top K
 * @param {Object} app Express app
 * @memberof Route_topk_lang
 */
async function init(app) {
    load()
    let wait = (data == null)
    app.get("/topk/lang", async (req, res) => {
        res.render("pages/top_k_lang.ejs",{lang: data, waiting: wait} )
    })
    app.get("/topk/lang/load", async (req, res) => {
        if(data == null) await load()
        res.render("pages/top_k_lang.ejs",{lang: data, waiting: false} )
    })
}

module.exports = {
    init
}