<?php
// lugares.php — GET http://localhost/itanes/api/lugares.php
// ¿Para qué? Es el endpoint REST que tu app consume con Retrofit.
// Devuelve JSON con los 5 puntos. Esto es lo que pruebas con Bruno/Postman.
header("Content-Type: application/json; charset=utf-8");
header("Access-Control-Allow-Origin: *"); // permite que Android/emulador la llame
require_once __DIR__ . "/../config/db.php";

try {
    $db = getDB();
    $stmt = $db->query("SELECT id, nombre, descripcion, latitud, longitud, imagen_url, orden FROM lugares ORDER BY orden ASC");
    $lugares = $stmt->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode(["ok" => true, "total" => count($lugares), "data" => $lugares], JSON_UNESCAPED_UNICODE);
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode(["ok" => false, "error" => $e->getMessage()]);
}
