package pt.ipp.estg.smarthome.common

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.NotificationCompat
import pt.ipp.estg.smarthome.MainActivity
import pt.ipp.estg.smarthome.R
import pt.ipp.estg.smarthome.Retrofit.Room.SHDevice

class DeviceNotification(var context :Context,var shDevice: SHDevice) {
    val channelID: String = "Device"
    val channelName : String = "DeviceNotification"
    val notificationManager= context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationBuilder: NotificationCompat.Builder
    fun fireNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationBuilder = NotificationCompat.Builder(context,channelID)
        notificationBuilder.setSmallIcon(R.drawable.icons8_smart_home_96)
        notificationBuilder.setContentTitle("SmartHome")
        notificationBuilder.setContentText("The device "+ shDevice.Nome + " has been turned on for a long time")
        notificationBuilder.setAutoCancel(true)
        notificationManager.notify(100,notificationBuilder.build())
    }
}