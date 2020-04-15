package br.com.covidbr.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.covidbr.R
import br.com.covidbr.extension.format
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
        observeRecords()
        viewModel.dashboard.observe(viewLifecycleOwner, Observer {
            it
        })
    }

    private fun observeRecords() {
        viewModel.records.observe(viewLifecycleOwner, Observer {
            textViewDeceased.text = it.deceased.format()
            textViewInfected.text = it.infected.format()
        })
    }

}
