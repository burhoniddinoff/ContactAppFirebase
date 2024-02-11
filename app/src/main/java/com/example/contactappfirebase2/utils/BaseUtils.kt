package com.example.contactappfirebase2.utils

import android.widget.EditText
import androidx.core.widget.addTextChangedListener

fun EditText.myAddTextChangedListener(block: (String) -> Unit) {
    this.addTextChangedListener { editable ->
        editable?.let { block.invoke(it.toString()) }
    }
}
