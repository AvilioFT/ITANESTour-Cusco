package pe.senati.itanes.tour.data.model

import com.google.gson.annotations.SerializedName
import pe.senati.itanes.tour.R
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
    @SerializedName("id") val id: Int = 0,
    @SerializedName("nombre") val nombre: String = "",
    @SerializedName("descripcion") val descripcion: String = "",
    @SerializedName("latitud") val latitud: Double = 0.0,
    @SerializedName("longitud") val longitud: Double = 0.0,
    @SerializedName("imagen_url") val imagenUrl: String = "",
    @SerializedName("orden") val orden: Int = 0,
    // favorito SOLO vive en SQLite local, no viene del PHP. Por defecto 0.
    var favorito: Int = 0
) : Serializable {

    /**
     * Imagen LOCAL empotrada (drawable-nodpi). ¿Por qué?
     * El caso exige funcionar SIN internet una vez descargada. Estas fotos reales
     * viajan dentro del APK y se muestran siempre, incluso en modo avión.
     * La remota (imagenUrl) es la que sincroniza cuando hay red (requisito "se actualiza con conexión").
     * Ejercicio para sustentar: Glide primero intenta la remota; si falla o hay offline, usa esta.
     */
    fun imagenLocal(): Int = when (id) {
        1 -> R.drawable.img_plaza
        2 -> R.drawable.img_sacsayhuaman
        3 -> R.drawable.img_pisac
        4 -> R.drawable.img_ollantaytambo
        5 -> R.drawable.img_machupicchu
        else -> R.drawable.img_plaza
    }
}

/** Respuesta exacta de lugares.php: { ok, total, data: [...] } */
data class LugaresResponse(
    @SerializedName("ok") val ok: Boolean,
    @SerializedName("total") val total: Int,
    @SerializedName("data") val data: List<Lugar>
)
