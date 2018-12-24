var data = {
    welcome_message: "Test"
};
import { Vue } from 'vue-property-decorator';

import usageChartComponent from './components/usageChartComponent.vue';
import timelineChartComponent from './components/timelineChartComponent.vue';

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