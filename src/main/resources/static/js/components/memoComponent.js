const memoComponent = {
    props: ['title', 'text', 'created'],
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
                    {{ text }}
                    <time datetime="2016-1-1">{{ created }}</time>
                </div>
            </div>
            <footer class="card-footer">
                <a href="#" class="card-footer-item">Save</a>
                <a href="#" class="card-footer-item">Edit</a>
                <a href="#" class="card-footer-item">Delete</a>
            </footer>
        </div>`
};

export default memoComponent;