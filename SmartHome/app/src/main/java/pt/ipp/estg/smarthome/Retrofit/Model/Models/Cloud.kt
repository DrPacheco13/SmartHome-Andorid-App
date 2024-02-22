package pt.ipp.estg.smarthome.Retrofit.Model.Models

import com.google.gson.annotations.SerializedName


data class Cloud (

    @SerializedName("enabled"   ) var enabled   : Boolean? = null,
    @SerializedName("connected" ) var connected : Boolean? = null

)