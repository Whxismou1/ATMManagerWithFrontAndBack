document.addEventListener("DOMContentLoaded", async function () {
  const urlParams = new URLSearchParams(window.location.search);
  const username = urlParams.get("username");

  if (username) {
    document.getElementById("username").innerText = username;
    getNumCuenta(username);
    getBalance(username);
    getTransactions(username);

    const formMoves = document.getElementById("formMoves");

    formMoves.addEventListener("submit", async (e) => {
      e.preventDefault();

      document.getElementById("withdrawBtn").disabled = true;
      document.getElementById("depositBtn").disabled = true;

      const amount = document.getElementById("depositAmount").value;

      const clickedButton = e.submitter.id;

      let transactionType;

      if (clickedButton === "withdrawBtn") {
        transactionType = "WITHDRAWAL";
      } else if (clickedButton === "depositBtn") {
        transactionType = "DEPOSIT";
      }

      // Crear el objeto de transacción
      const transactionData = {
        username: username,
        amount: amount,
        transactionType: transactionType,
        transactionDate: new Date().toISOString(),
      };

      formMoves.reset();
      try {
        const currentBalance = getBalance(username);

        const transactionAmount = parseFloat(amount);

        if (
          transactionType === "WITHDRAWAL" &&
          currentBalance < transactionAmount
        ) {
          throw new Error(
            "No hay suficiente saldo para realizar esta transacción"
          );
        }

        // Agregar la transacción antes de obtener las transacciones actualizadas
        await addTransaction(transactionData);
        // Obtener las transacciones actualizadas después de agregar la nueva transacción
        getTransactions(username);
        updateBalance(username, amount, transactionType);
        getBalance(username);
      } catch (error) {
        console.error("Error al agregar la transacción:", error.message);
        alert(error.message);
      } finally {
        // Habilitar los botones nuevamente después de realizar la solicitud
        document.getElementById("withdrawBtn").disabled = false;
        document.getElementById("depositBtn").disabled = false;
      }
    });
  }
});

async function addTransaction(transactionData) {
  try {
    const response = await fetch("http://localhost:8081/transactions", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(transactionData),
    });

    if (response.ok) {
      // alert("Transacción realizada con éxito");
    } else {
      console.error("Error al realizar la transacción:", response.statusText);
      // alert("Error al realizar la transacción");
    }
  } catch (error) {
    // console.error("Error al enviar la solicitud:", error);
    throw error;
  }
}

async function getTransactions(username) {
  try {
    const response = await fetch(
      `http://localhost:8081/transactions/${username}`
    );

    if (response.ok) {
      const transactions = await response.json();
      renderTransactions(transactions);
    } else {
      console.error("Error al obtener las transacciones:", response.statusText);
    }
  } catch (error) {
    console.error("Error al obtener las transacciones:", error.message);
  }
}

function renderTransactions(transactions) {
  const transactionsContainer = document.querySelector(".transactions");

  // Limpiar el contenedor antes de agregar las nuevas transacciones
  transactionsContainer.innerHTML = "";

  transactions.forEach((transaction) => {
    const transactionDiv = document.createElement("div");
    transactionDiv.classList.add("transaction");

    const transactionTypeDiv = document.createElement("div");
    transactionTypeDiv.innerText = transaction.transactionType;
    transactionDiv.appendChild(transactionTypeDiv);

    const amountDiv = document.createElement("div");
    amountDiv.innerText = `${transaction.amount} €`;
    transactionDiv.appendChild(amountDiv);

    const dateDiv = document.createElement("div");
    const date = transaction.transactionDate.join("-");
    dateDiv.innerText = date;
    transactionDiv.appendChild(dateDiv);

    transactionsContainer.appendChild(transactionDiv);
  });
}

async function getBalance(username) {
  const response = await fetch(
    `http://localhost:8081/transactions/balance/${username}`
  );

  if (response.ok) {
    const balance = parseFloat(await response.text());

    document.getElementById("balance").innerText = balance.toLocaleString('es-ES', { style: 'currency', currency: 'EUR' });
  }
}

async function updateBalance(username, ammount, transactionType) {
  if (transactionType === "WITHDRAWAL") {
    ammount = -ammount;
  }

  const response = await fetch(
    `http://localhost:8081/transactions/balance/${username}`,
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: ammount.toString(),
    }
  );

  if (response.ok) {
    alert("Balance actualizado con éxito");
    getBalance(username);
  } else {
    alert("Error al actualizar el balance");
  }
}

async function getNumCuenta(username) {
  const response = await fetch(
    `http://localhost:8081/users/numCuenta?username=${username}`
  );

  if (response.ok) {
    const numCuenta = await response.text();
    console.log("numCuenta: ", numCuenta);
    document.getElementById("iban").innerText = numCuenta;
  }
}
