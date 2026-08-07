console.log("slDashboard.js Loaded");
document.addEventListener("DOMContentLoaded", function () {

    /* ==========================================
       Total Works Doughnut Chart
    ========================================== */

    var worksOptions = {

        series: [5200, 1850, 350],

        chart: {
            type: 'donut',
            height: 350,
            toolbar: {
                show: false
            }
        },

        labels: [
            'Completed',
            'Ongoing',
            'Foreclosed'
        ],

        colors: [
            '#28a745',
            '#ffc107',
            '#dc3545'
        ],

        legend: {
            position: 'bottom',
            fontSize: '14px'
        },

        dataLabels: {
            enabled: true
        },

        plotOptions: {

            pie: {

                donut: {

                    size: '68%',

                    labels: {

                        show: true,

                        total: {

                            show: true,

                            label: 'Total Works',

                            formatter: function () {
                                return "7,400";
                            }

                        }

                    }

                }

            }

        },

        stroke: {
            width: 2
        }

    };

    var worksChart = new ApexCharts(
        document.querySelector("#worksChart"),
        worksOptions
    );

    worksChart.render();



    /* ==========================================
        Physical Progress Radial Chart
    ========================================== */

    var progressOptions = {

        series: [84],

        chart: {

            type: 'radialBar',

            height: 350

        },

        colors: ['#0d6efd'],

        plotOptions: {

            radialBar: {

                hollow: {

                    size: '60%'

                },

                dataLabels: {

                    name: {

                        show: true,

                        fontSize: '18px'

                    },

                    value: {

                        fontSize: '34px',

                        formatter: function (val) {
                            return val + "%";
                        }

                    }

                }

            }

        },

        labels: ['Achievement']

    };

    var progressChart = new ApexCharts(
        document.querySelector("#progressChart"),
        progressOptions
    );

    progressChart.render();

});