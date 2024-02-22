package pt.ipp.estg.smarthome.Retrofit.Model.Models

import com.google.gson.annotations.SerializedName


data class ActionsStats (

    @SerializedName("skipped" ) var skipped : Int? = null

)