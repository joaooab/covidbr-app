package br.com.covidbr.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.covidbr.R
import br.com.covidbr.data.region.RegionRecord
import br.com.covidbr.extension.format
import kotlinx.android.synthetic.main.item_record_region.view.*

class HomeAdapter(val records: MutableList<RegionRecord>, private var order: Boolean = false) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_record_region, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = records.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records[position]
        holder.bindView(record, position, order)
    }

    fun changeList(records: MutableList<RegionRecord>, order:Boolean = false) {
        this.records.clear()
        this.records.addAll(records)
        this.order = order
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(record: RegionRecord, position: Int, order: Boolean) {
            with(itemView) {
                if(order){textView.text = (position+1).toString()+" º"}
                else{textView.text = record.state}
                textViewState.text = record.stateName
                textViewDeceased.text = record.deceased.format()
                textViewInfected.text = record.infected.format()
            }
        }
    }

}