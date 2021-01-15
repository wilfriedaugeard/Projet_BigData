const hbase = require("../models/hbase")

async function init(app) {
    const data = await hbase.getTripletInfluencers()
    app.get("/topk/users", async (req, res) => {
        res.render("pages/top_k_user.ejs",{influencers: data})
    })
}

module.exports = {
    init
}