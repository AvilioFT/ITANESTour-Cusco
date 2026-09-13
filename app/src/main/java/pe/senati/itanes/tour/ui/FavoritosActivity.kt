package pe.senati.itanes.tour.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.senati.itanes.tour.R
import pe.senati.itanes.tour.data.TourRepository

/** FavoritosActivity: lee SOLO SQLite local (funciona 100% offline). */
class FavoritosActivity : AppCompatActivity() {
    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        setContentView(R.layout.activity_favoritos)
        title = "Mis favoritos"
        val rv = findViewById<RecyclerView>(R.id.rvFav)
        val vacio = findViewById<TextView>(R.id.txtVacio)
        rv.layoutManager = LinearLayoutManager(this)
        val ad = LugarAdapter(emptyList()) {
            startActivity(Intent(this, DetalleActivity::class.java).putExtra("lugar", it))
        }
        rv.adapter = ad
    }

    override fun onResume() {
        super.onResume()
        val favs = TourRepository(this).favoritos()
        (findViewById<RecyclerView>(R.id.rvFav).adapter as LugarAdapter).actualizar(favs)
        findViewById<TextView>(R.id.txtVacio).text =
            if (favs.isEmpty()) "Aún no guardas favoritos. Abre un lugar y pulsa ☆." else "${favs.size} favorito(s)"
    }
}
