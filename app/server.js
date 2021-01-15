const bodyParser    = require("body-parser")
const express       = require("express")
const path          = require("path")
const app           = express()
const port          = 3001 // quelqu'un occupe deja le port 3000 sur data 

// eslint-disable-next-line no-undef
app.use(express.static(path.join(__dirname, "/public")))
app.use(bodyParser.urlencoded({ extended: true }))
app.use(bodyParser.json())
app.set("view engine", "ejs")

// URI Path
const default_path          = require("./routes/default.js")
const dashboard             = require("./routes/dashboard.js")
const top_k_user            = require("./routes/top_k_user.js")
const top_k_lang            = require("./routes/top_k_lang.js")
const top_k_hashtag_simple  = require("./routes/top_k_hashtag_simple.js")
const top_k_hashtag_triplet = require("./routes/top_k_hashtag_triplet.js")
const user                  = require("./routes/user.js")
const hashtag               = require("./routes/hashtag.js")

default_path.init(app);
dashboard.init(app);
top_k_user.init(app)
top_k_lang.init(app)
top_k_hashtag_simple.init(app)
top_k_hashtag_triplet.init(app)
user.init(app)
hashtag.init(app)

app.listen(port, function () {
    console.log("listening on "+port)
})