const hashtag = require("../models/hashtag")

async function init(app) {
    const topk_hashtag = await hashtag.getTopKHashtag()
    app.get("/topk/hashtag_simple", async (req, res) => {
        res.render("pages/top_k_hashtag_simple.ejs", {object: topk_hashtag})
    })
    
}

module.exports = {
    init
}