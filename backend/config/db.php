<?php
// backend/config/db.php
$host = '127.0.0.1'; // ou localhost
$db_name = 'carpooling_db';
$username = 'root'; // par défaut pour XAMPP/Laragon
$password = ''; // par défaut vide pour XAMPP/Laragon

try {
    $conn = new PDO("mysql:host=" . $host . ";dbname=" . $db_name . ";charset=utf8", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch(PDOException $exception) {
    echo "Erreur de connexion : " . $exception->getMessage();
    exit;
}
?>
