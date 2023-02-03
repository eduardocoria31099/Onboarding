package com.example.onboarding.ui.dlg

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.onboarding.databinding.DialogPersonBinding
import com.example.onboarding.model.PersonEntity
import java.text.SimpleDateFormat
import java.util.*

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
            val dateBirthday: Date? = SimpleDateFormat("yyyy-MM-dd").parse(person.birthday ?: "")
            val dayActual = Date(System.currentTimeMillis())
            val differences = dayActual.time - dateBirthday!!.time
            val seconds = differences / 1000
            val minutes = seconds / 60
            val horas = minutes / 60
            val dias = horas / 24
            val years = dias / 365
            tvAge.text = years.toString()
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