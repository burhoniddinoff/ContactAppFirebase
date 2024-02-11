package com.example.contactappfirebase2.presentation.viewModel

import com.example.contactappfirebase2.data.ContactUIData
import kotlinx.coroutines.flow.MutableSharedFlow

interface ContactViewModel {

    val contactFlow: MutableSharedFlow<List<ContactUIData>>
    val successFlow: MutableSharedFlow<String>
    val errorMessage: MutableSharedFlow<String>

    fun deleteContact(id: String)

}