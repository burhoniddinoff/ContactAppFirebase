package com.example.contactappfirebase2.presentation.viewModel

import com.example.contactappfirebase2.data.ContactUIData
import kotlinx.coroutines.flow.MutableSharedFlow

interface UpdateContactViewModel {

    val successFlow: MutableSharedFlow<String>
    val errorMessage: MutableSharedFlow<String>

    fun updateContact(data: ContactUIData)

}