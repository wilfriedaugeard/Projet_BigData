function init(app) {
    app.get("/dashboard", async (req, res) => {
        res.render("pages/dashboard.ejs")
    })
}

module.exports = {
    init
}