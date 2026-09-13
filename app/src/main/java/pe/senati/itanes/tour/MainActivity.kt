package pe.senati.itanes.tour

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.senati.itanes.tour.ui.DetalleActivity
import pe.senati.itanes.tour.ui.FavoritosActivity
import pe.senati.itanes.tour.ui.LugarAdapter
import pe.senati.itanes.tour.ui.TourViewModel

/**
 * MainActivity: pantalla principal (lista del recorrido).
 * Flujo: Main -> Detalle (Intent explicito con Lugar) -> Maps (Intent implicito) / Favoritos.
 */
class MainActivity : AppCompatActivity() {

    private val vm: TourViewModel by viewModels()
    private lateinit var adapter: LugarAdapter

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.rvLugares)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val txtEstado = findViewById<TextView>(R.id.txtEstado)
        adapter = LugarAdapter(emptyList()) {
            startActivity(Intent(this, DetalleActivity::class.java).putExtra("lugar", it))
        }
        // Responsive: 1 col en smartphone, 2 col en tablet (values/values-sw600dp/integers.xml)
        val cols = resources.getInteger(R.integer.tour_grid_columns)
        rv.layoutManager = GridLayoutManager(this, cols)
        rv.adapter = adapter

        findViewById<Button>(R.id.btnFavoritos).setOnClickListener {
            startActivity(Intent(this, FavoritosActivity::class.java))
        }

        // Spinner SOLO en la primera carga (aún sin datos). Después no molesta.
        var primeraCarga = true
        vm.cargando.observe(this) {
            loading.visibility = if (it && primeraCarga) View.VISIBLE else View.GONE
        }
        vm.lugares.observe(this) { lista ->
            adapter.actualizar(lista)
            if (lista.isEmpty() && primeraCarga) {
                txtEstado.text = "Sin datos locales. Conecta una vez para sincronizar."
                Toast.makeText(this, "Sin datos: activa internet una vez para sincronizar", Toast.LENGTH_LONG).show()
            } else if (lista.isNotEmpty()) {
                txtEstado.text = "${lista.size} puntos en el recorrido"
            }
            primeraCarga = false
        }

        vm.cargar()
    }

    override fun onResume() {
        super.onResume()
        // Al volver de Favoritos/Detalle SOLO se actualizan las estrellas ★ (SQLite local).
        // Sin spinner, sin re-descarga por red: evita ese "refrescandose..." constante.
        vm.recargarLocales()
    }
}
