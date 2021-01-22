Chart.defaults.global.defaultFontFamily = "-apple-system,system-ui,BlinkMacSystemFont,\"Segoe UI\",Roboto,\"Helvetica Neue\",Arial,sans-serif"
Chart.defaults.global.defaultFontColor = "#292b2c"

// Bar Chart Example
let ctx1 = document.getElementById("myBarChart")
let myLineChart1 = new Chart(ctx1, {
    type: "bar",
    data: {
        labels: ["Mar 1", "Mar 2", "Mar 3", "Mar 4", "Mar 5", "Mar 6", "Mar 7", "Mar 8", "Mar 9", "Mar 10", "Mar 11", "Mar 12", "Mar 13","Mar 14", "Mar 15", "Mar 16","Mar 17", "Mar 18", "Mar 19","Mar 20", "Mar 21"],
        datasets: [{
            label: "Nb Hashtags",
            backgroundColor: "rgba(2,117,216,1)",
            borderColor: "rgba(2,117,216,1)",
            data: [4215, 5312, 6251, 7841, 9821, 14984, 5312, 6251, 7841, 9821, 2145, 8695, 5478, 7563, 2369, 5861, 4587, 6953, 12578, 18965, 16598],
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
                    max: 20000,
                    maxTicksLimit: 5
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
