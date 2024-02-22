package pt.ipp.estg.smarthome.Retrofit.Room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SHDeviceDao {

    @Query("select * from SHDevice")
    fun getDevices(): LiveData<List<SHDevice>>

    @Query("select * from SHDevice where Port = :port AND Ip = :ip ")
    fun getOneDevice(ip:String, port:String): LiveData<SHDevice>

    @Query("delete from SHDevice where Port = :port AND Ip = :ip ")
    suspend fun deleteOneDevice(ip:String, port:String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(device:SHDevice)

    @Update
    suspend fun update(device:SHDevice)

    @Query("DELETE FROM SHDevice")
    suspend fun delete()
}