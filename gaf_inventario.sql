-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
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
-- Table structure for table `inventario`
-- Comentario que indica que a continuación se definirá la estructura de la tabla inventario

DROP TABLE IF EXISTS `inventario`;
-- Elimina la tabla si existe para poder crearla de nuevo

/*!40101 SET @saved_cs_client     = @@character_set_client */;
-- Guarda el juego de caracteres actual del cliente

/*!50503 SET character_set_client = utf8mb4 */;
-- Establece el juego de caracteres del cliente a utf8mb4 (soporta emojis y más caracteres)

CREATE TABLE `inventario` (
  `id_Inventario` int NOT NULL,
  -- Identificador único del inventario, clave primaria

  `id_bodega` int DEFAULT NULL,
  -- Referencia a la bodega donde se encuentra el inventario

  `id_lote` int DEFAULT NULL,
  -- Referencia al lote del producto

  `fecha_ingreso` date DEFAULT NULL,
  -- Fecha en que el producto ingresó al inventario

  `fecha_salida` date NOT NULL,
  -- Fecha de salida del producto del inventario

  `estado` enum('en_bodega','salido') DEFAULT NULL,
  -- Estado del producto (en bodega o salido)

  `cantidad` decimal(10,0) DEFAULT NULL,
  -- Cantidad de productos en el inventario

  `direccion` varchar(45) DEFAULT NULL,
  -- Dirección de la bodega o ubicación específica

  PRIMARY KEY (`id_Inventario`),
  -- Define la clave primaria de la tabla

  KEY `id_bodega_idx` (`id_bodega`),
  -- Índice para optimizar consultas por id_bodega

  KEY `id_lote_idx` (`id_lote`),
  -- Índice para optimizar consultas por id_lote

  CONSTRAINT `id_bodega` FOREIGN KEY (`id_bodega`) REFERENCES `bodega` (`id_Bodega`),
  -- Llave foránea que relaciona id_bodega con la tabla bodega

  CONSTRAINT `id_lote` FOREIGN KEY (`id_lote`) REFERENCES `lote` (`idLote`)
  -- Llave foránea que relaciona id_lote con la tabla lote
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
-- Restaura el juego de caracteres del cliente

-- ------------------------------------------------------
-- Dumping data for table `inventario`
-- Sección para insertar los datos de la tabla inventario

LOCK TABLES `inventario` WRITE;
-- Bloquea la tabla para escritura mientras se insertan los datos

/*!40000 ALTER TABLE `inventario` DISABLE KEYS */;
-- Desactiva temporalmente los índices para acelerar inserciones masivas

/*!40000 ALTER TABLE `inventario` ENABLE KEYS */;
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
