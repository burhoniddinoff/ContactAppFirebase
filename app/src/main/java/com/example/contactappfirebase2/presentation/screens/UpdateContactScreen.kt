package com.example.contactappfirebase2.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.contactappfirebase2.R
import com.example.contactappfirebase2.data.ContactUIData
import com.example.contactappfirebase2.databinding.ScreenContactUpdateBinding
import com.example.contactappfirebase2.presentation.viewModel.UpdateContactViewModel
import com.example.contactappfirebase2.presentation.viewModel.impl.UpdateContactViewModelImpl
import com.example.contactappfirebase2.utils.myAddTextChangedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateContactScreen : Fragment(R.layout.screen_contact_update) {

    private val binding by viewBinding(ScreenContactUpdateBinding::bind)
    private val navArgs: UpdateContactScreenArgs by navArgs()
    private val viewModel: UpdateContactViewModel by viewModels<UpdateContactViewModelImpl>()

    private var prepareFirstName = false
    private var prepareLastName = false
    private var preparePhone = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.editTextFirstName.setText(navArgs.data.fName)
        binding.editTextLastName.setText(navArgs.data.lName)
        binding.editPhoneNumber.setText(navArgs.data.phone)

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

        binding.buttonEdit.setOnClickListener {
            viewModel.updateContact(
                ContactUIData(
                    navArgs.data.docID,
                    binding.editTextFirstName.text.toString(),
                    binding.editTextLastName.text.toString(),
                    binding.editPhoneNumber.text.toString()
                )
            )

            findNavController().navigateUp()

        }
    }

    private fun check() {
        binding.buttonEdit.isEnabled = prepareFirstName && prepareLastName && preparePhone
    }


}