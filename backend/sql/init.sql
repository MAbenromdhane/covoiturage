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
    user_name VARCHAR(100) NOT NULL references users(name),
    type ENUM('offre', 'demande') NOT NULL,
    depart VARCHAR(150) NOT NULL,
    arrivee VARCHAR(150) NOT NULL,
    date_heure DATETIME NOT NULL,
    prix DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    places INT NOT NULL DEFAULT 1,
    contact VARCHAR(20) NOT NULL,
    description TEXT,
    statut ENUM('actif', 'complet', 'expire', 'supprime') NOT NULL DEFAULT 'actif',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ride_id INT NOT NULL,
    passenger_name VARCHAR(100) NOT NULL,
    status ENUM('en_attente', 'confirme', 'annule') NOT NULL DEFAULT 'en_attente',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ride_id) REFERENCES posts(id) ON DELETE CASCADE
);
