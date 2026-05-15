const mainContent = document.getElementById('mainContent');
const navLinks = document.querySelectorAll('.nav-link');
const modalOverlay = document.getElementById('modalOverlay');

// Central State Engine
const appState = {
    theme: localStorage.getItem('eSHELF_THEME') || 'light',
    isSyncing: false,
    charts: {}
};

// Theme Synchronization Protocol
function syncTheme() {
    document.documentElement.setAttribute('data-theme', appState.theme);
    localStorage.setItem('eSHELF_THEME', appState.theme);
    
    Object.values(appState.charts).forEach(chart => {
        if (chart && typeof chart.updateOptions === 'function') {
            chart.updateOptions({
                theme: { mode: appState.theme },
                tooltip: { theme: appState.theme },
                grid: { borderColor: appState.theme === 'dark' ? '#1e293b' : '#f1f5f9' }
            });
        }
    });
}

document.getElementById('themeToggle').addEventListener('click', () => {
    appState.theme = appState.theme === 'light' ? 'dark' : 'light';
    syncTheme();
});

// Global UI Interactivity
function showLoading() {
    mainContent.style.opacity = '0.5';
    mainContent.style.pointerEvents = 'none';
}

function hideLoading() {
    mainContent.style.opacity = '1';
    mainContent.style.pointerEvents = 'all';
}

function handleError(error) {
    mainContent.innerHTML = `
        <div class="card" style="border-left: 5px solid #ef4444; padding: 4rem; text-align: center;">
            <i class="fas fa-triangle-exclamation" style="font-size: 4rem; color: #ef4444; margin-bottom: 2rem;"></i>
            <h2 style="font-weight: 900; margin-bottom: 1rem;">PROTOCOL_SYNC_FAILURE</h2>
            <p style="color: var(--text-muted); font-weight: 600; margin-bottom: 3rem;">${error.message}</p>
            <button class="btn btn-primary" onclick="location.reload()">REBOOT_SYSTEM</button>
        </div>
    `;
    hideLoading();
}

// Navigation Logic
navLinks.forEach(link => {
    link.addEventListener('click', (e) => {
        e.preventDefault();
        const view = link.dataset.view;
        if (!view) return;

        navLinks.forEach(l => l.classList.remove('active'));
        link.classList.add('active');
        
        if (views[view]) views[view]();
    });
});

