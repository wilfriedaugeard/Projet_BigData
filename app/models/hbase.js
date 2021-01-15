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
            console.log("==== "+tableName+" ====")
            while(data = scanner.read()){
                if(n == 0) console.log(data)
                data = scanner.read()
                if(n == 0) console.log(data)
                n++
            }
        })
        scanner.on('end', function (){
            resolve(n)
        })
    })
} 



module.exports = {
    getHbaseValue,
    getTableLength
}