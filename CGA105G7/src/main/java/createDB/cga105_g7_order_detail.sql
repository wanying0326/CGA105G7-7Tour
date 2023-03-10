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
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `ROOM_ID` int NOT NULL,
  `ORDER_ID` int NOT NULL,
  `ROOM_AMOUNT` int NOT NULL,
  PRIMARY KEY (`ROOM_ID`,`ORDER_ID`),
  KEY `FK_ORDERID_ODDETAIL` (`ORDER_ID`),
  CONSTRAINT `FK_ORDERID_ODDETAIL` FOREIGN KEY (`ORDER_ID`) REFERENCES `room_order` (`ORDER_ID`),
  CONSTRAINT `FK_ROOMID_ODDETAIL` FOREIGN KEY (`ROOM_ID`) REFERENCES `room` (`ROOM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (1,1,1),(1,5,1),(1,6,3),(1,8,1),(1,15,1),(1,32,1),(1,51,1),(1,57,1),(1,60,1),(1,61,3),(1,64,1),(1,65,1),(2,3,1),(2,5,1),(2,7,1),(2,10,1),(2,11,1),(2,12,2),(2,52,1),(3,4,1),(3,9,1),(3,11,1),(3,15,1),(4,13,1),(4,14,1),(5,38,1),(5,53,1),(5,54,1),(6,2,1),(6,39,1),(6,59,1),(8,58,1),(9,34,2),(9,42,1),(9,43,2),(9,49,1),(9,50,1),(10,43,1),(10,55,1),(10,56,1),(12,37,1),(13,35,2),(13,62,2),(14,33,2),(14,36,1),(14,40,2),(22,41,1);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-14 16:27:38
