<template>
    <div class="card">
        <div class="card-header">
            <p class="card-header-title">Total Counts</p>
        </div>
        <div class="card-content" style="width: 300px; height:300px">
            <canvas v-bind:id="canvas_id">
            </canvas>
        </div>
        <footer class="card-footer">
            <a class="card-footer-item">Detail</a>
        </footer>
    </div>
</template>

<script lang="ts">
    declare class Chart {
        constructor(context:any, data:any);
    }


    import { Component, Prop, Vue } from 'vue-property-decorator';
    @Component
    export default class UsageChartComponent extends Vue {
        @Prop()
        public canvas_id? : string;

        public async mounted() {
            var ctx = document.getElementById(this.canvas_id);
            var response = await fetch("/api/stats/total");
            if (!response.ok) {
                return;
            }

            let json = await response.json();
            let myPieChart = new Chart(ctx, {
                type: 'doughnut',
                data: {
                    labels: ["Completed", "Progressing", "Deleted"],
                    datasets: [{
                        data: [json.response.completed, json.response.progress, json.response.deleted],
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
    }

</script>
