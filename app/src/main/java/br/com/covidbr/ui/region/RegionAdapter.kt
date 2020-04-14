package br.com.covidbr.ui.region

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.covidbr.R
import br.com.covidbr.data.region.RegionRecord
import br.com.covidbr.extension.format
import br.com.covidbr.ui.filter.Filter
import kotlinx.android.synthetic.main.item_record_region.view.*

class RegionAdapter(val records: MutableList<RegionRecord>, private var filter: Filter = Filter()) :
    RecyclerView.Adapter<RegionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_record_region, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = records.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records[position]
        holder.bindView(record, position, filter)
    }

    fun changeList(records: MutableList<RegionRecord>, filter: Filter) {
        this.records.clear()
        this.records.addAll(records)
        this.filter = filter
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(record: RegionRecord, position: Int, filter: Filter) {
            with(itemView) {
                if (filter.order == Filter.ORDER_NAME) {
                    textView.text = record.state
                } else {
                    val postion = (position + 1).toString() + "º"
                    textView.text = postion
                }
                textViewState.text = record.stateName
                textViewDeceased.text = record.deceased.format()
                textViewInfected.text = record.infected.format()
            }
        }
    }

}