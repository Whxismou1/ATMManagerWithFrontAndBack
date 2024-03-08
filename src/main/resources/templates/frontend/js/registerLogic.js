document.addEventListener("DOMContentLoaded", function () {
  document.querySelector("#form").addEventListener("submit", async (e) => {
    e.preventDefault();
    const data = Object.fromEntries(new FormData(e.target));
    const initialAmmountValue = data["balance"];

    if (initialAmmountValue === undefined || initialAmmountValue === "") {
      data["balance"] = 0;
    }

    const jsonOb = JSON.stringify(data);

    // console.log(jsonOb);

    try {
      const response = await fetch("http://localhost:8081/users/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: jsonOb,
      });
      
      if (response.ok) {
        // Mostrar mensaje de éxito
        alert("Usuario registrado exitosamente");
        // const responseJson = await response.text();
        // console.log(responseJson);
        // alert(responseJson.message);
        window.location.href = "../html/loginForm.html";
      } else {
        // Mostrar mensaje de error si la solicitud falla
        alert("Error al registrar usuario");
      }
    } catch (error) {
      console.error("Error al enviar la solicitud:", error);
      // Mostrar mensaje de error si ocurre un error en la solicitud
      alert("Error al enviar la solicitud: " + error.message);
    }
  });
});
