function init(app) {
    app.get("/", (req, res) => {
        res.redirect("/dashboard")
    })
}

module.exports = {
    init
}