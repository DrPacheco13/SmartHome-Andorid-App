package pt.ipp.estg.smarthome.Retrofit.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["Port", "Ip"])
data class SHDevice(
    val Port:String,
    val Ip:String,
    val Nome:String,
    val Tipo:String,
    val Mac:String,
) {}
