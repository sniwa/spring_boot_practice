const doughnutChartComponent = {
    props: ['canvas_id'],
    template: `
        <div style="width: 300px; height:300px">
            <canvas v-bind:id="canvas_id">
            </canvas>
        </div>
    `,
    mounted: function () {
        var ctx = document.getElementById(this.canvas_id);
        var myPieChart = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ["Red", "Blue", "Yellow"],
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
                responsive: true
            }
        });
    }
};

export default doughnutChartComponent;