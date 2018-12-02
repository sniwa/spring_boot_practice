const memoComponent = {
    data: function() {
        return {
            edit: false,
            editableText: this.text
        }
    },
    props: ['id', 'title', 'text', 'created'],
    computed: {
        format_created: function() {
            let fmtTime = moment(this.created);
            return fmtTime.format("YYYY/MM/DD HH:mm:ss");
        }
    },
    template:
        `<div class="card">
            <header class="card-header">
                <p class="card-header-title">
                    {{ title }}
                </p>
                <a href="#" class="card-header-icon" aria-label="more options">
                    <span class="icon">
                    <i class="fas fa-angle-down" aria-hidden="true"></i>
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
                <a href="#" class="card-footer-item" v-if="!edit" v-on:click="requestEdit">Edit</a>
                <a href="#" class="card-footer-item" v-if="edit" v-on:click="requestSave(id)">Save</a>
                <a href="#" class="card-footer-item" v-on:click="$emit('delete-memo-request', id)">Delete</a>
            </footer>
        </div>`,
    methods: {
        requestEdit: function() {
            this.edit = true;
        },
        requestSave: function(id) {
            this.$emit('save-memo-request', id, {title: this.title, text: this.editableText});
            this.edit = false;
        }
    }
};

export default memoComponent;