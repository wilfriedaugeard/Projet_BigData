/* eslint-disable camelcase */
const path   = require("path")
/**
 * @namespace Route_initializer
 */


// URI Path
const default_path          = require(path.resolve("./routes/default.js"))
const dashboard             = require(path.resolve("./routes/dashboard.js"))
const top_k_user            = require(path.resolve("./routes/top_k_user.js"))
const top_k_lang            = require(path.resolve("./routes/top_k_lang.js"))
const top_k_hashtag_simple  = require(path.resolve("./routes/top_k_hashtag_simple.js"))
const top_k_hashtag_triplet = require(path.resolve("./routes/top_k_hashtag_triplet.js"))
const user                  = require(path.resolve("./routes/user.js"))
const hashtag               = require(path.resolve("./routes/hashtag.js"))
const convert               = require(path.resolve("./routes/convert.js"))


/**
 * Initialize all roots
 * @param {Object} app Express app
 * @memberof Route_initializer
 */
function init(app){
    const flagModel = require("../models/flag")
    let flag = new flagModel.Flag()

    default_path.init(app)
    dashboard.init(app)
    top_k_user.init(app)
    top_k_lang.init(app)
    top_k_hashtag_simple.init(app, flag)
    top_k_hashtag_triplet.init(app)
    user.init(app)
    hashtag.init(app, flag)
    convert.init(app, flag)
} 

module.exports = {
    init
} 