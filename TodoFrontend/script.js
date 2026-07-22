// Shared script for login, register, and todos pages
const SERVER_URL = "http://localhost:8080";
const token = localStorage.getItem("token");

// Login page logic
function login() {
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");

    if (!emailInput || !passwordInput) return;

    const email = emailInput.value.trim();
    const password = passwordInput.value;

    if (!email || !password) {
        alert("Please enter both email and password.");
        return;
    }

    fetch(`${SERVER_URL}/auth/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, password })
    })
    .then(async response => {
        if (response.ok) {
            const data = await response.json();
            if (data.token) {
                localStorage.setItem("token", data.token);
                window.location.href = "todos.html";
            } else {
                alert("Login succeeded, but no token was returned by the server.");
            }
        } else {
            const errorText = await response.text();
            alert(`Login failed: ${errorText || response.statusText}`);
        }
    })
    .catch(error => {
        console.error("Error during login:", error);
        alert("An error occurred during login. Please ensure the backend server is running.");
    });
}

// Register page logic
function register() {
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");

    if (!emailInput || !passwordInput) return;

    const email = emailInput.value.trim();
    const password = passwordInput.value;

    if (!email || !password) {
        alert("Please enter both email and password.");
        return;
    }

    fetch(`${SERVER_URL}/auth/register`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, password })
    })
    .then(async response => {
        if (response.ok) {
            alert("Registration successful! Redirecting to login page.");
            window.location.href = "login.html";
        } else {
            const errorText = await response.text();
            alert(`Registration failed: ${errorText || response.statusText}`);
        }
    })
    .catch(error => {
        console.error("Error during registration:", error);
        alert("An error occurred during registration. Please ensure the backend server is running.");
    });
}

// Todos page logic
function createTodoCard(todo) {
    const card = document.createElement("div");
    card.className = "todo-card";
    card.setAttribute("data-id", todo.id);

    // Create checkbox to toggle completion status
    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.className = "todo-checkbox";
    checkbox.checked = todo.isCompleted === "true";

    // Text span
    const titleSpan = document.createElement("span");
    titleSpan.textContent = todo.title;
    
    // Apply styling based on completion status
    if (todo.isCompleted === "true") {
        titleSpan.style.textDecoration = "line-through";
        titleSpan.style.color = "#888";
    }

    // Toggle logic
    checkbox.addEventListener("change", function () {
        todo.isCompleted = checkbox.checked ? "true" : "false";
        if (checkbox.checked) {
            titleSpan.style.textDecoration = "line-through";
            titleSpan.style.color = "#888";
        } else {
            titleSpan.style.textDecoration = "none";
            titleSpan.style.color = "#333";
        }
        updateTodoStatus(todo);
    });

    // Delete button
    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "Delete";
    deleteBtn.onclick = function () {
        deleteTodo(todo.id);
    };

    // Append to card
    card.appendChild(checkbox);
    card.appendChild(titleSpan);
    card.appendChild(deleteBtn);

    return card;
}

function loadTodos() {
    const todoList = document.getElementById("todo-list");
    const emptyMessage = document.getElementById("empty-message");

    if (!todoList) return;

    fetch(`${SERVER_URL}/todo`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
    .then(async response => {
        if (response.status === 401 || response.status === 403) {
            localStorage.removeItem("token");
            alert("Session expired or unauthorized. Please log in.");
            window.location.href = "login.html";
            return;
        }

        if (response.ok) {
            const todos = await response.json();
            todoList.innerHTML = ""; // Clear existing loading state or list items

            if (!todos || todos.length === 0) {
                if (emptyMessage) {
                    emptyMessage.textContent = "No todos found. Add one below!";
                    emptyMessage.style.display = "block";
                    todoList.appendChild(emptyMessage);
                } else {
                    const msg = document.createElement("p");
                    msg.id = "empty-message";
                    msg.textContent = "No todos found. Add one below!";
                    todoList.appendChild(msg);
                }
            } else {
                if (emptyMessage) {
                    emptyMessage.style.display = "none";
                }
                todos.forEach(todo => {
                    const todoCard = createTodoCard(todo);
                    todoList.appendChild(todoCard);
                });
            }
        } else {
            alert("Failed to fetch todos.");
        }
    })
    .catch(error => {
        console.error("Error loading todos:", error);
        if (emptyMessage) {
            emptyMessage.textContent = "Error connecting to server. Please check your backend connection.";
            emptyMessage.style.display = "block";
        }
    });
}

function addTodo() {
    const newTodoInput = document.getElementById("new-todo");
    if (!newTodoInput) return;

    const title = newTodoInput.value.trim();
    if (!title) {
        alert("Please enter a todo title.");
        return;
    }

    const newTodo = {
        title: title,
        description: "",
        isCompleted: "false"
    };

    fetch(`${SERVER_URL}/todo/create`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(newTodo)
    })
    .then(async response => {
        if (response.ok) {
            newTodoInput.value = "";
            loadTodos();
        } else {
            alert("Failed to create todo.");
        }
    })
    .catch(error => {
        console.error("Error adding todo:", error);
        alert("An error occurred while creating the todo.");
    });
}

function updateTodoStatus(todo) {
    fetch(`${SERVER_URL}/todo/update`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(todo)
    })
    .then(response => {
        if (!response.ok) {
            alert("Failed to update todo status on server.");
            loadTodos(); // Re-sync UI state
        }
    })
    .catch(error => {
        console.error("Error updating todo status:", error);
        loadTodos(); // Re-sync UI state
    });
}

function deleteTodo(id) {
    if (!confirm("Are you sure you want to delete this todo?")) {
        return;
    }

    fetch(`${SERVER_URL}/todo/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
    .then(response => {
        if (response.ok) {
            loadTodos();
        } else {
            alert("Failed to delete todo.");
        }
    })
    .catch(error => {
        console.error("Error deleting todo:", error);
        alert("An error occurred while deleting the todo.");
    });
}

// Page-specific initializations
document.addEventListener("DOMContentLoaded", function () {
    const isTodoPage = !!document.getElementById("todo-list");

    if (isTodoPage) {
        if (!token) {
            alert("Please log in to view your todos.");
            window.location.href = "login.html";
        } else {
            loadTodos();
        }
    }
});
