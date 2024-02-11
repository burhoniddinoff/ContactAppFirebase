package com.example.contactappfirebase2.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.contactappfirebase2.R
import com.example.contactappfirebase2.databinding.ScreenContactBinding
import com.example.contactappfirebase2.presentation.adapter.ContactAdapter
import com.example.contactappfirebase2.presentation.viewModel.ContactViewModel
import com.example.contactappfirebase2.presentation.viewModel.impl.ContactViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ContactScreen : Fragment(R.layout.screen_contact) {

    private val binding by viewBinding(ScreenContactBinding::bind)
    private val viewModel: ContactViewModel by viewModels<ContactViewModelImpl>()
    private val adapter = ContactAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())

        binding.addButton.setOnClickListener {
            findNavController().navigate(ContactScreenDirections.actionContactScreenToAddContactScreen())
        }

        adapter.setOnItemClick {
            findNavController().navigate(ContactScreenDirections.actionContactScreenToUpdateContactScreen(it))
        }

        viewModel.contactFlow.onEach {
            adapter.submitList(it)
        }.launchIn(lifecycleScope)

        adapter.setOnLongClick {
            viewModel.deleteContact(it.docID)
            Log.d("TTT", "set On Long Click")
        }

        viewModel.successFlow.onEach { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }.launchIn(lifecycleScope)

        viewModel.errorMessage.onEach { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }.launchIn(lifecycleScope)

    }
}