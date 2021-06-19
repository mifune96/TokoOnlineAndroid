package tomuch.coffee.tokoonline.app

import retrofit2.Call
import retrofit2.http.*
import tomuch.coffee.tokoonline.model.ResponModel

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") nomortlp: String,
        @Field("password") password: String
    ): Call<ResponModel>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponModel>

    @GET("produk")
    fun getProduk(): Call<ResponModel>

    @GET("provinsi")
    fun getProvinsi(): Call<ResponModel>

    @GET("kota")
    fun getKota(
        @Query("id_provinsi") id: Int
    ): Call<ResponModel>

    @GET("kecamatan")
    fun getKecamatan(
        @Query("id_kota") id: Int
    ): Call<ResponModel>



}