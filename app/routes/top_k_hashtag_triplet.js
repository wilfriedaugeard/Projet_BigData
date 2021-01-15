const hbase = require("../models/hbase")

async function init(app) {
    const data = await hbase.getTopKTriplet()
    app.get("/topk/hashtag_triplet", async (req, res) => {
        res.render("pages/top_k_hashtag_triplet.ejs", {triplet: data})
    })
}

module.exports = {
    init
}