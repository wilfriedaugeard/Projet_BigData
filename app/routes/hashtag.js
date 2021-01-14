function init(app) {
    app.get("/hashtag", async (req, res) => {
        res.render("pages/hashtag.ejs")
    })
}

module.exports = {
    init
}