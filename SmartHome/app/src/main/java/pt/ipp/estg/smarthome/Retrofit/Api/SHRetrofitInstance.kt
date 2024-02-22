package pt.ipp.estg.smarthome.Retrofit.Api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SHRetrofitInstance {

    // Local Url to communicate with the API
    val base_url = "http://10.0.2.2:3000"

    //basic retrofit builder
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    // val api: SHRetrofitApi by lazy {
    //     retrofit.create(SHRetrofitApi::class.java)
    // }
}