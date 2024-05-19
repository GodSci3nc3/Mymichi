<%@ page import="java.io.OutputStream, java.util.Base64" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.mymichi.utils.Publicacion" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../styles/profile_styles.css">
    <title>Perfil de Usuario - My Michi</title>
</head>

<body>
    <div class="container">

        <!--Esta es la barra de navegación-->
        <div class="navbar">
            <h1>My Michi</h1>

            <div class="search-container">
                <div class="search-bar">
                    <input type="text" id="searchInput">
                    <button type="submit"><img src="../assets/search_icon.png"></button>
                </div>

                <div class="search-results" id="searchResults">
                    <div class="info-result">
                        <img src="../assets/profile-photo.png" alt="Foto de perfil" style="width: 80px; border-radius: 505px;">
                        <h3>Nombre de usuario</h3>
                    </div>
                    <div class="info-result">
                        <img src="../assets/profile-photo2.png" alt="Foto de perfil" style="width: 80px; border-radius: 505px;">
                        <h3>Nombre de usuario</h3>
                    </div>
                    <div class="info-result">
                        <img src="../assets/profile-photo.png" alt="Foto de perfil" style="width: 80px; border-radius: 505px;">
                        <h3>Nombre de usuario</h3>
                    </div>
                </div>
            </div>
            <ul>
                <li><a href="feed_view.jsp"><img src="../assets/home_icon.png">Inicio</a></li>
            </ul>
        </div>
    </div>

    <div class="profile">
        <div class="profile-content">
            <div class="profile-user">
                <div class="user-information">
                    <%
                    // Obtener todos los datos del usuario
                    String username = (String) session.getAttribute("username");
                    byte[] photo = (byte[]) session.getAttribute("photo");
                    String correo = (String) session.getAttribute("correo");
                    int edad = (int) session.getAttribute("edad");
                    %>

                    <%
                    String imageDataUri = "";
                    if (photo != null) {
                        // Convertir los bytes de la imagen a una cadena Base64
                        String base64Image = Base64.getEncoder().encodeToString(photo);
                        imageDataUri = "data:image/jpeg;base64," + base64Image;
                    } else {
                        imageDataUri = "../assets/profile-photo.png";
                    }
                    %>

                    <img src="<%= imageDataUri %>" style="width: 300px; height: 300px;" alt="Foto de perfil">

                    <h2><%= username %></h2>
                    <h2><%= correo %></h2>
                    <h2><%= edad %></h2>

                    <form action="/mymichi/logoutServlet" method="post">
                        <button type="submit">Cerrar sesion</button>
                    </form>
                </div>
            </div>

            <div class="scrollable">
                <div class="publications">
                    <%
                    List<Publicacion> publicaciones = (List<Publicacion>) session.getAttribute("mispublicaciones");
                    if (publicaciones != null && !publicaciones.isEmpty()) {
                        for (Publicacion publicacion : publicaciones) {
                    %>
                    <div class="publication-contents">
                        <div class="user-publication">
                            <% if (publicacion.getImagenUsuario() != null) { %>
                            <img src="data:image/jpeg;base64,<%= Base64.getEncoder().encodeToString(publicacion.getImagenUsuario()) %>" alt="Foto de perfil" style="width: 80px; border-radius: 505px;">
                            <% } else { %>
                            <img src="../assets/profile-photo.png" alt="Foto de perfil" style="width: 80px; border-radius: 505px;">
                            <% } %>
                            <h3 style="margin-left: 10px;"><%= publicacion.getNombreUsuario() %></h3>
                        </div>
                        <h2><%= publicacion.getTitulo() %></h2>
                        <a href="#"><%= publicacion.getCategoria() %></a>
                        <p><%= publicacion.getContenido() %></p>
                        <% if (publicacion.getImagen() != null) { %>
                        <img src="data:image/jpeg;base64,<%= Base64.getEncoder().encodeToString(publicacion.getImagen()) %>" style="width: 350px; height: 250px; margin: 0 auto;">
                        <% } %>
                    </div>
                    <% } %>
                    <% } else { %>
                    <div class="publication-contents">
                        <h3>No hay publicaciones para mostrar</h3>
                    </div>
                    <% } %>
                </div>
                <div class="pages">
                    <a href="#">1</a>
                    <a href="#">2</a>
                    <a href="#">3</a>
                    <a href="#">4</a>
                    <a href="#">5</a>
                    <a href="#">></a>
                </div>
            </div>
        </div>
    </div>

    <!--Este es el pie de página-->
    <footer>
        Desarrollado por Ximena Rosales Velazquez
    </footer>

    <script src="../controllers/js/search_bar.js"></script>

</body>

<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Kiwi+Maru&display=swap">

</html>