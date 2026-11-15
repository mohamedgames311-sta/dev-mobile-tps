<?php
// Endpoint de connexion : POST JSON {"username": "...", "password": "..."}
require_once 'user.php';

header('Content-Type: application/json; charset=utf-8');

$data = file_get_contents('php://input');
$obj = json_decode($data, true);

$username = isset($obj['username']) ? trim($obj['username']) : "";
$password = isset($obj['password']) ? $obj['password'] : "";

if ($username !== "" && $password !== "") {
    $userObject = new User();
    echo json_encode($userObject->loginUsers($username, $password));
} else {
    echo json_encode(array('success' => 0, 'message' => 'Missing username or password'));
}
