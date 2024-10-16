const API_URL = 'http://localhost:8080'; // Ajusta esto a la URL de tu servidor

function showMessage(text, isError = false) {
    const messageDiv = document.getElementById('message');
    messageDiv.textContent = text;
    messageDiv.className = isError ? 'error' : 'success';
}

async function sendRequest(endpoint, data) {
    const response = await fetch(`${API_URL}${endpoint}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams(data),
    });
    const text = await response.text();
    if (!response.ok) throw new Error(text);
    return text;
}

document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;
    try {
        const result = await sendRequest('/login', { username, password });
        showMessage(result);
        // Redirigir a otra página tras un inicio de sesión exitoso
        window.location.href = '../delivery.html';  // Cambia 'delivery.html' por la página de destino
    } catch (error) {
        showMessage('No se pudo iniciar sesión. Por favor, verifica tus credenciales.', true);
    }
});

document.getElementById('registerForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const username = document.getElementById('registerUsername').value;
    const password = document.getElementById('registerPassword').value;
    try {
        const result = await sendRequest('/register', { username, password });
        showMessage(result);
    } catch (error) {
        showMessage(error.message, true);
    }
});
