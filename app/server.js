const bodyParser    = require("body-parser")
const express       = require("express")
const path          = require("path")
const app           = express()
const port          = 3000

// eslint-disable-next-line no-undef
app.use(express.static(path.join(__dirname, "/public")))
app.use(bodyParser.urlencoded({ extended: true }))
app.use(bodyParser.json())
app.set("view engine", "ejs")

// URI Path
const default_path = require("./routes/default.js")
const dashboard = require("./routes/dashboard.js")
const top_k_user = require("./routes/top_k_user.js")

default_path.init(app);
dashboard.init(app);
top_k_user.init(app)


app.listen(port, function () {
    console.log("listening on "+port)
})