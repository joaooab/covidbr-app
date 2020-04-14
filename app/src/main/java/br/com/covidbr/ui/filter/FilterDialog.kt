package br.com.covidbr.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import br.com.covidbr.R
import br.com.covidbr.ui.filter.Filter.Companion.ORDER_DECEASE
import br.com.covidbr.ui.filter.Filter.Companion.ORDER_INFECTED
import br.com.covidbr.ui.filter.Filter.Companion.ORDER_NAME
import br.com.covidbr.ui.filter.Filter.Companion.TYPE_ASC
import br.com.covidbr.ui.filter.Filter.Companion.TYPE_DESC
import kotlinx.android.synthetic.main.dialog_filter.*

class FilterDialog : DialogFragment() {

    private lateinit var onFinished: (filter: Filter) -> Unit
    private lateinit var type: String
    private lateinit var filter: Filter

    companion object {
        fun getInstance(
            filter: Filter,
            type: String = "Estado",
            onFinished: (filter: Filter) -> Unit
        ): FilterDialog {
            return FilterDialog().apply {
                this.onFinished = onFinished
                this.type = type
                this.filter = filter
            }
        }

        fun isFilterDefault(filter: Filter): Boolean {
            return filter.order == ORDER_NAME && filter.type == TYPE_ASC
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            900,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radioButtonName.text = type
        setupFilterOrder()
        setupFilterType()
        setupButtonApply()
        setupClean()
    }

    private fun setupFilterType() {
        when (filter.type) {
            TYPE_ASC -> checkButton(radioButtonAsc)
            TYPE_DESC -> checkButton(radioButtonDesc)
        }
    }

    private fun setupFilterOrder() {
        when (filter.order) {
            ORDER_NAME -> checkButton(radioButtonName)
            ORDER_INFECTED -> checkButton(radioButtonInfected)
            ORDER_DECEASE -> checkButton(radioButtonDecease)
        }
    }

    private fun checkButton(radioButton: RadioButton) {
        radioButton.isChecked = true
    }

    private fun setupClean() {
        buttonClean.setOnClickListener {
            val filter = Filter(ORDER_NAME, TYPE_ASC)
            onFinished(filter)
            dismiss()
        }
    }

    private fun setupButtonApply() {
        buttonApply.setOnClickListener {
            val order = when (radioGroupOrder.checkedRadioButtonId) {
                R.id.radioButtonName -> ORDER_NAME
                R.id.radioButtonInfected -> ORDER_INFECTED
                R.id.radioButtonDecease -> ORDER_DECEASE
                else -> ORDER_NAME
            }
            val type = when (radioGroupType.checkedRadioButtonId) {
                R.id.radioButtonAsc -> TYPE_ASC
                R.id.radioButtonDesc -> TYPE_DESC
                else -> TYPE_ASC
            }
            val filter = Filter(order, type)
            onFinished(filter)
            dismiss()
        }
    }
}