const user = require("../models/user")
const loadService = require("../services/loader_service") 

let data = "" 
let request = null

async function init(app) {
    app.get("/user", async (req, res) => {
        res.render("pages/user.ejs",{user: null, hashtagList: null, waiting: false})
    })
    app.post("/user", async (req, res) => {
        request = req.body.user
        res.render("pages/user.ejs", {user: null, hashtagList: null, waiting: true})
    })
    app.get("/user/load", async (req, res) => {
        data = await user.getInfo(request)
        console.log(data)
        res.render("pages/user.ejs", {user: data == null ? data:data[0], hashtagList: data == null ? data: data[1], waiting: false})
    })
}

module.exports = {
    init
}