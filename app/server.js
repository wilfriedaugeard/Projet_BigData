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


app.get("/", (req, res) => {
    res.render("pages/index.ejs")
})

app.listen(port, function () {
    console.log("listening on "+port)
})