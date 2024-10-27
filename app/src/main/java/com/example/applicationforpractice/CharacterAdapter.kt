package com.example.applicationforpractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CharacterAdapter(private val characters: List<Character>) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val characterName: TextView = view.findViewById(R.id.characterName)
        val characterCulture: TextView = view.findViewById(R.id.characterCulture)
        val characterBorn: TextView = view.findViewById(R.id.characterBorn)
        val characterTitles: TextView = view.findViewById(R.id.characterTitles)
        val characterAliases: TextView = view.findViewById(R.id.characterAliases)
        val characterPlayedBy: TextView = view.findViewById(R.id.characterPlayedBy)
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