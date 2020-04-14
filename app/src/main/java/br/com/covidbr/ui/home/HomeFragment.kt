package br.com.covidbr.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.covidbr.R
import br.com.covidbr.data.region.RegionRecord
import br.com.covidbr.extension.format
import br.com.covidbr.extension.supportFragmentManager
import br.com.covidbr.ui.filter.Filter
import br.com.covidbr.ui.filter.FilterDialog
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_footer.*
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by inject()
    private var menuFilter: MenuItem? = null
    private var filter: Filter = Filter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        observerError()
    }

    private fun observerError() {
        viewModel.onError.observe(viewLifecycleOwner, Observer {
            textViewNadaEncontrado.visibility = View.VISIBLE
        })
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list, menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = activity?.findViewById<MaterialSearchView>(R.id.searchview)
        searchView?.setMenuItem(item)
        menuFilter = menu.findItem(R.id.action_filter)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter -> {
                openFilterDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openFilterDialog() {
        supportFragmentManager {
            FilterDialog.getInstance(filter) {
                setIconFilter(it)
                val records = viewModel.order(it)
                (recyclerView.adapter as HomeAdapter).changeList(records)
            }.show(this, "")
        }
    }

    private fun setIconFilter(filter: Filter) {
        this.filter = filter
        if (FilterDialog.isFilterDefault(filter)) {
            menuFilter?.setIcon(R.drawable.ic_filter_list_white_24dp)
        } else {
            menuFilter?.setIcon(R.drawable.ic_filter_checked)
        }
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
