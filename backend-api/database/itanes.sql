-- itanes.sql — Importar en phpMyAdmin (http://localhost/phpmyadmin)
-- Coordenadas exactas fuente Wikipedia (WGS84) + fotos reales Wikimedia Commons.
CREATE DATABASE IF NOT EXISTS itanes CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE itanes;

CREATE TABLE IF NOT EXISTS lugares (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  descripcion TEXT NOT NULL,
  latitud DOUBLE NOT NULL,
  longitud DOUBLE NOT NULL,
  imagen_url VARCHAR(500) NOT NULL,
  orden INT NOT NULL
);

DELETE FROM lugares;
INSERT INTO lugares (nombre, descripcion, latitud, longitud, imagen_url, orden) VALUES
('Plaza de Armas del Cusco','Corazón histórico del Cusco. Catedral, portales y punto de partida del recorrido. Ideal para aclimatarse (3399 msnm).',-13.516711,-71.978823,'https://commons.wikimedia.org/wiki/Special:FilePath/Plaza_de_Cusco_Allison_Bellido.jpg?width=800',1),
('Sacsayhuamán','Fortaleza inca de piedras megalíticas. Vista panorámica del Cusco. Subida en auto 10 min desde la plaza.',-13.507778,-71.982222,'https://commons.wikimedia.org/wiki/Special:FilePath/Sacsayhuam%C3%A1n%2C_Cusco%2C_Per%C3%BA%2C_2015-07-31%2C_DD_01.JPG?width=800',2),
('Písac - Valle Sagrado','Andenes incas y zona arqueológica sobre el Valle Sagrado. Ruta Cusco-Písac 45 min en auto.',-13.408886,-71.842872,'https://commons.wikimedia.org/wiki/Special:FilePath/15-Pisac-nX-2.jpg?width=800',3),
('Ollantaytambo','Último pueblo inca vivo. Andenes, graneros y estación de tren a Machu Picchu. 2h desde Cusco.',-13.258056,-72.263333,'https://commons.wikimedia.org/wiki/Special:FilePath/Town_of_Ollantaytambo.jpg?width=800',4),
('Machu Picchu','Maravilla del mundo. Ciudadela inca entre montañas. Acceso desde Ollantaytambo en tren + bus.',-13.163333,-72.545556,'https://commons.wikimedia.org/wiki/Special:FilePath/Machu_Picchu%2C_Peru_%282018%29.jpg?width=800',5);
