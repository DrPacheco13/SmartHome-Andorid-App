package pt.ipp.estg.smarthome.Retrofit.Api

import pt.ipp.estg.smarthome.Retrofit.Model.SHModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface SHRetrofitApi {

    @GET
    suspend fun getDevice(@Url url:String): Response<SHModel>


    /*
    /**
     * Sends a get request for /status
     */
    @GET
    suspend fun getStatus(@Url url:String): Response<SHModel>

    /**
     * Sends a get request for /light/0?turn=on
     */
    @GET
    suspend fun lightOn(@Url url:String): Response<SHModel>

    /**
     * Sends a get request for /light/0?turn=of
     */
    @GET
    suspend fun lightOff(@Url url:String): Response<SHModel>

    /**
     * Sends a get request for /relay/0?turn=on
     */
    @GET
    suspend fun plugOn(@Url url:String): Response<SHModel>

    /**
     * Sends a get request for /relay/0?turn=off
     */
    @GET
    suspend fun plugOff(@Url url:String): Response<SHModel>

    /**
     * Sends a get request for /roller/0?go=open
     */
    @GET
    suspend fun blindOpen(@Url url:String): Response<SHModel>

    /**
     * Sends a get request for /roller/0?go=close
     */
    @GET
    suspend fun blindClose(@Url url:String): Response<SHModel>

     */
}