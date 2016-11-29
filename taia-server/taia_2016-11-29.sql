# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.6.13)
# Database: taia
# Generation Time: 2016-11-29 16:05:49 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table Items
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Items`;

CREATE TABLE `Items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Items` WRITE;
/*!40000 ALTER TABLE `Items` DISABLE KEYS */;

INSERT INTO `Items` (`id`, `name`, `description`, `image`)
VALUES
	(5,'Flipboard',NULL,NULL),
	(6,'Facebook',NULL,'f.jpg');

/*!40000 ALTER TABLE `Items` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Markets
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Markets`;

CREATE TABLE `Markets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Markets` WRITE;
/*!40000 ALTER TABLE `Markets` DISABLE KEYS */;

INSERT INTO `Markets` (`id`, `name`)
VALUES
	(5,'Apps');

/*!40000 ALTER TABLE `Markets` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Users`;

CREATE TABLE `Users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;

INSERT INTO `Users` (`id`, `name`, `email`)
VALUES
	(5,'Dan',NULL),
	(6,'Anca',NULL),
	(7,'Laci',NULL),
	(8,'Alex',NULL);

/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table UsersToItems
# ------------------------------------------------------------

DROP TABLE IF EXISTS `UsersToItems`;

CREATE TABLE `UsersToItems` (
  `user_id` int(11) NOT NULL,
  `market_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`market_id`,`item_id`),
  KEY `user_id` (`user_id`),
  KEY `market_id` (`market_id`),
  KEY `item_id` (`item_id`),
  CONSTRAINT `u2ifk1` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `u2ifk2` FOREIGN KEY (`market_id`) REFERENCES `Markets` (`id`) ON DELETE CASCADE,
  CONSTRAINT `u2ifk3` FOREIGN KEY (`item_id`) REFERENCES `Items` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `UsersToItems` WRITE;
/*!40000 ALTER TABLE `UsersToItems` DISABLE KEYS */;

INSERT INTO `UsersToItems` (`user_id`, `market_id`, `item_id`)
VALUES
	(5,5,5),
	(5,5,6),
	(6,5,5),
	(6,5,6),
	(7,5,6);

/*!40000 ALTER TABLE `UsersToItems` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table UsersToUsers
# ------------------------------------------------------------

DROP TABLE IF EXISTS `UsersToUsers`;

CREATE TABLE `UsersToUsers` (
  `user_id` int(11) NOT NULL,
  `market_id` int(11) NOT NULL,
  `follower_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`market_id`,`follower_id`),
  KEY `user_id` (`user_id`),
  KEY `market_id` (`market_id`),
  KEY `follower_id` (`follower_id`),
  CONSTRAINT `u2ufk1` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `u2ufk2` FOREIGN KEY (`market_id`) REFERENCES `Markets` (`id`) ON DELETE CASCADE,
  CONSTRAINT `u2ufk3` FOREIGN KEY (`follower_id`) REFERENCES `Users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `UsersToUsers` WRITE;
/*!40000 ALTER TABLE `UsersToUsers` DISABLE KEYS */;

INSERT INTO `UsersToUsers` (`user_id`, `market_id`, `follower_id`)
VALUES
	(5,5,6),
	(5,5,7);

/*!40000 ALTER TABLE `UsersToUsers` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
