-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: lerntool
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `admin` varchar(20) NOT NULL,
  `anzeigename` varchar(40) DEFAULT NULL,
  `passwort` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`admin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `antworten`
--

DROP TABLE IF EXISTS `antworten`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `antworten` (
  `antwort_id` int NOT NULL,
  `antwortText` varchar(255) DEFAULT NULL,
  `fragen_id` int NOT NULL,
  `istRichtig` tinyint NOT NULL,
  PRIMARY KEY (`antwort_id`),
  KEY `fragen_id_idx` (`fragen_id`),
  CONSTRAINT `fragen_id` FOREIGN KEY (`fragen_id`) REFERENCES `fragen` (`frage_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `antworten`
--

LOCK TABLES `antworten` WRITE;
/*!40000 ALTER TABLE `antworten` DISABLE KEYS */;
INSERT INTO `antworten` VALUES (1,'Integer',1,0),(2,'String',1,0),(3,'Boolean',1,1),(4,'Float',1,0),(5,'Integer',2,0),(6,'String',2,0),(7,'boolean',2,1),(8,'float',2,1),(9,'char',2,1);
/*!40000 ALTER TABLE `antworten` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `benutzer`
--

DROP TABLE IF EXISTS `benutzer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `benutzer` (
  `uid` varchar(20) NOT NULL,
  `anzeigename` varchar(40) DEFAULT NULL,
  `passwort` varchar(255) DEFAULT NULL,
  `gruppe` varchar(20) DEFAULT NULL,
  `type` enum('user','admin') NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `gruppe_idx` (`gruppe`),
  CONSTRAINT `gruppe` FOREIGN KEY (`gruppe`) REFERENCES `gruppe` (`gruppeNr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `benutzer`
--

LOCK TABLES `benutzer` WRITE;
/*!40000 ALTER TABLE `benutzer` DISABLE KEYS */;
INSERT INTO `benutzer` VALUES ('admin','Sebastian Adrian','$2a$10$NSokH8WUke86qSUXiX8Hee5.proUFeJenMJopzTa6bCzCT.znDO/G','2502','admin'),('basti','Sebastian Adrian','$2a$10$NSokH8WUke86qSUXiX8Hee5.proUFeJenMJopzTa6bCzCT.znDO/G','2502','user');
/*!40000 ALTER TABLE `benutzer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `benutzer_fragen`
--

DROP TABLE IF EXISTS `benutzer_fragen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `benutzer_fragen` (
  `benutzerfrage_id` int NOT NULL AUTO_INCREMENT,
  `richtig` int DEFAULT NULL,
  `falsch` int DEFAULT NULL,
  `abgefragt` int DEFAULT NULL,
  `benutzerfrage_uid` varchar(20) DEFAULT NULL,
  `benutzerfrage_frage_id` int DEFAULT NULL,
  PRIMARY KEY (`benutzerfrage_id`),
  KEY `uid_idx` (`benutzerfrage_uid`),
  KEY `frage_id_idx` (`benutzerfrage_frage_id`),
  CONSTRAINT `benutzerfrage_frage_id` FOREIGN KEY (`benutzerfrage_frage_id`) REFERENCES `fragen` (`frage_id`),
  CONSTRAINT `benutzerfrage_uid` FOREIGN KEY (`benutzerfrage_uid`) REFERENCES `benutzer` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `benutzer_fragen`
--

LOCK TABLES `benutzer_fragen` WRITE;
/*!40000 ALTER TABLE `benutzer_fragen` DISABLE KEYS */;
/*!40000 ALTER TABLE `benutzer_fragen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `benutzer_themen`
--

DROP TABLE IF EXISTS `benutzer_themen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `benutzer_themen` (
  `benutzerthemen_gruppe` varchar(20) NOT NULL,
  `benutzerthemen_thema_id` int NOT NULL,
  PRIMARY KEY (`benutzerthemen_gruppe`,`benutzerthemen_thema_id`),
  KEY `benutzerfaecher_thema_id_idx` (`benutzerthemen_thema_id`),
  CONSTRAINT `benutzerfaecher_gruppe` FOREIGN KEY (`benutzerthemen_gruppe`) REFERENCES `gruppe` (`gruppeNr`),
  CONSTRAINT `benutzerfaecher_thema_id` FOREIGN KEY (`benutzerthemen_thema_id`) REFERENCES `themen` (`thema_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `benutzer_themen`
--

LOCK TABLES `benutzer_themen` WRITE;
/*!40000 ALTER TABLE `benutzer_themen` DISABLE KEYS */;
INSERT INTO `benutzer_themen` VALUES ('2502',1),('2502',2);
/*!40000 ALTER TABLE `benutzer_themen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faecher`
--

DROP TABLE IF EXISTS `faecher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faecher` (
  `fach_id` int NOT NULL,
  `anzeigename` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`fach_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faecher`
--

LOCK TABLES `faecher` WRITE;
/*!40000 ALTER TABLE `faecher` DISABLE KEYS */;
INSERT INTO `faecher` VALUES (1,'JAVA');
/*!40000 ALTER TABLE `faecher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fragen`
--

DROP TABLE IF EXISTS `fragen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fragen` (
  `frage_id` int NOT NULL,
  `fragenText` varchar(255) DEFAULT NULL,
  `fragenTyp` enum('MultipleChoiceSelect','MultipleChoiceCheck','SingleChoice','TextEingabe') DEFAULT NULL,
  `thema_id` int NOT NULL,
  PRIMARY KEY (`frage_id`),
  KEY `fk_fragen_faecher_idx` (`thema_id`),
  CONSTRAINT `fk_fragen_faecher` FOREIGN KEY (`thema_id`) REFERENCES `themen` (`thema_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fragen`
--

LOCK TABLES `fragen` WRITE;
/*!40000 ALTER TABLE `fragen` DISABLE KEYS */;
INSERT INTO `fragen` VALUES (1,'Welcher Datentyp wird für das Speichern von Wahrheitswerten genutzt?','SingleChoice',1),(2,'Bei welchen der folgenden Datentypen handelt es sich um Primitive Datentypen?','MultipleChoiceCheck',1);
/*!40000 ALTER TABLE `fragen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gruppe`
--

DROP TABLE IF EXISTS `gruppe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gruppe` (
  `gruppeNr` varchar(20) NOT NULL,
  `ausbildungName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`gruppeNr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gruppe`
--

LOCK TABLES `gruppe` WRITE;
/*!40000 ALTER TABLE `gruppe` DISABLE KEYS */;
INSERT INTO `gruppe` VALUES ('2502','Staatl. anerkannter Wirtschaftsinformatiker');
/*!40000 ALTER TABLE `gruppe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `themen`
--

DROP TABLE IF EXISTS `themen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `themen` (
  `thema_id` int NOT NULL,
  `anzeigename` varchar(80) DEFAULT NULL,
  `fach_id` int NOT NULL,
  PRIMARY KEY (`thema_id`),
  KEY `fach_id_idx` (`fach_id`),
  CONSTRAINT `fach_id` FOREIGN KEY (`fach_id`) REFERENCES `faecher` (`fach_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `themen`
--

LOCK TABLES `themen` WRITE;
/*!40000 ALTER TABLE `themen` DISABLE KEYS */;
INSERT INTO `themen` VALUES (1,'Variablen',1),(2,'Methoden',1),(3,'Klassen',1);
/*!40000 ALTER TABLE `themen` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-21 22:14:26
