package br.com.covidbr.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun Fragment.supportFragmentManager(execute: FragmentManager.() -> Unit) {
    val supportFragmentManager = activity?.supportFragmentManager
        ?: throw IllegalArgumentException("Activity null")
    execute(supportFragmentManager)
}