package br.com.covidbr.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.covidbr.R
import br.com.covidbr.data.region.RegionRecord
import br.com.covidbr.extension.format
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_footer.*
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
        observeLoading()
        observeSearchView()
    }

    private fun observeSearchView() {
        val searchView = activity?.findViewById<MaterialSearchView>(R.id.searchview)
        searchView?.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val records = viewModel.filter(newText)
                (recyclerView.adapter as HomeAdapter).changeList(records)
                return true
            }
        })
    }

    private fun observeLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { onLoading ->
            if (onLoading) {
                recyclerView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        })
    }

    private fun observeRecords() {
        viewModel.records.observe(viewLifecycleOwner, Observer {
            val records = mutableListOf<RegionRecord>()
            records.addAll(it.records)
            recyclerView.adapter = HomeAdapter(records)
            textViewSumInfected.text = it.infected.format()
            textViewSumDeceased.text = it.deceased.format()
        })
    }
}
