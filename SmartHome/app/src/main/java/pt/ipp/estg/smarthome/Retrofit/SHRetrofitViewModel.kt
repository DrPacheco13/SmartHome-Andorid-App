package pt.ipp.estg.smarthome.Retrofit

import android.app.Application
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pt.ipp.estg.smarthome.DevicesList
import pt.ipp.estg.smarthome.NoDevice
import pt.ipp.estg.smarthome.Retrofit.Api.SHRetrofitApi
import pt.ipp.estg.smarthome.Retrofit.Api.SHRetrofitInstance
import pt.ipp.estg.smarthome.Retrofit.Repository.Repository
import pt.ipp.estg.smarthome.Retrofit.Room.SHDevice
import pt.ipp.estg.smarthome.Retrofit.Room.SHDeviceDatabase
import java.net.HttpURLConnection
import java.net.URL

class   SHRetrofitViewModel(application: Application) : AndroidViewModel(application){


    val rest: SHRetrofitApi
    val repository: Repository
    init {
        val db = SHDeviceDatabase.getDatabase(application)
        rest = SHRetrofitInstance.getInstance().create(SHRetrofitApi :: class.java)
        repository = Repository(db.getDeviceDao(), rest)
    }


    /**
     * Sends a get request for /status and saves it
     */
    fun getStatus(url:String,nome:String){
        var code: Int = 0
        try {
            val urls = URL(url)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val connection: HttpURLConnection = urls.openConnection() as HttpURLConnection
                    code = connection.getResponseCode()
                } catch (e: Exception) {
                }

                if (code == 404) {
                    addDevice(url, nome)
                } else {

                }
            }
        }catch (e: Exception){
        }

    }

    fun verifyDevices(ip:String , port:String) {
        var code: Int = 0
        val urls = URL("http://$ip:$port")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val connection: HttpURLConnection = urls.openConnection() as HttpURLConnection
                code = connection.getResponseCode()
            } catch (e: Exception) {
            }

            if (code != 404) {
                repository.deleteOne(ip, port)
            }
        }
    }

    fun getAllDevices(): LiveData<List<SHDevice>> {
        return repository.getDevices()
    }

    /**
     * Sends a get request for /light/0?turn=on
     */
    fun getDevicesUrl(url:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.getDevicesUrl(url)
        }
    }



    fun insertDevice(device:SHDevice){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(device)
        }
    }

    fun updateDevice(device:SHDevice){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(device)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete()
        }
    }

    fun deleteOne(ip:String,port:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteOne(ip,port)
        }
    }

    fun getOneDevice(ip:String,port:String): LiveData<SHDevice> {
        return repository.getDevice(ip,port)
    }

    fun addDevice(url:String,nome:String) {
        viewModelScope.launch() {
            val port = url.drop(16)
            var ip = url.drop(7)
            ip = ip.dropLast(5)
            val data = repository.getDevicesUrl(url + "/status")
            if (data.isSuccessful) {
                val content = data.body()
                val mac = content?.mac
                if (content?.lights.toString() != "[]") {
                    mac?.let { SHDevice(port, ip, nome, "lights", it) }
                        ?.let { insertDevice(it) }
                } else if (content?.relays.toString() != "[]") {
                    mac?.let { SHDevice(port, ip, nome, "relays", it) }
                        ?.let { insertDevice(it) }
                } else if (content?.rollers.toString() != "[]") {
                    mac?.let { SHDevice(port, ip, nome, "rollers", it) }
                        ?.let { insertDevice(it) }
                }
            }
        }
    }

    /*
     /**
     * Sends a get request for /light/0?turn=on
     */
    fun lightOn(url:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.getDevicesUrl(url+"/light/0?turn=on")
        }
    }

    /**
     * Sends a get request for /light/0?turn=off
     */
    fun lightOff(url:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.lightOff(url)
        }
    }

    /**
     * Sends a get request for /relay/0?turn=on
     */
    fun plugOn(url:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.plugOn(url)
        }
    }

    /**
     * Sends a get request for /relay/0?turn=off
     */
    fun plugOff(url:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.plugOff(url)
        }
    }

    /**
     * Sends a get request for /roller/0?go=open
     */
    fun blindOpen(url:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.blindOpen(url)
        }
    }

    /**
     * Sends a get request for /roller/0?go=close
     */
    fun blindClose(url:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.blindClose(url)
        }
    }
     */

}