const bodyParser    = require("body-parser")
const express       = require("express")
const path          = require("path")
const hbase         = require("hbase")
const app           = express()
const client        = hbase({host: 'localhost', port: 8080})
const port          = 3001

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

const tableName = "augeard-tarmil-top-hashtag"
hbase()
.table(tableName)
.regions(function(error, schema){
  console.info(schema)
});


hbase().table(tableName).row('0').get('Hashtag:value', (error, value) =>{
  console.info(value)
})


// const scanner = hbase().table(tableName).scan({})
// let rows = []
// scanner.on('readable', function(){
//   let chunk
//   while(chunk = scanner.read()){
//     rows.push(chunk)
//   } 
// }) 
// scanner.on('error', function(err){
//   console.error(err)
// })

// scanner.on('end', function(){
//   console.info(rows)
// })

app.listen(port, function () {
    console.log("listening on "+port)
})