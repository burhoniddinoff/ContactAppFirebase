package com.example.contactappfirebase2.presentation.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactappfirebase2.domain.AppRepository
import com.example.contactappfirebase2.presentation.viewModel.ContactViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ContactViewModelImpl @Inject constructor(
    private val repository: AppRepository,
) : ViewModel(), ContactViewModel {

    override val contactFlow = repository.contactDataFlow
    override val successFlow = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    override val errorMessage: MutableSharedFlow<String> = repository.errorMessage

    override fun deleteContact(id: String) {
        repository.deleteContact(id).onEach {
            it.onSuccess {
                successFlow.emit("Success")
            }
            it.onFailure { thr ->
                thr.message?.let { it1 -> errorMessage.emit(it1) }
            }
        }.launchIn(viewModelScope)
    }

}