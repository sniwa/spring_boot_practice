var data = {
    welcome_message: "Test",
    tasks: [],
    showModal: false
};

// コンポーネントを登録
import memoComponent from "./components/memoComponent.js";
import modalComponent from "./components/modalComponent.js";

new Vue({
    el: '#vue-app',
    data: data,
    components: {
        "memo": memoComponent,
        "modal": modalComponent
    },
    mounted: function() {
        // API一覧を取得して画面に表示する
        fetch("/api/task/list").then(async response =>  {
            let json = await response.json();
            for (let task of json) {
                data.tasks.push(task);
            }
        });
    },
    methods: {
        deleteMemo: function(id) {
            console.log("delete memo requested : [" + id + "]");

            fetch("/api/task/" + id + "/delete", {method: "POST"})
                .then(result => {
                    if (result.ok) {
                        data.tasks = data.tasks.filter(task => task.id !== id);
                    } else {
                        console.log(result);
                    }
                })
                .catch(error => console.log(error));
        },
        saveMemo: function(id, taskInfo) {
            const headers = {
                'Content-Type': 'application/json'
            };
            fetch("/api/task/" + id + "/update", {method: "POST", headers: headers, body: JSON.stringify(taskInfo)})
                .then(result => {
                    if (result.ok) {
                        data.tasks.filter(task => task.id === id)[0].text = text;
                    } else {
                        console.log(result);
                    }
                })
                .catch(error => console.log(error));
        },
        requestModal: function() {
            data.showModal = true;
        },
        submitTask: function(taskInfo) {
            const headers = {
                'Content-Type': 'application/json'
            };
            fetch("/api/task/new", {method: "POST", headers: headers, body: JSON.stringify(taskInfo)})
                .then(async result => {
                    if (result.ok) {
                        let addTask = await result.json();
                        data.tasks.push(addTask);
                        data.showModal = false;
                    } else {
                        console.log(result);
                    }
                })
                .catch(error => console.log(error));
        }
    }
});