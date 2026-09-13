package pe.senati.itanes.tour

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.senati.itanes.tour.ui.DetalleActivity
import pe.senati.itanes.tour.ui.FavoritosActivity
import pe.senati.itanes.tour.ui.LugarAdapter
import pe.senati.itanes.tour.ui.TourViewModel

/**
 * MainActivity: pantalla principal (lista del recorrido).
 * Flujo: Main -> Detalle (Intent explícito con Lugar) -> Maps (Intent implícito) / Favoritos.
 * ¿Qué aprende el alumno? Intent explícito = navegas tú; implícito = pides a otra app (Maps).
 */
class MainActivity : AppCompatActivity() {

    private val vm: TourViewModel by viewModels()
    private lateinit var adapter: LugarAdapter

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.rvLugares)
        val loading = findViewById<ProgressBar>(R.id.loading)
        adapter = LugarAdapter(emptyList()) {
            startActivity(Intent(this, DetalleActivity::class.java).putExtra("lugar", it))
        }
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        findViewById<Button>(R.id.btnFavoritos).setOnClickListener {
            startActivity(Intent(this, FavoritosActivity::class.java))
        }

        vm.lugares.observe(this) {
            adapter.actualizar(it)
            if (it.isEmpty()) Toast.makeText(this, "Sin datos: revisa XAMPP o mock", Toast.LENGTH_LONG).show()
        }
        vm.cargando.observe(this) { loading.visibility = if (it) View.VISIBLE else View.GONE }

        vm.cargar() // offline-first desde Repository
    }

    override fun onResume() { super.onResume(); vm.cargar() } // refresca ★ favoritos al volver
}
