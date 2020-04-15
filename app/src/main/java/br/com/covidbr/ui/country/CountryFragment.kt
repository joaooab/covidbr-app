package br.com.covidbr.ui.country

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.covidbr.R
import br.com.covidbr.data.contry.ContryRecord
import br.com.covidbr.extension.format
import br.com.covidbr.extension.supportFragmentManager
import br.com.covidbr.data.Filter
import br.com.covidbr.ui.filter.FilterDialog
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.fragment_country.*
import kotlinx.android.synthetic.main.include_footer.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class CountryFragment : Fragment() {

    private val viewModel: CountryViewModel by viewModel()
    private var menuFilter: MenuItem? = null
    private var filter: Filter =
        Filter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        observeRecords()
        observeSearchView()
        observerError()
    }

    private fun observerError() {
        viewModel.onError.observe(viewLifecycleOwner, Observer {
            textViewNotFound.visibility = View.VISIBLE
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
                (fragment_country_recyclerView.adapter as CountryAdapter).changeList(
                    records,
                    filter
                )
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
            FilterDialog.getInstance(filter, "Países") {
                setIconFilter(it)
                val records = viewModel.order(it)
                (fragment_country_recyclerView.adapter as CountryAdapter).changeList(
                    records,
                    filter
                )
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
                fragment_country_recyclerView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                fragment_country_recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        })
    }

    private fun observeRecords() {
        viewModel.records.observe(viewLifecycleOwner, Observer {
            val records = mutableListOf<ContryRecord>()
            records.addAll(it.records)
            fragment_country_recyclerView.adapter = CountryAdapter(records)
            val infected = it.records.sumBy { it.confirmed }
            val deceased = it.records.sumBy { it.deaths }
            textViewSumInfected.text = infected.format()
            textViewSumDeceased.text = deceased.format()
        })
    }

}
