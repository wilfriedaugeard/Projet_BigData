/* eslint-disable no-undef */

// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = "-apple-system,system-ui,BlinkMacSystemFont,\"Segoe UI\",Roboto,\"Helvetica Neue\",Arial,sans-serif"
Chart.defaults.global.defaultFontColor = "#292b2c"

// Pie Chart Example
let ctx2 = document.getElementById("myPieChart")
let dataStrName = document.getElementById("nameOfDataPieChart").innerHTML
let dataStrValue = document.getElementById("dataPieChart").innerHTML
let dataName = new Array()
let dataValue = new Array()
dataStrName.split(",").map(v => dataName.push(v))
dataStrValue.split(",").map(v => dataValue.push(parseInt(v)))

const sum = dataValue.reduce((a,b) => a+b, 0)
let dataPieChart = []
dataValue.forEach(v => dataPieChart.push((v*100/sum).toFixed(2))) 
let myPieChart = new Chart(ctx2, {
    type: "pie",
    data: {
        labels: dataName,
        datasets: [{
            data: dataPieChart,
            backgroundColor: ["#1CA1E3", "#76D7C4", "#F1948A", "#F7DC6F","#5F4E2F", "#77DD55"],
        }],
    },
})
