package com.example.onboarding.ui.dlg

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.onboarding.databinding.DialogPersonBinding
import com.example.onboarding.model.PersonEntity
import com.example.utils.Utils.birthday

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

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun setListeners() {
        binding.apply {
            tvName.text = person.name
            tvAge.text = birthday(person.birthday ?: "").toString() + " years"
            tvAddress.text = person.address
            tvNumber.text = person.number
            if (person.hobbies!!.isEmpty()) {
                tvHobbies.text = "No hobbies"
            } else {
                tvHobbies.text = person.hobbies ?: "No hobbies"
            }
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