<?php
// backend/api/login.php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../config/db.php';

// Récupération des données POST
// Compatible avec JSON body ou form-data
$data = json_decode(file_get_contents("php://input"));
if (isset($data->email)) {
    $email = $data->email;
    $password = $data->password;
} else if (isset($_POST['email'])) {
    $email = $_POST['email'];
    $password = $_POST['password'];
} else {
    echo json_encode(array("success" => false, "message" => "Données incomplètes."));
    exit;
}

if (!empty($email) && !empty($password)) {
    $query = "SELECT id, nom, email, password, role FROM users WHERE email = :email LIMIT 1";
    $stmt = $conn->prepare($query);
    
    // Nettoyer l'email
    $email = htmlspecialchars(strip_tags($email));
    $stmt->bindParam(":email", $email);
    
    $stmt->execute();
    
    if ($stmt->rowCount() > 0) {
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        
        // Vérification du mot de passe
        if (password_verify($password, $row['password'])) {
            // Retirer le mot de passe avant de renvoyer l'utilisateur
            unset($row['password']);
            
            echo json_encode(array(
                "success" => true,
                "message" => "Connexion réussie.",
                "user" => $row
            ));
        } else {
            // Pour le mot de passe non haché lors de tests initiaux, on peut gérer ce cas exceptionnel.
            // Il est recommandé de n'utiliser que des mots de passe hachés.
            if ($password === $row['password']) {
                unset($row['password']);
                echo json_encode(array(
                    "success" => true,
                    "message" => "Connexion réussie (Plain text warning).",
                    "user" => $row
                ));
            } else {
                echo json_encode(array("success" => false, "message" => "Mot de passe incorrect."));
            }
        }
    } else {
        echo json_encode(array("success" => false, "message" => "Utilisateur non trouvé."));
    }
} else {
    echo json_encode(array("success" => false, "message" => "Veuillez fournir un email et un mot de passe."));
}
?>
