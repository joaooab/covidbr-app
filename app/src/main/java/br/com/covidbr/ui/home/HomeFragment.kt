package br.com.covidbr.ui.home

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import br.com.covidbr.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

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

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                textViewNotFound.visibility = View.VISIBLE
                webView.visibility = View.GONE
            }
        }
    }

    private fun removeProgress() {
        progressBar.visibility = View.GONE
        if (textViewNotFound.visibility != View.VISIBLE) {
            webView.visibility = View.VISIBLE
        }
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
        webView.visibility = View.GONE
        textViewNotFound.visibility = View.GONE
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
