<?php
// backend/api/register.php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../config/db.php';

// Récupération des données
$data = json_decode(file_get_contents("php://input"));
if (isset($data->name)) {
    $name = $data->name;
    $email = $data->email;
    $password = $data->password;
    $role = $data->role;
} else if (isset($_POST['name'])) {
    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    $role = $_POST['role'];
} else {
    echo json_encode(array("success" => false, "message" => "Données incomplètes."));
    exit;
}

if (!empty($name) && !empty($email) && !empty($password) && !empty($role)) {
    
    // Vérifier si l'email existe déjà
    $query_check = "SELECT id FROM users WHERE email = :email";
    $stmt_check = $conn->prepare($query_check);
    $stmt_check->bindParam(":email", $email);
    $stmt_check->execute();
    
    if ($stmt_check->rowCount() > 0) {
        echo json_encode(array("success" => false, "message" => "Cet email est déjà utilisé."));
        exit;
    }

    // Hachage du mot de passe
    $hashed_password = password_hash($password, PASSWORD_BCRYPT);

    // Préparation de l'insertion
    $query = "INSERT INTO users (name, email, password, role) VALUES (:name, :email, :password, :role)";
    $stmt = $conn->prepare($query);

    // Nettoyage
    $name = htmlspecialchars(strip_tags($name));
    $email = htmlspecialchars(strip_tags($email));
    $role = htmlspecialchars(strip_tags($role));

    $stmt->bindParam(":name", $name);
    $stmt->bindParam(":email", $email);
    $stmt->bindParam(":password", $hashed_password);
    $stmt->bindParam(":role", $role);

    if ($stmt->execute()) {
        echo json_encode(array("success" => true, "message" => "Utilisateur créé avec succès."));
    } else {
        echo json_encode(array("success" => false, "message" => "Impossible de créer l'utilisateur."));
    }
} else {
    echo json_encode(array("success" => false, "message" => "Veuillez remplir tous les champs."));
}
?>
