package com.example.contactappfirebase2.presentation.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactappfirebase2.data.AddContact
import com.example.contactappfirebase2.domain.AppRepository
import com.example.contactappfirebase2.presentation.viewModel.AddContactViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddContactViewModelImpl @Inject constructor(
    private val repository: AppRepository,
) : ViewModel(), AddContactViewModel {

    override val successFlow = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val errorMessage: MutableSharedFlow<String> = repository.errorMessage

    override fun addContact(data: AddContact) {
        repository.addContact(AddContact(data.fName, data.lName, data.phone)).onEach {
            it.onSuccess {
                successFlow.emit("Success")
            }
            it.onFailure {
                errorMessage.run { emit(it.toString()) }
            }
        }.launchIn(viewModelScope)
    }

}