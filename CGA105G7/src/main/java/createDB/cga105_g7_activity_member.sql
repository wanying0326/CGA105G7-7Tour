-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: cga105_g7
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `activity_member`
--

DROP TABLE IF EXISTS `activity_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity_member` (
  `USER_ID` int NOT NULL,
  `ACTIVITY_ID` int NOT NULL,
  `ACTIVITY_NOTICE` varchar(100) DEFAULT NULL,
  `EVALUATION_CONTENT` varchar(300) DEFAULT NULL,
  `EVALUATION_SCORE` int DEFAULT NULL,
  `EVALUATION_TIME` datetime NOT NULL,
  `MEMBER_STATUS` int DEFAULT NULL,
  PRIMARY KEY (`USER_ID`,`ACTIVITY_ID`),
  KEY `FK_ACTMEM_ACTID` (`ACTIVITY_ID`),
  CONSTRAINT `FK_ACTMEM_ACTID` FOREIGN KEY (`ACTIVITY_ID`) REFERENCES `activity` (`ACTIVITY_ID`),
  CONSTRAINT `FK_ACTMEM_USERID` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_member`
--

LOCK TABLES `activity_member` WRITE;
/*!40000 ALTER TABLE `activity_member` DISABLE KEYS */;
INSERT INTO `activity_member` VALUES (1,1,NULL,NULL,1,'2023-02-12 14:24:39',1),(2,2,'活動開始','這揪團活動尚可',4,'2022-01-10 00:00:00',0),(3,3,'活動開始','這揪團活動普通!!',3,'2022-01-15 00:00:00',0),(4,4,'活動開始','這揪團活動有點無聊!!',2,'2022-01-20 00:00:00',0),(5,5,'活動開始','這揪團活動很無聊!!',1,'2022-01-25 00:00:00',0);
/*!40000 ALTER TABLE `activity_member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-14 16:27:43