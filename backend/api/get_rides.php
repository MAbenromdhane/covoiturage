<?php
// backend/api/get_rides.php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET, POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../config/db.php';

// Check if it's a search (can be GET or POST)
$user_name = isset($_GET['user_name']) ? $_GET['user_name'] : (isset($_POST['user_name']) ? $_POST['user_name'] : "");
$depart = isset($_GET['depart']) ? $_GET['depart'] : (isset($_POST['depart']) ? $_POST['depart'] : "");
$arrivee = isset($_GET['arrivee']) ? $_GET['arrivee'] : (isset($_POST['arrivee']) ? $_POST['arrivee'] : "");

$query = "SELECT * FROM posts WHERE statut = 'actif'";
$params = [];

if (!empty($user_name)) {
    $query .= " AND user_name = :user_name";
    $params[':user_name'] = $user_name;
}

if (!empty($depart)) {
    $query .= " AND depart LIKE :depart";
    $params[':depart'] = "%$depart%";
}

if (!empty($arrivee)) {
    $query .= " AND arrivee LIKE :arrivee";
    $params[':arrivee'] = "%$arrivee%";
}

$query .= " ORDER BY date_heure ASC";

try {
    $stmt = $conn->prepare($query);
    foreach ($params as $key => $val) {
        $stmt->bindValue($key, $val);
    }
    $stmt->execute();
    
    $rides = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
    echo json_encode(array("success" => true, "rides" => $rides));
} catch(PDOException $exception) {
    echo json_encode(array("success" => false, "message" => "Erreur SQL: " . $exception->getMessage()));
}
?>
