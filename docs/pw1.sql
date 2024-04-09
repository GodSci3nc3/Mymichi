SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `categoria` (
  `ID_Categoria` int(11) NOT NULL,
  `Categoria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

CREATE TABLE `publicación` (
  `ID_Publición` int(11) NOT NULL,
  `Titulo` varchar(80) NOT NULL,
  `Contenido` varchar(140) NOT NULL,
  `Imagen` longblob NOT NULL,
  `Estado` int(11) NOT NULL,
  `ID_Usuario` int(11) NOT NULL,
  `ID_Categoria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

CREATE TABLE `usuario` (
  `ID_Usuario` int(11) NOT NULL,
  `Nombre_Usuario` varchar(60) NOT NULL,
  `Correo` varchar(80) NOT NULL,
  `Contraseña` varchar(20) NOT NULL,
  `Nombres` varchar(60) NOT NULL,
  `Apellidos` varchar(60) NOT NULL,
  `Fecha_Nacimiento` date NOT NULL,
  `Imagen_Perfil` longblob NOT NULL,
  `Fecha_Registro` datetime NOT NULL,
  `Edad` int(11) NOT NULL,
  `Género` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

ALTER TABLE `categoria`
  ADD PRIMARY KEY (`ID_Categoria`);

ALTER TABLE `publicación`
  ADD PRIMARY KEY (`ID_Publición`),
  ADD KEY `ID_Usuario` (`ID_Usuario`),
  ADD KEY `ID_Categoria` (`ID_Categoria`);

ALTER TABLE `usuario`
  ADD PRIMARY KEY (`ID_Usuario`);

ALTER TABLE `categoria`
  MODIFY `ID_Categoria` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `publicación`
  MODIFY `ID_Publición` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `usuario`
  MODIFY `ID_Usuario` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `publicación`
  ADD CONSTRAINT `publicación_ibfk_1` FOREIGN KEY (`ID_Usuario`) REFERENCES `usuario` (`ID_Usuario`),
  ADD CONSTRAINT `publicación_ibfk_2` FOREIGN KEY (`ID_Categoria`) REFERENCES `categoria` (`ID_Categoria`);
COMMIT;

