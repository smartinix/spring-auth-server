DROP DATABASE IF EXISTS auth_server_db;
CREATE DATABASE auth_server_db;
DROP USER IF EXISTS `auth_admin`@`%`;
CREATE DATABASE IF NOT EXISTS auth_server_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `auth_admin`@`%` IDENTIFIED WITH mysql_native_password BY 'mysql';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `auth_server_db`.* TO `auth_admin`@`%`;

use auth_server_db;


-- Adminer 4.8.1 MySQL 8.3.0 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `authentication_methods`;
CREATE TABLE `authentication_methods` (
  `id` int NOT NULL AUTO_INCREMENT,
  `authentication_method` varchar(100) NOT NULL,
  `client_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `client_id` (`client_id`),
  CONSTRAINT `authentication_methods_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `authentication_methods` (`id`, `authentication_method`, `client_id`) VALUES
(1, 'client_secret_basic',  1),
(2, 'client_secret_post',   1),
(3, 'client_secret_jwt',    1),
(4, 'client_secret_basic',  2),
(5, 'client_secret_jwt',    2),
(6, 'client_secret_post',   2);

DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `authorities` (`id`, `name`, `user_id`) VALUES
(1, 'read', 'c7a0fc0b-84eb-4764-aad6-0979de43d1b2'),
(2, 'read', '243d2029-1846-4306-aa4a-13ea2775b497'),
(3, 'read', 'c7a0fc0b-84eb-4764-aad6-0979de43d1b2');

DROP TABLE IF EXISTS `clients`;
CREATE TABLE `clients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_id` varchar(45) NOT NULL,
  `secret` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `clients` (`id`, `client_id`, `secret`) VALUES
(1, 'client',   'secret'),
(2, 'client_app_2', 'secret');

DROP TABLE IF EXISTS `grant_types`;
CREATE TABLE `grant_types` (
  `id` int NOT NULL AUTO_INCREMENT,
  `grant_type` varchar(45) NOT NULL,
  `client_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `client_id` (`client_id`),
  CONSTRAINT `grant_types_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `grant_types` (`id`, `grant_type`, `client_id`) VALUES
(1, 'authorization_code',   1),
(2, 'client_credentials',   1),
(3, 'refresh_token',    1),
(4, 'authorization_code',   2);

DROP TABLE IF EXISTS `redirect_urls`;
CREATE TABLE `redirect_urls` (
  `id` int NOT NULL AUTO_INCREMENT,
  `url` varchar(500) NOT NULL,
  `client_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `client_id` (`client_id`),
  CONSTRAINT `redirect_urls_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `redirect_urls` (`id`, `url`, `client_id`) VALUES
(1, 'https://example.com/auth', 1),
(2, 'https://example.com/auth', 2);

DROP TABLE IF EXISTS `scopes`;
CREATE TABLE `scopes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `scope` varchar(45) NOT NULL,
  `client_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `client_id` (`client_id`),
  CONSTRAINT `scopes_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `scopes` (`id`, `scope`, `client_id`) VALUES
(1, 'openid',   1),
(2, 'openid',   2);

DROP TABLE IF EXISTS `token_settings`;
CREATE TABLE `token_settings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `access_token_ttl` int NOT NULL,
  `type` varchar(45) NOT NULL,
  `client_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `client_id` (`client_id`),
  CONSTRAINT `token_settings_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `token_settings` (`id`, `access_token_ttl`, `type`, `client_id`) VALUES
(1, 5,  'reference',    1),
(2, 1,  'self-contained',   2);

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` varchar(36) NOT NULL,
  `username` varchar(36) NOT NULL,
  `password` varchar(36) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `expired` tinyint(1) NOT NULL DEFAULT '0',
  `locked` tinyint(1) NOT NULL DEFAULT '0',
  `credentials_expired` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users` (`id`, `username`, `password`, `enabled`, `expired`, `locked`, `credentials_expired`) VALUES
('0ee5b89f-f746-4286-b072-329963f2cbbe',    'admin',    'admin',    1,  0,  0,  0),
('243d2029-1846-4306-aa4a-13ea2775b497',    'user', 'user', 1,  0,  0,  0),
('c7a0fc0b-84eb-4764-aad6-0979de43d1b2',    'macko',    'test123',  1,  0,  0,  0);

-- 2024-04-25 13:44:32
