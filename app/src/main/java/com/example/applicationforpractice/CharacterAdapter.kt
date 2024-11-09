package com.example.applicationforpractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationforpractice.databinding.CharacterItemBinding

class CharacterAdapter(private val characters: List<Character>) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(val binding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        with(holder.binding) {
            characterName.text = if (character.name.isNotEmpty()) character.name else "Unknown Name"
            characterCulture.text = if (character.culture.isNotEmpty()) character.culture else "Unknown Culture"
            characterBorn.text = if (character.born.isNotEmpty()) character.born else "Unknown Birth"
            characterTitles.text = if (character.titles.isNotEmpty()) character.titles.joinToString(", ") else "No Titles"
            characterAliases.text = if (character.aliases.isNotEmpty()) character.aliases.joinToString(", ") else "No Aliases"
            characterPlayedBy.text = if (character.playedBy.isNotEmpty()) character.playedBy.joinToString(", ") else "Not Portrayed"
        }
    }

    override fun getItemCount(): Int {
        return characters.size
    }
}