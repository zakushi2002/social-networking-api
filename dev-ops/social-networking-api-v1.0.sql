-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: social_networking_api
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
INSERT INTO `databasechangelog` VALUES ('1695400755902-1','TOAN (generated)','liquibase/db.changelog-1.0.0.xml','2023-09-24 03:26:46',1,'EXECUTED','8:5fd7feab168b71b71a3e5f51d16264e9','createTable tableName=db_social_networking_account','',NULL,'4.19.0',NULL,NULL,'5500806783'),('1695400755902-2','TOAN (generated)','liquibase/db.changelog-1.0.0.xml','2023-09-24 03:26:46',2,'EXECUTED','8:e1d3a9e1d4f909a9a69be78249ebc468','createTable tableName=db_social_networking_group','',NULL,'4.19.0',NULL,NULL,'5500806783'),('1695400755902-3','TOAN (generated)','liquibase/db.changelog-1.0.0.xml','2023-09-24 03:26:46',3,'EXECUTED','8:37a46e1dc954e184953856fdf4551768','createTable tableName=db_social_networking_permission','',NULL,'4.19.0',NULL,NULL,'5500806783'),('1695400755902-4','TOAN (generated)','liquibase/db.changelog-1.0.0.xml','2023-09-24 03:26:46',4,'EXECUTED','8:33261906f4732f24abed8ceffc70730e','createTable tableName=db_social_networking_permission_group','',NULL,'4.19.0',NULL,NULL,'5500806783'),('1688988515879-13','Authentication','liquibase/db.changelog-1.0.0.xml','2023-09-24 03:26:46',5,'EXECUTED','8:89c4987f2cb2acb9a4ff0f89c0ff679b','createTable tableName=oauth_access_token','',NULL,'4.19.0',NULL,NULL,'5500806783'),('1688988515879-14','Authentication','liquibase/db.changelog-1.0.0.xml','2023-09-24 03:26:46',6,'EXECUTED','8:ee69a51b1e3f5b4eedc461d29e275810','createTable tableName=oauth_approvals','',NULL,'4.19.0',NULL,NULL,'5500806783'),('1688988515879-15','Authentication','liquibase/db.changelog-1.0.0.xml','2023-09-24 03:26:47',7,'EXECUTED','8:26854011a8686d1df658a39ac61b38e1','createTable tableName=oauth_client_details','',NULL,'4.19.0',NULL,NULL,'5500806783'),('1688988515879-16','Authentication','liquibase/db.changelog-1.0.0.xml','2023-09-24 03:26:47',8,'EXECUTED','8:d4999b070522397eb65cf863a7bc1a04','createTable tableName=oauth_client_token','',NULL,'4.19.0',NULL,NULL,'5500806783'),('1688988515879-17','Authentication','liquibase/db.changelog-1.0.0.xml','2023-09-24 03:26:47',9,'EXECUTED','8:7061cbda9d6c7df17ad07bd48009d9af','createTable tableName=oauth_code','',NULL,'4.19.0',NULL,NULL,'5500806783'),('1688988515879-18','Authentication','liquibase/db.changelog-1.0.0.xml','2023-09-24 03:26:47',10,'EXECUTED','8:732dc4d00a0b816328a2b0b93ba3e021','createTable tableName=oauth_refresh_token','',NULL,'4.19.0',NULL,NULL,'5500806783'),('1695400755902-5','TOAN (generated)','liquibase/db.changelog-1.0.0.xml','2023-09-24 03:26:47',11,'EXECUTED','8:066fe6973aaf8dfc23134535a867e9be','addUniqueConstraint constraintName=UC_DB_SOCIAL_NETWORKING_GROUPNAME_COL, tableName=db_social_networking_group','',NULL,'4.19.0',NULL,NULL,'5500806783'),('1695400755902-6','TOAN (generated)','liquibase/db.changelog-1.0.0.xml','2023-09-24 03:26:47',12,'EXECUTED','8:73af11ed9bd195f8a5d32d78e0fe82bf','addUniqueConstraint constraintName=UC_DB_SOCIAL_NETWORKING_PERMISSIONNAME_COL, tableName=db_social_networking_permission','',NULL,'4.19.0',NULL,NULL,'5500806783'),('1695400755902-7','TOAN (generated)','liquibase/db.changelog-1.0.0.xml','2023-09-24 03:26:47',13,'EXECUTED','8:99b18917b6571c0144bf6a1a9c60f0ad','addUniqueConstraint constraintName=UC_DB_SOCIAL_NETWORKING_PERMISSIONPERMISSION_CODE_COL, tableName=db_social_networking_permission','',NULL,'4.19.0',NULL,NULL,'5500806783'),('1695400755902-8','TOAN (generated)','liquibase/db.changelog-1.0.0.xml','2023-09-24 03:26:47',14,'EXECUTED','8:32766ccf9542864cc1d474c578196a94','addForeignKeyConstraint baseTableName=db_social_networking_account, constraintName=FK49bvfua7qd4v7q4p7fi54wj3k, referencedTableName=db_social_networking_group','',NULL,'4.19.0',NULL,NULL,'5500806783'),('1695400755902-9','TOAN (generated)','liquibase/db.changelog-1.0.0.xml','2023-09-24 03:26:47',15,'EXECUTED','8:4988892d28947be60756723c895552a5','addForeignKeyConstraint baseTableName=db_social_networking_permission_group, constraintName=FKkyo29cicik47yrpx6b4647iba, referencedTableName=db_social_networking_group','',NULL,'4.19.0',NULL,NULL,'5500806783'),('1695400755902-10','TOAN (generated)','liquibase/db.changelog-1.0.0.xml','2023-09-24 03:26:47',16,'EXECUTED','8:9cd25edbc190a4d332a86a6290b1e8e4','addForeignKeyConstraint baseTableName=db_social_networking_permission_group, constraintName=FKn1md4o49ejp7r5bo655j8cf8i, referencedTableName=db_social_networking_permission','',NULL,'4.19.0',NULL,NULL,'5500806783');
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,_binary '\0',NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_social_networking_account`
--

DROP TABLE IF EXISTS `db_social_networking_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_social_networking_account` (
  `id` bigint NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `kind` int DEFAULT NULL,
  `group_id` bigint DEFAULT NULL,
  `avatar_path` longtext,
  `is_super_admin` bit(1) DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `attempt_forget_pwd` int DEFAULT NULL,
  `attempt_login` int DEFAULT NULL,
  `reset_pwd_code` varchar(255) DEFAULT NULL,
  `reset_pwd_time` datetime DEFAULT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(255) NOT NULL,
  `modified_date` datetime NOT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK49bvfua7qd4v7q4p7fi54wj3k` (`group_id`),
  CONSTRAINT `FK49bvfua7qd4v7q4p7fi54wj3k` FOREIGN KEY (`group_id`) REFERENCES `db_social_networking_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_social_networking_account`
