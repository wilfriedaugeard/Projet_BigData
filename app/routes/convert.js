const hashtag = require("../models/hashtag")

async function init(app) {
    let data = null;
    app.get("/convert", async (req, res) => {
        res.render("pages/convert.ejs", {tweet: data} )
    })
    app.post("/convert", async (req, res) => {
        data = hashtag.convert(req.body.tweet)
        res.render("pages/convert.ejs", {tweet: data[0], nbHashtag: data[1], oldTweet: req.body.tweet})
    })
    
}

module.exports = {
    init
}