<?php
include_once 'backend/config/db.php';
try {
    $stmt = $conn->query("SHOW CREATE TABLE posts");
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    echo $row['Create Table'];
} catch (Exception $e) {
    echo "Erreur: " . $e->getMessage();
}
?>
