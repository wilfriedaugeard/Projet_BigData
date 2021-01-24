const tweet = require("../models/tweet")
const hashtag =  require("../models/hashtag")
const loadService = require("../services/loader_service") 

let tweetData = ""
let hashtagRepData = "" 

let tweetDataWaiting = true
let hashtagRepDataWaiting = true

async function init(app) {
    app.get("/dashboard", async (req, res) => {
        res.render("pages/dashboard.ejs", {waitingNbTweet: tweetDataWaiting, waitingRep: hashtagRepDataWaiting, waiting: (tweetDataWaiting||hashtagRepDataWaiting), nbTweetList: tweetData, hashtagRepData: hashtagRepData} )
    })

    app.get("/dashboard/load", async (req, res) => {
        if(tweetData === ""){
            tweetData = await loadService.load(tweet.getNbTweetByDay)
            tweetDataWaiting = false
        } 
        if(hashtagRepData === ""){
            hashtagRepData = await loadService.load(hashtag.getHashtagRepartition)
            hashtagRepDataWaiting = false
        } 
        return res.render("pages/dashboard.ejs", {waitingNbTweet: tweetDataWaiting, waitingRep: hashtagRepDataWaiting, waiting: false, nbTweetList: tweetData, hashtagRepData: hashtagRepData} )
    })
}

module.exports = {
    init
}