package dev.nairnei.stonewallet.service.mercadobitcoin

import dev.nairnei.stonewallet.model.MercadoBitcoinResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MercadoBitcoinApi {

    @GET("/api/{coin}/{method}")
    fun getBitcoinCotation(
        @Path("coin") coin: String,
        @Path("method") method: String,
    ): Call<MercadoBitcoinResponse>

}