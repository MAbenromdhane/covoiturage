<?php
// backend/api/book_seat.php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../config/db.php';

$data = json_decode(file_get_contents("php://input"));

if (isset($data->ride_id) && isset($data->passenger_name) && isset($data->passenger_phone)) {
    $ride_id = $data->ride_id;
    $passenger_name = $data->passenger_name;
    $passenger_phone = $data->passenger_phone;
} else if (isset($_POST['ride_id']) && isset($_POST['passenger_name']) && isset($_POST['passenger_phone'])) {
    $ride_id = $_POST['ride_id'];
    $passenger_name = $_POST['passenger_name'];
    $passenger_phone = $_POST['passenger_phone'];
} else {
    echo json_encode(array("success" => false, "message" => "Données incomplètes."));
    exit;
}

if (!empty($ride_id) && !empty($passenger_name) && !empty($passenger_phone)) {
    
    try {
        $conn->beginTransaction();

        // 1. Vérifier la disponibilité des places
        $query_check = "SELECT places FROM posts WHERE id = :ride_id AND places > 0 FOR UPDATE";
        $stmt_check = $conn->prepare($query_check);
        $stmt_check->bindParam(":ride_id", $ride_id);
        $stmt_check->execute();

        if ($stmt_check->rowCount() > 0) {
            $row = $stmt_check->fetch(PDO::FETCH_ASSOC);
            
            // 2. Créer la réservation
            $query_book = "INSERT INTO bookings (ride_id, passenger_name, passenger_phone) VALUES (:ride_id, :passenger_name, :passenger_phone)";
            $stmt_book = $conn->prepare($query_book);
            $stmt_book->bindParam(":ride_id", $ride_id);
            $stmt_book->bindParam(":passenger_name", $passenger_name);
            $stmt_book->bindParam(":passenger_phone", $passenger_phone);
            $stmt_book->execute();

            // 3. Décrémenter le nombre de places
            $query_update = "UPDATE posts SET places = places - 1 WHERE id = :ride_id";
            $stmt_update = $conn->prepare($query_update);
            $stmt_update->bindParam(":ride_id", $ride_id);
            $stmt_update->execute();

            $conn->commit();
            echo json_encode(array("success" => true, "message" => "Réservation effectuée avec succès."));
        } else {
            $conn->rollBack();
            echo json_encode(array("success" => false, "message" => "Désolé, plus de places disponibles pour ce trajet."));
        }

    } catch(Exception $e) {
        $conn->rollBack();
        echo json_encode(array("success" => false, "message" => "Erreur: " . $e->getMessage()));
    }

} else {
    echo json_encode(array("success" => false, "message" => "Champs obligatoires manquants."));
}
?>
