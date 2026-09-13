package pe.senati.itanes.tour.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.Gson
import pe.senati.itanes.tour.data.local.ItanesDbHelper
import pe.senati.itanes.tour.data.model.Lugar
import pe.senati.itanes.tour.data.model.LugaresResponse
import pe.senati.itanes.tour.data.remote.RetrofitClient

/**
 * TourRepository: decide DE DÓNDE salen los datos (offline-first).
 * ¿Para qué? Es el corazón de "funciona sin internet pero se actualiza con conexión":
 *  1) ¿Hay internet? -> intenta PHP (Retrofit). Si ok, guarda en SQLite y devuelve.
 *  2) ¿Falló red o no hay internet? -> lee SQLite local.
 *  3) ¿SQLite vacío (primera vez offline)? -> lee assets/mock_tour.json y lo guarda.
 * En sustentación dibuja: UI -> Repository -> {API | SQLite | mock}.
 */
class TourRepository(private val ctx: Context) {
    private val db = ItanesDbHelper(ctx)
    private val api = RetrofitClient.crear()

    suspend fun obtenerLugares(): List<Lugar> {
        if (hayInternet()) {
            try {
                val res = api.getLugares()
                if (res.ok && res.data.isNotEmpty()) {
                    db.guardarLugares(res.data)
                    return db.listarTodos()
                }
            } catch (_: Exception) { /* cae a local, no crashea: control de errores */ }
        }
        val local = db.listarTodos()
        if (local.isNotEmpty()) return local
        // Primer arranque sin internet: cargar mock
        val json = ctx.assets.open("mock_tour.json").bufferedReader().use { it.readText() }
        val mock = Gson().fromJson(json, LugaresResponse::class.java)
        db.guardarLugares(mock.data)
        return db.listarTodos()
    }

    fun toggleFavorito(l: Lugar, fav: Boolean) = db.setFavorito(l.id, fav)
    fun favoritos(): List<Lugar> = db.listarFavoritos()

    private fun hayInternet(): Boolean {
        val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val n = cm.activeNetwork ?: return false
        val c = cm.getNetworkCapabilities(n) ?: return false
        return c.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
