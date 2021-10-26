package dev.nairnei.stonewallet.view.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.nairnei.stonewallet.R
import dev.nairnei.stonewallet.viewModel.DaoViewModel
import dev.nairnei.stonewallet.viewModel.HomeViewModel

///todo: refactor sugestion: olinda -> brita
///todo: refactor sugestion: mercadoBitcoin -> bitcoin
class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var repositoryViewModel: DaoViewModel
    private lateinit var currentUser: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val extras = intent.extras
        if (extras != null) {
            currentUser = extras.getString("token").toString()
        }
        ///find textViews
        var textViewWelcomeUser = findViewById<TextView>(R.id.textViewWelcomeUser)

        var textViewRealAmout = findViewById<TextView>(R.id.textViewRealAmount)
        var textViewBritaAmount = findViewById<TextView>(R.id.textViewBritaAmount)
        var textViewBitcoinAmout = findViewById<TextView>(R.id.textViewBitcoinAmount)
        var textViewLastUpdate = findViewById<TextView>(R.id.textViewLastUpdate)

        var textViewBritaBuy = findViewById<TextView>(R.id.textViewBritaBuy)
        var textViewBritaSell = findViewById<TextView>(R.id.textViewBritaSell)

        var textViewBitcoinBuy = findViewById<TextView>(R.id.textViewBitcoinBuy)
        var textViewBitcoinSell = findViewById<TextView>(R.id.textViewBitcoinSell)

        ///find buttons
        var buttonReport = findViewById<Button>(R.id.buttonReport)
        var buttonUpdateQuotation = findViewById<Button>(R.id.buttonUpdateQuotation)

        ///instantiate
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        repositoryViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

        ///add observers
        homeViewModel.getMBitcoin().observe(this, Observer {
            textViewBitcoinBuy.text = "Compra: " + it?.ticker?.buy
            textViewBitcoinSell.text = "Venda: " + it?.ticker?.sell
        })

        homeViewModel.getOlinda().observe(this, Observer {
            textViewBritaBuy.text = "Compra: " + (it?.value?.get(0)?.cotacaoCompra.toString())
            textViewBritaSell.text = "Venda: " + (it?.value?.get(0)?.cotacaoVenda.toString())
        })

        homeViewModel.getLastFetch().observe(this, {
            textViewLastUpdate.text = "Ultima cotação atualizada as " + it
        })

        repositoryViewModel.currentUser().observe(this, {
            textViewWelcomeUser.text = "Bem vindo " + it?.email
            textViewRealAmout.text = "R\$ " + it?.amountReal.toString()
            textViewBritaAmount.text = "USD\$ " + it?.amountBrita.toString()
            textViewBitcoinAmout.text = "BTC " + it?.amountBitcoin.toString()
        })

        ///start ViewModel
        homeViewModel.init()
        repositoryViewModel.init(this.applicationContext)
        repositoryViewModel.setCurrentUser(currentUser)

        ///buttons actions
        buttonUpdateQuotation.setOnClickListener {
            println(repositoryViewModel.currentUser().value?.email)
            homeViewModel.forceFetch()
        }
    }
}