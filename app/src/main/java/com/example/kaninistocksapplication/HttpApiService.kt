package com.example.kaninistocksapplication

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface HttpApiService {
    //@GET("pets")

    //suspend fun getAllPets() : Response<StockListModel>

    @POST("login")
    suspend fun login(@Body user: User):LoginResult

    @POST("register")
    suspend fun register(@Body user: User): Call<Void>?

   /* @POST ("users/me/petInterests")
    suspend fun createNewPetInterest(@Body petId:PetIdClass)*/

}