/**
 * @namespace Route_default 
 */


/**
 * Manage root "/" redirection
 * @param {Object} app Express app
 * @memberof Route_default 
 */
function init(app) {
    app.get("/", (req, res) => {
        res.redirect("/dashboard")
    })
}

module.exports = {
    init
}