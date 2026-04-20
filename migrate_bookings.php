<?php
include_once 'backend/config/db.php';
try {
    $sql = "CREATE TABLE IF NOT EXISTS bookings (
        id INT AUTO_INCREMENT PRIMARY KEY,
        ride_id INT NOT NULL,
        passenger_name VARCHAR(100) NOT NULL,
        status ENUM('en_attente', 'confirme', 'annule') NOT NULL DEFAULT 'en_attente',
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (ride_id) REFERENCES posts(id) ON DELETE CASCADE
    )";
    $conn->exec($sql);
    echo "Table 'bookings' créée avec succès !";
} catch (Exception $e) {
    echo "Erreur: " . $e->getMessage();
}
?>
