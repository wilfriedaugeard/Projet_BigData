const hbase = require("../models/hbase")

async function init(app) {
    app.get("/dashboard", async (req, res) => {
        res.render("pages/dashboard.ejs")
    })
}

module.exports = {
    init
}