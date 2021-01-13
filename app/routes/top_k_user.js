function init(app) {
    app.get("/topk/users", async (req, res) => {
        res.render("pages/top_k_user.ejs")
    })
}

module.exports = {
    init
}