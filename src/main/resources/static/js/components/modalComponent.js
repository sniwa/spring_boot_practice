const modalComponent = {
    data: function() {
        return {
            modalActive: this.active,
            title: '',
            text: ''
        }
    },
    props: ['active'],
    watch: {
        active: function(newVal, oldVal) {
            this.modalActive = newVal;
        }
    },
    template: `
<div class="modal" v-bind:class="modalActive ? 'is-active' : ''">
    <div class="modal-background"></div>
    <div class="modal-card">
    <header class="modal-card-head">
        <p class="modal-card-title">新規タスク</p>
        <button class="delete" aria-label="close" v-on:click="closeModal"></button>
    </header>
    <section class="modal-card-body">
        <div class="field">
          <label class="label">Title</label>
          <div class="control">
            <input class="input" type="text" placeholder="Text input" v-model="title">
          </div>
        </div>

        <div class="field">
          <label class="label">Text</label>
          <div class="control">
            <textarea class="textarea" placeholder="Textarea" v-model="text"></textarea>
          </div>
        </div>
    </section>
    <footer class="modal-card-foot">
      <button class="button is-success" v-on:click="submitTask">Submit</button>
      <button class="button" v-on:click="closeModal">Cancel</button>
    </footer>
  </div>
</div>
    `,
    methods: {
        submitTask: function () {
            this.$emit('modal-submit', {title: this.title, text: this.text});
        },
        closeModal: function () {
            this.$emit('modal-close');
        }
    }
};

export default modalComponent;