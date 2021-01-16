const bodyParser    = require("body-parser")
const express       = require("express")
const path          = require("path")
const { init } = require("./routes/convert")
const app           = express()
const port          = 3001 // quelqu'un occupe deja le port 3000 sur data 

// eslint-disable-next-line no-undef
app.use(express.static(path.join(__dirname, "/public")))
app.use(bodyParser.urlencoded({ extended: true }))
app.use(bodyParser.json())
app.set("view engine", "ejs")

const initializer = require(path.resolve("./routes/initializer.js"))
initializer.init(app)

app.listen(port, function () {
    console.log("listening on "+port)
})