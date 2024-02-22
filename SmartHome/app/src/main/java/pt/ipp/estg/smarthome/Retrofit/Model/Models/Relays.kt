package pt.ipp.estg.smarthome.Retrofit.Model.Models

import com.google.gson.annotations.SerializedName


data class Relays (

    @SerializedName("ison"            ) var ison           : Boolean? = null,
    @SerializedName("has_timer"       ) var hasTimer       : Boolean? = null,
    @SerializedName("timer_started"   ) var timerStarted   : Int?     = null,
    @SerializedName("timer_duration"  ) var timerDuration  : Int?     = null,
    @SerializedName("timer_remaining" ) var timerRemaining : Int?     = null,
    @SerializedName("overpower"       ) var overpower      : Boolean? = null,
    @SerializedName("source"          ) var source         : String?  = null

)