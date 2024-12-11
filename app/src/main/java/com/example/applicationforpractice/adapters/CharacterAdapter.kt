package com.example.applicationforpractice.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationforpractice.R
import com.example.applicationforpractice.data.Character
import com.example.applicationforpractice.databinding.CharacterItemBinding

class CharacterAdapter(private val characters: List<Character>) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = CharacterItemBinding.bind(itemView)
        val characterName: TextView = binding.characterName
        val characterCulture: TextView = binding.characterCulture
        val characterBorn: TextView = binding.characterBorn
        val characterTitles: TextView = binding.characterTitles
        val characterAliases: TextView = binding.characterAliases
        val characterPlayedBy: TextView = binding.characterPlayedBy
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.characterName.text = if (character.name.isNotEmpty()) character.name else "Unknown Name"
        holder.characterCulture.text = if (character.culture.isNotEmpty()) character.culture else "Unknown Culture"
        holder.characterBorn.text = if (character.born.isNotEmpty()) character.born else "Unknown Birth"
        holder.characterTitles.text = if (character.titles.isNotEmpty()) character.titles.joinToString(", ") else "No Titles"
        holder.characterAliases.text = if (character.aliases.isNotEmpty()) character.aliases.joinToString(", ") else "No Aliases"
        holder.characterPlayedBy.text = if (character.playedBy.isNotEmpty()) character.playedBy.joinToString(", ") else "Not Portrayed"
    }

    override fun getItemCount(): Int {
        return characters.size
    }
}