package tomuch.coffee.tokoonline.app

import retrofit2.Call
import retrofit2.http.*
import tomuch.coffee.tokoonline.model.Checkout
import tomuch.coffee.tokoonline.model.ResponModel
import tomuch.coffee.tokoonline.model.rajaongkir.ResponOngkir

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") nomortlp: String,
        @Field("password") password: String,
        @Field("fcm") fcm: String
    ): Call<ResponModel>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("fcm") fcm: String
    ): Call<ResponModel>

    @POST("chekout")
    fun chekout(
        @Body data: Checkout
    ): Call<ResponModel>

    @GET("produk")
    fun getProduk(): Call<ResponModel>

    @GET("province")
    fun getProvinsi(
        @Header("key") key: String
    ): Call<ResponModel>

    @GET("city")
    fun getKota(
        @Header("key") key: String,
        @Query("province") id: String
    ): Call<ResponModel>

    @GET("kecamatan")
    fun getKecamatan(
        @Query("id_kota") id: Int
    ): Call<ResponModel>

    @FormUrlEncoded
    @POST("cost")
    fun onkir(
        @Header("key") key: String,
        @Field("origin") origin: String,
        @Field("destination") destination: String,
        @Field("weight") weight: Int,
        @Field("courier") courier: String
    ): Call<ResponOngkir>

    @GET("chekout/user/{id}")
    fun getHistory(
        @Path("id") id: Int
    ): Call<ResponModel>

    @POST("chekout/batal/{id}")
    fun batalChekout(
        @Path("id") id: Int
    ): Call<ResponModel>

}