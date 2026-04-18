CREATE DATABASE IF NOT EXISTS covoiturage_db;
USE covoiturage_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('passager', 'conducteur') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE posts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    type ENUM('offre', 'demande') NOT NULL,
    depart VARCHAR(150) NOT NULL,
    arrivee VARCHAR(150) NOT NULL,
    date_heure DATETIME NOT NULL,
    prix DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    places INT NOT NULL DEFAULT 1,
    contact VARCHAR(20) NOT NULL,
    statut ENUM('actif', 'complet', 'expire', 'supprime') NOT NULL DEFAULT 'actif',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
