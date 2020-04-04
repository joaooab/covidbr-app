package br.com.covidbr.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.covidbr.R
import br.com.covidbr.extension.format
import kotlinx.android.synthetic.main.fragment_country.*
import kotlinx.android.synthetic.main.include_footer.*
import org.koin.android.ext.android.inject

class CountryFragment : Fragment() {

    private val viewModel: CountryViewModel by inject()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoading()
        obsereveRecords()
    }

    private fun obsereveRecords() {
        viewModel.records.observe(viewLifecycleOwner, Observer {
            fragment_country_recyclerView.adapter = CountryAdapter(
                it.result.sortedBy { r -> r.contry })
            val infected = it.result.sumBy { it.confirmed.toInt() }
            val deceased = it.result.sumBy { it.deaths.toInt() }
            textViewSumInfected.text = infected.format()
            textViewSumDeceased.text = deceased.format()
        })
    }

    private fun observeLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { onLoading ->
            if (onLoading) {
                fragment_country_recyclerView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                fragment_country_recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        })
    }


}
