<?php
// backend/api/create_ride.php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../config/db.php';

// Récupération des données
$data = json_decode(file_get_contents("php://input"));

if (isset($data->user_id)) {
    $user_id = $data->user_id;
    $type = $data->type;
    $depart = $data->depart;
    $arrivee = $data->arrivee;
    $date_heure = $data->date_heure;
    $prix = isset($data->prix) ? $data->prix : 0;
    $places = isset($data->places) ? $data->places : 1;
    $contact = $data->contact;
    $description = isset($data->description) ? $data->description : "";
} else if (isset($_POST['user_id'])) {
    $user_id = $_POST['user_id'];
    $type = $_POST['type'];
    $depart = $_POST['depart'];
    $arrivee = $_POST['arrivee'];
    $date_heure = $_POST['date_heure'];
    $prix = isset($_POST['prix']) ? $_POST['prix'] : 0;
    $places = isset($_POST['places']) ? $_POST['places'] : 1;
    $contact = $_POST['contact'];
    $description = isset($_POST['description']) ? $_POST['description'] : "";
} else {
    echo json_encode(array("success" => false, "message" => "Données incomplètes."));
    exit;
}

if (!empty($user_id) && !empty($depart) && !empty($arrivee) && !empty($date_heure) && !empty($contact)) {
    
    // Préparation de l'insertion
    $query = "INSERT INTO posts (user_id, type, depart, arrivee, date_heure, prix, places, contact, description) 
              VALUES (:user_id, :type, :depart, :arrivee, :date_heure, :prix, :places, :contact, :description)";
    
    $stmt = $conn->prepare($query);

    // Nettoyage
    $type = htmlspecialchars(strip_tags($type));
    $depart = htmlspecialchars(strip_tags($depart));
    $arrivee = htmlspecialchars(strip_tags($arrivee));
    $date_heure = htmlspecialchars(strip_tags($date_heure));
    $contact = htmlspecialchars(strip_tags($contact));
    $description = htmlspecialchars(strip_tags($description));

    $stmt->bindParam(":user_id", $user_id);
    $stmt->bindParam(":type", $type);
    $stmt->bindParam(":depart", $depart);
    $stmt->bindParam(":arrivee", $arrivee);
    $stmt->bindParam(":date_heure", $date_heure);
    $stmt->bindParam(":prix", $prix);
    $stmt->bindParam(":places", $places);
    $stmt->bindParam(":contact", $contact);
    $stmt->bindParam(":description", $description);

    if ($stmt->execute()) {
        echo json_encode(array("success" => true, "message" => "Trajet publié avec succès."));
    } else {
        $error = $stmt->errorInfo();
        echo json_encode(array("success" => false, "message" => "Erreur SQL: " . $error[2]));
    }
} else {
    echo json_encode(array("success" => false, "message" => "Veuillez remplir tous les champs obligatoires. Champs manquants: " . 
        (empty($user_id) ? "user_id " : "") . 
        (empty($depart) ? "depart " : "") . 
        (empty($arrivee) ? "arrivee " : "") . 
        (empty($date_heure) ? "date_heure " : "") . 
        (empty($contact) ? "contact" : "")));
}
?>
