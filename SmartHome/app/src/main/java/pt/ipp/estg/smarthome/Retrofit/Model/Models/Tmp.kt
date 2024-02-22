package pt.ipp.estg.smarthome.Retrofit.Model.Models

import com.google.gson.annotations.SerializedName


data class Tmp (

    @SerializedName("tC"       ) var tC      : Double?  = null,
    @SerializedName("tF"       ) var tF      : Double?  = null,
    @SerializedName("is_valid" ) var isValid : Boolean? = null

)