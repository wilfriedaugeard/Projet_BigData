const countryLanguage = require("country-language")


async function getCountry(lang){
    if(lang === 'en') return 'us'
    if(lang === 'ja') return 'jp'
    if(lang === 'fr') return 'fr'
    return new Promise((resolve, reject) =>{
        countryLanguage.getLanguage(lang, function(err, value){
            if(err){
                resolve(lang)
            }else{
                let val
                try{
                    val = value.countries[0].code_2
                } catch (error){
                    val = lang
                } 
                resolve(val.toLowerCase())
            } 
        })
    }) 
} 

async function getLanguageName(lang){
    if(lang === 'und') return 'other'
    return new Promise((resolve, reject) =>{
        countryLanguage.getLanguage(lang, function(err, value){
            if(err){
                resolve(lang)
            }else{
                resolve(value.name[0])
            } 
        })
    }) 
} 

function getFlagImg(language){
    let link = "https://lipis.github.io/flag-icon-css/flags/4x3/"
    let ext = ".svg"
    return link+language+ext
} 

module.exports = {
    getFlagImg,
    getLanguageName,
    getCountry
} 