const memoComponent = {
    data: function() {
        return {
            edit: false,
            editableText: this.text
        }
    },
    props: ['id', 'title', 'text', 'created', 'done'],
    computed: {
        format_created: function() {
            let fmtTime = moment(this.created);
            return fmtTime.format("YYYY/MM/DD HH:mm:ss");
        }
    },
    template:
        `<div class="card task-list" v-bind:class="done != 0 ? 'task-completed' : ''">
            <header class="card-header">
                <p class="card-header-title">
                    {{ title }}
                </p>
                <a class="card-header-icon" aria-label="delete this task" v-on:click="$emit('delete-memo-request', id)">
                    <span class="icon">
                        <i class="fas fa-times" aria-hidden="true"></i>
                    </span>
                </a>
            </header>
            <div class="card-content">
                <div class="content">
                    <p v-if="!edit">
                        {{ text }}
                    </p>
                    <textarea class="textarea" v-if="edit" v-model="editableText">
                    </textarea>
                    <br>
                    <time datetime="2016-1-1" class="has-text-weight-light is-size-7 has-text-grey">{{ format_created }}</time>
                </div>
            </div>
            <footer class="card-footer">
                <template v-if="!edit">
                    <a class="card-footer-item" v-on:click="requestDone(id)">Done</a>
                    <a class="card-footer-item" v-on:click="requestEdit">Edit</a>
                </template>
                <template v-if="edit">
                    <a class="card-footer-item" v-on:click="requestSave(id)">Save</a>
                    <a class="card-footer-item" v-on:click="requestCancel">Cancel</a>
                </template>
                
            </footer>
        </div>`,
    methods: {
        requestEdit: function() {
            this.edit = true;
        },
        requestSave: function(id) {
            this.$emit('save-memo-request', id, {title: this.title, text: this.editableText});
            this.edit = false;
        },
        requestCancel: function() {
            this.edit = false;
            this.editableText = this.text;
        },
        requestDone: function(id) {
            this.$emit('done-memo-request', id);
        }
    }
};

export default memoComponent;