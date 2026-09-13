package pe.senati.itanes.tour.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Lugar: un punto del recorrido (1 de 5).
 * ¿Para qué sirve? Es el molde único para 3 fuentes:
 *  1) JSON del PHP (Retrofit+Gson lo llena solo),
 *  2) JSON local assets/mock_tour.json (fallback offline),
 *  3) Fila SQLite local (ItanesDbHelper).
 * Serializable = puedes pasarlo entre Activities con Intent.putExtra (flujo de navegación).
 */
data class Lugar(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("latitud") val latitud: Double,
    @SerializedName("longitud") val longitud: Double,
    @SerializedName("imagen_url") val imagenUrl: String,
    @SerializedName("orden") val orden: Int,
    // favorito SOLO vive en SQLite local, no viene del PHP. Por defecto 0.
    var favorito: Int = 0
) : Serializable

/** Respuesta exacta de lugares.php: { ok, total, data: [...] } */
data class LugaresResponse(
    @SerializedName("ok") val ok: Boolean,
    @SerializedName("total") val total: Int,
    @SerializedName("data") val data: List<Lugar>
)
