package dev.nairnei.stonewallet.service.mercadobitcoin

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MercadoBitcoinService {

    companion object {
        private var baseUrl = "https://www.mercadobitcoin.net/"
    }

    fun getRetrofitInstace(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}