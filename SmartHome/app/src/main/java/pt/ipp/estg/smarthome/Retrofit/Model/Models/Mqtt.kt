package pt.ipp.estg.smarthome.Retrofit.Model.Models

import com.google.gson.annotations.SerializedName


data class Mqtt (

    @SerializedName("connected" ) var connected : Boolean? = null

)