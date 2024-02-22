package pt.ipp.estg.smarthome.Retrofit.Model.Models

import com.google.gson.annotations.SerializedName


data class Rollers (

    @SerializedName("state"           ) var state           : String?  = null,
    @SerializedName("source"          ) var source          : String?  = null,
    @SerializedName("power"           ) var power           : Int?     = null,
    @SerializedName("is_valid"        ) var isValid         : Boolean? = null,
    @SerializedName("safety_switch"   ) var safetySwitch    : Boolean? = null,
    @SerializedName("overtemperature" ) var overtemperature : Boolean? = null,
    @SerializedName("stop_reason"     ) var stopReason      : String?  = null,
    @SerializedName("last_direction"  ) var lastDirection   : String?  = null,
    @SerializedName("current_pos"     ) var currentPos      : Int?     = null,
    @SerializedName("calibrating"     ) var calibrating     : Boolean? = null,
    @SerializedName("positioning"     ) var positioning     : Boolean? = null

)