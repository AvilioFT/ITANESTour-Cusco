package pe.senati.itanes.tour.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.senati.itanes.tour.R
import pe.senati.itanes.tour.data.TourRepository
import pe.senati.itanes.tour.data.model.Lugar

/**
 * DetalleActivity: foto + info + 3 acciones (ruta, favorito, compartir).
 * Ruta en auto: Intent implicito a Google Maps, sin API Key.
 * Favorito: se guarda en SQLite local, funciona offline.
 */
class DetalleActivity : AppCompatActivity() {

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        setContentView(R.layout.activity_detalle)
        @Suppress("DEPRECATION")
        val lugar = intent.getSerializableExtra("lugar") as? Lugar
            ?: run { finish(); return }
        val repo = TourRepository(this)

        findViewById<TextView>(R.id.txtNombre).text = lugar.nombre
        findViewById<TextView>(R.id.txtDesc).text = lugar.descripcion
        Glide.with(this)
            .load(lugar.imagenUrl)
            .centerCrop()
            .into(findViewById<ImageView>(R.id.imgDetalle))

        val btnFav = findViewById<Button>(R.id.btnFav)
        fun pintaFav() {
            btnFav.text = if (lugar.favorito == 1) "Quitar favorito" else "Guardar favorito"
        }
        pintaFav()

        btnFav.setOnClickListener {
            val nuevo = lugar.favorito != 1
            lifecycleScope.launch(Dispatchers.IO) {
                repo.toggleFavorito(lugar, nuevo)
                lugar.favorito = if (nuevo) 1 else 0
                withContext(Dispatchers.Main) {
                    pintaFav()
                    Toast.makeText(
                        this@DetalleActivity,
                        if (nuevo) "Guardado en favoritos" else "Quitado de favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        findViewById<Button>(R.id.btnRuta).setOnClickListener {
            val uri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=${lugar.latitud},${lugar.longitud}&travelmode=driving")
            try { startActivity(Intent(Intent.ACTION_VIEW, uri)) }
            catch (_: Exception) { Toast.makeText(this, "No se pudo abrir Maps", Toast.LENGTH_SHORT).show() }
        }

        findViewById<Button>(R.id.btnCompartir).setOnClickListener {
            val txt = "ITANES Tour: ${lugar.nombre} - ${lugar.descripcion} https://www.google.com/maps/search/?api=1&query=${lugar.latitud},${lugar.longitud}"
            startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"; putExtra(Intent.EXTRA_TEXT, txt)
            }, "Compartir lugar"))
        }
    }
}
