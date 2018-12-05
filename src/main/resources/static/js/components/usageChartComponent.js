const usageChartComponent = {
    props: ['canvas_id'],
    template: `
        <div class="card">
            <div class="card-header">
                <p class="card-header-title">Total Counts</p>
            </div>
            <div class="card-content" style="width: 300px; height:300px">
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
        var myPieChart = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ["Completed", "Progressing", "Deleted"],
                datasets: [{
                    data: [20, 50, 30],
                    backgroundColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)'
                    ]
                }]
            },
            options: {
                legend: {
                    display: false,
                },
                responsive: true
            }
        });
    }
};

export default usageChartComponent;