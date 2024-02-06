document.addEventListener("DOMContentLoaded", function () {
  document.querySelector("#form").addEventListener("submit", async (e) => {
    e.preventDefault();
    const data = Object.fromEntries(new FormData(e.target));
    const jsonOb = JSON.stringify(data);

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
        window.location.href = "../html/login.html";
      } else {
        // Mostrar mensaje de error si la solicitud falla
        alert("Error al registrar usuario");
      }
    } catch (error) {
      console.error("Error al enviar la solicitud:", error);
      // Mostrar mensaje de error si ocurre un error en la solicitud
      alert("Error al enviar la solicitud");
    }
  });
});

// async function validarFormulario() {
//   const user = document.getElementById("username").value;

//   const usuarioExistente = await verificarUsuarioExistente(user);
//   if (usuarioExistente) {
//     alert("El usuario ya existe.");
//     return false;
//   }

//   const formData = {
//     fullname: document.getElementById("fullname").value,
//     email: document.getElementById("email").value,
//     phone: document.getElementById("phone").value,
//     address: document.getElementById("address").value,
//     username: user,
//     password: document.getElementById("password").value,
//     fechaNacimiento: document.getElementById("fechaNacimiento").value,
//   };

//   // Enviar datos del formulario en formato JSON al backend
//   const response = await fetch("http://localhost:8081/users/register", {
//     method: "POST",
//     headers: {
//       "Content-Type": "application/json",
//     },
//     body: JSON.stringify(formData),
//   });

//   // Verificar la respuesta del backend
//   if (response.ok) {
//     console.log("Formulario enviado con éxito");
//     // Puedes hacer más acciones aquí si es necesario
//     return true;
//   } else {
//     console.error("Error al enviar el formulario:", response.statusText);
//     return false;
//   }
// }

// async function verificarUsuarioExistente(usuario) {
//   try {
//     const response = await fetch(`http://localhost:8081/users/${usuario}`, {
//       method: "GET",
//     });

//     if (response.status === 200) {
//       const data = await response.json();
//       console.log("Usuario encontrado:", data);
//       return true;
//     } else if (response.status === 404) {
//       console.log("Usuario no encontrado");
//       return false;
//     } else {
//       console.error(
//         "Error al verificar usuario existente:",
//         response.statusText
//       );
//       return false;
//     }
//   } catch (error) {
//     console.error("Error en la verificación del usuario existente:", error);
//     return false;
//   }
// }
// function submitForm() {
//   // Crear un nuevo objeto FormData que contiene los datos del formulario
//   const formData = new FormData(document.getElementById("form"));

//   // Convertir el objeto FormData a un objeto JSON
//   const jsonData = {};
//   formData.forEach((value, key) => {
//     jsonData[key] = value;
//   });

//   // Enviar datos al backend
//   fetch("http://localhost:8081/users/register", {
//     method: "POST",
//     headers: {
//       "Content-Type": "application/x-www-form-urlencoded",
//     },
//     body: JSON.stringify(jsonData),
//   })
//     .then((response) => response.json())
//     .then((data) => {
//       // Manejar la respuesta del backend
//       console.log(data);
//     })
//     .catch((error) => {
//       console.error("Error:", error);
//     });
// }
