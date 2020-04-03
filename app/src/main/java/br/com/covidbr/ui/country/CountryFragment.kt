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
        viewModel.records.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = CountryAdapter(
                    it.result.sortedBy { r -> r.contry })
            it.result.sumBy { it.deaths.toInt() }
        })
    }
}
