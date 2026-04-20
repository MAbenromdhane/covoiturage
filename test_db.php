<?php
include_once 'backend/config/db.php';
try {
    $stmt = $conn->query("DESCRIBE posts");
    $columns = $stmt->fetchAll(PDO::FETCH_ASSOC);
    foreach ($columns as $col) {
        echo $col['Field'] . " (" . $col['Type'] . ")\n";
    }
} catch (Exception $e) {
    echo "Erreur: " . $e->getMessage();
}
?>
