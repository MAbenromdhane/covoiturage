<?php
include_once 'backend/config/db.php';
try {
    $conn->exec("ALTER TABLE posts ADD COLUMN description TEXT AFTER contact");
    echo "Colonne 'description' ajoutée avec succès !";
} catch (Exception $e) {
    if (strpos($e->getMessage(), "Duplicate column name") !== false) {
        echo "La colonne 'description' existe déjà.";
    } else {
        echo "Erreur: " . $e->getMessage();
    }
}
?>
