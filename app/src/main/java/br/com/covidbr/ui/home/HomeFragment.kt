package br.com.covidbr.ui.home

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.covidbr.R
import br.com.covidbr.data.region.RegionRecord
import br.com.covidbr.extension.format
import br.com.covidbr.extension.supportFragmentManager
import br.com.covidbr.ui.filter.Filter
import br.com.covidbr.ui.filter.FilterDialog
import br.com.covidbr.ui.region.RegionViewModel
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_footer.*
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

//    companion object {
//        fun newInstance(fragment: Fragment): DialogWebViewFragment {
//            val dialog = DialogWebViewFragment()
//            dialog.simpleNameFragment = fragment.javaClass.simpleName
//            return dialog
//        }
//    }
//
//    private lateinit var simpleNameFragment: String
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NORMAL, R.style.AppTheme)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val link = "https://covid.saude.gov.br/"
        setUpSettings()
        setUpClient()
        webView.loadUrl(link)
    }

//    private fun showError() {
//        Toast.makeText(
//            context,
//            "Não foi possível abrir artigo da base de conhecimento, por favor tente mais tarde",
//            Toast.LENGTH_LONG
//        ).show()
//    }

    private fun setUpClient() {
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showProgress()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                removeProgress()
            }
        }
    }

    private fun removeProgress() {
        progressBar.visibility = View.GONE
        webView.visibility = View.VISIBLE
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
        webView.visibility = View.GONE
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpSettings() {
        webView.settings.apply {
            useWideViewPort = true
            loadWithOverviewMode = true
            builtInZoomControls = true
            displayZoomControls = false
            javaScriptEnabled = true
        }
    }

}
