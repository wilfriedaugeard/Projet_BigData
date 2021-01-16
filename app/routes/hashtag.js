const hashtag = require("../models/hashtag")

async function init(app) {
    let data = null;
    app.get("/hashtag", async (req, res) => {
        res.render("pages/hashtag.ejs", {list: data} )
    })
    app.post("/hashtag", async (req, res) => {
        data = await hashtag.getHashtagInfo(req.body.hashtag)
        res.render("pages/hashtag.ejs", {list: data, word:req.body.hashtag} )
    })
}

module.exports = {
    init
}