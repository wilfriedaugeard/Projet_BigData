const lang = require("../models/lang")
const loadService = require("../services/loader_service") 
/**
 * @namespace Route_topk_lang 
 */

let data = null

/**
 * Manage roots about language top K
 * @param {Object} app Express app
 * @memberof Route_topk_lang
 */
async function init(app) {
    data = await loadService.load(lang.getTopKLang)
    let wait = (data == null)
    app.get("/topk/lang", async (req, res) => {
        res.render("pages/top_k_lang.ejs",{lang: data, waiting: wait} )
    })
    app.get("/topk/lang/load", async (req, res) => {
        if(data == null) data = await loadService.load(lang.getTopKLang)
        res.render("pages/top_k_lang.ejs",{lang: data, waiting: false} )
    })
}

module.exports = {
    init
}