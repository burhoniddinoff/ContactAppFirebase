package com.example.contactappfirebase2.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contactappfirebase2.data.ContactUIData
import com.example.contactappfirebase2.databinding.ItemContactBinding


class ContactAdapter : ListAdapter<ContactUIData, ContactAdapter.ContactViewHolder>(ContactDiffUtil) {

    private var onLongClick: ((ContactUIData) -> Unit)? = null
    private var onItemClick: ((ContactUIData) -> Unit)? = null

    object ContactDiffUtil : DiffUtil.ItemCallback<ContactUIData>() {
        override fun areItemsTheSame(oldItem: ContactUIData, newItem: ContactUIData): Boolean =
            oldItem.docID == newItem.docID

        override fun areContentsTheSame(oldItem: ContactUIData, newItem: ContactUIData): Boolean =
            oldItem == newItem
    }

    inner class ContactViewHolder(private val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {

        init {

            binding.photoGender.setOnLongClickListener {
                onLongClick?.invoke(getItem(adapterPosition))
                return@setOnLongClickListener true
            }

            binding.root.setOnClickListener {
                onItemClick?.invoke(getItem(adapterPosition))
            }

        }

        @SuppressLint("SetTextI18n")
        fun bind() {

            getItem(adapterPosition).apply {
                binding.textName.text = "$fName $lName"
                binding.textNumber.text = phone
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
        ContactViewHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) =
        holder.bind()

    fun setOnLongClick(block: (ContactUIData) -> Unit) {
        onLongClick = block
    }

    fun setOnItemClick(block: (ContactUIData) -> Unit) {
        onItemClick = block
    }

}