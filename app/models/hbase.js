const hbase  = require('hbase')
const client = hbase({ host: 'localhost', port: 8080 })

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

async function getTableLength(tableName){
    let n = 0
    const scanner = await client.table(tableName).scan({
        startRow: '0',
        maxVersions: 1
    })
    
    return new Promise(async (resolve, reject) =>{
        scanner.on('readable', async function(){
            while(data = scanner.read()){
                data = scanner.read()
                n++
            }
        })
        scanner.on('end', function (){
            resolve(n)
        })
    })
} 


function buildRegex(element){
    const word = element.split('')
    let regex = ''
    word.forEach(element => {
        regex+="["+element.toUpperCase()+element.toLowerCase()+"]"
    });
    return regex
} 


async function getElement(tableName, element){
    let rows = [] 
    const scanner = await client.table(tableName).scan({
        filter:{
            "op":"MUST_PASS_ALL", "type":"FilterList","filters":[
                {
                "op":"EQUAL",
                "type":"ValueFilter",
                "comparator":{"value":buildRegex(element), "type":"RegexStringComparator"} 
            }]
        } 
    })
    return new Promise(async (resolve, reject) =>{
        scanner.on('readable', async function(){
            while(data = scanner.read()){
                rows.push(data.key)
            }
        })
        scanner.on('end', function (){
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