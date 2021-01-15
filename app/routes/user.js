const hbase = require("../models/hbase")

async function init(app) {
    app.get("/user", async (req, res) => {
        res.render("pages/user.ejs")
    })
}

module.exports = {
    init
}