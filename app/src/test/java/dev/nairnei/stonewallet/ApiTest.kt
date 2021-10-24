package dev.nairnei.stonewallet

import android.util.Log
import dev.nairnei.stonewallet.model.MercadoBitcoinResponse
import dev.nairnei.stonewallet.model.OlindaResponse
import dev.nairnei.stonewallet.service.mercadobitcoin.MercadoBitcoinApi
import dev.nairnei.stonewallet.service.mercadobitcoin.MercadoBitcoinService
import dev.nairnei.stonewallet.service.olinda.OlindaApi
import dev.nairnei.stonewallet.service.olinda.OlindaService
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiTest {

    @Test
    fun `Verify we can get Brita from Olinda Api`() {
        val mRepo = OlindaService().getRetrofitInstace()
        val endPoint = mRepo.create(OlindaApi::class.java)
        val sdf = SimpleDateFormat("MM-dd-yyyy")
        val currentDate = sdf.format(Date())
        val response = endPoint.getDolarCotation(
            "'10-22-2020'",
            "1",
            "json",
            "cotacaoCompra,cotacaoVenda,dataHoraCotacao"
        ).execute()
        assertEquals(response.code(), 200)
    }

    @Test
    fun `Verify we can get BITCOIN from MercadoBitcoin Api`() {
        val mRepo = MercadoBitcoinService().getRetrofitInstace()
        val endPoint = mRepo.create(MercadoBitcoinApi::class.java)
        val response = endPoint.getBitcoinCotation("BTC", "ticker").execute();
        assertEquals(response.code(), 200)
    }
}