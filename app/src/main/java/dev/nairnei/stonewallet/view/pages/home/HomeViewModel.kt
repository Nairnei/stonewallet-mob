package dev.nairnei.stonewallet.view.pages.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.nairnei.stonewallet.model.MercadoBitcoinResponse
import dev.nairnei.stonewallet.model.OlindaResponse
import dev.nairnei.stonewallet.service.mercadobitcoin.MercadoBitcoinApi
import dev.nairnei.stonewallet.service.mercadobitcoin.MercadoBitcoinService
import dev.nairnei.stonewallet.service.olinda.OlindaApi
import dev.nairnei.stonewallet.service.olinda.OlindaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel : ViewModel() {

    lateinit var repositoryOlinda: Retrofit
    lateinit var repositoryMbcoin: Retrofit

    private var olindaErrorService = MutableLiveData<String?>()
    private var mBitcoinErrorService = MutableLiveData<String?>()

    private var olindaLiveData = MutableLiveData<OlindaResponse?>()
    private var mBicoinLiveData = MutableLiveData<MercadoBitcoinResponse?>()

    fun init() {
        repositoryOlinda = MercadoBitcoinService().getRetrofitInstace();
        repositoryMbcoin = OlindaService().getRetrofitInstace();
    }

    fun fetchMercadoBitcoin() {
        val endPoint = repositoryOlinda.create(MercadoBitcoinApi::class.java)
        val request = endPoint.getBitcoinCotation("BTC", "ticker")

        request.enqueue(object : Callback<MercadoBitcoinResponse> {
            override fun onResponse(
                call: Call<MercadoBitcoinResponse>,
                response: Response<MercadoBitcoinResponse>
            ) {
                mBicoinLiveData.value = response.body()
            }

            override fun onFailure(call: Call<MercadoBitcoinResponse>, t: Throwable) {
                mBitcoinErrorService.value = t.message
            }
        })
    }

    fun fetchOlinda() {
        val endPoint = repositoryMbcoin.create(OlindaApi::class.java)

        ///fixme: on weekends, there no quotation return
        val sdf = SimpleDateFormat("MM-dd-yyyy")
        val currentDate = sdf.format(Date())

        val request = endPoint.getDolarCotation(
            "'10-22-2020'",
            "1",
            "json",
            "cotacaoCompra,cotacaoVenda,dataHoraCotacao"
        )

        request.enqueue(object : Callback<OlindaResponse> {
            override fun onResponse(
                call: Call<OlindaResponse>,
                response: Response<OlindaResponse>
            ) {
                olindaLiveData.value = response.body()
            }

            override fun onFailure(call: Call<OlindaResponse>, t: Throwable) {
                olindaErrorService.value = t.message
            }
        })

    }

    fun getOlinda(): MutableLiveData<OlindaResponse?> {
        return olindaLiveData
    }

    fun getMBitcoin(): MutableLiveData<MercadoBitcoinResponse?> {
        return mBicoinLiveData
    }

    ///todo: how show error fetch? on toast? in a textView or in alertDialog?
    fun getErrorOlinda(): MutableLiveData<String?> {
        return olindaErrorService
    }

    ///todo: how show error fetch? on toast? in a textView or in alertDialog?
    fun getErrorMBitcoin(): MutableLiveData<String?> {
        return mBitcoinErrorService
    }

}