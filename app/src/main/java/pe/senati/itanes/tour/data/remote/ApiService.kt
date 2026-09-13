package pe.senati.itanes.tour.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pe.senati.itanes.tour.data.model.LugaresResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * ApiService: define QUÉ endpoint PHP consumes.
 * ¿Para qué? Retrofit genera el código HTTP por ti. Solo declaras el método.
 * En sustentación di: "GET a lugares.php, respuesta JSON -> Gson -> List<Lugar>".
 *
 * BASE_URL: cámbiala según dónde corras XAMPP:
 *  - Emulador Android Studio -> http://10.0.2.2/itanes/
 *  - Celular físico misma wifi -> http://192.168.1.7/itanes/ (tu IP actual)
 *  - XAMPP local para Bruno -> http://localhost/itanes/
 */
interface ApiService {
    @GET("api/lugares.php")
    suspend fun getLugares(): LugaresResponse
}

object RetrofitClient {
    // TODO alumno: cambia a tu IP si pruebas en celular físico.
    var BASE_URL = "http://10.0.2.2/itanes/"

    fun crear(): ApiService {
        val log = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val client = OkHttpClient.Builder().addInterceptor(log).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }
}
