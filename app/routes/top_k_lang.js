const hbase = require("../models/hbase")

async function init(app) {
    let data = await hbase.country()
    console.log(data)
    app.get("/topk/lang", async (req, res) => {
        res.render("pages/top_k_lang.ejs",{lang: data} )
    })
}

module.exports = {
    init
}