const views = {
    dashboard: async () => {
        try {
            showLoading();
            const [books, orders, users] = await Promise.all([
                api.getBooks(),
                api.getOrders(),
                api.getUsers()
            ]);

            mainContent.innerHTML = `
                <div class="view-header" style="margin-bottom: 4rem; display: flex; justify-content: space-between; align-items: center;">
                    <div>
                        <h1 style="font-size: 3rem; font-weight: 900; letter-spacing: -2px;">Intelligence <span class="text-primary">Hub</span></h1>
                        <p style="color: var(--text-muted); font-size: 1.1rem; margin-top: 0.5rem; font-weight: 500;">Real-time ecosystem analytics & strategic asset monitoring.</p>
                    </div>
                    <div style="display: flex; gap: 1.5rem;">
                        <div class="status-badge badge-info" style="padding: 0.85rem 1.5rem;">
                            <i class="fas fa-satellite-dish fa-spin"></i> LIVE_LATTICE_FEED
                        </div>
                        <button class="btn btn-primary" id="btnQuickAdd"><i class="fas fa-plus"></i> NEW_PROTOCOL</button>
                    </div>
                </div>
                
                <div class="stats-grid">
                    <div class="stat-card">
                        <div class="stat-top">
                            <div class="stat-icon" style="background: var(--accent-glow); color: var(--accent);">
                                <i class="fas fa-microchip"></i>
                            </div>
                            <div style="text-align: right;">
                                <div style="font-size: 0.75rem; font-weight: 900; color: var(--accent);">+14.2% EPS</div>
                                <div style="font-size: 0.65rem; color: var(--text-muted); font-weight: 700;">STOCHASTIC_GAINS</div>
                            </div>
                        </div>
                        <div class="stat-info">
                            <h3>${books.length}</h3>
                            <p>Global Inventory Nodes</p>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-top">
                            <div class="stat-icon" style="background: var(--primary-glow); color: var(--primary);">
                                <i class="fas fa-bolt-lightning"></i>
                            </div>
                            <div style="text-align: right;">
                                <div style="font-size: 0.75rem; font-weight: 900; color: var(--primary);">98.4% SYNC</div>
                            </div>
                        </div>
                        <div class="stat-info">
                            <h3>${orders.length}</h3>
                            <p>Operational Flux</p>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-top">
                            <div class="stat-icon" style="background: rgba(139, 92, 246, 0.15); color: #8b5cf6;">
                                <i class="fas fa-users-viewfinder"></i>
                            </div>
                            <div style="text-align: right;">
                                <div style="font-size: 0.75rem; font-weight: 900; color: #8b5cf6;">ACTIVE</div>
                            </div>
                        </div>
                        <div class="stat-info">
                            <h3>${users.length}</h3>
                            <p>Verified Participants</p>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-top">
                            <div class="stat-icon" style="background: rgba(245, 158, 11, 0.15); color: #f59e0b;">
                                <i class="fas fa-shield-halved"></i>
                            </div>
                        </div>
                        <div class="stat-info">
                            <h3>v3.0.4-PRO</h3>
                            <p>System Kernel State</p>
                        </div>
                    </div>
                </div>

                <div style="display: grid; grid-template-columns: 1.8fr 1.2fr; gap: 2.5rem; margin-bottom: 4rem;">
                    <div class="card" style="padding: 2.5rem;">
                        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                            <h2 style="font-weight: 900; letter-spacing: -0.5px;">Market Absorption & Resource Flux</h2>
                            <i class="fas fa-expand" style="color: var(--text-muted); cursor: pointer;"></i>
                        </div>
                        <div id="mainChart"></div>
                    </div>
                    <div class="card" style="padding: 2.5rem;">
                        <div style="margin-bottom: 2rem;">
                            <h2 style="font-weight: 900; letter-spacing: -0.5px;">Sector Distribution</h2>
                        </div>
                        <div id="donutChart"></div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        <h2 style="font-weight: 900;">High-Priority Asset Registry</h2>
                        <div style="display: flex; gap: 1rem;">
                            <input type="text" class="form-control" placeholder="Filter assets..." style="width: 250px; padding: 0.6rem 1.2rem;">
                            <button class="btn btn-icon"><i class="fas fa-rotate"></i></button>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table>
                            <thead>
                                <tr>
                                    <th>Strategic Asset</th>
                                    <th>Allocation Protocol</th>
                                    <th>Valuation</th>
                                    <th>Node Capacity</th>
                                    <th>Interface</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${books.slice(-8).reverse().map(book => `
                                    <tr>
                                        <td>
                                            <div style="display: flex; align-items: center; gap: 1.5rem;">
                                                <div class="btn-icon" style="background: var(--bg-main);"><i class="fas fa-atom"></i></div>
                                                <div>
                                                    <div style="font-weight: 800; font-size: 1.1rem;">${book.name}</div>
                                                    <div class="monospace" style="font-size: 0.8rem; color: var(--text-muted);">ARC-IDX-${book.id}</div>
                                                </div>
                                            </div>
                                        </td>
                                        <td><span class="status-badge ${book.bookType === 'EBOOK' ? 'badge-info' : 'badge-warning'}">${book.bookType}</span></td>
                                        <td class="text-primary" style="font-weight: 800;">$${book.price}</td>
                                        <td>
                                            <div style="display: flex; align-items: center; gap: 1rem;">
                                                <div class="progress-container">
                                                    <div class="progress-bar" style="width: ${Math.min(book.quantity, 100)}%;"></div>
                                                </div>
                                                <span style="font-weight: 900; font-size: 0.9rem;">${book.quantity}</span>
                                            </div>
                                        </td>
                                        <td><button class="btn-icon"><i class="fas fa-sliders"></i></button></td>
                                    </tr>
                                `).join('')}
                            </tbody>
                        </table>
                    </div>
                </div>
            `;

            document.getElementById('btnQuickAdd').onclick = () => showBookModal();
            initializeCharts();
            hideLoading();
        } catch (error) {
            handleError(error);
        }
    },

    inventory: async () => {
        try {
            showLoading();
            const books = await api.getBooks();
            mainContent.innerHTML = `
                <div class="view-header" style="margin-bottom: 4rem; display: flex; justify-content: space-between; align-items: center;">
                    <div>
                        <h1 style="font-size: 3rem; font-weight: 900; letter-spacing: -2px;">Asset <span class="text-primary">Intelligence</span></h1>
                        <p style="color: var(--text-muted); font-size: 1.1rem; margin-top: 0.5rem; font-weight: 500;">Managing the global lattice of information nodes.</p>
                    </div>
                    <button class="btn btn-primary" id="btnAddBook"><i class="fas fa-plus"></i> INITIALIZE_NEW_ASSET</button>
                </div>
                <div class="card">
                    <div class="table-responsive">
                        <table>
                            <thead>
                                <tr>
                                    <th>Asset Nomenclature</th>
                                    <th>Architecture</th>
                                    <th>Valuation</th>
                                    <th>Node Integrity</th>
                                    <th>Interface</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${books.map(book => `
                                    <tr>
                                        <td>
                                            <div style="display: flex; align-items: center; gap: 1.5rem;">
                                                <div class="btn-icon" style="color: var(--primary);"><i class="fas fa-database"></i></div>
                                                <div>
                                                    <div style="font-weight: 800; font-size: 1.1rem;">${book.name}</div>
                                                    <div class="monospace" style="font-size: 0.8rem; color: var(--text-muted);">UUID-${book.id}</div>
                                                </div>
                                            </div>
                                        </td>
                                        <td><span class="status-badge ${book.bookType === 'EBOOK' ? 'badge-info' : 'badge-warning'}">${book.bookType}</span></td>
                                        <td style="font-weight: 800;">$${book.price}</td>
                                        <td>
                                            <div style="display: flex; align-items: center; gap: 1rem;">
                                                <div class="progress-container" style="height: 10px;">
                                                    <div class="progress-bar" style="width: ${Math.min(book.quantity, 100)}%; background: ${book.quantity >= 20 ? 'var(--accent)' : '#ef4444'};"></div>
                                                </div>
                                                <span style="font-weight: 900;">${book.quantity}</span>
                                            </div>
                                        </td>
                                        <td>
                                            <div style="display: flex; gap: 0.5rem;">
                                                <button class="btn-icon btnEditBook" data-id="${book.id}"><i class="fas fa-pen-nib"></i></button>
                                                <button class="btn-icon btnDeleteBook" data-id="${book.id}" style="color: #ef4444;"><i class="fas fa-trash-can"></i></button>
                                            </div>
                                        </td>
                                    </tr>
                                `).join('')}
                            </tbody>
                        </table>
                    </div>
                </div>
            `;
            document.getElementById('btnAddBook').onclick = () => showBookModal();
            addTableListeners();
            hideLoading();
        } catch (error) {
            handleError(error);
        }
    },

    authors: async () => {
        try {
            showLoading();
            const authors = await api.getAuthors();
            mainContent.innerHTML = `
                <div class="view-header" style="margin-bottom: 4rem; display: flex; justify-content: space-between; align-items: center;">
                    <div>
                        <h1 style="font-size: 3rem; font-weight: 900; letter-spacing: -2px;">Creator <span class="text-primary">Lattice</span></h1>
                        <p style="color: var(--text-muted); font-size: 1.1rem; margin-top: 0.5rem; font-weight: 500;">Maintaining the registry of verified intellectual property originators.</p>
                    </div>
                    <button class="btn btn-primary" id="btnAddAuthor"><i class="fas fa-fingerprint"></i> INITIALIZE_CREATOR</button>
                </div>
                <div class="card">
                    <div class="table-responsive">
                        <table>
                            <thead>
                                <tr>
                                    <th>Creator Identity</th>
                                    <th>Protocol Type</th>
                                    <th>Registry Description</th>
                                    <th>Interface</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${authors.map(author => `
                                    <tr>
                                        <td>
                                            <div style="display: flex; align-items: center; gap: 1.5rem;">
                                                <div class="stat-icon" style="width: 50px; height: 50px; background: var(--bg-main); font-size: 1.2rem;">
                                                    <i class="fas fa-signature"></i>
                                                </div>
                                                <div style="font-weight: 800; font-size: 1.2rem;">${author.name}</div>
                                            </div>
                                        </td>
                                        <td><span class="status-badge badge-warning">${author.authorType || 'INTERNAL'}</span></td>
                                        <td style="max-width: 400px; color: var(--text-muted); font-size: 0.95rem;">${author.discription || 'NO_DATA_AVAILABLE'}</td>
                                        <td>
                                            <button class="btn-icon btnDeleteAuthor" data-id="${author.id}" style="color: #ef4444;"><i class="fas fa-trash-can"></i></button>
                                        </td>
                                    </tr>
                                `).join('')}
                            </tbody>
                        </table>
                    </div>
                </div>
            `;
            document.getElementById('btnAddAuthor').onclick = () => showAuthorModal();
            addTableListeners();
            hideLoading();
        } catch (error) {
            handleError(error);
        }
    },

    users: async () => {
        try {
            showLoading();
            const users = await api.getUsers();
            mainContent.innerHTML = `
                <div class="view-header" style="margin-bottom: 4rem; display: flex; justify-content: space-between; align-items: center;">
                    <div>
                        <h1 style="font-size: 3rem; font-weight: 900; letter-spacing: -2px;">Network <span class="text-accent">Entities</span></h1>
                        <p style="color: var(--text-muted); font-size: 1.1rem; margin-top: 0.5rem; font-weight: 500;">Authorized participants within the secure synchronization lattice.</p>
                    </div>
                    <button class="btn btn-primary" id="btnAddUser" style="background: var(--accent);"><i class="fas fa-user-plus"></i> AUTHORIZE_ENTITY</button>
                </div>
                <div class="card">
                    <div class="table-responsive">
                        <table>
                            <thead>
                                <tr>
                                    <th>Verified Profile</th>
                                    <th>Clearance Protocol</th>
                                    <th>Chrono Age</th>
                                    <th>Sync State</th>
                                    <th>Interface</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${users.map(user => `
                                    <tr>
                                        <td>
                                            <div style="display: flex; align-items: center; gap: 1.5rem;">
                                                <img src="https://ui-avatars.com/api/?name=${user.name}&background=6366f1&color=fff&bold=true&size=128" class="profile-img" style="border-radius: 16px;">
                                                <div>
                                                    <div style="font-weight: 800; font-size: 1.1rem;">${user.name}</div>
                                                    <div class="monospace" style="font-size: 0.8rem; color: var(--text-muted);">${user.gmail}</div>
                                                </div>
                                            </div>
                                        </td>
                                        <td><span class="status-badge ${user.userType === 'ADMIN' ? 'badge-danger' : 'badge-success'}">${user.userType}</span></td>
                                        <td style="font-weight: 700;">NODE_T_${user.age}</td>
                                        <td><span class="status-badge badge-info"><i class="fas fa-lock"></i> ENCRYPTED</span></td>
                                        <td>
                                            <button class="btn-icon btnDeleteUser" data-id="${user.id}" style="color: #ef4444;"><i class="fas fa-user-minus"></i></button>
                                        </td>
                                    </tr>
                                `).join('')}
                            </tbody>
                        </table>
                    </div>
                </div>
            `;
            document.getElementById('btnAddUser').onclick = () => showUserModal();
            addTableListeners();
            hideLoading();
        } catch (error) {
            handleError(error);
        }
    },

    orders: async () => {
        try {
            showLoading();
            const orders = await api.getOrders();
            mainContent.innerHTML = `
                <div class="view-header" style="margin-bottom: 4rem;">
                    <h1 style="font-size: 3rem; font-weight: 900; letter-spacing: -2px;">Operational <span class="text-primary">Flux</span></h1>
                    <p style="color: var(--text-muted); font-size: 1.1rem; margin-top: 0.5rem; font-weight: 500;">Real-time audit trail of all system-level transactional nodes.</p>
                </div>
                <div class="card">
                    <div class="table-responsive">
                        <table>
                            <thead>
                                <tr>
                                    <th>Log UUID</th>
                                    <th>Execution Sequence</th>
                                    <th>Origin Node</th>
                                    <th>Verification State</th>
                                    <th>Interface</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${orders.map(order => `
                                    <tr>
                                        <td class="monospace text-primary" style="font-weight: 800;">#L-ARC-${order.id.toString().padStart(6, '0')}</td>
                                        <td style="font-weight: 600;">${order.date}</td>
                                        <td style="font-weight: 700;">NODE_USR_${order.userId}</td>
                                        <td><span class="status-badge badge-success"><i class="fas fa-shield-check"></i> SYNC_VERIFIED</span></td>
                                        <td>
                                            <button class="btn-icon btnDeleteOrder" data-id="${order.id}" style="color: #ef4444;"><i class="fas fa-file-shield"></i></button>
                                        </td>
                                    </tr>
                                `).join('')}
                            </tbody>
                        </table>
                    </div>
                </div>
            `;
            addTableListeners();
            hideLoading();
        } catch (error) {
            handleError(error);
        }
    },

    reviews: async () => {
        try {
            showLoading();
            const reviews = await api.getReviews();
            mainContent.innerHTML = `
                <div class="view-header" style="margin-bottom: 4rem;">
                    <h1 style="font-size: 3rem; font-weight: 900; letter-spacing: -2px;">Public <span class="text-accent">Sentiment</span></h1>
                    <p style="color: var(--text-muted); font-size: 1.1rem; margin-top: 0.5rem; font-weight: 500;">Aggregated feedback and qualitative asset performance indices.</p>
                </div>
                <div class="card">
                    <div class="table-responsive">
                        <table>
                            <thead>
                                <tr>
                                    <th>Sentiment UUID</th>
                                    <th>Qualitative Index</th>
                                    <th>Raw Input Documentation</th>
                                    <th>Interface</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${reviews.map(rev => `
                                    <tr>
                                        <td class="monospace text-accent" style="font-weight: 800;">#SENT-IDX-${rev.id}</td>
                                        <td>
                                            <div style="color: #f59e0b; display: flex; gap: 4px;">
                                                ${Array(5).fill(0).map((_, i) => `<i class="${i < rev.rating ? 'fas' : 'far'} fa-star"></i>`).join('')}
                                            </div>
                                        </td>
                                        <td style="font-style: italic; font-weight: 500;">"${rev.comment}"</td>
                                        <td>
                                            <button class="btn-icon btnDeleteReview" data-id="${rev.id}" style="color: #ef4444;"><i class="fas fa-comment-slash"></i></button>
                                        </td>
                                    </tr>
                                `).join('')}
                            </tbody>
                        </table>
                    </div>
                </div>
            `;
            addTableListeners();
            hideLoading();
        } catch (error) {
            handleError(error);
        }
    },

    admin: async () => {
        try {
            showLoading();
            const users = await api.getUsers();
            const admins = users.filter(u => u.userType === 'ADMIN');
            mainContent.innerHTML = `
                <div class="view-header" style="margin-bottom: 4rem;">
                    <h1 style="font-size: 3rem; font-weight: 900; letter-spacing: -2px;">Security <span style="color: #ef4444;">Core</span></h1>
                    <p style="color: var(--text-muted); font-size: 1.1rem; margin-top: 0.5rem; font-weight: 500;">High-level administrative lattice control and network oversight.</p>
                </div>
                <div class="stats-grid">
                    <div class="stat-card" style="border-left: 6px solid #ef4444;">
                        <div class="stat-info">
                            <h3 style="color: #ef4444;">${admins.length}</h3>
                            <p>Synchronized Overseers</p>
                        </div>
                    </div>
                    <div class="stat-card" style="border-left: 6px solid var(--accent);">
                        <div class="stat-info">
                            <h3 class="text-accent">OPTIMIZED</h3>
                            <p>Neural Lattice State</p>
                        </div>
                    </div>
                </div>
                <div class="card">
                    <div class="table-responsive">
                        <table>
                            <thead>
                                <tr>
                                    <th>Overseer Identity</th>
                                    <th>Sync Address</th>
                                    <th>Clearance Status</th>
                                    <th>Interface</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${admins.map(admin => `
                                    <tr>
                                        <td style="font-weight: 800; font-size: 1.2rem;">${admin.name}</td>
                                        <td class="monospace text-primary">${admin.gmail}</td>
                                        <td><span class="status-badge badge-danger">LATTICE_OVERSEER</span></td>
                                        <td><button class="btn-icon"><i class="fas fa-atom"></i></button></td>
                                    </tr>
                                `).join('')}
                            </tbody>
                        </table>
                    </div>
                </div>
            `;
            hideLoading();
        } catch (error) {
            handleError(error);
        }
    }
};

