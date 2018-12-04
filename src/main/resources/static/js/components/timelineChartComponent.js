const timelineChartComponent = {
    props: ['canvas_id'],
    template: `
        <div class="card">
            <div class="card-header">
                <p class="card-header-title">Per Week Chart</p>
            </div>
            <div class="card-content" style="width: 800px; height:480px">
                <canvas v-bind:id="canvas_id">
                </canvas>
            </div>
            <footer class="card-footer">
                <a class="card-footer-item">Change</a>
            </footer>
        </div>
    `,
    mounted: function () {
        var ctx = document.getElementById(this.canvas_id);
        var labels = [];
        for (var i = 0; i < 7; i++) {
            labels.push(moment().subtract(i, 'days').format('YYYY/MM/DD'));
        }
        labels = labels.reverse();
        var myTimeline = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labels, //['January', 'February', 'March', 'April', 'May', 'June', 'July'],
                    datasets: [{
                        label: 'Completed',
                        backgroundColor: 'rgba(255, 99, 132, 1)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        data: [
                            1, 3, 5, 4, 3, 2, 1
                        ],
                        fill: false,
                    }, {
                        label: 'Progressing',
                        fill: false,
                        backgroundColor: 'rgba(54, 162, 235, 1)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        data: [
                            2, 8, 7, 1, 0, 3, 2
                        ],
                    }]
                },
                options: {
                    responsive: true,
                    legend: {
                        display: false
                    },
                    title: {
                        display: false
                    },
                    tooltips: {
                        mode: 'index',
                        intersect: false,
                    },
                    hover: {
                        mode: 'nearest',
                        intersect: true
                    },
                    scales: {
                        xAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: 'Week'
                            }
                        }],
                        yAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: 'Count'
                            }
                        }]
                    }
                }
            });
    }
};

export default timelineChartComponent;