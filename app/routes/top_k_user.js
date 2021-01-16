const user = require("../models/user")

let data = null

async function load(){
    data = await user.getTripletInfluencers()
} 

async function init(app) {
    load()
    app.get("/topk/users", async (req, res) => {
        let wait = (data == null)
        res.render("pages/top_k_user.ejs",{influencers: data, waiting: wait})
    })
    app.get("/topk/users/lod", async (req, res) => {
        if(data == null) await load()
        res.render("pages/top_k_user.ejs",{influencers: data, waiting: false})
    })
}

module.exports = {
    init
}