package dev.nairnei.stonewallet.view.pages

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
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
        var textViewLastUpdate = findViewById<TextView>(R.id.textViewNegociateLastQuotation)

        var textViewBritaBuy = findViewById<TextView>(R.id.textViewBritaBuy)
        var textViewBritaSell = findViewById<TextView>(R.id.textViewBritaSell)

        var textViewBitcoinBuy = findViewById<TextView>(R.id.textViewBitcoinBuy)
        var textViewBitcoinSell = findViewById<TextView>(R.id.textViewBitcoinSell)

        ///find buttons
        var buttonReport = findViewById<Button>(R.id.buttonReport)
        var buttonUpdateQuotation = findViewById<Button>(R.id.buttonUpdateQuotation)

        ///find card
        var cardBrita = findViewById<CardView>(R.id.cardViewBrita)
        var cardBitcoin = findViewById<CardView>(R.id.cardViewBitcoin)
        var cardReal = findViewById<CardView>(R.id.cardViewReal)

        ///instantiate
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        repositoryViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

        ///add observers
        homeViewModel.getMBitcoin().observe(this, Observer {
            textViewBitcoinBuy.text = "Compra: " + it?.ticker?.buy
            textViewBitcoinSell.text = "Venda: " + it?.ticker?.sell
        })

        homeViewModel.getOlinda().observe(this, Observer {
            textViewBritaBuy.text = "Compra: " + it?.value?.get(0)?.cotacaoCompra
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

        cardBrita.setOnClickListener {
            negociateDialog("Brita")
        }

        cardBitcoin.setOnClickListener {
            negociateDialog("Bitcoin")
        }

        cardReal.setOnClickListener {
            negociateDialog("Real")
        }


    }

    fun negociateDialog(typeDialog: String) {
        val choiceDialog = Dialog(this)

        choiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        choiceDialog.setCancelable(true)
        choiceDialog.setContentView(R.layout.dialog_choice)

        var firstButton = choiceDialog.findViewById<Button>(R.id.button3)
        var secondButton = choiceDialog.findViewById<Button>(R.id.button4)

        when (typeDialog) {
            "Brita" -> {
                firstButton.text = "Trocar Brita por Bitcoin"
                secondButton.text = "Vender Brita por Real"

                firstButton.setOnClickListener {
                    val intent = Intent(this, NegotiateActivity::class.java)
                    intent.putExtra("fromCoin", "Brita")
                    intent.putExtra("toCoin", "Bitcoin")
                    intent.putExtra("token", currentUser)
                    choiceDialog.dismiss()
                    startActivity(intent)
                }

                secondButton.setOnClickListener {
                    val intent = Intent(this, NegotiateActivity::class.java)
                    intent.putExtra("fromCoin", "Brita")
                    intent.putExtra("toCoin", "Real")
                    intent.putExtra("token", currentUser)
                    choiceDialog.dismiss()
                    startActivity(intent)
                }
            }
            "Bitcoin" -> {
                firstButton.text = "Trocar Bitcoin por Brita"
                secondButton.text = "Vender Bitcoin por Real"
                firstButton.setOnClickListener {
                    val intent = Intent(this, NegotiateActivity::class.java)
                    intent.putExtra("fromCoin", "Bitcoin")
                    intent.putExtra("toCoin", "Brita")
                    intent.putExtra("token", currentUser)
                    choiceDialog.dismiss()
                    startActivity(intent)
                }
                secondButton.setOnClickListener {
                    val intent = Intent(this, NegotiateActivity::class.java)
                    intent.putExtra("fromCoin", "Bitcoin")
                    intent.putExtra("toCoin", "Real")
                    intent.putExtra("token", currentUser)
                    choiceDialog.dismiss()
                    startActivity(intent)
                }
            }
            "Real" -> {
                firstButton.text = "Comprar Brita com Real"
                secondButton.text = "Comprar Bitcoin com Real"
                firstButton.setOnClickListener {
                    val intent = Intent(this, NegotiateActivity::class.java)
                    intent.putExtra("fromCoin", "Real")
                    intent.putExtra("toCoin", "Brita")
                    intent.putExtra("token", currentUser)
                    choiceDialog.dismiss()
                    startActivity(intent)
                }

                secondButton.setOnClickListener {
                    val intent = Intent(this, NegotiateActivity::class.java)
                    intent.putExtra("fromCoin", "Real")
                    intent.putExtra("toCoin", "Bitcoin")
                    intent.putExtra("token", currentUser)
                    choiceDialog.dismiss()
                    startActivity(intent)
                }
            }
        }
        choiceDialog.show()
    }
}