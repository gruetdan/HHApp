document.addEventListener("DOMContentLoaded", function () {
    let selectedRow = null;

    // ----------------------------------------------------------------------------
    // Load Data Logic (Fetching and populating the table)
    // ----------------------------------------------------------------------------
    function loadData() {
        fetch("/api/expenses")
            .then((response) => response.json())
            .then((expenses) => {
                const tbody = document.querySelector("#expenseTable tbody");
                tbody.innerHTML = ""; // Clear existing rows before reloading
                expenses.forEach((expense) => {
                    const row = document.createElement("tr");
                    row.setAttribute("data-id", expense.id);
                    row.innerHTML = `
            <td>${expense.id}</td>
            <td>${expense.amount}</td>
            <td>${expense.date}</td>
            <td>${expense.description}</td>
            <td>${expense.source || ""}</td>
            <td>${expense.userName}</td>
          `;
                    // Row click listener for selection handling
                    row.addEventListener("click", function () {
                        if (selectedRow) selectedRow.classList.remove("selected");
                        selectedRow = row;
                        selectedRow.classList.add("selected");
                    });
                    tbody.appendChild(row);
                });
            })
            .catch((error) => console.error("Error fetching expense data:", error));
    }

    // ----------------------------------------------------------------------------
    // Utility Functions to add or update a row in the table
    // ----------------------------------------------------------------------------
    function addRowToTable(expense) {
        const tbody = document.querySelector("#expenseTable tbody");
        const row = document.createElement("tr");
        row.setAttribute("data-id", expense.id);
        row.innerHTML = `
      <td>${expense.id}</td>
      <td>${expense.amount}</td>
      <td>${expense.date}</td>
      <td>${expense.description}</td>
      <td>${expense.source || ""}</td>
      <td>${expense.userName}</td>
    `;
        // Setup click listener for new row (for selection highlighting)
        row.addEventListener("click", function () {
            if (selectedRow) selectedRow.classList.remove("selected");
            selectedRow = row;
            selectedRow.classList.add("selected");
        });
        tbody.appendChild(row);
    }

    function updateRowInTable(expense) {
        const row = document.querySelector(
            `#expenseTable tbody tr[data-id='${expense.id}']`
        );
        if (row) {
            row.innerHTML = `
        <td>${expense.id}</td>
        <td>${expense.amount}</td>
        <td>${expense.date}</td>
        <td>${expense.description}</td>
        <td>${expense.source || ""}</td>
        <td>${expense.userName}</td>
      `;
            // Reassign click handler for selection
            row.addEventListener("click", function () {
                if (selectedRow) selectedRow.classList.remove("selected");
                selectedRow = row;
                selectedRow.classList.add("selected");
            });
        }
    }

    // ----------------------------------------------------------------------------
    // Add Row Logic (now including SOURCE prompt)
    // ----------------------------------------------------------------------------
    document.getElementById("addRow").addEventListener("click", function () {
        const amount = prompt("Enter expense amount:");
        if (amount === null) return;
        const description = prompt("Enter expense description:");
        if (description === null) return;
        const date = prompt(
            "Enter expense date (dd.MM.yyyy):",
            new Date().toLocaleDateString("en-GB")
        );
        if (date === null) return;
        const userName = prompt("Enter user name:");
        if (userName === null) return;
        const source = prompt("Enter source:");
        if (source === null) return;

        const newExpense = {
            amount: parseFloat(amount),
            description: description,
            date: date,
            userName: userName,
            source: source
        };

        fetch("/api/expenses", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(newExpense)
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to add expense");
                }
                return response.json();
            })
            .then((addedExpense) => {
                addRowToTable(addedExpense);
                alert("Expense added successfully.");
            })
            .catch((error) => console.error("Error adding expense:", error));
    });

    // ----------------------------------------------------------------------------
    // Edit Row Logic (now including SOURCE update)
    // ----------------------------------------------------------------------------
    document.getElementById("editRow").addEventListener("click", function () {
        if (!selectedRow) {
            alert("Please select a row to edit.");
            return;
        }
        const id = selectedRow.getAttribute("data-id");
        const currentAmount = selectedRow.children[1].textContent;
        const currentDate = selectedRow.children[2].textContent;
        const currentDescription = selectedRow.children[3].textContent;
        const currentSource = selectedRow.children[4].textContent;
        const currentUserName = selectedRow.children[5].textContent;

        const newAmount = prompt("Edit expense amount:", currentAmount);
        if (newAmount === null) return;
        const newDate = prompt("Edit expense date (dd.MM.yyyy):", currentDate);
        if (newDate === null) return;
        const newDescription = prompt("Edit expense description:", currentDescription);
        if (newDescription === null) return;
        const newUserName = prompt("Edit user name:", currentUserName);
        if (newUserName === null) return;
        const newSource = prompt("Edit source:", currentSource);
        if (newSource === null) return;

        const updatedExpense = {
            amount: parseFloat(newAmount),
            date: newDate,
            description: newDescription,
            userName: newUserName,
            source: newSource
        };

        fetch("/api/expenses/" + id, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedExpense)
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to update expense");
                }
                return response.json();
            })
            .then((expense) => {
                updateRowInTable(expense);
                alert("Expense updated successfully.");
                selectedRow.classList.remove("selected");
                selectedRow = null;
            })
            .catch((error) => console.error("Error updating expense:", error));
    });

    // ----------------------------------------------------------------------------
    // Delete Row Logic
    // ----------------------------------------------------------------------------
    document.getElementById("deleteRow").addEventListener("click", function () {
        if (!selectedRow) {
            alert("Please select a row to delete.");
            return;
        }
        const id = selectedRow.getAttribute("data-id");
        if (confirm("Are you sure you want to delete this expense?")) {
            fetch("/api/expenses/" + id, { method: "DELETE" })
                .then((response) => {
                    if (!response.ok) {
                        throw new Error("Failed to delete expense");
                    }
                    selectedRow.remove();
                    alert("Expense deleted successfully.");
                    selectedRow = null;
                })
                .catch((error) => console.error("Error deleting expense:", error));
        }
    });

    // ----------------------------------------------------------------------------
    // Save Button Logic
    // ----------------------------------------------------------------------------
    document.getElementById("saveBtn").addEventListener("click", function () {
        // Call the new REST endpoint that triggers DataDownloader logic
        fetch("/api/expenses/save", { method: "GET" })
            .then(response => response.text())
            .then(message => alert("Save: " + message))
            .catch(error => console.error("Error saving data:", error));
    });

    // ----------------------------------------------------------------------------
    // Initial Data Load
    // ----------------------------------------------------------------------------
    loadData();
});