function addTableListeners() {
    document.querySelectorAll('.btnEditBook').forEach(btn => {
        btn.onclick = async (e) => {
            const id = e.currentTarget.dataset.id;
            const books = await api.getBooks();
            const book = books.find(b => b.id == id);
            showBookModal(book);
        };
    });
    
    document.querySelectorAll('.btnDeleteBook').forEach(btn => {
        btn.onclick = async (e) => {
            if(confirm('INITIATE_DELETION_PROTOCOL?')) {
                await api.deleteBook(e.currentTarget.dataset.id);
                views.inventory();
            }
        };
    });
    
    document.querySelectorAll('.btnDeleteAuthor').forEach(btn => {
        btn.onclick = async (e) => {
            if(confirm('REVOKE_CREATOR_ACCESS?')) {
                await api.deleteAuthor(e.currentTarget.dataset.id);
                views.authors();
            }
        };
    });

    document.querySelectorAll('.btnDeleteUser').forEach(btn => {
        btn.onclick = async (e) => {
            if(confirm('DISCONNECT_ENTITY?')) {
                await api.deleteUser(e.currentTarget.dataset.id);
                views.users();
            }
        };
    });

    document.querySelectorAll('.btnDeleteOrder').forEach(btn => {
        btn.onclick = async (e) => {
            if(confirm('PURGE_OPERATIONAL_LOG?')) {
                await api.deleteOrder(e.currentTarget.dataset.id);
                views.orders();
            }
        };
    });

    document.querySelectorAll('.btnDeleteReview').forEach(btn => {
        btn.onclick = async (e) => {
            if(confirm('PURGE_SENTIMENT_NODE?')) {
                await api.deleteReview(e.currentTarget.dataset.id);
                views.reviews();
            }
        };
    });
}

