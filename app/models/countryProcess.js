const countryLanguage = require("country-language")

/**
 * @namespace Model_countryProcess
 */

/**
 * Get a country name from its country code
 * @param {string} lang language code
 * @memberof Model_countryProcess 
 */
async function getCountry(lang){
    if(lang === "en") return "us"
    if(lang === "ja") return "jp"
    if(lang === "fr") return "fr"
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

/**
 * Get a language name from its short name
 * @param {string} lang language short name
 * @memberof Model_countryProcess 
 */
async function getLanguageName(lang){
    if(lang === "und") return "other"
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

module.exports = {
    getLanguageName,
    getCountry
} 