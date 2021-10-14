package com.drozdova.catapi


import androidx.annotation.IntRange
import com.drozdova.catapi.data.ApiData
import com.drozdova.catapi.data.Cat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {
    @GET("/v1/images/search?api_key=a3434525-23dd-47fb-941d-ba3c551553f9") //
    suspend fun getCatsList(
        @Query("page") @IntRange(from = 0) page: Int = 0,
        @Query("limit") @IntRange(from = 1) limit: Int = 10
    ) : Response<List<ApiData>>
}

object CatApiImpl {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://api.thecatapi.com")
        .build()

//    val catService: CatApi = retrofit.create(CatApi::class.java)
    private val _catService: CatApi = retrofit.create(CatApi::class.java)
    val catService = _catService
}

