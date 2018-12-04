const doneModalComponent = {
    data: function() {
        return {
            modalActive: this.active
        }
    },
    props: ['active', 'target', 'callback'],
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
        <p class="modal-card-title">タスクの完了</p>
        <button class="delete" aria-label="close" v-on:click="closeModal"></button>
    </header>
    <section class="modal-card-body">
       <p>このタスクを完了にしますか？</p>
    </section>
    <footer class="modal-card-foot">
      <button class="button is-success" v-on:click="performDone">Done</button>
      <button class="button" v-on:click="closeModal">Cancel</button>
    </footer>
  </div>
</div>
    `,
    methods: {
        performDone: function () {
            this.$emit('modal-submit', this.target);
        },
        closeModal: function () {
            this.$emit('modal-close');
        }
    }
};

export default doneModalComponent;