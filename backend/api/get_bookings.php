<?php
// backend/api/get_bookings.php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET, POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../config/db.php';

$driver_name = isset($_GET['driver_name']) ? $_GET['driver_name'] : (isset($_POST['driver_name']) ? $_POST['driver_name'] : "");

if (empty($driver_name)) {
    echo json_encode(array("success" => false, "message" => "Nom du conducteur manquant."));
    exit;
}

try {
    // Sélectionner les réservations pour les trajets créés par ce conducteur
    $query = "SELECT b.*, p.depart, p.arrivee, p.date_heure 
              FROM bookings b 
              JOIN posts p ON b.ride_id = p.id 
              WHERE p.user_name = :driver_name 
              ORDER BY b.created_at DESC";
              
    $stmt = $conn->prepare($query);
    $stmt->bindParam(":driver_name", $driver_name);
    $stmt->execute();
    
    $bookings = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
    echo json_encode(array("success" => true, "bookings" => $bookings));
} catch(PDOException $exception) {
    echo json_encode(array("success" => false, "message" => "Erreur SQL: " . $exception->getMessage()));
}
?>
