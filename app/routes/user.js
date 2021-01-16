async function init(app) {
    app.get("/user", async (req, res) => {
        res.render("pages/user.ejs",{waiting: false} )
    })
}

module.exports = {
    init
}