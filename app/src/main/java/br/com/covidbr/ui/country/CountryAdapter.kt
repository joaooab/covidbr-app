package br.com.covidbr.ui.country

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.covidbr.R
import br.com.covidbr.data.contry.ContryRecord
import br.com.covidbr.extension.format
import br.com.covidbr.data.Filter
import br.com.covidbr.data.Filter.Companion.ORDER_NAME
import kotlinx.android.synthetic.main.item_record_country.view.*


class CountryAdapter(
    val records: MutableList<ContryRecord>,
    private var filter: Filter = Filter()
) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_record_country, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = records.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val infoCountry = records[position]
        holder.bindView(infoCountry, position, filter)
    }

    fun changeList(records: MutableList<ContryRecord>, filter: Filter) {
        this.records.clear()
        this.records.addAll(records)
        this.filter = filter
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(record: ContryRecord, position: Int, filter: Filter) {
            with(itemView) {
                if (filter.order == ORDER_NAME) {
                    textView.text = record.contry
                } else {
                    val postion = (position + 1).toString() + "º"
                    textView.text = postion
                }
                textViewState.text = record.contryName
                textViewInfected.text = record.confirmed.format()
                textViewDeceased.text = record.deaths.format()
            }
        }
    }

}