// Advanced Visual Engine
function initializeCharts() {
    const commonOptions = {
        theme: { mode: appState.theme, palette: 'palette1' },
        chart: { background: 'transparent', toolbar: { show: false }, zoom: { enabled: false } },
        grid: { borderColor: appState.theme === 'dark' ? '#1e293b' : '#f1f5f9' },
        stroke: { curve: 'smooth', width: 4 }
    };

    const mainOptions = {
        ...commonOptions,
        series: [
            { name: "Operational Flux", data: [45, 52, 38, 65, 48, 120, 110] },
            { name: "Market Absorption", data: [25, 40, 55, 42, 50, 70, 60] }
        ],
        xaxis: { categories: ['MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN'] },
        colors: ['#6366f1', '#10b981'],
        fill: { type: 'gradient', gradient: { opacityFrom: 0.4, opacityTo: 0.05 } }
    };

    const donutOptions = {
        ...commonOptions,
        chart: { ...commonOptions.chart, type: 'donut', height: 400 },
        series: [440, 320, 280, 380],
        labels: ['Technical Core', 'Strategic Assets', 'Market Flux', 'Network Nodes'],
        colors: ['#6366f1', '#10b981', '#f59e0b', '#ef4444'],
        plotOptions: { pie: { donut: { size: '75%', labels: { show: true, total: { show: true, label: 'TOTAL_NODES', formatter: () => '1,420' } } } } },
        legend: { position: 'bottom' }
    };

    if (appState.charts.main) appState.charts.main.destroy();
    if (appState.charts.donut) appState.charts.donut.destroy();

    appState.charts.main = new ApexCharts(document.querySelector("#mainChart"), mainOptions);
    appState.charts.donut = new ApexCharts(document.querySelector("#donutChart"), donutOptions);
    
    appState.charts.main.render();
    appState.charts.donut.render();
}

