const API_URL = '/api/deliveries';
let currentId = null;

// Fetch all deliveries
async function fetchDeliveries() {
    const response = await fetch(API_URL);
    const deliveries = await response.json();
    const tableBody = document.querySelector('#deliveryTable tbody');
    tableBody.innerHTML = '';
    deliveries.forEach(delivery => {
        const row = tableBody.insertRow();
        row.innerHTML = `
            <td>${delivery.userFirstName} ${delivery.userLastName}</td>
            <td>${delivery.productName}</td>
            <td>
                <button class="action-btn" onclick="viewDelivery(${delivery.id})">View</button>
                <button class="action-btn" onclick="editDelivery(${delivery.id})">Edit</button>
                <button class="action-btn" onclick="deleteDelivery(${delivery.id})">Delete</button>
            </td>
        `;
    });
}

// View delivery details
async function viewDelivery(id) {
    const response = await fetch(`${API_URL}/${id}`);
    const delivery = await response.json();
    const detailsDiv = document.getElementById('deliveryDetails');
    detailsDiv.innerHTML = `
        <p><strong>Name:</strong> ${delivery.userFirstName} ${delivery.userLastName}</p>
        <p><strong>Address:</strong> ${delivery.address}</p>
        <p><strong>Product:</strong> ${delivery.productName}</p>
        <p><strong>Price:</strong> ${delivery.price}</p>
        <p><strong>Amount:</strong> ${delivery.amount}</p>
        <p><strong>Size:</strong> ${delivery.size}</p>
        <p><strong>Description:</strong> ${delivery.description}</p>
    `;
    document.getElementById('viewModal').style.display = 'block';
}

// Edit delivery
async function editDelivery(id) {
    const response = await fetch(`${API_URL}/${id}`);
    const delivery = await response.json();
    currentId = id;
    document.getElementById('userFirstName').value = delivery.userFirstName;
    document.getElementById('userLastName').value = delivery.userLastName;
    document.getElementById('address').value = delivery.address;
    document.getElementById('productName').value = delivery.productName;
    document.getElementById('price').value = delivery.price;
    document.getElementById('amount').value = delivery.amount;
    document.getElementById('size').value = delivery.size;
    document.getElementById('description').value = delivery.description;
}

// Delete delivery
async function deleteDelivery(id) {
    if (confirm('Are you sure you want to delete this delivery?')) {
        const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        if (response.ok) {
            fetchDeliveries();
        } else {
            alert('Error deleting delivery');
        }
    }
}

// Initialize the application
function initApp() {
    const form = document.getElementById('deliveryForm');
    if (form) {
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            if (!e.target.checkValidity()) {
                alert('Please fill out all required fields with valid data.');
                return;
            }
            const formData = new FormData(e.target);
            const delivery = Object.fromEntries(formData.entries());
            const url = currentId ? `${API_URL}/${currentId}` : API_URL;
            const method = currentId ? 'PUT' : 'POST';

            const response = await fetch(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(delivery),
            });

            if (response.ok) {
                e.target.reset();
                currentId = null;
                fetchDeliveries();
            } else {
                alert('Error saving delivery');
            }
        });
    }

    const closeButton = document.querySelector('.close');
    if (closeButton) {
        closeButton.addEventListener('click', () => {
            document.getElementById('viewModal').style.display = 'none';
        });
    }

    // Initial fetch
    fetchDeliveries();
}

// Run the init function when the DOM is fully loaded
document.addEventListener('DOMContentLoaded', initApp);