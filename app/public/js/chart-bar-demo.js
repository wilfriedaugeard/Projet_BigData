Chart.defaults.global.defaultFontFamily = "-apple-system,system-ui,BlinkMacSystemFont,\"Segoe UI\",Roboto,\"Helvetica Neue\",Arial,sans-serif"
Chart.defaults.global.defaultFontColor = "#292b2c"

// Bar Chart Example
let ctx1 = document.getElementById("myBarChart")
let dataStr = document.getElementById("dataBarChart").innerHTML
let data = new Array()
dataStr.split(",").map(v => data.push(parseInt(v)))
let myLineChart1 = new Chart(ctx1, {
    type: "bar",
    data: {
        labels: ["1 Mars", "2 Mars", "3 Mars", "4 Mars", "5 Mars", "6 Mars", "7 Mars", "8 Mars", "9 Mars", "10 Mars", "11 Mars", "12 Mars", "13 Mars","14 Mars", "15 Mars", "16 Mars","17 Mars", "18 Mars", "19 Mars","20 Mars", "21 Mars"],
        datasets: [{
            label: "Nb Hashtags",
            backgroundColor: "rgba(2,117,216,1)",
            borderColor: "rgba(2,117,216,1)",
            data: data,
        }],
    },
    options: {
        scales: {
            xAxes: [{
                time: {
                    unit: "day"
                },
                gridLines: {
                    display: false
                },
                ticks: {
                    maxTicksLimit: 6
                }
            }],
            yAxes: [{
                ticks: {
                    min: 0,
                    max: 1500000,
                    maxTicksLimit: 5,
                    callback: function(value, index, values){
                        return value/(1000000)+" M"
                    } 
                },
                gridLines: {
                    display: true
                }
            }],
        },
        legend: {
            display: false
        }
    }
})
