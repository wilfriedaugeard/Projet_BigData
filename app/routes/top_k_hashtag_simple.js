const hbase = require("../models/hbase")

async function init(app) {
    const topk_hashtag = await hbase.getTopKHashtag()
    app.get("/topk/hashtag_simple", async (req, res) => {
        res.render("pages/top_k_hashtag_simple.ejs", {object: topk_hashtag})
    })
}

module.exports = {
    init
}