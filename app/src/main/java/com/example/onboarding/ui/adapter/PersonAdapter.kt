package com.example.onboarding.ui.adapter

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.onboarding.R
import com.example.onboarding.databinding.ItemListPeopleBinding
import com.example.onboarding.model.PersonEntity
import java.text.SimpleDateFormat
import java.util.*

class PersonAdapter(
    private var person: List<PersonEntity>,
    private val onClick: (PersonEntity) -> Unit
) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflate = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflate.inflate(R.layout.item_list_people, parent, false), onClick)
    }

    override fun getItemCount(): Int = person.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(person[position])


    class ViewHolder(
        view: View,
        private val onClick: (PersonEntity) -> Unit,
    ) : RecyclerView.ViewHolder(view) {
        private val binding = ItemListPeopleBinding.bind(view)

        @SuppressLint("SimpleDateFormat")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(person: PersonEntity) = with(binding) {
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
            val imageBytes = Base64.decode(person.img, 0)
            val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            imgPeople.setImageBitmap(image)
            layout.setOnClickListener { onClick(person) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(codesList: List<PersonEntity>) {
        person = codesList as MutableList<PersonEntity>
        notifyDataSetChanged()
    }

}

