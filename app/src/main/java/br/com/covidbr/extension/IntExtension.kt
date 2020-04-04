package br.com.covidbr.extension

import java.text.NumberFormat

fun Int.format(): String = NumberFormat.getInstance().format(this)