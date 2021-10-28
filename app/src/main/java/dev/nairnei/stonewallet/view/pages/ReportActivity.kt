package dev.nairnei.stonewallet.view.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.nairnei.stonewallet.R
import dev.nairnei.stonewallet.viewHolder.ReportAdapter
import dev.nairnei.stonewallet.viewModel.DaoViewModel

class ReportActivity : AppCompatActivity() {

    private lateinit var  recycleViewReports : RecyclerView
    private lateinit var repositoryViewModel: DaoViewModel
    private lateinit var currentUser: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        title = "Reports"

        val extras = intent.extras
        if (extras != null) {

            currentUser = extras.getString("token").toString()
        }

        recycleViewReports = findViewById(R.id.recyclerViewReport)
        recycleViewReports.layoutManager = LinearLayoutManager(this)

        repositoryViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

        repositoryViewModel.listReport().observe(this, {

            recycleViewReports.adapter = ReportAdapter(it)
        })

        repositoryViewModel.init(this.applicationContext)

        repositoryViewModel.listReports(currentUser)
    }
}