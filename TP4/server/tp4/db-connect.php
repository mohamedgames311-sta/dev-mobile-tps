<?php
include_once 'config.php';

class DbConnect {

    private $connect;

    public function __construct() {
        mysqli_report(MYSQLI_REPORT_OFF);
        $this->connect = @mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

        if (!$this->connect) {
            header('Content-Type: application/json; charset=utf-8');
            echo json_encode(array(
                'success' => 0,
                'message' => 'Unable to connect to MySQL Database: ' . mysqli_connect_error()
            ));
            exit;
        }
        mysqli_set_charset($this->connect, 'utf8mb4');
    }

    public function getDb() {
        return $this->connect;
    }
}
