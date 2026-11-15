-- Base de données du TP4 (à importer via phpMyAdmin / EasyPHP)
CREATE DATABASE IF NOT EXISTS tp4database CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE tp4database;

CREATE TABLE IF NOT EXISTS users (
    id        INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50)  NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,          -- hash généré par password_hash()
    nom       VARCHAR(50)  NOT NULL DEFAULT '',
    prenom    VARCHAR(50)  NOT NULL DEFAULT '',
    email     VARCHAR(100) NOT NULL DEFAULT '',
    telephone VARCHAR(20)  NOT NULL DEFAULT ''
) ENGINE=InnoDB;

-- Compte de test : admin / admin123
INSERT IGNORE INTO users (username, password, nom, prenom, email, telephone)
VALUES ('admin', '$2y$10$HufxStj0LxVzwF0rUrafZe5aeCUioL3JJTFyrCoLfsYn7ShZ7pxjW', 'Admin', 'Test', 'admin@example.com', '00000000');
