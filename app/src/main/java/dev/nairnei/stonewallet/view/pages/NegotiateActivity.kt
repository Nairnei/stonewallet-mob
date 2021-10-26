package dev.nairnei.stonewallet.view.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import dev.nairnei.stonewallet.R

class NegotiateActivity : AppCompatActivity() {

    lateinit var toCoin: String
    lateinit var fromCoin: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_negotiate)

        val extras = intent.extras
        if (extras != null) {
             toCoin = extras.getString("toCoin").toString()
             fromCoin = extras.getString("fromCoin").toString()
        }

        var textViewTitle = findViewById<TextView>(R.id.textViewNegotiateTitle)

        textViewTitle.text = "Swap $fromCoin to $toCoin"
    }
}