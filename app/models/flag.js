class Flag {
    constructor(){
        this.LOADED_FLAG = false
        this.data = []  
    } 

    collectEnd(data){
        this.LOADED_FLAG = true
        this.data = data
    } 

    getData(){
        return this.data
    } 

    isActivated(){
        return this.LOADED_FLAG
    } 
} 

module.exports = {
    Flag
}