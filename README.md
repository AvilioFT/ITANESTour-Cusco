# ITANESTour — Recorrido Cusco Imperial (SENATI)

App Android offline-first + API PHP para la empresa de turismo ITANES.
Carrera: Desarrollo de Software — 6to semestre.

## Estructura mono-repo (app + php en el mismo GitHub)
```
ITANESTour/
 ├── app/               # App Android (Kotlin, Views+XML, MVVM)
 ├── backend-api/       # API PHP para XAMPP (Apache + MySQL)
 │   ├── config/db.php
 │   ├── api/lugares.php
 │   └── database/itanes.sql
 ├── bruno/             # Colección Bruno (como Postman, para Linux)
 ├── docs/              # Informe, diagramas, capturas
 └── README.md
```
Gradle solo mira `app/`, ignora `backend-api/`. Por eso no se rompe.

## 5 puntos (Cusco)
1. Plaza de Armas del Cusco (-13.5167, -71.9788)
2. Sacsayhuamán (-13.5091, -71.9822)
3. Písac - Valle Sagrado (-13.4215, -71.8575)
4. Ollantaytambo (-13.2572, -72.2630)
5. Machu Picchu (-13.1631, -72.5450)

## Cómo correr (resumen)
1. **API:** copiar `backend-api/` a `C:/xampp/htdocs/itanes/` o `/opt/lampp/htdocs/itanes/`, importar `database/itanes.sql`, probar con Bruno: `GET http://localhost/itanes/api/lugares.php`
2. **App:** abrir en Android Studio, `minSdk 26`, Run en emulador. Si hay internet sincroniza de PHP, si no usa Room/SQLite local + `assets/mock_tour.json`.
3. **Mapa:** botón "Cómo llegar" abre Google Maps por Intent (sin API Key): `https://www.google.com/maps/dir/?api=1&destination=LAT,LNG`

## Arquitectura
`Activity -> ViewModel -> Repository -> { Room (offline) | Retrofit (PHP online) }` + Glide para fotos.