// Modal Handlers
function showBookModal(book = null) {
    const modalContent = document.getElementById('modalContent');
    modalContent.innerHTML = `
        <div class="modal-header">
            <h2 style="font-weight: 900;">${book ? 'RECONFIGURE_ASSET' : 'INITIALIZE_PROTOCOL'}</h2>
            <i class="fas fa-circle-xmark" id="closeModal" style="cursor: pointer; font-size: 1.8rem; color: var(--text-muted);"></i>
        </div>
        <div class="modal-body">
            <form id="bookForm" class="form-grid">
                <input type="hidden" name="id" value="${book ? book.id : ''}">
                <div class="form-group full-width">
                    <label>ASSET_NOMENCLATURE</label>
                    <input type="text" name="name" class="form-control" placeholder="Identify Asset" value="${book ? book.name : ''}" required>
                </div>
                <div class="form-group">
                    <label>CORE_ARCHITECTURE</label>
                    <select name="bookType" class="form-control">
                        <option value="BOOK" ${book?.bookType === 'BOOK' ? 'selected' : ''}>PRINT_CORE</option>
                        <option value="EBOOK" ${book?.bookType === 'EBOOK' ? 'selected' : ''}>DIGITAL_SYNC</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>MARKET_VALUATION ($)</label>
                    <input type="number" name="price" class="form-control" value="${book ? book.price : ''}" required>
                </div>
                <div class="form-group">
                    <label>NODE_DENSITY</label>
                    <input type="number" name="quantity" class="form-control" value="${book ? book.quantity : ''}" required>
                </div>
                <div class="form-group">
                    <label>CREATOR_ID</label>
                    <input type="number" name="authorId" class="form-control" value="${book ? book.authorId : ''}" required>
                </div>
                <div class="full-width" style="margin-top: 1rem;">
                    <button type="submit" class="btn btn-primary" style="width: 100%;">COMMIT_TO_LATTICE</button>
                </div>
            </form>
        </div>
    `;
    modalOverlay.style.display = 'flex';
    document.getElementById('closeModal').onclick = () => modalOverlay.style.display = 'none';
    document.getElementById('bookForm').onsubmit = async (e) => {
        e.preventDefault();
        const data = Object.fromEntries(new FormData(e.target).entries());
        if (data.id) await api.updateBook(data);
        else await api.createBook(data);
        modalOverlay.style.display = 'none';
        views.inventory();
    };
}

