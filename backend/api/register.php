<?php
// backend/api/register.php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../config/db.php';

// Support JSON body or Form data
$data = json_decode(file_get_contents("php://input"));
if (isset($data->nom)) {
    $nom = $data->nom;
    $email = $data->email;
    $password = $data->password;
    $role = $data->role;
} else if (isset($_POST['nom'])) {
    $nom = $_POST['nom'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    $role = $_POST['role'];
} else {
    echo json_encode(array("success" => false, "message" => "Données incomplètes."));
    exit;
}

if (!empty($nom) && !empty($email) && !empty($password) && !empty($role)) {
    
    // Vérifier si l'email existe déjà
    $query_check = "SELECT id FROM users WHERE email = :email LIMIT 1";
    $stmt_check = $conn->prepare($query_check);
    $stmt_check->bindParam(":email", $email);
    $stmt_check->execute();
    
    if ($stmt_check->rowCount() > 0) {
        echo json_encode(array("success" => false, "message" => "Cet email est déjà utilisé."));
        exit;
    }

    // Préparation de l'insertion
    $query = "INSERT INTO users (nom, email, password, role) VALUES (:nom, :email, :password, :role)";
    $stmt = $conn->prepare($query);

    // Nettoyage et Hachage
    $nom = htmlspecialchars(strip_tags($nom));
    $email = htmlspecialchars(strip_tags($email));
    $role = htmlspecialchars(strip_tags($role));
    $hashed_password = password_hash($password, PASSWORD_BCRYPT);

    $stmt->bindParam(":nom", $nom);
    $stmt->bindParam(":email", $email);
    $stmt->bindParam(":password", $hashed_password);
    $stmt->bindParam(":role", $role);

    if ($stmt->execute()) {
        echo json_encode(array("success" => true, "message" => "Inscription terminée. Vous pouvez vous connecter."));
    } else {
        echo json_encode(array("success" => false, "message" => "Erreur lors de l'enregistrement."));
    }
} else {
    echo json_encode(array("success" => false, "message" => "Veuillez remplir tous les champs."));
}
?>
