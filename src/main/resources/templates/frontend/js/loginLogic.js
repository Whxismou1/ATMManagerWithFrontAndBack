document.addEventListener("DOMContentLoaded", function () {
  function showErrorModal(message) {
    const modal = document.getElementById("myModal");
    const modalMessage = document.getElementById("modal-message");
    modalMessage.textContent = message;
    modal.style.display = "block";
  }

  // Ocultar el modal
  function hideErrorModal() {
    const modal = document.getElementById("myModal");
    modal.style.display = "none";
  }

  // Evento para cerrar el modal haciendo clic en la 'x'
  document.querySelector(".close").addEventListener("click", hideErrorModal);

  // Evento para cerrar el modal haciendo clic fuera del modal
  window.addEventListener("click", function (event) {
    const modal = document.getElementById("myModal");
    if (event.target === modal) {
      hideErrorModal();
    }
  });

  document.querySelector("#form").addEventListener("submit", async (e) => {
    e.preventDefault();

    const usernameIntroduced = document.getElementById("username").value;
    const passwordIntroduced = document.getElementById("password").value;

    try {
      const response = await fetch(
        `http://localhost:8081/users/login?username=${usernameIntroduced}&password=${passwordIntroduced}`
      );

      if (response.ok) {
        // Usuario autenticado correctamente, redirigir al home
        // const userData = await response.json();

        window.location.href = `../html/home.html?username=${encodeURIComponent(
          usernameIntroduced
        )}`;
      } else if (response.status === 401) {
        // Credenciales incorrectas
        const resText = await response.text();

        if (resText === "Usuario no verificado") {
          console.log("Usuario no verificado");
        } else if (resText === "Password incorrecta") {
          showErrorModal("Credenciales incorrectas");
        }

        // alert(resText);

        // alert("Credenciales incorrectas");
      } else if (response.status === 404) {
        // Usuario no encontrado
        alert("Usuario no encontrado");
      } else {
        // Error en la solicitud
        alert("Error al iniciar sesión");
      }
    } catch (error) {
      console.error("Error al iniciar sesión:", error);
      alert("Error al iniciar sesión");
    }
  });
});