function showAuthorModal() {
    const modalContent = document.getElementById('modalContent');
    modalContent.innerHTML = `
        <div class="modal-header">
            <h2 style="font-weight: 900;">CREATOR_LATTICE_SYNC</h2>
            <i class="fas fa-circle-xmark" id="closeModal" style="cursor: pointer; font-size: 1.8rem; color: var(--text-muted);"></i>
        </div>
        <div class="modal-body">
            <form id="authorForm" class="form-grid">
                <div class="form-group full-width">
                    <label>CREATOR_NOMENCLATURE</label>
                    <input type="text" name="name" class="form-control" placeholder="Identify Creator" required>
                </div>
                <div class="form-group full-width">
                    <label>BIO_DOCUMENTATION</label>
                    <textarea name="discription" class="form-control" rows="4" placeholder="Initialize biographical documentation..."></textarea>
                </div>
                <div class="full-width" style="margin-top: 1rem;">
                    <button type="submit" class="btn btn-primary" style="width: 100%;">AUTHORIZE_CREATOR</button>
                </div>
            </form>
        </div>
    `;
    modalOverlay.style.display = 'flex';
    document.getElementById('closeModal').onclick = () => modalOverlay.style.display = 'none';
    document.getElementById('authorForm').onsubmit = async (e) => {
        e.preventDefault();
        const data = Object.fromEntries(new FormData(e.target).entries());
        await api.createAuthor(data);
        modalOverlay.style.display = 'none';
        views.authors();
    };
}

