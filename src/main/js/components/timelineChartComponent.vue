<template>
    <div class="card">
        <div class="card-header">
            <p class="card-header-title">Per Week Chart</p>
        </div>
        <div class="card-content" style="width: 800px; height:480px">
            <canvas v-bind:id="canvas_id">
            </canvas>
        </div>
        <footer class="card-footer">
            <a class="card-footer-item">Detail</a>
        </footer>
    </div>
</template>

<script>
    module.exports = {
        props: ['canvas_id'],
        mounted: async function () {
        var ctx = document.getElementById(this.canvas_id);
        var labels = [];
        var completed = [];
        var created = [];

        let response = await fetch("/api/stats/week");
        let json = await response.json();
        // make labels
        for (var date of json.response.dates) {
            labels.push(moment(date.date).format("YYYY/MM/DD"));
            completed.push(date.completed);
            created.push(date.created);
        }
        labels = labels.reverse();
        completed = completed.reverse();
        created = created.reverse();

        var myTimeline = new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels, //['January', 'February', 'March', 'April', 'May', 'June', 'July'],
                datasets: [{
                    label: 'Completed',
                    backgroundColor: 'rgba(255, 99, 132, 1)',
                    borderColor: 'rgba(255, 99, 132, 1)',
                    data: completed,
                    fill: false,
                },{
                    label: 'Created',
                    backgroundColor:  'rgba(54, 162, 235, 1)',
                    borderColor:      'rgba(54, 162, 235, 1)',
                    data: created,
                    fill: false,
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
    }
</script>