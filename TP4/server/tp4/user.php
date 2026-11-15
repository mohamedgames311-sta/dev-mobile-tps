<?php
include_once 'db-connect.php';

class User {

    private $db;
    private $db_table = "users";

    public function __construct() {
        $this->db = new DbConnect();
    }

    /** Vérifie le couple username / mot de passe (requête préparée + password_verify). */
    public function isLoginExist($username, $password) {
        $stmt = mysqli_prepare($this->db->getDb(),
            "SELECT password FROM " . $this->db_table . " WHERE username = ? LIMIT 1");
        mysqli_stmt_bind_param($stmt, "s", $username);
        mysqli_stmt_execute($stmt);
        mysqli_stmt_bind_result($stmt, $hash);

        $ok = false;
        if (mysqli_stmt_fetch($stmt)) {
            $ok = password_verify($password, $hash);
        }
        mysqli_stmt_close($stmt);
        return $ok;
    }

    public function loginUsers($username, $password) {
        $json = array();

        if ($this->isLoginExist($username, $password)) {
            $json['success'] = 1;
            $json['message'] = "Successfully logged in";
        } else {
            $json['success'] = 0;
            $json['message'] = "Incorrect details";
        }
        return $json;
    }

    public function usernameExists($username) {
        $stmt = mysqli_prepare($this->db->getDb(),
            "SELECT id FROM " . $this->db_table . " WHERE username = ? LIMIT 1");
        mysqli_stmt_bind_param($stmt, "s", $username);
        mysqli_stmt_execute($stmt);
        mysqli_stmt_store_result($stmt);
        $exists = mysqli_stmt_num_rows($stmt) > 0;
        mysqli_stmt_close($stmt);
        return $exists;
    }

    /** Activité 1 : création de compte (nom, prénom, email, téléphone). */
    public function registerUser($data) {
        $json = array();

        if ($this->usernameExists($data['username'])) {
            $json['success'] = 0;
            $json['message'] = "Username already exists";
            return $json;
        }

        $hash = password_hash($data['password'], PASSWORD_DEFAULT);

        $stmt = mysqli_prepare($this->db->getDb(),
            "INSERT INTO " . $this->db_table .
            " (username, password, nom, prenom, email, telephone) VALUES (?, ?, ?, ?, ?, ?)");
        mysqli_stmt_bind_param($stmt, "ssssss",
            $data['username'], $hash, $data['nom'], $data['prenom'], $data['email'], $data['telephone']);

        if (mysqli_stmt_execute($stmt)) {
            $json['success'] = 1;
            $json['message'] = "Account created successfully";
        } else {
            $json['success'] = 0;
            $json['message'] = "Could not create account";
        }
        mysqli_stmt_close($stmt);
        return $json;
    }
}
