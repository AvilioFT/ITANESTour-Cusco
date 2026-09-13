package pe.senati.itanes.tour.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.senati.itanes.tour.R
import pe.senati.itanes.tour.data.model.Lugar

/**
 * Adapter: convierte List<Lugar> en filas visibles.
 * RecyclerView recicla vistas. Glide usa cache para no descargar dos veces.
 */
class LugarAdapter(
    private var items: List<Lugar>,
    private val onClick: (Lugar) -> Unit
) : RecyclerView.Adapter<LugarAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.imgLugar)
        val nombre: TextView = v.findViewById(R.id.txtNombre)
        val desc: TextView = v.findViewById(R.id.txtDesc)
        val fav: TextView = v.findViewById(R.id.txtFav)
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int): VH =
        VH(LayoutInflater.from(p.context).inflate(R.layout.item_lugar, p, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(h: VH, pos: Int) {
        val l = items[pos]
        h.nombre.text = "${l.orden}. ${l.nombre}"
        h.desc.text = if (l.descripcion.length > 90) l.descripcion.take(90) + "..." else l.descripcion
        h.fav.visibility = if (l.favorito == 1) View.VISIBLE else View.GONE
        Glide.with(h.itemView)
            .load(l.imagenUrl)
            .centerCrop()
            .into(h.img)
        h.itemView.setOnClickListener { onClick(l) }
    }

    fun actualizar(nueva: List<Lugar>) { items = nueva; notifyDataSetChanged() }
}
