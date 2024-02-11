package com.example.contactappfirebase2.presentation.viewModel

import com.example.contactappfirebase2.data.AddContact
import kotlinx.coroutines.flow.MutableSharedFlow

interface AddContactViewModel {

    val successFlow: MutableSharedFlow<String>
    val errorMessage: MutableSharedFlow<String>

    fun addContact(data: AddContact)

}