<?php
include_once 'backend/config/db.php';
try {
    // Add status column to bookings if it doesn't exist
    $conn->exec("ALTER TABLE bookings ADD COLUMN status ENUM('pending', 'accepted', 'rejected') DEFAULT 'pending' AFTER passenger_phone");
    echo "Colonne 'status' ajoutée à la table 'bookings' avec succès !\n";
} catch (Exception $e) {
    if (strpos($e->getMessage(), "Duplicate column name") !== false) {
        echo "La colonne 'status' existe déjà dans 'bookings'.\n";
    } else {
        echo "Erreur bookings: " . $e->getMessage() . "\n";
    }
}
?>
