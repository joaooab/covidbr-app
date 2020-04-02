package br.com.covidbr.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.covidbr.R
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.records.observe(viewLifecycleOwner, Observer {
            textView2.text = it.deceased.toString()
            textView3.text = it.infected.toString()
            recyclerView.adapter = HomeAdapter(
                it.deceasedByRegion.sortedBy { deceased -> deceased.state },
                it.infectedByRegion.sortedBy { infected -> infected.state })
        })
    }
}
