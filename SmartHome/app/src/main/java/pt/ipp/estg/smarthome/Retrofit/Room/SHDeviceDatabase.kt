package pt.ipp.estg.smarthome.Retrofit.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SHDevice::class], version = 6)
abstract class SHDeviceDatabase : RoomDatabase() {

    abstract fun getDeviceDao():SHDeviceDao

    companion object{
        private var INSTANCE:SHDeviceDatabase?=null

        fun getDatabase(context: Context):SHDeviceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    SHDeviceDatabase::class.java,
                    "device-database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}