package br.com.covidbr.ui.country

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.covidbr.R
import br.com.covidbr.data.contry.Result
import kotlinx.android.synthetic.main.item_record_country.view.*


class CountryAdapter(
        private val info: List<Result>
) :
        RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_record_region, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = info.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val infoCountry = info[position]
        holder.bindView(infoCountry)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(info: Result) {
            with(itemView) {
                textView.text = info.contry
                textViewState.text = info.contry
                textViewInfected.text = info.confirmed
                textViewDeceased.text = info.deaths
            }
        }
    }

}