package dev.nairnei.stonewallet.view.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.nairnei.stonewallet.R
import dev.nairnei.stonewallet.viewModel.DaoViewModel
import dev.nairnei.stonewallet.viewModel.NegociateViewModel

class NegotiateActivity : AppCompatActivity() {

    lateinit var toCoin: String
    lateinit var fromCoin: String

    private lateinit var negociateViewModel: NegociateViewModel
    private lateinit var repositoryViewModel: DaoViewModel

    ///fixme
    private lateinit var currentUser: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_negotiate)

        val extras = intent.extras
        if (extras != null) {
            toCoin = extras.getString("toCoin").toString()
            fromCoin = extras.getString("fromCoin").toString()
            currentUser = extras.getString("token").toString()
        }

        var textViewTitle = findViewById<TextView>(R.id.textViewNegotiateTitle)
        var textViewCurrentA = findViewById<TextView>(R.id.textViewValueA)
        var textViewQuotationA = findViewById<TextView>(R.id.textViewQuotationA)
        var textViewQuotationB = findViewById<TextView>(R.id.textViewQuotationB)
        var textViewPreview = findViewById<TextView>(R.id.textViewPreviewB)
        var textViewLastQuotation = findViewById<TextView>(R.id.textViewNegociateLastQuotation)
        var editTextSoldA = findViewById<EditText>(R.id.editTextSwap)

        textViewTitle.text = "Swap $fromCoin to $toCoin"

        negociateViewModel = ViewModelProvider(this).get(NegociateViewModel::class.java)
        repositoryViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

        ///add observers
        negociateViewModel.getMBitcoin().observe(this, Observer {
            when (fromCoin) {
                "Bitcoin" -> {
                    textViewQuotationA.text = negociateViewModel.getMBitcoin().value?.ticker?.buy.toString()
                }
                "Brita" -> {
                    textViewQuotationA.text = negociateViewModel.getOlinda().value?.value?.first()?.cotacaoCompra.toString()
                }
            }

        })

        negociateViewModel.getOlinda().observe(this, Observer {
            when (fromCoin) {
                "Bitcoin" -> {
                    when (toCoin) {
                        "Brita" -> {
                            textViewQuotationB.text = negociateViewModel.getOlinda().value?.value?.first()?.cotacaoVenda.toString()
                        }
                        "Real" -> {
                            textViewQuotationB.text = "1"
                        }
                    }
                }
                "Brita" -> {
                    when (toCoin) {
                        "Bitcoin" -> {
                            textViewQuotationB.text = negociateViewModel.getMBitcoin().value?.ticker?.sell.toString()
                        }
                        "Real" -> {
                            textViewQuotationB.text = "1"
                        }
                    }
                }
                "Real" -> {
                    textViewQuotationA.text = "1"
                    when (toCoin) {
                        "Bitcoin" -> {

                            textViewQuotationB.text = negociateViewModel.getMBitcoin().value?.ticker?.sell.toString()
                        }
                        "Brita" -> {

                            textViewQuotationB.text = negociateViewModel.getOlinda().value?.value?.first()?.cotacaoVenda.toString()
                        }
                    }
                }

            }
        })
//
        negociateViewModel.getLastFetch().observe(this, {
            textViewLastQuotation.text = "Ultima cotação atualizada as " + it
        })

        editTextSoldA.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                if (editTextSoldA.text.isBlank() || editTextSoldA.text.isEmpty()) {
                    textViewPreview.text = "0"
                } else {
                    textViewPreview.text = ((editTextSoldA.text.toString()
                        .toDouble() * textViewQuotationA.text.toString()
                        .toDouble()) / (textViewQuotationB.text.toString().toDouble())).toString()
                }

            }

        })
//
        repositoryViewModel.currentUser().observe(this, {
            when (fromCoin) {
                "Bitcoin" -> {
                    textViewCurrentA.text = it?.amountBitcoin.toString()
                }
                "Brita" -> {
                    textViewCurrentA.text = it?.amountBrita.toString()
                }
                "Real" -> {
                    textViewCurrentA.text = it?.amountReal.toString()
                }

            }
        })

        ///start ViewModel
        negociateViewModel.init()
        repositoryViewModel.init(this.applicationContext)
        repositoryViewModel.setCurrentUser(currentUser)
        negociateViewModel.forceFetch()

    }
}