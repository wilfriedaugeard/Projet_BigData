const user = require("../models/user")

async function init(app) {
    const data = await user.getTripletInfluencers()
    app.get("/topk/users", async (req, res) => {
        res.render("pages/top_k_user.ejs",{influencers: data})
    })
}

module.exports = {
    init
}