function showUserModal() {
    const modalContent = document.getElementById('modalContent');
    modalContent.innerHTML = `
        <div class="modal-header">
            <h2 style="font-weight: 900;">ENTITY_AUTH_SYNC</h2>
            <i class="fas fa-circle-xmark" id="closeModal" style="cursor: pointer; font-size: 1.8rem; color: var(--text-muted);"></i>
        </div>
        <div class="modal-body">
            <form id="userForm" class="form-grid">
                <div class="form-group full-width">
                    <label>PROFILE_LABEL</label>
                    <input type="text" name="name" class="form-control" placeholder="Verified Entity Name" required>
                </div>
                <div class="form-group full-width">
                    <label>NETWORK_ID (GMAIL)</label>
                    <input type="email" name="gmail" class="form-control" placeholder="entity@core.sync" required>
                </div>
                <div class="form-group">
                    <label>CHRONO_INDEX</label>
                    <input type="number" name="age" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>CLEARANCE_LEVEL</label>
                    <select name="userType" class="form-control">
                        <option value="USER">STANDARD_NODE</option>
                        <option value="ADMIN">GRID_OVERSEER</option>
                    </select>
                </div>
                <div class="full-width" style="margin-top: 1rem;">
                    <button type="submit" class="btn btn-primary" style="width: 100%;">INITIALIZE_SYNC</button>
                </div>
            </form>
        </div>
    `;
    modalOverlay.style.display = 'flex';
    document.getElementById('closeModal').onclick = () => modalOverlay.style.display = 'none';
    document.getElementById('userForm').onsubmit = async (e) => {
        e.preventDefault();
        const data = Object.fromEntries(new FormData(e.target).entries());
        await api.createUser(data);
        modalOverlay.style.display = 'none';
        views.users();
    };
}

// System Boot Sequence
syncTheme();
views.dashboard();
