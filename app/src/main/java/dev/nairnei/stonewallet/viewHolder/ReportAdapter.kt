package dev.nairnei.stonewallet.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.nairnei.stonewallet.R
import dev.nairnei.stonewallet.model.ReportModel

class ReportAdapter(private val reportList: List<ReportModel>) :
    RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.inflate_report, parent, false)
        return ReportViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val item = reportList[position]
        holder.textViewCoinTo.text = item.to
        holder.textViewValueTo.text = item.amoutTo
        holder.textViewCoinFrom.text = item.from
        holder.textViewValueFrom.text = item.amountFrom
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewCoinFrom = itemView.findViewById<TextView>(R.id.textViewReportFrom)
        val textViewValueFrom = itemView.findViewById<TextView>(R.id.textViewReportValueFrom)
        val textViewCoinTo = itemView.findViewById<TextView>(R.id.textViewReportTo)
        val textViewValueTo = itemView.findViewById<TextView>(R.id.textViewReportValueTo)
    }
}