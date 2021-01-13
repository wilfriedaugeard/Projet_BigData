function init(app) {
    app.get("/topk/lang", async (req, res) => {
        res.render("pages/top_k_lang.ejs")
    })
}

module.exports = {
    init
}