package com.example.myapplication

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface Endpoint {

    @GET("getcars/")
    suspend fun getcars(): Response<List<Voiture>>

    @GET("getcar/{id}")
    suspend fun getcar(@Path("id")id:Int): Response<Voiture>
    @GET("getuser/{id}")
    suspend fun getuser(@Path("id")id:Int?): Response<user>

    @GET("getreservations/{id}")
    suspend fun getreservations(@Path("id")id:Int?): Response<List<ReservationCars>>

    @POST("addreservation")
    suspend fun addReservation(@Body reservation: Reservation):Response<String>


    @POST("addaccountStep02")
    suspend fun addaccountStep02(@Body credit_card: credit_card):Response<String>

    @GET("getuser/{phone_number}/{password}")
    suspend fun verifyUser(@Path("phone_number")phone_number:String, @Path("password") password:String): Response<user?>

    @Multipart
    @POST("addaccountStep01")
    suspend fun addPDV(@Part image: MultipartBody.Part,
                       @Part user: MultipartBody.Part):Response<String>

    @Multipart
    @POST("addaccountStep03")
    suspend fun addPDL(@Part image: MultipartBody.Part,
                       @Part driverlicense: MultipartBody.Part):Response<String>

    @POST("CarAfterReservation")
    suspend fun CarAfterReservation(@Body credit_card: credit_card):Response<String>

}