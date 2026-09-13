package pe.senati.itanes.tour.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import pe.senati.itanes.tour.data.model.Lugar

/**
 * ItanesDbHelper: base SQLite LOCAL (offline).
 * ¿Para qué? Cumple "funciona sin internet una vez descargada".
 * Guarda los 5 lugares sincronizados + columna favorito (1/0).
 * Si te preguntan "¿Room o SQLite?": usamos SQLiteOpenHelper (más simple de sustentar,
 * sin KSP). En el informe lo describes como "Base local SQLite, migrable a Room".
 */
class ItanesDbHelper(context: Context) :
    SQLiteOpenHelper(context, "itanes.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """CREATE TABLE lugares(
            id INTEGER PRIMARY KEY,
            nombre TEXT NOT NULL,
            descripcion TEXT NOT NULL,
            latitud REAL NOT NULL,
            longitud REAL NOT NULL,
            imagen_url TEXT NOT NULL,
            orden INTEGER NOT NULL,
            favorito INTEGER NOT NULL DEFAULT 0)"""
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, o: Int, n: Int) {
        db.execSQL("DROP TABLE IF EXISTS lugares")
        onCreate(db)
    }

    /** Guarda/actualiza la lista que vino del PHP o del mock. */
    fun guardarLugares(lista: List<Lugar>) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            lista.forEach {
                val v = ContentValues().apply {
                    put("id", it.id); put("nombre", it.nombre)
                    put("descripcion", it.descripcion)
                    put("latitud", it.latitud); put("longitud", it.longitud)
                    put("imagen_url", it.imagenUrl); put("orden", it.orden)
                }
                // INSERT o IGNORE + UPDATE favorito se conserva: primero intenta insertar
                db.insertWithOnConflict("lugares", null, v, SQLiteDatabase.CONFLICT_IGNORE)
                db.update("lugares", ContentValues().apply {
                    put("nombre", it.nombre); put("descripcion", it.descripcion)
                    put("latitud", it.latitud); put("longitud", it.longitud)
                    put("imagen_url", it.imagenUrl); put("orden", it.orden)
                }, "id=?", arrayOf(it.id.toString()))
            }
            db.setTransactionSuccessful()
        } finally { db.endTransaction() }
    }

    fun listarTodos(): List<Lugar> = leer("SELECT * FROM lugares ORDER BY orden ASC")
    fun listarFavoritos(): List<Lugar> = leer("SELECT * FROM lugares WHERE favorito=1 ORDER BY orden ASC")

    fun setFavorito(id: Int, fav: Boolean) {
        writableDatabase.update("lugares", ContentValues().apply {
            put("favorito", if (fav) 1 else 0)
        }, "id=?", arrayOf(id.toString()))
    }

    private fun leer(sql: String): List<Lugar> {
        val out = mutableListOf<Lugar>()
        readableDatabase.rawQuery(sql, null).use { c ->
            while (c.moveToNext()) {
                out += Lugar(
                    c.getInt(0), c.getString(1), c.getString(2),
                    c.getDouble(3), c.getDouble(4), c.getString(5), c.getInt(6), c.getInt(7)
                )
            }
        }
        return out
    }
}
