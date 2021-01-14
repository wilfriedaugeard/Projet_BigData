function init(app) {
    app.get("/topk/hashtag_simple", async (req, res) => {
        res.render("pages/top_k_hashtag_simple.ejs")
    })
}

module.exports = {
    init
}