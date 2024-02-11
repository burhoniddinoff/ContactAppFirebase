package com.example.contactappfirebase2.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.contactappfirebase2.R
import com.example.contactappfirebase2.data.AddContact
import com.example.contactappfirebase2.databinding.ScreenContactAddBinding
import com.example.contactappfirebase2.presentation.viewModel.AddContactViewModel
import com.example.contactappfirebase2.presentation.viewModel.impl.AddContactViewModelImpl
import com.example.contactappfirebase2.utils.myAddTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class AddContactScreen : Fragment(R.layout.screen_contact_add) {

    private val binding by viewBinding(ScreenContactAddBinding::bind)
    private val viewModel: AddContactViewModel by viewModels<AddContactViewModelImpl>()
    private var prepareFirstName = false
    private var prepareLastName = false
    private var preparePhone = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.editTextFirstName.myAddTextChangedListener {
            prepareFirstName = it.length > 3
            check()
        }

        binding.editTextLastName.myAddTextChangedListener {
            prepareLastName = it.length > 3
            check()
        }

        binding.editPhoneNumber.myAddTextChangedListener {
            preparePhone = it.startsWith("+998") && it.length == 13
            check()
        }

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonAdd.setOnClickListener {
            viewModel.addContact(
                AddContact(
                    binding.editTextFirstName.text.toString(),
                    binding.editTextLastName.text.toString(),
                    binding.editPhoneNumber.text.toString()
                )
            )
            findNavController().navigateUp()
        }

        viewModel.successFlow.onEach {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)


        viewModel.errorMessage.onEach {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)

    }

    private fun check() {
        binding.buttonAdd.isEnabled = prepareFirstName && prepareLastName && preparePhone
    }

}

