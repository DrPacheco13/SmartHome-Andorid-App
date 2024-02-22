package pt.ipp.estg.smarthome.Retrofit.Model.Models

import com.google.gson.annotations.SerializedName


data class Lights (

    @SerializedName("ison"            ) var ison           : Boolean? = null,
    @SerializedName("source"          ) var source         : String?  = null,
    @SerializedName("has_timer"       ) var hasTimer       : Boolean? = null,
    @SerializedName("timer_started"   ) var timerStarted   : Int?     = null,
    @SerializedName("timer_duration"  ) var timerDuration  : Int?     = null,
    @SerializedName("timer_remaining" ) var timerRemaining : Int?     = null,
    @SerializedName("mode"            ) var mode           : String?  = null,
    @SerializedName("red"             ) var red            : Int?     = null,
    @SerializedName("green"           ) var green          : Int?     = null,
    @SerializedName("blue"            ) var blue           : Int?     = null,
    @SerializedName("white"           ) var white          : Int?     = null,
    @SerializedName("gain"            ) var gain           : Int?     = null,
    @SerializedName("temp"            ) var temp           : Int?     = null,
    @SerializedName("brightness"      ) var brightness     : Int?     = null,
    @SerializedName("effect"          ) var effect         : Int?     = null,
    @SerializedName("transition"      ) var transition     : Int?     = null

)