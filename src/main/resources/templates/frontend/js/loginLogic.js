document.addEventListener("DOMContentLoaded", function () {
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
        const userData = await response.json();
        window.location.href = `../html/home.html?username=${encodeURIComponent(usernameIntroduced)}`;
      } else if (response.status === 401) {
        // Credenciales incorrectas

        alert("Credenciales incorrectas");
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
