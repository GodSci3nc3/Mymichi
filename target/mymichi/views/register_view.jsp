<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../styles/register_styles.css">
    <title>Registro - My Michi</title>
</head>

<body>
    <div class="container">
        <div class="register">
            <div id="pink-cat"></div>
            <div class="titles">
                <h1>Registro</h1>
            </div>

            <!-- Formulario de registro - Seis campos, todos requeridos **Incluir foto** -->
            <form class="register-form" action="/mymichi/registerServlet" method="post" enctype="multipart/form-data">
                <div class="input-container">
                    <label for="Nombre" style="font-size: 30px; color: #5F5FA2;">Nombre</label>
                    <input type="text" id="name" name="nombre">
                </div>
                <div class="input-container">
                    <label for="Apellido" style="font-size: 30px; color: #5F5FA2;">Apellido</label>
                    <input type="text" id="lastname" name="apellido">
                </div>
                <div class="input-container">
                    <label for="Fecha de nacimiento" style="font-size: 30px; color: #5F5FA2;">Fecha de
                        nacimiento</label>
                    <input type="date" id="birthday" name="fecha_nacimiento"><br>
                </div>
                <div class="input-container">
                    <label for="Correo" style="font-size: 30px; color: #5F5FA2;">Correo</label>
                    <input type="email" id="email" name="correo">
                </div>
                <div class="input-container">
                    <label for="usuario" style="font-size: 30px; color: #5F5FA2;">Usuario</label>
                    <input type="text" id="username" name="nombre_usuario">
                </div>
                <div class="input-container">
                    <label for="contraseña" style="font-size: 30px; color: #5F5FA2;">Contrasena</label>
                    <input type="password" id="password" name="password"><br>
                </div>
                <div class="input-container">
                    <label for="image" style="font-size: 30px; color: #5F5FA2;">Foto de perfil</label>
                    <input type="file" id="image" name="image"><br>
                </div>


                <a class="login-button" href="login_view.jsp">Ya tienes una cuenta? Inicia sesion</a>
                <div class="btn_send">
                    <input type="submit" value="Ok" style="font-family: 'Kiwi Maru', sans-serif;">
                </div>
            </form>
        </div>
    </div>

    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Kiwi+Maru&display=swap">
    <!--Este es el pie de página-->
    <footer>
        Desarrollado por Ximena Rosales Velazquez
    </footer>
</body>

</html>