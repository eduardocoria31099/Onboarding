package com.example.onboarding.ui.dlg

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.onboarding.databinding.DialogPersonBinding
import com.example.onboarding.model.PersonEntity

class DialogPerson(context: Context, personEntity: PersonEntity) {
    private var binding: DialogPersonBinding
    private var dialog: AlertDialog? = null
    private var onOptionSelectedListener: OnOptionSelectedListener? = null
    private var person: PersonEntity

    init {
        person = personEntity
        val inflater = LayoutInflater.from(context)
        binding = DialogPersonBinding.inflate(inflater)

        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)
        dialog = builder.create()

        dialog!!.setCancelable(true)

        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            tvName.text = person.name
            tvAge.text = person.birthday
            tvAddress.text = person.address
            tvNumber.text = person.number
            tvHobbies.text = person.hobbies ?: "No hobbies"
            imgExit.setOnClickListener {
                if (onOptionSelectedListener != null) {
                    onOptionSelectedListener!!.onCancelOptionSelected()
                }
            }
        }
    }

    fun setOnOptionSelectedListener(onOptionSelectedListener: OnOptionSelectedListener) {
        this.onOptionSelectedListener = onOptionSelectedListener
    }

    interface OnOptionSelectedListener {
        fun onCancelOptionSelected()
    }

    fun show() {
        dialog!!.show()
    }

    fun hide() {
        dialog!!.dismiss()
    }
}