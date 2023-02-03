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
import com.example.utils.Utils.birthday
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
            tvAge.text = birthday(person.birthday ?: "").toString()
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

