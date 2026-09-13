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
import androidx.recyclerview.widget.LinearLayoutManager
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
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        findViewById<Button>(R.id.btnFavoritos).setOnClickListener {
            startActivity(Intent(this, FavoritosActivity::class.java))
        }

        // Fix mensaje falso: el LiveData arranca vacio antes de cargar.
        // Solo avisamos cuando termino de cargar (cargando == false) y sigue vacio.
        var cargandoAhora = true
        vm.cargando.observe(this) {
            cargandoAhora = it
            loading.visibility = if (it) View.VISIBLE else View.GONE
            if (it) txtEstado.text = "Cargando recorrido..."
        }
        vm.lugares.observe(this) { lista ->
            adapter.actualizar(lista)
            if (lista.isEmpty() && !cargandoAhora) {
                txtEstado.text = "Sin datos locales. Conecta internet una vez para sincronizar."
                Toast.makeText(this, "Sin datos: activa internet una vez para sincronizar", Toast.LENGTH_LONG).show()
            } else if (lista.isNotEmpty()) {
                txtEstado.text = "${lista.size} puntos en el recorrido"
            }
        }

        vm.cargar()
    }

    override fun onResume() { super.onResume(); vm.cargar() }
}
