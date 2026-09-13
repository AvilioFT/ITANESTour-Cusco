package pe.senati.itanes.tour.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.senati.itanes.tour.data.TourRepository
import pe.senati.itanes.tour.data.model.Lugar

/**
 * TourViewModel: parte MVVM.
 * ¿Para qué? La Activity NO pide datos directo; observa este ViewModel.
 * Sobrevive a rotación de pantalla y usa coroutines (hilo IO) para no congelar la UI.
 * Pregunta guía 1: elegimos MVVM porque separa UI/datos y es el estándar actual.
 */
class TourViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = TourRepository(app.applicationContext)
    private val _lugares = MutableLiveData<List<Lugar>>(emptyList())
    val lugares: LiveData<List<Lugar>> = _lugares
    private val _cargando = MutableLiveData(false)
    val cargando: LiveData<Boolean> = _cargando

    fun cargar() {
        _cargando.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try { _lugares.postValue(repo.obtenerLugares()) }
            catch (_: Exception) { _lugares.postValue(emptyList()) }
            finally { _cargando.postValue(false) }
        }
    }

    /**
     * Recarga SOLO local (SQLite), sin red ni spinner.
     * ¿Para qué? Al volver de Favoritos la lista refleja los ★ guardados,
     * pero SIN ese "refrescandose..." de arriba ni re-descarga por red.
     */
    fun recargarLocales() {
        viewModelScope.launch(Dispatchers.IO) {
            _lugares.postValue(repo.favoritosConEstado())
        }
    }
}
