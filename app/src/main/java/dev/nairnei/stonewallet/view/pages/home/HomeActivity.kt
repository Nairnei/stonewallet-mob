package dev.nairnei.stonewallet.view.pages.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.nairnei.stonewallet.R

///todo: refactor sugestion: olinda -> brita
///todo: refactor sugestion: mercadoBitcoin -> bitcoin
class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var textViewWelcomeUser = findViewById<TextView>(R.id.textViewWelcomeUser)
        var textViewCurrentCash = findViewById<TextView>(R.id.textViewCurrentCash)

        var textViewOlindaValue = findViewById<TextView>(R.id.textViewOlindaValue)
        var textViewMercadoBitcoinValue = findViewById<TextView>(R.id.textViewMercadoBitcoinValue)
        var textViewRealValue = findViewById<TextView>(R.id.textViewRealValue)

        var buttonReport = findViewById<Button>(R.id.buttonReport)
        var buttonUpdateQuotation = findViewById<Button>(R.id.buttonUpdateQuotation)

        ///instantiate and init viewModel
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.init()

        ///add observers
        homeViewModel.getMBitcoin().observe(this, Observer {
            textViewMercadoBitcoinValue.text = it?.ticker?.buy ?: ""
        })

        homeViewModel.getOlinda().observe(this, Observer {
            textViewOlindaValue.text = (it?.value?.get(0)?.cotacaoCompra ?: "").toString()
        })

        ///fetchData
        homeViewModel.fetchMercadoBitcoin()
        homeViewModel.fetchOlinda()

        ///buttons actions
        buttonUpdateQuotation.setOnClickListener {
            homeViewModel.fetchMercadoBitcoin()
            homeViewModel.fetchOlinda()
        }


    }
}