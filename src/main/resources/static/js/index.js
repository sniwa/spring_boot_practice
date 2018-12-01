var data = {
    welcome_message: "Test",
    tasks: []
};

// コンポーネントを登録
import memoComponent from "./components/memoComponent.js";

new Vue({
    el: '#vue-app',
    data: data,
    components: {
        "memo": memoComponent
    },
    mounted: function() {
        // API一覧を取得して画面に表示する
        fetch("/api/list").then(async response =>  {
            let json = await response.json();
            for (let task of json) {
                data.tasks.push(task);
            }
        });
    }
});