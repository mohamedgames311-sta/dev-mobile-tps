<?php
// Endpoint d'inscription (Activité 1) :
// POST JSON {"username","password","nom","prenom","email","telephone"}
require_once 'user.php';

header('Content-Type: application/json; charset=utf-8');

$obj = json_decode(file_get_contents('php://input'), true);

$fields = array('username', 'password', 'nom', 'prenom', 'email', 'telephone');
$data = array();
foreach ($fields as $f) {
    $data[$f] = isset($obj[$f]) ? trim($obj[$f]) : "";
}

if ($data['username'] === "" || $data['password'] === "" || $data['nom'] === ""
    || $data['prenom'] === "" || $data['email'] === "") {
    echo json_encode(array('success' => 0, 'message' => 'Missing required fields'));
    exit;
}
if (!filter_var($data['email'], FILTER_VALIDATE_EMAIL)) {
    echo json_encode(array('success' => 0, 'message' => 'Invalid email'));
    exit;
}

$userObject = new User();
echo json_encode($userObject->registerUser($data));
