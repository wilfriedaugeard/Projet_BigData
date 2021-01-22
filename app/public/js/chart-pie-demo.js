/* eslint-disable no-undef */

// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = "-apple-system,system-ui,BlinkMacSystemFont,\"Segoe UI\",Roboto,\"Helvetica Neue\",Arial,sans-serif"
Chart.defaults.global.defaultFontColor = "#292b2c"

// Pie Chart Example
let ctx2 = document.getElementById("myPieChart")
let myPieChart = new Chart(ctx2, {
    type: "pie",
    data: {
        labels: ["0", "1 à 3", "4 à 10", "10+"],
        datasets: [{
            data: [12.21, 15.58, 11.25, 8.32],
            backgroundColor: ["#1CA1E3", "#76D7C4", "#F1948A", "#F7DC6F"],
        }],
    },
})
