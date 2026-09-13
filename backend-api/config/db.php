<?php
// db.php — ¿Para qué sirve? Centraliza la conexión MySQL (XAMPP).
// Si cambias usuario/clave solo tocas aquí, no cada API.
function getDB(): PDO {
    $host = "localhost";
    $dbname = "itanes";
    $user = "root";   // XAMPP por defecto: root sin clave
    $pass = "";       // En Linux/Lampp también suele ser vacío
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8mb4", $user, $pass);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    return $pdo;
}
