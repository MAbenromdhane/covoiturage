<?php
// backend/api/respond_booking.php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../config/db.php';

$data = json_decode(file_get_contents("php://input"));

if (isset($data->booking_id) && isset($data->action)) {
    $booking_id = $data->booking_id;
    $action = $data->action; // 'accepted' or 'rejected'
} else if (isset($_POST['booking_id']) && isset($_POST['action'])) {
    $booking_id = $_POST['booking_id'];
    $action = $_POST['action'];
} else {
    echo json_encode(array("success" => false, "message" => "Données incomplètes."));
    exit;
}

if (!empty($booking_id) && ($action == 'accepted' || $action == 'rejected')) {
    
    try {
        $conn->beginTransaction();

        // 1. Get ride_id associated with the booking
        $query_ride = "SELECT b.ride_id, b.status FROM bookings b WHERE b.id = :booking_id FOR UPDATE";
        $stmt_ride = $conn->prepare($query_ride);
        $stmt_ride->bindParam(":booking_id", $booking_id);
        $stmt_ride->execute();

        if ($stmt_ride->rowCount() == 0) {
            throw new Exception("Réservation non trouvée.");
        }

        $booking = $stmt_ride->fetch(PDO::FETCH_ASSOC);
        if ($booking['status'] != 'pending') {
            throw new Exception("Cette réservation a déjà été traitée.");
        }

        $ride_id = $booking['ride_id'];

        if ($action == 'accepted') {
            // 2. Vérifier la disponibilité des places
            $query_check = "SELECT places FROM posts WHERE id = :ride_id AND places > 0 FOR UPDATE";
            $stmt_check = $conn->prepare($query_check);
            $stmt_check->bindParam(":ride_id", $ride_id);
            $stmt_check->execute();

            if ($stmt_check->rowCount() > 0) {
                $row = $stmt_check->fetch(PDO::FETCH_ASSOC);
                $remaining_places = $row['places'] - 1;

                // 3. Décrémenter le nombre de places
                $query_update = "UPDATE posts SET places = :remaining_places WHERE id = :ride_id";
                $stmt_update = $conn->prepare($query_update);
                $stmt_update->bindParam(":remaining_places", $remaining_places);
                $stmt_update->bindParam(":ride_id", $ride_id);
                $stmt_update->execute();

                // 4. Update booking status
                $query_status = "UPDATE bookings SET status = 'accepted' WHERE id = :booking_id";
                $stmt_status = $conn->prepare($query_status);
                $stmt_status->bindParam(":booking_id", $booking_id);
                $stmt_status->execute();

                // 5. If places reach 0, delete the post
                if ($remaining_places == 0) {
                    $query_delete = "DELETE FROM posts WHERE id = :ride_id";
                    $stmt_delete = $conn->prepare($query_delete);
                    $stmt_delete->bindParam(":ride_id", $ride_id);
                    $stmt_delete->execute();
                }

                $msg = "Réservation acceptée.";
            } else {
                throw new Exception("Désolé, plus de places disponibles.");
            }
        } else {
            // Logic for 'rejected'
            $query_status = "UPDATE bookings SET status = 'rejected' WHERE id = :booking_id";
            $stmt_status = $conn->prepare($query_status);
            $stmt_status->bindParam(":booking_id", $booking_id);
            $stmt_status->execute();
            $msg = "Réservation refusée.";
        }

        $conn->commit();
        echo json_encode(array("success" => true, "message" => $msg));

    } catch(Exception $e) {
        $conn->rollBack();
        echo json_encode(array("success" => false, "message" => "Erreur: " . $e->getMessage()));
    }

} else {
    echo json_encode(array("success" => false, "message" => "Champs obligatoires manquants ou action invalide."));
}
?>
