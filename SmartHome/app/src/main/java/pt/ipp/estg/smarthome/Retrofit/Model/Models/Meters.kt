package pt.ipp.estg.smarthome.Retrofit.Model.Models

import com.google.gson.annotations.SerializedName


data class Meters (

    @SerializedName("power"     ) var power     : Int?           = null,
    @SerializedName("overpower" ) var overpower : Int?           = null,
    @SerializedName("is_valid"  ) var isValid   : Boolean?       = null,
    @SerializedName("timestamp" ) var timestamp : Int?           = null,
    @SerializedName("counters"  ) var counters  : ArrayList<Float> = arrayListOf(),
    @SerializedName("total"     ) var total     : Double?        = null

)