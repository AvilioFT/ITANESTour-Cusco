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
('Plaza de Armas del Cusco','Corazón histórico del Cusco. Catedral, portales y punto de partida del recorrido. Ideal para aclimatarse (3399 msnm).',-13.5167,-71.9788,'https://picsum.photos/seed/itanes-cusco/800/600',1),
('Sacsayhuamán','Fortaleza inca de piedras megalíticas. Vista panorámica del Cusco. Subida en auto 10 min desde la plaza.',-13.5091,-71.9822,'https://picsum.photos/seed/itanes-sacsayhuaman/800/600',2),
('Písac - Valle Sagrado','Andenes incas + mercado artesanal. Ruta Cusco-Písac 45 min en auto por el Valle Sagrado.',-13.4215,-71.8575,'https://picsum.photos/seed/itanes-pisac/800/600',3),
('Ollantaytambo','Último pueblo inca vivo. Andenes, graneros y estación de tren a Machu Picchu. 2h desde Cusco.',-13.2572,-72.2630,'https://picsum.photos/seed/itanes-ollantaytambo/800/600',4),
('Machu Picchu','Maravilla del mundo. Ciudadela inca entre montañas. Acceso desde Ollantaytambo en tren + bus.',-13.1631,-72.5450,'https://picsum.photos/seed/itanes-machupicchu/800/600',5);
-- Si ya importaste antes con URLs rotas, ejecuta esto en phpMyAdmin para corregir fotos:
-- UPDATE lugares SET imagen_url='https://picsum.photos/seed/itanes-cusco/800/600' WHERE id=1;
-- UPDATE lugares SET imagen_url='https://picsum.photos/seed/itanes-sacsayhuaman/800/600' WHERE id=2;
-- UPDATE lugares SET imagen_url='https://picsum.photos/seed/itanes-pisac/800/600' WHERE id=3;
-- UPDATE lugares SET imagen_url='https://picsum.photos/seed/itanes-ollantaytambo/800/600' WHERE id=4;
-- UPDATE lugares SET imagen_url='https://picsum.photos/seed/itanes-machupicchu/800/600' WHERE id=5;
