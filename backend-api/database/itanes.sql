-- itanes.sql — Importar en phpMyAdmin (http://localhost/phpmyadmin)
-- ¿Para qué? Crea la BD remota que tu app consume cuando HAY internet.
-- Cuando NO hay internet, la app usa Room/SQLite local (misma estructura).
CREATE DATABASE IF NOT EXISTS itanes CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE itanes;

CREATE TABLE IF NOT EXISTS lugares (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  descripcion TEXT NOT NULL,
  latitud DOUBLE NOT NULL,
  longitud DOUBLE NOT NULL,
  imagen_url VARCHAR(255) NOT NULL,
  orden INT NOT NULL
);

INSERT INTO lugares (nombre, descripcion, latitud, longitud, imagen_url, orden) VALUES
('Plaza de Armas del Cusco','Corazón histórico del Cusco. Catedral, portales y punto de partida del recorrido. Ideal para aclimatarse (3399 msnm).',-13.5167,-71.9788,'https://upload.wikimedia.org/wikipedia/commons/thumb/5/5f/Plaza_de_Armas_Cusco.jpg/800px-Plaza_de_Armas_Cusco.jpg',1),
('Sacsayhuamán','Fortaleza inca de piedras megalíticas. Vista panorámica del Cusco. Subida en auto 10 min desde la plaza.',-13.5091,-71.9822,'https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Sacsayhuaman.jpg/800px-Sacsayhuaman.jpg',2),
('Písac - Valle Sagrado','Andenes incas + mercado artesanal. Ruta Cusco-Písac 45 min en auto por el Valle Sagrado.',-13.4215,-71.8575,'https://upload.wikimedia.org/wikipedia/commons/thumb/4/4e/Pisac.jpg/800px-Pisac.jpg',3),
('Ollantaytambo','Último pueblo inca vivo. Andenes, graneros y estación de tren a Machu Picchu. 2h desde Cusco.',-13.2572,-72.2630,'https://upload.wikimedia.org/wikipedia/commons/thumb/8/8e/Ollantaytambo.jpg/800px-Ollantaytambo.jpg',4),
('Machu Picchu','Maravilla del mundo. Ciudadela inca entre montañas. Acceso desde Ollantaytambo en tren + bus.',-13.1631,-72.5450,'https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Machu_Picchu%2C_Peru.jpg/800px-Machu_Picchu%2C_Peru.jpg',5);
