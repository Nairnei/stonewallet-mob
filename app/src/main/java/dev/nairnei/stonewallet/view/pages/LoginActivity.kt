package dev.nairnei.stonewallet.view.pages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import dev.nairnei.stonewallet.R
import dev.nairnei.stonewallet.viewModel.DaoViewModel
import dev.nairnei.stonewallet.viewModel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var repositoryViewModel: DaoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = "Carteira Stone"
        ///find textViews
        val editTextEmailAddress = findViewById<EditText>(R.id.editTextEmailAddress)

        ///find buttons
        val buttonLoginOrCreate = findViewById<Button>(R.id.buttonLoginOrCreate)

        repositoryViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        repositoryViewModel.init(this.applicationContext)
        repositoryViewModel.currentUser().observe(this, {
            if(it != null)
            {
                val intent = Intent(this, HomeActivity::class.java)
                editTextEmailAddress.text.clear()
                intent.putExtra("token", it.email)
                startActivity(intent)
            }
        })

        ///buttons actions
        buttonLoginOrCreate.setOnClickListener {
            if(editTextEmailAddress.text.isNotBlank() && editTextEmailAddress.text.isNotEmpty())
            repositoryViewModel.createOrLogin(editTextEmailAddress.text.toString())
        }
    }
}