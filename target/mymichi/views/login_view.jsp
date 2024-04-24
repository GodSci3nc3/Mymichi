<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../styles/login_styles.css">
    <script src="/lib/node_modules/lottie-web/build/player/lottie.min.js"></script>
    <title>Inicio de Sesion - My Michi</title>
</head>
<body>
<div class="container">
    <div id="pink-cat"></div>
    <div class="login">
        <div class="titles">
            <h1>My Michi</h1>
            <div class="subtitulo">
                <h2>Inicia Sesion</h2>
            </div>
        </div>
        <form class="login-form" action="/mymichi/loginServlet" method="post" enctype="multipart/form-data">
            <label for="usuario" style="font-size: 30px; color: #5F5FA2;">Usuario</label>
            <input type="text" id="usuario" name="usuario"><br>
            <label for="contraseña" style="font-size: 30px; color: #5F5FA2;">Contrasena</label>
            <input type="password" id="contraseña"  name = "password"><br>
            <a class="register-button" href="register_view.jsp">No tienes cuenta? Registrate</a>
            <div class="enviar">
                <input type="submit" value="Ok" style="font-family: 'Kiwi Maru', sans-serif;">
            </div>
        </form>
    </div>
    
</div>


<script src="../controllers/js/main.js"></script>

<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Kiwi+Maru&display=swap">


</body>
<!--Este es el pie de página-->
<footer>
    Desarrollado por Ximena Rosales Velazquez
</footer>
</html>
