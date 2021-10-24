package dev.nairnei.stonewallet.service.olinda

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OlindaService {

    companion object {
        private var baseUrl = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/"
    }

    fun getRetrofitInstace(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}