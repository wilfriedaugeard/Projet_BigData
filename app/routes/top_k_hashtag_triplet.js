
async function init(app) {
    
    app.get("/topk/hashtag_triplet", async (req, res) => {
        res.render("pages/top_k_hashtag_triplet.ejs")
    })
}

module.exports = {
    init
}