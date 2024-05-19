<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<%@ page import="java.util.List, com.mymichi.utils.Publicacion"%>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../styles/feed_styles.css">
    <title>Home - MyMichi</title>
</head>

<body>
    <div class="header">
        <div class="border">
            <span>My Michi</span>
        </div>

        <!--Aquí se reciben los datos de del usuario-->
        <% 
        String username = (String)session.getAttribute("username");
        byte[] photoBytes = (byte[])session.getAttribute("photo");
        %>

        <!--Esta es la barra de navegación-->
        <div class="navbar">
            <ul>
                <li><a href="feed_view.jsp"><img src="../assets/home_icon.png">Inicio</a></li>
            </ul>

            
            <div class="search-container">
                <div class="search-bar">
                    <button type="submit"><img src="../assets/search_icon.png"></button>
                    <input type="text" id="searchInput">
                </div>
                <div class="search-results" id="searchResults">
                    <div class="info-result">
                    <img src="../assets/profile-photo.png" alt="Foto de perfil" style="width: 80px; border-radius: 505px;">
                    <h3>Nombre de usuario</h3>
                    </div>
                    <div class="info-result">
                        <img src="../assets/profile-photo.png" alt="Foto de perfil" style="width: 80px; border-radius: 505px;">
                        <h3>Nombre de usuario</h3>
                        </div>
                        <div class="info-result">
                            <img src="../assets/profile-photo.png" alt="Foto de perfil" style="width: 80px; border-radius: 505px;">
                            <h3>Nombre de usuario</h3>
                            </div>
                </div>
            </div>


            <div class="profile">
                <h3><%=username%></h3>
                <a href="profile_view.jsp"><img style="width: 80px; border: 2px solid #55589E; border-radius: 505px;" src="/mymichi/imageServlet"/></a>
            </div>
        </div>
    </div>


    <div class="scrollable">
        <!-- Enlace para abrir ventana emergente-->
        <div class="goPublish">
            <img style="width: 90px; border: 2px solid #55589E; border-radius: 505px;" src="/mymichi/imageServlet" href="profile_view.jsp"/>
            <a id="openCreatePublishContent" style="text-decoration: none; font-size: 30px; margin-left: 65px;">En que estas pensando?</a>
        </div>

        <!-- Ventana emergente para CREAR una publicación-->
        <div class="createPublishContent" id="createPublishContent">
            <span class="closeCreatePublishContent" id="closeCreatePublishContent">&times;</span>

            <h1>Crear publicacion</h1>
            <div class="createPublishContentForm">
                <form action="/mymichi/newPost" id="createMyPublish" method="post" enctype="multipart/form-data">

                    <!-- Título de la publicación-->
                    <label for="title">Titulo:</label><br>
                    <input type="text" id="title" name="title"><br><br>
                <!-- Contenido de la publicación-->
                    <label for="content">Contenido:</label><br>
                    <textarea id="content" name="content" rows="4" cols="50"></textarea><br><br>
                

                <!-- Categoría de la publicación-->
                    <label for="category">Categoria:</label><br>
                    <select id="category" name="category">
                        <option value="Amigos">Amigos</option>
                        <option value="Familia">Familia</option>
                        <option value="Gracioso">Gracioso</option>
                        <!-- Aquí se pueden agregar más opciones de categorías -->
                    </select><br><br>
                <!-- Insertar una imagen para laDESCRIBE publicación; publicación-->
                    <label for="image">Imagen:</label><br>
                    <input type="file" id="image" name="image"><br><br>

                    <input type="submit" value="Crear publicación">
                </form>
            </div>
        </div>


        <!-- Botón para abrir ventana emergente-->
            <div class="advancePublish">
                <button  id="openAdvancePublishContent">Busqueda Avanzada</button>
            </div>


            <!-- Ventana emergente para BUSCAR una publicación-->
            <div class="advancePublishContent" id="advancePublishContent">
                <h1>Busque publicaciones con base a:</h1>
                <div class="advancePublishForm">
                    <span class="closeAdvancePublishContent" id="closeAdvancePublishContent">&times;</span>
                    <form id="advanceSearchByTextAndCategory">
                        <div class="advanceSearchByText">
                            <label for="texto">Texto:</label>
                            <input type="text" id="texto" name="texto" placeholder="Busque publicaciones basado en texto">
            
                            <label for="categoria">Categoría:</label>
                            <select id="categoria" name="categoria" required>
                                <option value="">Selecciona una categoría</option>
                                <option value="categoria1">Categoria 1</option>
                                <option value="categoria2">Categoria 2</option>
                                <option value="categoria3">Categoria 3</option>
                            </select>
                        </div>
            
                        <div class="advanceSearchByDates">
                            <label for="fechaInicio">Fecha de Inicio:</label>
                            <input type="date" id="fechaInicio" name="fechaInicio" required>
            
                            <label for="fechaFin">Fecha de Fin:</label>
                            <input type="date" id="fechaFin" name="fechaFin" required>
                        </div>
                        <button type="submit">Buscar</button>
                    </form>
                </div>
            </div>
            


            <div class="publications">
                <%
                    List<Publicacion> publicaciones = (List<Publicacion>) session.getAttribute("publicaciones");
                        if (publicaciones != null) {
                        for (Publicacion publicacion : publicaciones) {
                %>
                <div class="publication-contents">
                    <div class="user-publication">
                        <%
                            if (publicacion.getImagenUsuario() != null) {
                                String imageDataUri = "data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(publicacion.getImagenUsuario());
                                %>
                        <img src="<%= imageDataUri %>" alt="Foto de perfil" style="width: 80px; border-radius: 505px;">
                        <% } else { %>
                        <img src="../assets/profile-photo.png" alt="Foto de perfil" style="width: 80px; border-radius: 505px;">
                        <% } %>
                        <h3 style="margin-left: 10px;"><%= publicacion.getNombreUsuario() %></h3>
                    </div>
                    <h2><%= publicacion.getTitulo() %></h2>
                    <a href="#"><%= publicacion.getCategoria() %></a>
                    <p><%= publicacion.getContenido() %></p>
                    <%
                        if (publicacion.getImagen() != null) {
                            String imageDataUriPublication = "data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(publicacion.getImagen());
                            %>
                    <img src="<%= imageDataUriPublication %>" style="width: 350px; height: 250px; margin: 0 auto;">
                    <% } %>
                </div>
                <% }
                } else{
                    response.sendRedirect("/mymichi/getPost"); 
                } %>
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

    
    <script src="../controllers/js/search_bar.js"></script>
    <script src="../controllers/js/advanceSearch.js"></script>
    <script src="../controllers/js/createPublish.js"></script>

    <!--Este es el pie de página-->
    <footer>
        Desarrollado por Ximena Rosales Velazquez
    </footer>
</body>

<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Kiwi+Maru&display=swap">
</html>