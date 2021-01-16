const lang = require("../models/lang")

async function init(app) {
    let data = await lang.getTopKLang()
    app.get("/topk/lang", async (req, res) => {
        res.render("pages/top_k_lang.ejs",{lang: data} )
    })
}

module.exports = {
    init
}