--

LOCK TABLES `db_social_networking_account` WRITE;
/*!40000 ALTER TABLE `db_social_networking_account` DISABLE KEYS */;
INSERT INTO `db_social_networking_account` VALUES (1,'toannguyenit239@gmail.com','{bcrypt}$2a$10$0tyVOFn.RrH.9VMV0bV7JeFdQVHgIVIdioj97y/xTeung.niV9ZFy','Super Admin',1,1,'https://cdn.minhtuanmobile.com/uploads/blog/tong-hop-hinh-nen-luffy-gear-5-dep-mat-cho-dien-thoai-230814055729.jpg',_binary '',NULL,NULL,NULL,NULL,NULL,'anonymousUser','2023-09-23 20:50:42','toannguyenit239@gmail.com','2023-09-23 20:36:17',1),(7111447404991598592,'admin@gmail.com','{bcrypt}$2a$10$Peg26afO8TlNRIJ1k0HHJutClBIYxTlMmlnHTK2v7siu/qchjxYti','Admin',1,7111447275895115776,'https://i1-giaitri.vnecdn.net/2023/04/18/anh5-4318-1681789890.jpg?w=1020&h=0&q=100&dpr=1&fit=crop&s=xzvyPjsUGZcdIK0OZnD3Dw',_binary '\0',NULL,NULL,NULL,NULL,NULL,'toannguyenit239@gmail.com','2023-09-23 20:33:05','toannguyenit239@gmail.com','2023-09-23 20:33:05',1);
/*!40000 ALTER TABLE `db_social_networking_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_social_networking_group`
--

DROP TABLE IF EXISTS `db_social_networking_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_social_networking_group` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `kind` int DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `is_system_role` bit(1) DEFAULT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(255) NOT NULL,
  `modified_date` datetime NOT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UC_DB_SOCIAL_NETWORKING_GROUPNAME_COL` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_social_networking_group`
--

LOCK TABLES `db_social_networking_group` WRITE;
/*!40000 ALTER TABLE `db_social_networking_group` DISABLE KEYS */;
INSERT INTO `db_social_networking_group` VALUES (1,'ROLE SUPER ADMIN',1,NULL,_binary '','anonymousUser','2023-09-23 20:50:42','toannguyenit239@gmail.com','2023-09-23 20:31:02',1),(7111447275895115776,'ROLE ADMIN',1,'Role for admin',_binary '\0','toannguyenit239@gmail.com','2023-09-23 20:32:35','toannguyenit239@gmail.com','2023-09-23 20:32:35',1);
/*!40000 ALTER TABLE `db_social_networking_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_social_networking_permission`
--

DROP TABLE IF EXISTS `db_social_networking_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_social_networking_permission` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  `name_group` varchar(255) DEFAULT NULL,
  `permission_code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `show_menu` bit(1) DEFAULT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` varchar(255) NOT NULL,
  `modified_date` datetime NOT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UC_DB_SOCIAL_NETWORKING_PERMISSIONNAME_COL` (`name`),
  UNIQUE KEY `UC_DB_SOCIAL_NETWORKING_PERMISSIONPERMISSION_CODE_COL` (`permission_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_social_networking_permission`
--

LOCK TABLES `db_social_networking_permission` WRITE;
/*!40000 ALTER TABLE `db_social_networking_permission` DISABLE KEYS */;
INSERT INTO `db_social_networking_permission` VALUES (1,'Create Permission','/v1/permission/create','Permission','PER_C','Create a new permission',_binary '','Admin','2023-09-23 20:50:42','Admin','2023-09-23 20:50:42',1),(2,'Update Permission','/v1/permission/list','Permission','PER_L','List permission',_binary '','Admin','2023-09-23 20:50:42','Admin','2023-09-23 20:50:42',1),(3,'List Permission','/v1/permission/delete','Permission','PER_D','Delete a permission',_binary '','Admin','2023-09-23 20:50:42','Admin','2023-09-23 20:50:42',1),(4,'Create Group','/v1/group/create','Group','GR_C','Create a new group',_binary '','Admin','2023-09-23 20:50:42','Admin','2023-09-23 20:50:42',1),(5,'Update Group','/v1/group/update','Group','GR_U','Update group',_binary '','Admin','2023-09-23 20:50:42','Admin','2023-09-23 20:50:42',1),(6,'Get Group','/v1/group/get','Group','GR_V','Get a group',_binary '','Admin','2023-09-23 20:50:42','Admin','2023-09-23 20:50:42',1),(7,'List Group','/v1/group/list','Group','GR_L','List group',_binary '','Admin','2023-09-23 20:50:42','Admin','2023-09-23 20:50:42',1),(8,'Create Admin','/v1/account/create-admin','Account','ACC_C_AD','Create a new admin',_binary '','Admin','2023-09-23 20:50:42','Admin','2023-09-23 20:50:42',1),(7111364086962966528,'Update Admin','/v1/account/update-admin','Account','ACC_U_AD','Update Admin (require id)',_binary '','toannguyenit239@gmail.com','2023-09-23 15:02:01','toannguyenit239@gmail.com','2023-09-23 15:02:01',1),(7111405972574556160,'List Account','/v1/account/list','Account','ACC_L','List Account',_binary '','toannguyenit239@gmail.com','2023-09-23 17:48:27','toannguyenit239@gmail.com','2023-09-23 17:48:27',1),(7111433131066249216,'Delete Admin','/v1/account/delete-admin','Account','ACC_D_AD','Delete a Admin Account',_binary '','toannguyenit239@gmail.com','2023-09-23 19:36:22','toannguyenit239@gmail.com','2023-09-23 19:36:22',1),(7111433500672512000,'Get Account','/v1/account/get','Account','ACC_V','Get a account',_binary '','toannguyenit239@gmail.com','2023-09-23 19:37:50','toannguyenit239@gmail.com','2023-09-23 19:37:50',1);
/*!40000 ALTER TABLE `db_social_networking_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_social_networking_permission_group`
--

DROP TABLE IF EXISTS `db_social_networking_permission_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_social_networking_permission_group` (
  `group_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  KEY `FKkyo29cicik47yrpx6b4647iba` (`group_id`),
  KEY `FKn1md4o49ejp7r5bo655j8cf8i` (`permission_id`),
  CONSTRAINT `FKkyo29cicik47yrpx6b4647iba` FOREIGN KEY (`group_id`) REFERENCES `db_social_networking_group` (`id`),
  CONSTRAINT `FKn1md4o49ejp7r5bo655j8cf8i` FOREIGN KEY (`permission_id`) REFERENCES `db_social_networking_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_social_networking_permission_group`
--

LOCK TABLES `db_social_networking_permission_group` WRITE;
/*!40000 ALTER TABLE `db_social_networking_permission_group` DISABLE KEYS */;
INSERT INTO `db_social_networking_permission_group` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,7111364086962966528),(1,7111405972574556160),(1,7111433131066249216),(1,7111433500672512000),(7111447275895115776,1),(7111447275895115776,2),(7111447275895115776,3),(7111447275895115776,6),(7111447275895115776,7),(7111447275895115776,8),(7111447275895115776,7111364086962966528),(7111447275895115776,7111405972574556160),(7111447275895115776,7111433500672512000);
/*!40000 ALTER TABLE `db_social_networking_permission_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_access_token`
--

DROP TABLE IF EXISTS `oauth_access_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` longblob,
  `authentication_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `authentication` longblob,
  `refresh_token` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_access_token`
--

LOCK TABLES `oauth_access_token` WRITE;
/*!40000 ALTER TABLE `oauth_access_token` DISABLE KEYS */;
INSERT INTO `oauth_access_token` VALUES ('2d3e59668d0b0581195aadd22696f3f7',_binary '¨\Ì\0sr\0Corg.springframework.security.oauth2.common.DefaultOAuth2AccessToken≤û6$˙\Œ\0L\0additionalInformationt\0Ljava/util/Map;L\0\nexpirationt\0Ljava/util/Date;L\0refreshTokent\0?Lorg/springframework/security/oauth2/common/OAuth2RefreshToken;L\0scopet\0Ljava/util/Set;L\0	tokenTypet\0Ljava/lang/String;L\0valueq\0~\0xpsr\0java.util.LinkedHashMap4¿N\\l¿˚\0Z\0accessOrderxr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0	user_kindsr\0java.lang.Integer‚†§˜Åá8\0I\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0t\0user_idsr\0java.lang.Long;ã\‰êÃè#\ﬂ\0J\0valuexq\0~\0\0\0\0\0\0\0\0t\0\ngrant_typet\0passwordt\0additional_infot\0@eJwzrDGssbEDEiX5iXl56aWVqXmZJUbGlg7puYmZOXrJ+bk1ukDJotJUAEmhD44=t\0jtit\0$8fd06a49-ec87-4bd2-a379-e541fa6a4251x\0sr\0java.util.DatehjÅKYt\0\0xpw\0\0ã^@k)xsr\0Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/\ﬂGcù\–…∑\0L\0\nexpirationq\0~\0xr\0Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokens\·\ncT\‘^\0L\0valueq\0~\0xpteyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2tpbmQiOjEsInVzZXJfaWQiOjEsImdyYW50X3R5cGUiOiJwYXNzd29yZCIsImFkZGl0aW9uYWxfaW5mbyI6ImVKd3pyREdzc2JFREVpWDVpWGw1NmFXVnFYbVpKVWJHbGc3cHVZbVpPWHJKK2JrMXVrREpvdEpVQUVtaEQ0ND0iLCJ1c2VyX25hbWUiOiJ0b2Fubmd1eWVuaXQyMzlAZ21haWwuY29tIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6IjhmZDA2YTQ5LWVjODctNGJkMi1hMzc5LWU1NDFmYTZhNDI1MSIsImV4cCI6MTY5ODA5MzM2MSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BQ0NfVV9BRCIsIlJPTEVfUEVSX0wiLCJST0xFX0FDQ19WIiwiUk9MRV9HUl9DIiwiUk9MRV9HUl9VIiwiUk9MRV9HUl9WIiwiUk9MRV9BQ0NfRF9BRCIsIlJPTEVfUEVSX0MiLCJST0xFX1BFUl9EIiwiUk9MRV9BQ0NfQ19BRCIsIlJPTEVfR1JfTCIsIlJPTEVfQUNDX0wiXSwianRpIjoiYzJjNjYxZmYtNGFjNS00YTNmLWEzOTEtODY0ZmIzNmJkNGMyIiwiY2xpZW50X2lkIjoiZmFtaWx5X2NpcmNsZV9jbGllbnQifQ.xd7eKQmS6oUiGIuWzKSdMmY45tPKoUqOQa__J-KMSzIsq\0~\0w\0\0ã^@k(xsr\0%java.util.Collections$UnmodifiableSetÄí—èõÄU\0\0xr\0,java.util.Collections$UnmodifiableCollectionB\0Ä\À^˜\0L\0ct\0Ljava/util/Collection;xpsr\0java.util.LinkedHashSet\ÿl\◊Zï\›*\0\0xr\0java.util.HashSet∫DÖïñ∏∑4\0\0xpw\0\0\0?@\0\0\0\0\0t\0readt\0writext\0bearert\ÀeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2tpbmQiOjEsInVzZXJfaWQiOjEsImdyYW50X3R5cGUiOiJwYXNzd29yZCIsImFkZGl0aW9uYWxfaW5mbyI6ImVKd3pyREdzc2JFREVpWDVpWGw1NmFXVnFYbVpKVWJHbGc3cHVZbVpPWHJKK2JrMXVrREpvdEpVQUVtaEQ0ND0iLCJ1c2VyX25hbWUiOiJ0b2Fubmd1eWVuaXQyMzlAZ21haWwuY29tIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTY5ODA5MzM2MSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BQ0NfVV9BRCIsIlJPTEVfUEVSX0wiLCJST0xFX0FDQ19WIiwiUk9MRV9HUl9DIiwiUk9MRV9HUl9VIiwiUk9MRV9HUl9WIiwiUk9MRV9BQ0NfRF9BRCIsIlJPTEVfUEVSX0MiLCJST0xFX1BFUl9EIiwiUk9MRV9BQ0NfQ19BRCIsIlJPTEVfR1JfTCIsIlJPTEVfQUNDX0wiXSwianRpIjoiOGZkMDZhNDktZWM4Ny00YmQyLWEzNzktZTU0MWZhNmE0MjUxIiwiY2xpZW50X2lkIjoiZmFtaWx5X2NpcmNsZV9jbGllbnQifQ.Qd5KoutZKD7swW1-9jdTX1zu6JVzR_ahB1kYbFz9tYs','ac395f76bbdf5eb53a582f798e654f33','toannguyenit239@gmail.com','family_circle_client',_binary '¨\Ì\0sr\0Aorg.springframework.security.oauth2.provider.OAuth2AuthenticationΩ@bR\0L\0\rstoredRequestt\0<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\0userAuthenticationt\02Lorg/springframework/security/core/Authentication;xr\0Gorg.springframework.security.authentication.AbstractAuthenticationToken”™(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailst\0Ljava/lang/Object;xp\0sr\0&java.util.Collections$UnmodifiableList¸%1µ\Ïé\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0Ä\À^˜\0L\0cq\0~\0xpsr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0\0L\0rolet\0Ljava/lang/String;xpt\0\rROLE_ACC_C_ADsq\0~\0\rt\0\rROLE_ACC_D_ADsq\0~\0\rt\0\nROLE_ACC_Lsq\0~\0\rt\0\rROLE_ACC_U_ADsq\0~\0\rt\0\nROLE_ACC_Vsq\0~\0\rt\0	ROLE_GR_Csq\0~\0\rt\0	ROLE_GR_Lsq\0~\0\rt\0	ROLE_GR_Usq\0~\0\rt\0	ROLE_GR_Vsq\0~\0\rt\0\nROLE_PER_Csq\0~\0\rt\0\nROLE_PER_Dsq\0~\0\rt\0\nROLE_PER_Lxq\0~\0psr\0:org.springframework.security.oauth2.provider.OAuth2Request\0\0\0\0\0\0\0\0Z\0approvedL\0authoritiesq\0~\0L\0\nextensionst\0Ljava/util/Map;L\0redirectUriq\0~\0L\0refresht\0;Lorg/springframework/security/oauth2/provider/TokenRequest;L\0resourceIdst\0Ljava/util/Set;L\0\rresponseTypesq\0~\0*xr\08org.springframework.security.oauth2.provider.BaseRequest6(z>£qiΩ\0L\0clientIdq\0~\0L\0requestParametersq\0~\0(L\0scopeq\0~\0*xpt\0family_circle_clientsr\0%java.util.Collections$UnmodifiableMapÒ•®˛tıB\0L\0mq\0~\0(xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\ngrant_typet\0passwordt\0usernamet\0toannguyenit239@gmail.comxsr\0%java.util.Collections$UnmodifiableSetÄí—èõÄU\0\0xq\0~\0	sr\0java.util.LinkedHashSet\ÿl\◊Zï\›*\0\0xr\0java.util.HashSet∫DÖïñ∏∑4\0\0xpw\0\0\0?@\0\0\0\0\0t\0readt\0writexsq\0~\09w\0\0\0?@\0\0\0\0\0sq\0~\0\rt\0ROLE_CLIENTsq\0~\0\rt\0ROLE_TRUSTED_CLIENTxsq\0~\00?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xppsq\0~\09w\0\0\0?@\0\0\0\0\0\0xsq\0~\09w\0\0\0?@\0\0\0\0\0\0xsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\0\0L\0credentialsq\0~\0L\0	principalq\0~\0xq\0~\0sq\0~\0sq\0~\0\0\0\0w\0\0\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0!q\0~\0#q\0~\0%xq\0~\0Hsr\0java.util.LinkedHashMap4¿N\\l¿˚\0Z\0accessOrderxq\0~\00?@\0\0\0\0\0w\0\0\0\0\0\0q\0~\02q\0~\03q\0~\04q\0~\05x\0psr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\0\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiesq\0~\0*L\0passwordq\0~\0L\0usernameq\0~\0xpsq\0~\06sr\0java.util.TreeSet›òPìï\Ìá[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\0\0\0xpw\0\0\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0!q\0~\0#q\0~\0%xpt\0toannguyenit239@gmail.com','e29ed34b3f94364dbd43789a85351cc6');
/*!40000 ALTER TABLE `oauth_access_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_approvals`
--

DROP TABLE IF EXISTS `oauth_approvals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_approvals` (
  `userId` varchar(255) DEFAULT NULL,
  `clientId` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NULL DEFAULT NULL,
  `lastModifiedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_approvals`
--

LOCK TABLES `oauth_approvals` WRITE;
/*!40000 ALTER TABLE `oauth_approvals` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth_approvals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_client_details`
--

DROP TABLE IF EXISTS `oauth_client_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `access_token_validity` int DEFAULT NULL,
  `refresh_token_validity` int DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_client_details`
--

LOCK TABLES `oauth_client_details` WRITE;
/*!40000 ALTER TABLE `oauth_client_details` DISABLE KEYS */;
INSERT INTO `oauth_client_details` VALUES ('family_circle_client',NULL,'{bcrypt}$2a$12$zlKHpSw7.8asgQFCexLLkudLYkd9wQGElMps2yVG5p4vm2f.CTYcy','read,write','password,refresh_token,client_credentials,authorization_code',NULL,'ROLE_CLIENT,ROLE_TRUSTED_CLIENT',2592000,2592000,NULL,NULL);
/*!40000 ALTER TABLE `oauth_client_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_client_token`
--

DROP TABLE IF EXISTS `oauth_client_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_client_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` longblob,
  `authentication_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_client_token`
--

LOCK TABLES `oauth_client_token` WRITE;
/*!40000 ALTER TABLE `oauth_client_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth_client_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_code`
--

DROP TABLE IF EXISTS `oauth_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_code` (
  `code` varchar(255) DEFAULT NULL,
  `authentication` longblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_code`
--

LOCK TABLES `oauth_code` WRITE;
/*!40000 ALTER TABLE `oauth_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_refresh_token`
--

DROP TABLE IF EXISTS `oauth_refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` longblob,
  `authentication` longblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_refresh_token`
--

LOCK TABLES `oauth_refresh_token` WRITE;
/*!40000 ALTER TABLE `oauth_refresh_token` DISABLE KEYS */;
INSERT INTO `oauth_refresh_token` VALUES ('e29ed34b3f94364dbd43789a85351cc6',_binary '¨\Ì\0sr\0Lorg.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken/\ﬂGcù\–…∑\0L\0\nexpirationt\0Ljava/util/Date;xr\0Dorg.springframework.security.oauth2.common.DefaultOAuth2RefreshTokens\·\ncT\‘^\0L\0valuet\0Ljava/lang/String;xpteyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2tpbmQiOjEsInVzZXJfaWQiOjEsImdyYW50X3R5cGUiOiJwYXNzd29yZCIsImFkZGl0aW9uYWxfaW5mbyI6ImVKd3pyREdzc2JFREVpWDVpWGw1NmFXVnFYbVpKVWJHbGc3cHVZbVpPWHJKK2JrMXVrREpvdEpVQUVtaEQ0ND0iLCJ1c2VyX25hbWUiOiJ0b2Fubmd1eWVuaXQyMzlAZ21haWwuY29tIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6IjhmZDA2YTQ5LWVjODctNGJkMi1hMzc5LWU1NDFmYTZhNDI1MSIsImV4cCI6MTY5ODA5MzM2MSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BQ0NfVV9BRCIsIlJPTEVfUEVSX0wiLCJST0xFX0FDQ19WIiwiUk9MRV9HUl9DIiwiUk9MRV9HUl9VIiwiUk9MRV9HUl9WIiwiUk9MRV9BQ0NfRF9BRCIsIlJPTEVfUEVSX0MiLCJST0xFX1BFUl9EIiwiUk9MRV9BQ0NfQ19BRCIsIlJPTEVfR1JfTCIsIlJPTEVfQUNDX0wiXSwianRpIjoiYzJjNjYxZmYtNGFjNS00YTNmLWEzOTEtODY0ZmIzNmJkNGMyIiwiY2xpZW50X2lkIjoiZmFtaWx5X2NpcmNsZV9jbGllbnQifQ.xd7eKQmS6oUiGIuWzKSdMmY45tPKoUqOQa__J-KMSzIsr\0java.util.DatehjÅKYt\0\0xpw\0\0ã^@k(x',_binary '¨\Ì\0sr\0Aorg.springframework.security.oauth2.provider.OAuth2AuthenticationΩ@bR\0L\0\rstoredRequestt\0<Lorg/springframework/security/oauth2/provider/OAuth2Request;L\0userAuthenticationt\02Lorg/springframework/security/core/Authentication;xr\0Gorg.springframework.security.authentication.AbstractAuthenticationToken”™(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailst\0Ljava/lang/Object;xp\0sr\0&java.util.Collections$UnmodifiableList¸%1µ\Ïé\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0Ä\À^˜\0L\0cq\0~\0xpsr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0\0L\0rolet\0Ljava/lang/String;xpt\0\rROLE_ACC_C_ADsq\0~\0\rt\0\rROLE_ACC_D_ADsq\0~\0\rt\0\nROLE_ACC_Lsq\0~\0\rt\0\rROLE_ACC_U_ADsq\0~\0\rt\0\nROLE_ACC_Vsq\0~\0\rt\0	ROLE_GR_Csq\0~\0\rt\0	ROLE_GR_Lsq\0~\0\rt\0	ROLE_GR_Usq\0~\0\rt\0	ROLE_GR_Vsq\0~\0\rt\0\nROLE_PER_Csq\0~\0\rt\0\nROLE_PER_Dsq\0~\0\rt\0\nROLE_PER_Lxq\0~\0psr\0:org.springframework.security.oauth2.provider.OAuth2Request\0\0\0\0\0\0\0\0Z\0approvedL\0authoritiesq\0~\0L\0\nextensionst\0Ljava/util/Map;L\0redirectUriq\0~\0L\0refresht\0;Lorg/springframework/security/oauth2/provider/TokenRequest;L\0resourceIdst\0Ljava/util/Set;L\0\rresponseTypesq\0~\0*xr\08org.springframework.security.oauth2.provider.BaseRequest6(z>£qiΩ\0L\0clientIdq\0~\0L\0requestParametersq\0~\0(L\0scopeq\0~\0*xpt\0family_circle_clientsr\0%java.util.Collections$UnmodifiableMapÒ•®˛tıB\0L\0mq\0~\0(xpsr\0java.util.HashMap\⁄¡\√`\—\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\ngrant_typet\0passwordt\0usernamet\0toannguyenit239@gmail.comxsr\0%java.util.Collections$UnmodifiableSetÄí—èõÄU\0\0xq\0~\0	sr\0java.util.LinkedHashSet\ÿl\◊Zï\›*\0\0xr\0java.util.HashSet∫DÖïñ∏∑4\0\0xpw\0\0\0?@\0\0\0\0\0t\0readt\0writexsq\0~\09w\0\0\0?@\0\0\0\0\0sq\0~\0\rt\0ROLE_CLIENTsq\0~\0\rt\0ROLE_TRUSTED_CLIENTxsq\0~\00?@\0\0\0\0\0\0w\0\0\0\0\0\0\0xppsq\0~\09w\0\0\0?@\0\0\0\0\0\0xsq\0~\09w\0\0\0?@\0\0\0\0\0\0xsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\0\0L\0credentialsq\0~\0L\0	principalq\0~\0xq\0~\0sq\0~\0sq\0~\0\0\0\0w\0\0\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0!q\0~\0#q\0~\0%xq\0~\0Hsr\0java.util.LinkedHashMap4¿N\\l¿˚\0Z\0accessOrderxq\0~\00?@\0\0\0\0\0w\0\0\0\0\0\0q\0~\02q\0~\03q\0~\04q\0~\05x\0psr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\0\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiesq\0~\0*L\0passwordq\0~\0L\0usernameq\0~\0xpsq\0~\06sr\0java.util.TreeSet›òPìï\Ìá[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\0\0\0xpw\0\0\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0q\0~\0!q\0~\0#q\0~\0%xpt\0toannguyenit239@gmail.com');
/*!40000 ALTER TABLE `oauth_refresh_token` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-24  3:39:36
