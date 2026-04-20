<?php
include_once 'backend/config/db.php';
try {
    // 1. Supprimer la clé étrangère existante
    try {
        $conn->exec("ALTER TABLE posts DROP FOREIGN KEY posts_ibfk_1");
        echo "Clé étrangère 'posts_ibfk_1' supprimée.\n";
    } catch (Exception $e) {
        echo "Note: Impossible de supprimer la clé étrangère (elle n'existe peut-être plus).\n";
    }

    // 2. Renommer et changer le type de user_id en user_name
    $conn->exec("ALTER TABLE posts CHANGE user_id user_name VARCHAR(100) NOT NULL");
    echo "Colonne 'user_id' renommée en 'user_name' (VARCHAR(100)).\n";

} catch (Exception $e) {
    echo "Erreur: " . $e->getMessage();
}
?>
