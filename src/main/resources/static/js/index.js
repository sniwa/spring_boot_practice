var data = {
    welcome_message: "Test"
};

import doughnutComponent from './components/doughnutChartComponent.js';

new Vue({
    el: '#vue-app',
    data: data,
    components: {
     'doughnut': doughnutComponent
    },
    mounted: function() {

    },
    methods: {
    }
});