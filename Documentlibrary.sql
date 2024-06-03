CREATE DATABASE  IF NOT EXISTS `documentlibrary` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `documentlibrary`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: documentlibrary
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `articles`
--

DROP TABLE IF EXISTS `articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articles` (
  `document_id` int NOT NULL,
  `ssn` varchar(255) NOT NULL,
  PRIMARY KEY (`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articles`
--

LOCK TABLES `articles` WRITE;
/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` VALUES (14,''),(15,'');
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authors`
--

DROP TABLE IF EXISTS `authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authors` (
  `author_id` int NOT NULL AUTO_INCREMENT,
  `document_id` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`author_id`),
  KEY `document_id` (`document_id`),
  CONSTRAINT `authors_ibfk_1` FOREIGN KEY (`document_id`) REFERENCES `documents` (`document_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authors`
--

LOCK TABLES `authors` WRITE;
/*!40000 ALTER TABLE `authors` DISABLE KEYS */;
INSERT INTO `authors` VALUES (1,6,'','','',''),(2,7,'','','','');
/*!40000 ALTER TABLE `authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `document_id` int NOT NULL,
  `page_count` int NOT NULL,
  PRIMARY KEY (`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (5,55),(16,0),(17,0);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documentauthors`
--

DROP TABLE IF EXISTS `documentauthors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documentauthors` (
  `document_id` int NOT NULL,
  `author_name` varchar(255) NOT NULL,
  PRIMARY KEY (`document_id`,`author_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documentauthors`
--

LOCK TABLES `documentauthors` WRITE;
/*!40000 ALTER TABLE `documentauthors` DISABLE KEYS */;
/*!40000 ALTER TABLE `documentauthors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documenthistory`
--

DROP TABLE IF EXISTS `documenthistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documenthistory` (
  `history_id` int NOT NULL AUTO_INCREMENT,
  `document_id` int NOT NULL,
  `action` varchar(50) NOT NULL,
  `action_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`history_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documenthistory`
--

LOCK TABLES `documenthistory` WRITE;
/*!40000 ALTER TABLE `documenthistory` DISABLE KEYS */;
INSERT INTO `documenthistory` VALUES (1,1,'ADDITION','2024-06-02 04:22:18','omar'),(2,2,'ADDITION','2024-06-02 04:35:06','omar'),(3,2,'UPDATE','2024-06-02 04:50:39','omar'),(4,2,'UPDATE','2024-06-02 04:50:45','omar'),(5,2,'UPDATE','2024-06-02 04:51:47','omar'),(6,2,'UPDATE','2024-06-02 04:52:06','omar'),(7,1,'DELETION','2024-06-02 04:57:56','omar'),(8,2,'DELETION','2024-06-02 04:57:59','omar'),(9,3,'ADDITION','2024-06-02 04:58:14','omar'),(10,4,'ADDITION','2024-06-02 05:00:35','omar'),(11,4,'UPDATE','2024-06-02 05:16:50','omar'),(12,4,'UPDATE','2024-06-02 05:16:57','omar'),(13,4,'UPDATE','2024-06-02 05:17:06','omar'),(14,4,'UPDATE','2024-06-02 05:17:14','omar'),(15,5,'ADDITION','2024-06-02 05:17:46','omar'),(16,4,'RESERVATION','2024-06-02 05:18:06','omar'),(17,4,'DEACTIVATION','2024-06-02 05:18:15','omar'),(18,4,'REACTIVATION','2024-06-02 05:18:33','omar'),(19,4,'RESERVATION','2024-06-02 05:18:39','omar'),(20,4,'RETURN','2024-06-02 05:18:40','omar'),(21,4,'RESERVATION','2024-06-02 05:18:45','omar'),(22,4,'DELETION','2024-06-02 05:19:03','omar'),(23,5,'RESERVATION','2024-06-02 05:24:22','omar'),(24,5,'REACTIVATION','2024-06-02 05:24:47','omar'),(25,5,'DEACTIVATION','2024-06-02 05:24:51','omar'),(26,5,'REACTIVATION','2024-06-02 05:33:51','omar'),(27,5,'RESERVATION','2024-06-02 05:34:00','omar'),(28,5,'RETURN','2024-06-02 05:39:32','omar'),(29,3,'RESERVATION','2024-06-02 05:40:31','omar'),(30,5,'RESERVATION','2024-06-02 06:00:59','omar'),(31,5,'RETURN','2024-06-02 06:01:01','omar'),(32,5,'RESERVATION','2024-06-02 06:01:03','omar'),(33,5,'RETURN','2024-06-02 06:01:08','omar'),(34,5,'DEACTIVATION','2024-06-02 06:01:09','omar'),(35,5,'REACTIVATION','2024-06-02 06:01:10','omar'),(36,6,'ADDITION','2024-06-02 06:53:07','omar'),(37,7,'ADDITION','2024-06-02 06:57:01','omar'),(38,8,'ADDITION','2024-06-02 07:13:41','omar'),(39,8,'UPDATE','2024-06-02 07:14:02','omar'),(40,9,'ADDITION','2024-06-03 00:59:15','omar'),(41,9,'UPDATE','2024-06-03 00:59:32','omar'),(42,9,'UPDATE','2024-06-03 00:59:43','omar'),(43,9,'DELETION','2024-06-03 00:59:53','omar'),(44,8,'RESERVATION','2024-06-03 01:00:37','omar'),(45,8,'RETURN','2024-06-03 01:00:40','omar'),(46,8,'RESERVATION','2024-06-03 01:00:41','omar'),(47,8,'RETURN','2024-06-03 01:00:44','omar'),(48,8,'DEACTIVATION','2024-06-03 01:00:45','omar'),(49,8,'REACTIVATION','2024-06-03 01:00:46','omar'),(50,10,'ADDITION','2024-06-03 01:00:56','omar'),(51,11,'ADDITION','2024-06-03 02:06:37','omar'),(52,11,'UPDATE','2024-06-03 02:06:46','omar'),(53,11,'DELETION','2024-06-03 02:07:18','omar'),(54,10,'DEACTIVATION','2024-06-03 02:07:25','omar'),(55,10,'REACTIVATION','2024-06-03 02:07:28','omar'),(56,10,'RESERVATION','2024-06-03 02:07:31','omar'),(57,3,'RETURN','2024-06-03 03:36:25','omar'),(58,3,'RESERVATION','2024-06-03 04:03:31','omar'),(59,3,'UPDATE','2024-06-03 04:10:58','omar'),(60,3,'UPDATE','2024-06-03 04:11:07','omar'),(61,3,'UPDATE','2024-06-03 04:23:14','omar'),(62,3,'UPDATE','2024-06-03 04:23:38','omar'),(63,3,'UPDATE','2024-06-03 04:23:40','omar'),(64,3,'UPDATE','2024-06-03 04:23:47','omar'),(65,3,'UPDATE','2024-06-03 04:23:48','omar'),(66,3,'UPDATE','2024-06-03 04:25:01','omar'),(67,12,'ADDITION','2024-06-03 04:25:47','omar'),(68,12,'UPDATE','2024-06-03 04:25:59','omar'),(69,12,'DELETION','2024-06-03 04:26:17','omar'),(70,3,'UPDATE','2024-06-03 04:35:10','omar'),(71,13,'ADDITION','2024-06-03 04:51:14','omar'),(72,13,'UPDATE','2024-06-03 04:51:33','omar'),(73,14,'ADDITION','2024-06-03 05:02:05','omar'),(74,3,'UPDATE','2024-06-03 05:09:39','omar'),(75,3,'UPDATE','2024-06-03 05:17:04','omar'),(76,15,'ADDITION','2024-06-03 05:17:24','omar'),(77,3,'UPDATE','2024-06-03 05:32:28','omar'),(78,3,'UPDATE','2024-06-03 05:32:39','omar'),(79,3,'UPDATE','2024-06-03 05:33:19','omar'),(80,3,'UPDATE','2024-06-03 05:36:06','omar'),(81,3,'UPDATE','2024-06-03 05:36:14','omar'),(82,3,'UPDATE','2024-06-03 05:36:16','omar'),(83,3,'UPDATE','2024-06-03 05:36:16','omar'),(84,3,'UPDATE','2024-06-03 05:36:59','omar'),(85,3,'UPDATE','2024-06-03 05:37:08','omar'),(86,6,'RESERVATION','2024-06-03 05:50:08','omar'),(87,6,'RETURN','2024-06-03 05:50:20','omar'),(88,16,'ADDITION','2024-06-03 05:51:05','omar'),(89,17,'ADDITION','2024-06-03 05:56:12','omar'),(90,3,'RESERVATION','2024-06-03 05:59:37','omar'),(91,5,'DEACTIVATION','2024-06-03 06:00:29','omar'),(92,5,'REACTIVATION','2024-06-03 06:00:32','omar'),(93,5,'DEACTIVATION','2024-06-03 06:08:32','omar'),(94,5,'REACTIVATION','2024-06-03 06:08:36','omar'),(95,3,'RETURN','2024-06-03 06:35:59','omar'),(96,3,'DEACTIVATION','2024-06-03 06:36:05','omar'),(97,3,'REACTIVATION','2024-06-03 06:36:07','omar'),(98,3,'UPDATE','2024-06-03 06:36:42','omar'),(99,3,'UPDATE','2024-06-03 06:39:52','omar'),(100,3,'UPDATE','2024-06-03 06:47:49','omar');
/*!40000 ALTER TABLE `documenthistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documents` (
  `document_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `publication_date` date DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `creation_date` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `creator_id` varchar(255) NOT NULL,
  `modifier_id` int DEFAULT NULL,
  `is_available` tinyint(1) DEFAULT '1',
  `document_type` enum('book','paper','article') NOT NULL,
  `status` varchar(50) DEFAULT 'active',
  `authors` varchar(255) DEFAULT NULL,
  `author_contact_email` varchar(255) DEFAULT NULL,
  `author_contact_address` varchar(255) DEFAULT NULL,
  `author_contact_phone` varchar(255) DEFAULT NULL,
  `publisher_contact_email` varchar(255) DEFAULT NULL,
  `publisher_contact_address` varchar(255) DEFAULT NULL,
  `publisher_contact_phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`document_id`),
  KEY `creator_id` (`creator_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES (3,'M',NULL,'','2024-06-01','2024-06-03','omar',NULL,1,'paper','active','',NULL,NULL,NULL,NULL,NULL,NULL),(5,'EL QUIJOTE DE LA MANCHA','2001-11-03','norma','2001-11-03',NULL,'omar',NULL,1,'book','active','Miguel de cervantes',NULL,NULL,NULL,NULL,NULL,NULL),(6,'eqwe','2001-04-11','','2024-06-02',NULL,'omar',NULL,1,'paper','active','',NULL,NULL,NULL,NULL,NULL,NULL),(7,'weqeqw','2008-06-07','','2024-06-02',NULL,'omar',NULL,1,'paper','active','',NULL,NULL,NULL,NULL,NULL,NULL),(8,'e',NULL,'','2024-06-02',NULL,'omar',NULL,1,'paper','active','ewe',NULL,NULL,NULL,NULL,NULL,NULL),(10,'test',NULL,'','2024-06-02',NULL,'omar',NULL,1,'paper','reserved','','yecop123a@gmail.com','calle7 # 3-85','','','',''),(13,'4',NULL,'3342','2024-06-02','2024-06-02','omar',NULL,1,'paper','active','',NULL,NULL,NULL,NULL,NULL,NULL),(14,'wqeeqwe',NULL,'','2024-06-03',NULL,'omar',NULL,1,'article','active','',NULL,NULL,NULL,NULL,NULL,NULL),(15,'Test2','2007-08-09','Norma','2024-06-03',NULL,'omar',NULL,1,'article','active','Olivia',NULL,NULL,NULL,NULL,NULL,NULL),(16,'2qeawsdw','2003-11-04','','2024-06-03',NULL,'omar',NULL,1,'book','active','',NULL,NULL,NULL,NULL,NULL,NULL),(17,'3424',NULL,'','2024-06-03',NULL,'omar',NULL,1,'book','active','',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `papers`
--

DROP TABLE IF EXISTS `papers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `papers` (
  `document_id` int NOT NULL,
  `conference_name` varchar(255) NOT NULL,
  PRIMARY KEY (`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `papers`
--

LOCK TABLES `papers` WRITE;
/*!40000 ALTER TABLE `papers` DISABLE KEYS */;
INSERT INTO `papers` VALUES (3,''),(6,''),(7,''),(8,''),(10,''),(13,'');
/*!40000 ALTER TABLE `papers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publishers`
--

DROP TABLE IF EXISTS `publishers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publishers` (
  `publisher_id` int NOT NULL AUTO_INCREMENT,
  `document_id` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`publisher_id`),
  KEY `document_id` (`document_id`),
  CONSTRAINT `publishers_ibfk_1` FOREIGN KEY (`document_id`) REFERENCES `documents` (`document_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publishers`
--

LOCK TABLES `publishers` WRITE;
/*!40000 ALTER TABLE `publishers` DISABLE KEYS */;
INSERT INTO `publishers` VALUES (1,6,'','','',''),(2,7,'','','','');
/*!40000 ALTER TABLE `publishers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservations` (
  `reservation_id` int NOT NULL AUTO_INCREMENT,
  `document_id` int NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `reservation_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `return_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`reservation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservations`
--

LOCK TABLES `reservations` WRITE;
/*!40000 ALTER TABLE `reservations` DISABLE KEYS */;
INSERT INTO `reservations` VALUES (1,4,'omar','2024-06-02 05:18:06','2024-06-02 05:18:40'),(2,4,'omar','2024-06-02 05:18:39','2024-06-02 05:18:40'),(3,4,'omar','2024-06-02 05:18:45',NULL),(4,5,'omar','2024-06-02 05:24:22','2024-06-02 05:39:32'),(5,5,'omar','2024-06-02 05:34:00','2024-06-02 05:39:32'),(6,3,'omar','2024-06-02 05:40:31','2024-06-03 03:36:25'),(7,5,'omar','2024-06-02 06:00:59','2024-06-02 06:01:01'),(8,5,'omar','2024-06-02 06:01:03','2024-06-02 06:01:08'),(9,8,'omar','2024-06-03 01:00:37','2024-06-03 01:00:40'),(10,8,'omar','2024-06-03 01:00:41','2024-06-03 01:00:44'),(11,10,'omar','2024-06-03 02:07:31',NULL),(12,3,'omar','2024-06-03 04:03:31','2024-06-03 06:35:59'),(13,6,'omar','2024-06-03 05:50:08','2024-06-03 05:50:20'),(14,3,'omar','2024-06-03 05:59:37','2024-06-03 06:35:59');
/*!40000 ALTER TABLE `reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'omar','1234','test@test.com','cll 7 # 3-85','3228793562'),(2,'test','1223','test2@test.com','cll 7 # 3-85','3789667923');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'documentlibrary'
--

--
-- Dumping routines for database 'documentlibrary'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-03 14:16:10
