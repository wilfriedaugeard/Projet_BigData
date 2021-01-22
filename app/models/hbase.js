/* eslint-disable no-async-promise-executor */
/* eslint-disable no-cond-assign */
/* eslint-disable no-undef */
const hbase  = require("hbase")
const client = hbase({ host: "localhost", port: 8080 })

/**
 * @namespace Model_hbase
 */

/**
 * Get row content on a hbase table
 * @param {string} tableName Name of the table
 * @param {string} i Row position 
 * @param {string} columnName Name of the column 
 * @memberof Model_hbase 
 */
async function getHbaseValue(tableName, i, columnName) {
    return new Promise((resolve, reject) =>{
        try{
            hbase().table(tableName).row(i).get(columnName, (error, [value]) => {
                resolve(value.$) 
            }) 
        } catch(error){
            resolve(-1)
        } 
        
    })    
    
}

/**
 * Compute the table length
 * @param {string} tableName Name of the table 
 * @memberof Model_hbase 
 */
async function getTableLength(tableName){
    let n = 0
    const scanner = await client.table(tableName).scan({
        startRow: "0",
        maxVersions: 1
    })
    
    return new Promise(async (resolve, reject) =>{
        scanner.on("readable", async function(){
            while(data = scanner.read()){
                data = scanner.read()
                n++
            }
        })
        scanner.on("end", function (){
            resolve(n)
        })
    })
} 

/**
 * Build a regex from a string as: ab => [Aa][Bb]  
 * @param {string} element build regex from element
 * @memberof Model_hbase 
 */
function buildRegex(element){
    const word = element.split("")
    let regex = ""
    word.forEach(element => {
        regex+="["+element.toUpperCase()+element.toLowerCase()+"]"
    })
    return regex
} 

/**
 * Get element informations from hbase table
 * @param {string} tableName Name of the table
 * @param {string} element element to get 
 * @memberof Model_hbase 
 */
async function getElement(tableName, element){
    let rows = [] 
    const scanner = await client.table(tableName).scan({
        filter:{
            "op":"MUST_PASS_ALL", "type":"FilterList","filters":[
                {
                    "op":"EQUAL",
                    "type":"ValueFilter",
                    "comparator":{"value":buildRegex(element), "type":"RegexStringComparator"} 
                }
            ]
        } 
    })
    return new Promise(async (resolve, reject) =>{
        scanner.on("readable", async function(){
            while(data = scanner.read()){
                rows.push(data.key)
            }
        })
        scanner.on("end", function (){
            resolve(rows)
        })
    })

} 

module.exports = {
    getHbaseValue,
    getTableLength,
    getElement,
    buildRegex
}