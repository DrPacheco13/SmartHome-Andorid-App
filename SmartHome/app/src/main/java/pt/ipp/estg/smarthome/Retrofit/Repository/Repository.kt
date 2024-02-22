package pt.ipp.estg.smarthome.Retrofit.Repository

import androidx.lifecycle.LiveData
import pt.ipp.estg.smarthome.Retrofit.Api.SHRetrofitApi
import pt.ipp.estg.smarthome.Retrofit.Model.SHModel
import pt.ipp.estg.smarthome.Retrofit.Room.SHDevice
import pt.ipp.estg.smarthome.Retrofit.Room.SHDeviceDao
import retrofit2.Response

class Repository(val deviceDao: SHDeviceDao, val rest: SHRetrofitApi) {


    /**
     * Sends a get request to api
     */
    suspend fun getDevicesUrl(url: String): Response<SHModel> {
        return rest.getDevice(url)
    }


    fun getDevices(): LiveData<List<SHDevice>> {
        return deviceDao.getDevices()
    }

    fun getDevice(ip:String,port:String): LiveData<SHDevice> {
        return deviceDao.getOneDevice(ip,port)
    }

    suspend fun insert(device:SHDevice){
        deviceDao.insert(device)
    }

    suspend fun update(device:SHDevice){
        deviceDao.update(device)
    }

    suspend fun delete(){
        deviceDao.delete()
    }

    suspend fun deleteOne(ip:String,port:String){
        deviceDao.deleteOneDevice(ip,port)
    }

    /*

    /**
     * Sends a get request to api for /status
     */
     suspend fun getStatus(url: String): Response<SHModel> {
        return rest.getDevice(url+"/status")
    }

    /**
     * Sends a get request to api for /light/0?turn=on
     */
    suspend fun lightOn(url: String): Response<SHModel> {
        return rest.getDevice(url+"/light/0?turn=on")
    }

    /**
     * Sends a get request to api for /light/0?turn=off
     */
    suspend fun lightOff(url: String): Response<SHModel> {
        return rest.getDevice(url+"/light/0?turn=of")
    }

    /**
     * Sends a get request to api for /relay/0?turn=on
     */
    suspend fun plugOn(url: String): Response<SHModel> {
        return rest.getDevice(url+"/relay/0?turn=on")
    }

    /**
     * Sends a get request to api for /relay/0?turn=off
     */
    suspend fun plugOff(url: String): Response<SHModel> {
        return rest.getDevice(url+"/relay/0?turn=off")
    }

    /**
     * Sends a get request to api for /roller/0?go=open
     */
    suspend fun blindOpen(url: String): Response<SHModel> {
        return rest.getDevice(url+"/roller/0?go=open")
    }

    /**
     * Sends a get request to api for /roller/0?go=close
     */
    suspend fun blindClose(url: String): Response<SHModel> {
        return rest.getDevice(url+"/roller/0?go=close")
    }
     */
}