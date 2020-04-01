package br.com.covidbr.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.covidbr.R
import br.com.covidbr.data.region.DeceasedByRegion
import br.com.covidbr.data.region.InfectedByRegion
import kotlinx.android.synthetic.main.item_record_region.view.*

class HomeAdapter(
    private val deceasedBIES: List<DeceasedByRegion>,
    private val infectedBIES: List<InfectedByRegion>
) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_record_region, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = deceasedBIES.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deceasedRegion = deceasedBIES[position]
        val infectedRegion = infectedBIES[position]
        holder.bindView(deceasedRegion, infectedRegion)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(deceasedBy: DeceasedByRegion, infectedBy: InfectedByRegion) {
            with(itemView) {
                textView4.text = deceasedBy.state
                textView5.text = deceasedBy.count.toString()
                textView6.text = infectedBy.count.toString()
            }
        }
    }

}