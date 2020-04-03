package br.com.covidbr.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.covidbr.R
import kotlinx.android.synthetic.main.fragment_country.*
import org.koin.android.ext.android.inject
import java.text.DecimalFormat

class CountryFragment : Fragment() {

    private val viewModel: CountryViewModel by inject()
    val formatNumber = "#,###.##"

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.records.observe(viewLifecycleOwner, Observer {
            fragment_country_recyclerView.adapter = CountryAdapter(
                    it.result.sortedBy { r -> r.contry })
            val infected = it.result.sumBy { it.confirmed.toInt() }
            val deceased = it.result.sumBy { it.deaths.toInt() }
            textViewSumInfected.text = formatter(infected)
            textViewSumDeceased.text = formatter(deceased)
        })
    }

    fun formatter(numero:Int): String {
        val formatter = DecimalFormat(formatNumber)
        val numberFormat = formatter.format(numero)
        return numberFormat.toString()
    }
}
