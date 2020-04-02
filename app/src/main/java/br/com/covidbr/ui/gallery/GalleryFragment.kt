package br.com.covidbr.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.covidbr.R
import com.google.gson.JsonObject
import org.koin.android.ext.android.inject

class GalleryFragment : Fragment() {

    private val viewModel: GalleryViewModel by inject()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.records.observe(viewLifecycleOwner, Observer {
            it.result.forEach {json ->
                json
            }
            (it.result[0] as JsonObject).entrySet().
        })
    }
}
