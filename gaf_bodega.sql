--- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
-- Información sobre la versión del cliente MySQL que generó el dump

-- Host: localhost    Database: gaf
-- Indica el servidor y la base de datos de origen

-- ------------------------------------------------------
-- Server version	9.4.0
-- Versión del servidor MySQL/MariaDB que creó la base de datos

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
-- Guarda el juego de caracteres actual del cliente

/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
-- Guarda el juego de caracteres actual para los resultados

/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
-- Guarda la collation actual de la conexión

/*!50503 SET NAMES utf8 */;
-- Fuerza la conexión a usar UTF-8

/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
-- Guarda la zona horaria actual

/*!40103 SET TIME_ZONE='+00:00' */;
-- Establece la zona horaria a UTC

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
-- Desactiva temporalmente la verificación de valores únicos

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
-- Desactiva temporalmente la verificación de llaves foráneas

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
-- Guarda el modo SQL actual y establece que NO se auto incremente al insertar cero

/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
-- Desactiva temporalmente las notas de SQL

-- ------------------------------------------------------
-- Table structure for table `bodega`
-- Comentario que indica que a continuación se definirá la estructura de la tabla bodega

DROP TABLE IF EXISTS `bodega`;
-- Elimina la tabla si existe para poder crearla de nuevo

/*!40101 SET @saved_cs_client     = @@character_set_client */;
-- Guarda el juego de caracteres actual del cliente

/*!50503 SET character_set_client = utf8mb4 */;
-- Establece el juego de caracteres del cliente a utf8mb4 (soporta emojis y más caracteres)

CREATE TABLE `bodega` (
  `id_Bodega` int NOT NULL,
  -- Identificador único de la bodega, clave primaria

  `nombre` varchar(45) DEFAULT NULL,
  -- Nombre de la bodega

  `capacidad` decimal(10,0) DEFAULT NULL,
  -- Capacidad máxima de la bodega

  PRIMARY KEY (`id_Bodega`)
  -- Define la clave primaria de la tabla
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
-- Restaura el juego de caracteres del cliente

-- ------------------------------------------------------
-- Dumping data for table `bodega`
-- Sección para insertar los datos de la tabla bodega

LOCK TABLES `bodega` WRITE;
-- Bloquea la tabla para escritura mientras se insertan los datos

/*!40000 ALTER TABLE `bodega` DISABLE KEYS */;
-- Desactiva temporalmente los índices para acelerar inserciones masivas

/*!40000 ALTER TABLE `bodega` ENABLE KEYS */;
-- Vuelve a habilitar los índices

UNLOCK TABLES;
-- Desbloquea la tabla

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
-- Restaura la zona horaria original

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
-- Restaura el modo SQL original

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
-- Vuelve a habilitar las verificaciones de llaves foráneas

/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
-- Vuelve a habilitar la verificación de valores únicos

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
-- Restaura el juego de caracteres del cliente

/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
-- Restaura el juego de caracteres para resultados

/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
-- Restaura la collation de la conexión

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
-- Restaura las notas de SQL

-- Dump completed on 2025-10-10 23:56:53
-- Indica la fecha y hora en que se completó el dump
