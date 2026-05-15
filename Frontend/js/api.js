const BASE_URL = 'http://localhost:8080';

window.api = {
    // Auth
    login: (data) => fetch(`${BASE_URL}/User/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }),

    // Users
    getUsers: () => fetch(`${BASE_URL}/User`).then(r => r.json()),
    createUser: (data) => fetch(`${BASE_URL}/User/Add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }).then(r => r.json()),
    updateUser: (data) => fetch(`${BASE_URL}/User/Update`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }).then(r => r.json()),
    deleteUser: (id) => fetch(`${BASE_URL}/User/delete/${id}`, { method: 'DELETE' }),

    // Books
    getBooks: () => fetch(`${BASE_URL}/Books`).then(r => r.json()),
    createBook: (data) => fetch(`${BASE_URL}/Books/Add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }).then(r => r.json()),
    updateBook: (data) => fetch(`${BASE_URL}/Books/Update`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }).then(r => r.json()),
    deleteBook: (id) => fetch(`${BASE_URL}/Books/delete/${id}`, { method: 'DELETE' }),
    uploadImage: (file) => {
        const formData = new FormData();
        formData.append('file', file);
        return fetch(`${BASE_URL}/Books/Upload`, {
            method: 'POST',
            body: formData
        }).then(r => {
            if(!r.ok) return r.text().then(t => { throw new Error(t); });
            return r.text();
        });
    },

    // Authors
    getAuthors: () => fetch(`${BASE_URL}/Author`).then(r => r.json()),
    createAuthor: (data) => fetch(`${BASE_URL}/Author/add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }).then(r => r.json()),
    updateAuthor: (data) => fetch(`${BASE_URL}/Author/update`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }).then(r => r.json()),
    deleteAuthor: (id) => fetch(`${BASE_URL}/Author/delete/${id}`, { method: 'DELETE' }),

    // Orders
    getOrders: () => fetch(`${BASE_URL}/Order`).then(r => r.json()),
    createOrder: (data) => fetch(`${BASE_URL}/Order/Add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }).then(r => r.json()),
    updateOrder: (data) => fetch(`${BASE_URL}/Order/Update`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }).then(r => r.json()),
    deleteOrder: (id) => fetch(`${BASE_URL}/Order/delete/${id}`, { method: 'DELETE' }),

    // Helper to get average rating
    getAverageRating: async (bookId) => {
        const reviews = await fetch(`${BASE_URL}/Reviews/book/${bookId}`).then(r => r.json());
        if (!reviews || reviews.length === 0) return 4.5; // Default fallback
        const sum = reviews.reduce((acc, r) => acc + (r.rating || 0), 0);
        return (sum / reviews.length).toFixed(1);
    },

    // Reviews
    getReviews: () => fetch(`${BASE_URL}/Reviews`).then(r => r.json()),
    getReviewsByBook: (bookId) => fetch(`${BASE_URL}/Reviews/book/${bookId}`).then(r => r.json()),
    createReview: (data) => fetch(`${BASE_URL}/Reviews/Add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }).then(r => r.json()),
    deleteReview: (id) => fetch(`${BASE_URL}/Reviews/delete/${id}`, { method: 'DELETE' }),

    // Categories
    getCategories: () => fetch(`${BASE_URL}/Category`).then(r => r.json()),
    createCategory: (data) => fetch(`${BASE_URL}/Category/Add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }),
    updateCategory: (data) => fetch(`${BASE_URL}/Category/Update`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }),
    deleteCategory: (id) => fetch(`${BASE_URL}/Category/delete/${id}`, { method: 'DELETE' }),
    
    getImageUrl: (path) => {
        if (!path) return '';
        if (path.startsWith('http')) return path;
        
        // If it's an uploaded file (starting with assets/uploads/), 
        // point it to our backend server which now serves these files.
        if (path.startsWith('assets/uploads/')) {
            const fileName = path.split('/').pop();
            return `${BASE_URL}/uploads/${fileName}`;
        }
        
        // Other assets (like site UI icons) stay relative
        return path;
    }
};

// Session helpers
window.session = {
    save: (user) => localStorage.setItem('lumina_user', JSON.stringify(user)),
    get: () => JSON.parse(localStorage.getItem('lumina_user')),
    clear: () => localStorage.removeItem('lumina_user'),
    isAdmin: () => {
        const u = JSON.parse(localStorage.getItem('lumina_user'));
        return u && (u.userType === 'ADMIN' || u.isadmin === 1);
    }
};
