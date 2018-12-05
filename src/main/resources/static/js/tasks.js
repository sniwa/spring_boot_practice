function getContext() {
    let dataModel = document.getElementById("vue-app").getAttribute("data-model");
    return JSON.parse(dataModel);
}

var data = {
    welcome_message: "Test",
    tasks: [],
    showModal: false,
    // for delete
    showDeleteModal: false,
    deleteTarget: -1,
    // for done
    showDoneModal: false,
    doneTarget: -1
};

// コンポーネントを登録
import memoComponent from "./components/memoComponent.js";
import modalComponent from "./components/modalComponent.js"
import deleteModalComponent from './components/deleteModalComponent.js';
import doneModalComponent from './components/doneModalComponent.js';

new Vue({
    el: '#vue-app',
    data: data,
    components: {
        "memo": memoComponent,
        "modal": modalComponent,
        "delete-modal": deleteModalComponent,
        "done-modal": doneModalComponent
    },
    mounted: function() {
        let context = getContext();
        let fetchApiUrl = context.taskFetchMode === "completed" ?
            "/api/task/list/completed" :
            "/api/task/list";
        // API一覧を取得して画面に表示する
        fetch(fetchApiUrl).then(async response =>  {
            let json = await response.json();
            for (let task of json) {
                data.tasks.push(task);
            }
        });
    },
    methods: {
        deleteMemoRequest: function(id) {
            data.deleteTarget = id;
            data.showDeleteModal = true;
        },
        deleteMemo: function(id) {
            console.log("delete target: " + id);
            fetch("/api/task/" + id + "/delete", {method: "POST"})
                .then(result => {
                    if (result.ok) {
                        data.tasks = data.tasks.filter(task => task.id !== id);
                        data.showDeleteModal = false;
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
        },
        doneMemoRequest: function(id) {
            data.doneTarget = id;
            data.showDoneModal = true;
        },
        doneMemo: function(id) {
            console.log("done target: " + id);
            fetch("/api/task/" + id + "/done", {method: "POST"})
                .then(result => {
                    if (result.ok) {
                        data.tasks = data.tasks.filter(task => task.id !== id);
                        data.showDeleteModal = false;
                    } else {
                        console.log(result);
                    }
                })
                .catch(error => console.log(error));
            data.showDoneModal = false;
        }
    }
});