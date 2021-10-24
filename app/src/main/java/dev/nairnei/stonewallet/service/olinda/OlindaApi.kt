package dev.nairnei.stonewallet.service.olinda

import dev.nairnei.stonewallet.model.OlindaResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OlindaApi {

    @GET("CotacaoDolarDia(dataCotacao=@dataCotacao)")
    fun getDolarCotation(@Query("@dataCotacao") dataCotacao : String,@Query("top") top : String,@Query("@format") format : String, @Query("@select") select : String): Call<OlindaResponse>

}