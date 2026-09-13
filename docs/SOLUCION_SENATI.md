# Trabajo Final — ITANESTour (para pegar en FORMATOALUMNOTRABAJOFINAL.docx)
Carrera: Desarrollo de Software | Semestre: 6to | Tema: App turística offline-first Cusco

## 1. Problemática
ITANES pierde clientes porque su recorrido depende de internet y folletos. El turista en Sacsayhuamán/Machu Picchu se queda sin señal y sin info, fotos ni rutas. Se necesita app Android 8.0+ que funcione offline y se actualice con conexión.

## 2. Propuesta + evidencias
App `pe.senati.itanes.tour` MVVM offline-first: Main(lista 5) -> Detalle(foto+desc+ruta/fav/compartir) -> Favoritos(local). Datos: PHP `GET /itanes/api/lugares.php` (MySQL) + fallback `assets/mock_tour.json` + SQLite local `itanes.db`. Fotos con Glide (caché), rutas con Intent a Google Maps (sin API Key), favoritos en SQLite.
Evidencias: APK 8.9MB `app-debug.apk`, capturas Main/Detalle/Favoritos, Bruno `GET lugares` 200 OK, código documentado.

## 3. Preguntas guía (resumen para sustentar)
1. **¿Arquitectura? MVVM** (`Activity observa ViewModel; ViewModel usa Repository; Repository elige API/SQLite/mock`). Separa UI/datos, sobrevive a rotación, testeable. MVC acopla todo en Activity.
2. **¿Multimedia sin peso? Glide**: carga por URL, caché disco+memoria, `centerCrop/resize`, no guarda fotos en APK. Solo URLs en BD.
3. **¿Mapas precisos? Intent implícito** `https://www.google.com/maps/dir/?api=1&destination=LAT,LNG&travelmode=driving`. Sin key, abre Maps con ruta auto. MapFragment requeriría key de facturación.
4. **¿Offline? 3 niveles**: PHP si hay red -> guarda en SQLite; si no hay red -> SQLite; si SQLite vacío -> mock assets. `ConnectivityManager` decide. Prueba: modo avión -> lista igual abre.
5. **¿Validaciones/errores?** try/catch red (no crashea, cae a local), `Toast` si vacío, `Glide placeholder` si falla foto, `usesCleartextTraffic` + `10.0.2.2` emulador / IP wifi físico, permisos INTERNET+NETWORK_STATE.

## 4. Cronograma (sáb 12 - dom 13)
| N° | Actividad | Sáb AM | Sáb PM | Dom AM | Dom PM |
|---|---|---|---|---|---|
|1|Fix base minSdk26 + namespace + GitHub| X | | | |
|2|PHP + SQL + Bruno en XAMPP| X | | | |
|3|Modelo + SQLite + Retrofit| | X | | |
|4|UI Lista/Detalle/Fav/Maps + APK| | | X | |
|5|Documento + capturas + video| | | | X |

## 5. Recursos
Máquinas: Laptop i5/8GB x1, celular Android 8.0+ x1. Herramientas: Android Studio, XAMPP 8.x, Bruno, Git. Materiales: cable USB, wifi local (192.168.1.7).

## 6. Operaciones + Normas
| Paso | Norma técnica/seguridad/medio ambiente |
|---|---|
|Instalar/gradlear con minSdk26| Android 8.0+ (compatibilidad), no pedir ubicación fina (privacidad) |
|PHP con PDO + JSON UTF-8| Evita SQL injection, CORS solo demo |
|SQLite local + mock| Ley datos: favoritos solo local, sin subir datos personales |
|Intent Maps driving| No distraer conduciendo, “solo copiloto” en manual |
|APK debug firmado| No publicar keystore, APK solo pruebas |

## 7. Tablas
SQLite `lugares(id PK, nombre, descripcion, latitud REAL, longitud REAL, imagen_url, orden, favorito DEFAULT 0)`. MySQL idéntica sin favorito (favorito es local).

## 8. Ejemplo API (para el doc)
`GET http://10.0.2.2/itanes/api/lugares.php` -> `{"ok":true,"total":5,"data":[{"id":1,"nombre":"Plaza de Armas del Cusco",...}]}`. Retrofit: `@GET("api/lugares.php") suspend fun getLugares(): LugaresResponse`.

## 9. Flujo navegación
`MainActivity --(putExtra Lugar)--> DetalleActivity --(ACTION_VIEW geo)--> Google Maps`; `Main --(Intent)--> FavoritosActivity --(putExtra)--> Detalle`. Back stack natural.

## 10. Checklist entrega
- [x] APK funcional, [x] código documentado, [x] mock JSON, [x] capturas (tomar en emulador), [ ] video 1min (opcional), [x] pruebas offline/modo avión.
