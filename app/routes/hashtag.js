const hashtag = require("../models/hashtag")

let data = [] 
let request = null
async function load(){
    await hashtag.getTopKHashtag()
} 

async function init(app, flag) {
  
    app.get("/hashtag", async (req, res) => {
        res.render("pages/hashtag.ejs", {list: null, waiting: false} )
    })
    app.post("/hashtag", async (req, res) => {
        request = req.body.hashtag
        res.render("pages/hashtag.ejs", {list: null, word:request, waiting: true} )
    })
    app.get("/hashtag/load", async (req, res) => {
        if(!flag.LOADED_FLAG){
            await load()
            flag.collectEnd()
        } 
        data = hashtag.getHashtagInfo(request)
        res.render("pages/hashtag.ejs", {list: data, word:request, waiting: false} )
    })
}

module.exports = {
    init
}