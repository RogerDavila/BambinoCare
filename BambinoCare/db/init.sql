CREATE DATABASE bambino
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

use bambino;

-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: bambino
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bambino`
--

DROP TABLE IF EXISTS `bambino`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bambino` (
  `bambino_id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(11) NOT NULL,
  `comments` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `grade` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) NOT NULL,
  `medical_situation` varchar(255) NOT NULL,
  `client_id` int(11) NOT NULL,
  PRIMARY KEY (`bambino_id`),
  KEY `FK8xkp2ykp3yvpw8i2f12q14dxd` (`client_id`),
  CONSTRAINT `FK8xkp2ykp3yvpw8i2f12q14dxd` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bambino`
--

LOCK TABLES `bambino` WRITE;
/*!40000 ALTER TABLE `bambino` DISABLE KEYS */;
/*!40000 ALTER TABLE `bambino` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking` (
  `booking_id` int(11) NOT NULL AUTO_INCREMENT,
  `cost` double NOT NULL,
  `date` date NOT NULL,
  `duration` double NOT NULL,
  `finish_datetime` datetime NOT NULL,
  `hour` varchar(255) NOT NULL,
  `start_datetime` datetime NOT NULL,
  `booking_status_id` int(11) NOT NULL,
  `booking_type_id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `event_id` int(11) DEFAULT NULL,
  `nanny_id` int(11) DEFAULT NULL,
  `payment_type_id` int(11) NOT NULL,
  `tutory_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`booking_id`),
  KEY `FKm3d0q9s1hos02eamx9wrsupaq` (`booking_status_id`),
  KEY `FK51vos7pwhsvoyt2jd6s783c0e` (`booking_type_id`),
  KEY `FKhs7eej4m2orrmr5cfbcrqs8yw` (`client_id`),
  KEY `FKiy2tdi4vrw2mljj6rqwmd698q` (`event_id`),
  KEY `FK1c7blpffuitthxh9hbsoxb0ul` (`nanny_id`),
  KEY `FKtrw278g8dubwmktpulfn6l7bd` (`payment_type_id`),
  KEY `FKf3nk7mdsxqkb95ivuumkfpokf` (`tutory_id`),
  CONSTRAINT `FK1c7blpffuitthxh9hbsoxb0ul` FOREIGN KEY (`nanny_id`) REFERENCES `nanny` (`nanny_id`),
  CONSTRAINT `FK51vos7pwhsvoyt2jd6s783c0e` FOREIGN KEY (`booking_type_id`) REFERENCES `booking_type` (`booking_type_id`),
  CONSTRAINT `FKf3nk7mdsxqkb95ivuumkfpokf` FOREIGN KEY (`tutory_id`) REFERENCES `tutory` (`tutory_id`),
  CONSTRAINT `FKhs7eej4m2orrmr5cfbcrqs8yw` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`),
  CONSTRAINT `FKiy2tdi4vrw2mljj6rqwmd698q` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`),
  CONSTRAINT `FKm3d0q9s1hos02eamx9wrsupaq` FOREIGN KEY (`booking_status_id`) REFERENCES `booking_status` (`booking_status_id`),
  CONSTRAINT `FKtrw278g8dubwmktpulfn6l7bd` FOREIGN KEY (`payment_type_id`) REFERENCES `payment_type` (`payment_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_bambino`
--

DROP TABLE IF EXISTS `booking_bambino`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking_bambino` (
  `booking_booking_id` int(11) NOT NULL,
  `bambino_bambino_id` int(11) NOT NULL,
  PRIMARY KEY (`booking_booking_id`,`bambino_bambino_id`),
  KEY `FKy40pohffnxj7dvo9jykct62b` (`bambino_bambino_id`),
  CONSTRAINT `FK7jonmktt7vdxviok4r9c0nirh` FOREIGN KEY (`booking_booking_id`) REFERENCES `booking` (`booking_id`),
  CONSTRAINT `FKy40pohffnxj7dvo9jykct62b` FOREIGN KEY (`bambino_bambino_id`) REFERENCES `bambino` (`bambino_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_bambino`
--

LOCK TABLES `booking_bambino` WRITE;
/*!40000 ALTER TABLE `booking_bambino` DISABLE KEYS */;
/*!40000 ALTER TABLE `booking_bambino` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_status`
--

DROP TABLE IF EXISTS `booking_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking_status` (
  `booking_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_status_desc` varchar(255) NOT NULL,
  PRIMARY KEY (`booking_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_status`
--

LOCK TABLES `booking_status` WRITE;
/*!40000 ALTER TABLE `booking_status` DISABLE KEYS */;
INSERT INTO `booking_status` VALUES (1,'Pendiente Pago'),(2,'Abierta'),(3,'Agendada'),(4,'Rechazada'),(5,'Cancelada');
/*!40000 ALTER TABLE `booking_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_type`
--

DROP TABLE IF EXISTS `booking_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking_type` (
  `booking_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_type_desc` varchar(255) NOT NULL,
  PRIMARY KEY (`booking_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_type`
--

LOCK TABLES `booking_type` WRITE;
/*!40000 ALTER TABLE `booking_type` DISABLE KEYS */;
INSERT INTO `booking_type` VALUES (1,'Bambino Care'),(2,'Bambino Tutoring'),(3,'Bambino Events'),(4,'Bambino ASAP');
/*!40000 ALTER TABLE `booking_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `city_id` int(11) NOT NULL AUTO_INCREMENT,
  `city_desc` varchar(255) NOT NULL,
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'Monterrey'),(2,'San Pedro'),(3,'Santa Catarina');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `client_id` int(11) NOT NULL AUTO_INCREMENT,
  `job` varchar(255) NOT NULL,
  `neighborhood` varchar(255) NOT NULL,
  `street` varchar(255) NOT NULL,
  `city_id` int(11) NOT NULL,
  `state_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`client_id`),
  KEY `FKkb5fr3nx3qucrbme4wewcmwbf` (`city_id`),
  KEY `FKkfgmfgcino835c6ncixhq76wn` (`state_id`),
  KEY `FKk1fi84oi1yyuswr40h38kjy1s` (`user_id`),
  CONSTRAINT `FKk1fi84oi1yyuswr40h38kjy1s` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKkb5fr3nx3qucrbme4wewcmwbf` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`),
  CONSTRAINT `FKkfgmfgcino835c6ncixhq76wn` FOREIGN KEY (`state_id`) REFERENCES `state` (`state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cost`
--

DROP TABLE IF EXISTS `cost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cost` (
  `cost_id` int(11) NOT NULL AUTO_INCREMENT,
  `bambino_quantity` int(11) NOT NULL,
  `cost` double NOT NULL,
  `cost_extra_hour` double DEFAULT NULL,
  `hour_quantity` double NOT NULL,
  `booking_type_id` int(11) NOT NULL,
  PRIMARY KEY (`cost_id`),
  KEY `FKfcbpfyfcetw9wmjy77106aq5h` (`booking_type_id`),
  CONSTRAINT `FKfcbpfyfcetw9wmjy77106aq5h` FOREIGN KEY (`booking_type_id`) REFERENCES `booking_type` (`booking_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cost`
--

LOCK TABLES `cost` WRITE;
/*!40000 ALTER TABLE `cost` DISABLE KEYS */;
INSERT INTO `cost` VALUES (1,1,140,NULL,1,1),(2,2,190,NULL,1,1),(3,3,240,NULL,1,1),(4,1,130,NULL,4,1),(5,2,180,NULL,4,1),(6,3,230,NULL,4,1),(7,1,120,NULL,10,1),(8,2,170,NULL,10,1),(9,3,220,NULL,10,1),(10,1,110,NULL,15,1),(11,2,160,NULL,15,1),(12,3,210,NULL,15,1),(13,1,100,NULL,20,1),(14,2,150,NULL,20,1),(15,3,200,NULL,20,1),(16,1,95,NULL,30,1),(17,2,145,NULL,30,1),(18,3,195,NULL,30,1),(19,1,90,NULL,40,1),(20,2,130,NULL,40,1),(21,3,180,NULL,40,1),(22,1,300,NULL,1,2),(23,1,250,NULL,5,2),(24,1,200,NULL,10,2),(25,10,500,400,4,3),(26,15,625,500,4,3),(27,20,750,600,4,3),(28,25,875,700,4,3),(29,30,1000,800,4,3),(30,35,1125,900,4,3),(31,40,1250,1000,4,3),(32,45,1375,1100,4,3),(33,50,1500,1200,4,3),(34,1,250,NULL,1,4),(35,2,300,NULL,1,4),(36,3,350,NULL,1,4),(37,1,200,NULL,4,4),(38,2,250,NULL,4,4),(39,3,300,NULL,4,4),(40,1,150,NULL,10,4),(41,2,200,NULL,10,4),(42,3,250,NULL,10,4);
/*!40000 ALTER TABLE `cost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emergency_contact`
--

DROP TABLE IF EXISTS `emergency_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emergency_contact` (
  `contact_id` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `neighborhood` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `relationship` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `street` varchar(255) NOT NULL,
  `client_id` int(11) NOT NULL,
  PRIMARY KEY (`contact_id`),
  KEY `FKhe7aaikqxagw6o9jc32wyvpyu` (`client_id`),
  CONSTRAINT `FKhe7aaikqxagw6o9jc32wyvpyu` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emergency_contact`
--

LOCK TABLES `emergency_contact` WRITE;
/*!40000 ALTER TABLE `emergency_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `emergency_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `age` varchar(255) NOT NULL,
  `bambinos_qty` int(11) NOT NULL,
  `city` varchar(255) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `neighborhood` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `street` varchar(255) NOT NULL,
  `event_type_id` int(11) NOT NULL,
  PRIMARY KEY (`event_id`),
  KEY `FKgxoo7ftgbsrwr4i27wb9ylu1` (`event_type_id`),
  CONSTRAINT `FKgxoo7ftgbsrwr4i27wb9ylu1` FOREIGN KEY (`event_type_id`) REFERENCES `event_type` (`event_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_type`
--

DROP TABLE IF EXISTS `event_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_type` (
  `event_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_type_desc` varchar(255) NOT NULL,
  PRIMARY KEY (`event_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_type`
--

LOCK TABLES `event_type` WRITE;
/*!40000 ALTER TABLE `event_type` DISABLE KEYS */;
INSERT INTO `event_type` VALUES (1,'XV'),(2,'Boda'),(3,'Piñata'),(4,'Reunión Familiar'),(5,'Reunión de Negocios');
/*!40000 ALTER TABLE `event_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `laboral_experience`
--

DROP TABLE IF EXISTS `laboral_experience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `laboral_experience` (
  `laboral_experience_id` int(11) NOT NULL AUTO_INCREMENT,
  `activities` varchar(255) NOT NULL,
  `job_title` varchar(255) NOT NULL,
  `organization` varchar(255) NOT NULL,
  `time_worked` varchar(255) NOT NULL,
  `nanny_id` int(11) NOT NULL,
  PRIMARY KEY (`laboral_experience_id`),
  KEY `FKhjgrfmxislnt6d2crw5ak9mie` (`nanny_id`),
  CONSTRAINT `FKhjgrfmxislnt6d2crw5ak9mie` FOREIGN KEY (`nanny_id`) REFERENCES `nanny` (`nanny_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `laboral_experience`
--

LOCK TABLES `laboral_experience` WRITE;
/*!40000 ALTER TABLE `laboral_experience` DISABLE KEYS */;
/*!40000 ALTER TABLE `laboral_experience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nanny`
--

DROP TABLE IF EXISTS `nanny`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nanny` (
  `nanny_id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(11) NOT NULL,
  `bambino_reason` varchar(255) NOT NULL,
  `career` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `curp_file` tinyblob NOT NULL,
  `degree_file` tinyblob,
  `hobbies` varchar(255) NOT NULL,
  `ife_file` tinyblob NOT NULL,
  `neighborhood` varchar(255) NOT NULL,
  `qualities` varchar(255) NOT NULL,
  `school` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `street` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`nanny_id`),
  KEY `FKpyn7q2qp8rsio9nj2ytxhdr2w` (`user_id`),
  CONSTRAINT `FKpyn7q2qp8rsio9nj2ytxhdr2w` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nanny`
--

LOCK TABLES `nanny` WRITE;
/*!40000 ALTER TABLE `nanny` DISABLE KEYS */;
INSERT INTO `nanny` VALUES (1,23,'Me gustan los niños','Licenciatura de Educación Preescolar','San Pedro Garza García','',NULL,'Leer','','Los Sabinos','Trabajo bajo presión','UDEM','Nuevo León','Vasconcelos 314',4);
/*!40000 ALTER TABLE `nanny` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `neighborhood`
--

DROP TABLE IF EXISTS `neighborhood`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `neighborhood` (
  `neighborhood_id` int(11) NOT NULL AUTO_INCREMENT,
  `neighborhood_desc` varchar(255) NOT NULL,
  `city_id` int(11) NOT NULL,
  PRIMARY KEY (`neighborhood_id`),
  KEY `FKfyiurgf3xs46jlgsqym1bnl49` (`city_id`),
  CONSTRAINT `FKfyiurgf3xs46jlgsqym1bnl49` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `neighborhood`
--

LOCK TABLES `neighborhood` WRITE;
/*!40000 ALTER TABLE `neighborhood` DISABLE KEYS */;
INSERT INTO `neighborhood` VALUES (1,'Contry',1),(2,'Valle Alto',1),(3,'San Jerónimo',1),(4,'Cumbres',1);
/*!40000 ALTER TABLE `neighborhood` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parameter`
--

DROP TABLE IF EXISTS `parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parameter` (
  `parameter_id` int(11) NOT NULL AUTO_INCREMENT,
  `parameter_key` varchar(255) NOT NULL,
  `parameter_value` varchar(255) NOT NULL,
  PRIMARY KEY (`parameter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parameter`
--

LOCK TABLES `parameter` WRITE;
/*!40000 ALTER TABLE `parameter` DISABLE KEYS */;
INSERT INTO `parameter` VALUES (1,'Hora Apertura','14:00'),(2,'Hora Cierre',''),(3,'Hora Apertura Fin de Semana',''),(4,'Hora Cierre Fin de Semana','');
/*!40000 ALTER TABLE `parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_type`
--

DROP TABLE IF EXISTS `payment_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_type` (
  `payment_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `payment_type_desc` varchar(255) NOT NULL,
  PRIMARY KEY (`payment_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_type`
--

LOCK TABLES `payment_type` WRITE;
/*!40000 ALTER TABLE `payment_type` DISABLE KEYS */;
INSERT INTO `payment_type` VALUES (1,'Paypal'),(2,'Pago en Oxxo o a cuenta Bancaria'),(3,'Pago en efectivo');
/*!40000 ALTER TABLE `payment_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_reference`
--

DROP TABLE IF EXISTS `personal_reference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_reference` (
  `reference_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) NOT NULL,
  `how_long_known` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `relationship` varchar(255) NOT NULL,
  `nanny_id` int(11) NOT NULL,
  PRIMARY KEY (`reference_id`),
  KEY `FKliinna01xmesks9p52bpcwqsa` (`nanny_id`),
  CONSTRAINT `FKliinna01xmesks9p52bpcwqsa` FOREIGN KEY (`nanny_id`) REFERENCES `nanny` (`nanny_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_reference`
--

LOCK TABLES `personal_reference` WRITE;
/*!40000 ALTER TABLE `personal_reference` DISABLE KEYS */;
/*!40000 ALTER TABLE `personal_reference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_desc` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Administrador'),(2,'Nanny'),(3,'Cliente');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `state` (
  `state_id` int(11) NOT NULL AUTO_INCREMENT,
  `state_desc` varchar(255) NOT NULL,
  PRIMARY KEY (`state_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `state`
--

LOCK TABLES `state` WRITE;
/*!40000 ALTER TABLE `state` DISABLE KEYS */;
INSERT INTO `state` VALUES (1,'Nuevo León');
/*!40000 ALTER TABLE `state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutory`
--

DROP TABLE IF EXISTS `tutory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tutory` (
  `tutory_id` int(11) NOT NULL AUTO_INCREMENT,
  `comments` varchar(255) DEFAULT NULL,
  `course` varchar(255) NOT NULL,
  `topic` varchar(255) NOT NULL,
  PRIMARY KEY (`tutory_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutory`
--

LOCK TABLES `tutory` WRITE;
/*!40000 ALTER TABLE `tutory` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `password` varchar(200) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`),
  CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'roger.davila@stech.com','','Roger Ivan','Dávila Reyna','$2a$10$4/gq4pJ45ymHDb43HNDuU.AC8RgiE.6gQZPzMmXbMIOTwR9Shq5WC','8120658867',1),(2,'david.cruz@stech.com','','David Fernando','Cruz Florencia','$2a$10$4/gq4pJ45ymHDb43HNDuU.AC8RgiE.6gQZPzMmXbMIOTwR9Shq5WC','8120658867',1),(3,'rog.davila94@gmail.com','','Roger Ivan','Dávila Reyna','$2a$10$4/gq4pJ45ymHDb43HNDuU.AC8RgiE.6gQZPzMmXbMIOTwR9Shq5WC','8120658867',1),(4,'erika.rodriguez@bambinocare.com','','Erika','Rodriguez','$2a$10$4/gq4pJ45ymHDb43HNDuU.AC8RgiE.6gQZPzMmXbMIOTwR9Shq5WC','8120658867',2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification_token`
--

DROP TABLE IF EXISTS `verification_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `verification_token` (
  `token_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expiry_date` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`token_id`),
  KEY `FKrdn0mss276m9jdobfhhn2qogw` (`user_id`),
  CONSTRAINT `FKrdn0mss276m9jdobfhhn2qogw` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_token`
--

LOCK TABLES `verification_token` WRITE;
/*!40000 ALTER TABLE `verification_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `verification_token` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-01 23:30:50
