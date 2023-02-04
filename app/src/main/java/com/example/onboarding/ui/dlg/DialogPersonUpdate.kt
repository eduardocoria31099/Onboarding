package com.example.onboarding.ui.dlg

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import com.example.onboarding.databinding.DialogPersonUpdateBinding
import com.example.onboarding.model.PersonEntity

class DialogPersonUpdate(context: Context, personEntity: PersonEntity) {
    private var binding: DialogPersonUpdateBinding
    private var dialog: AlertDialog? = null
    private var onOptionSelectedListener: OnOptionSelectedListener? = null
    private var person: PersonEntity

    init {
        person = personEntity
        val inflater = LayoutInflater.from(context)
        binding = DialogPersonUpdateBinding.inflate(inflater)

        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)
        dialog = builder.create()
        dialog!!.setCancelable(false)
        setListeners()
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun setListeners() {
        binding.apply {
            val imageBytes = Base64.decode(person.img, 0)
            val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            imgPerson.setImageBitmap(image)
            txtName.setText(person.name)
            txtBirthdayDay.setText(person.birthday)
            txtAddress.setText(person.address)
            txtPhoneNumber.setText(person.number)
            txtHobbies.setText(person.hobbies ?: "No Hobbies")
            imgExit.setOnClickListener {
                if (onOptionSelectedListener != null) {
                    onOptionSelectedListener!!.onCancel()
                }
            }
        }
    }

    fun setOnOptionSelectedListener(onOptionSelectedListener: OnOptionSelectedListener) {
        this.onOptionSelectedListener = onOptionSelectedListener
    }

    interface OnOptionSelectedListener {
        fun onCancel()
        fun onUpdatePerson()
    }

    fun show() {
        dialog!!.show()
    }

    fun hide() {
        dialog!!.dismiss()
    }
}