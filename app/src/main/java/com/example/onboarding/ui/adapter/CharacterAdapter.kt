package com.example.onboarding.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onboarding.R
import com.example.onboarding.databinding.ItemCharacterBinding
import com.example.onboarding.domain.Result
import com.squareup.picasso.Picasso

class CharacterAdapter(
    var character: List<Result>,
    private val onClick: (Result) -> Unit,
) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflate = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflate.inflate(R.layout.item_character, parent, false), onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(character[position])

    override fun getItemCount(): Int = character.size

    class ViewHolder(
        view: View,
        private val onClick: (Result) -> Unit,
    ) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCharacterBinding.bind(view)
        fun bind(character: Result) = with(binding) {
            tvName.text = character.name
            Picasso.get().load(character.image).into(imgCharacter)
            itemView.setOnClickListener { onClick(character) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(character: List<Result>) {
        this.character = character
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(character: List<Result>) {
        this.character = character
        notifyDataSetChanged()
    }
}

