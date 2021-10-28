package dev.nairnei.stonewallet.viewModel

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
    private var lastUpdateTimeLiveData = MutableLiveData<String?>()

    private var olindaLiveData = MutableLiveData<OlindaResponse?>()
    private var mBicoinLiveData = MutableLiveData<MercadoBitcoinResponse?>()
    private val timer: Timer = Timer()

    fun init() {
        repositoryOlinda = MercadoBitcoinService().getRetrofitInstace()
        repositoryMbcoin = OlindaService().getRetrofitInstace()

        ///timer to fetch data every 15 minutes
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                fetchOlinda(null)
                fetchMercadoBitcoin()
                updateLastData()
            }
        }, 0, 900000)
    }

    private fun updateLastData() {
        val formatedDate = SimpleDateFormat("dd-MM-yyyy HH:mm")
        val currentDate = formatedDate.format(Date())
        lastUpdateTimeLiveData.postValue(currentDate)
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

    fun fetchOlinda(date: Date?) {
        val endPoint = repositoryMbcoin.create(OlindaApi::class.java)
        val recursiveDate = Date()
        ///on weekends, there no quotation return, on the current day, quotation is empty :C
        ///I will take the previous business day's quotation
        if(date != null)
        {
            ///86400000: one day in milliseconds
            recursiveDate.time = date.time - 86400000
        }

        val sdf = SimpleDateFormat("MM-dd-yyyy")
        val currentDate = sdf.format(recursiveDate)

        val request = endPoint.getDolarCotation(
            "'$currentDate'",
            "1",
            "json",
            "cotacaoCompra,cotacaoVenda,dataHoraCotacao"
        )

        request.enqueue(object : Callback<OlindaResponse> {
            override fun onResponse(
                call: Call<OlindaResponse>,
                response: Response<OlindaResponse>
            ) {
                if(response.body()?.value?.isEmpty() == true)
                {
                    fetchOlinda(recursiveDate)
                }
                else
                {
                    olindaLiveData.value = response.body()
                }
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

    fun getLastFetch(): MutableLiveData<String?> {
        return lastUpdateTimeLiveData
    }

    fun forceFetch() {
        fetchMercadoBitcoin()
        fetchOlinda(null)
        updateLastData()
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