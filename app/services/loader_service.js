/**
 * @namespace Service_load
 */
/**
 * Load data from hbase
 * @param {function} dataFunction Function to call to get data from hbase
 * @memberof Service_load 
 */
async function load(dataFunction){
    return await dataFunction()
} 


module.exports = {
    load
} 