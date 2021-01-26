// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = "-apple-system,system-ui,BlinkMacSystemFont,\"Segoe UI\",Roboto,\"Helvetica Neue\",Arial,sans-serif"
Chart.defaults.global.defaultFontColor = "#292b2c"


// Area Chart Example
let ctxChartArea = document.getElementById("myAreaChart")
let dataStr1 = document.getElementById("dataAreaChart").innerHTML
let data1 = new Array()
dataStr1.split(",").map(v => data1.push(parseInt(v)))
let myLineChart = new Chart(ctxChartArea, {
    type: "line",
    data: {
        labels: ["1 Mars", "2 Mars", "3 Mars", "4 Mars", "5 Mars", "6 Mars", "7 Mars", "8 Mars", "9 Mars", "10 Mars", "11 Mars", "12 Mars", "13 Mars","14 Mars", "15 Mars", "16 Mars","17 Mars", "18 Mars", "19 Mars","20 Mars", "21 Mars", "22 Mars", "23 Mars", "24 Mars", "25 Mars", "26 Mars", "27 Mars", "28 Mars", "29 Mars", "30 Mars"],
        datasets: [{
            label: "Nb Tweets",
            lineTension: 0.3,
            backgroundColor: "rgba(2,117,216,0.2)",
            borderColor: "rgba(2,117,216,1)",
            pointRadius: 5,
            pointBackgroundColor: "rgba(2,117,216,1)",
            pointBorderColor: "rgba(255,255,255,0.8)",
            pointHoverRadius: 5,
            pointHoverBackgroundColor: "rgba(2,117,216,1)",
            pointHitRadius: 50,
            pointBorderWidth: 2,
            data: data1,
        }],
    },
    options: {
        scales: {
            xAxes: [{
                time: {
                    unit: "date"
                },
                gridLines: {
                    display: false
                },
                ticks: {
                    maxTicksLimit: 7
                }
            }],
            yAxes: [{
                ticks: {
                    min: 0,
                    max: 5000000,
                    maxTicksLimit: 5,
                    callback: function(value, index, values){
                        return value/(1000000)+" M"
                    } 
                },
                gridLines: {
                    color: "rgba(0, 0, 0, .125)",
                }
            }],
        },
        legend: {
            display: false
        }
    }
})
