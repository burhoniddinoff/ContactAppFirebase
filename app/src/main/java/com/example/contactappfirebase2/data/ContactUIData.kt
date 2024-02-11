package com.example.contactappfirebase2.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactUIData(
    val docID: String,
    val fName: String,
    val lName: String,
    val phone: String,
) : Parcelable