package com.example.applicationforpractice.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationforpractice.R
import com.example.applicationforpractice.data.Character
import com.example.applicationforpractice.databinding.CharacterItemBinding
import com.example.applicationforpractice.db.CharacterEntity

class CharacterAdapter(private var characters: List<CharacterEntity>) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

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
        holder.characterName.text = character.name ?: "Unknown Name"
        holder.characterCulture.text = character.culture ?: "Unknown Culture"
        holder.characterBorn.text = character.born ?: "Unknown Birth"
        holder.characterTitles.text = character.titles ?: "No Titles"
        holder.characterAliases.text = character.aliases ?: "No Aliases"
        holder.characterPlayedBy.text = character.playedBy ?: "Not Portrayed"
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    // Метод для обновления данных в адаптере
    fun updateCharacters(newCharacters: List<CharacterEntity>) {
        // Сравниваем новые данные с текущими, если они изменились, обновляем список
        if (characters != newCharacters) {
            characters = newCharacters
            notifyDataSetChanged() // Уведомляем адаптер о том, что данные обновились
        }
    }
}
