var data = {
    welcome_message: "Test"
};

import usageChartComponent from './components/usageChartComponent.js';
import timelineChartComponent from './components/timelineChartComponent.js';

new Vue({
    el: '#vue-app',
    data: data,
    components: {
     'usage': usageChartComponent,
     'timeline' : timelineChartComponent
    },
    mounted: function() {

    },
    methods: {
    }
});