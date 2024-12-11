package com.example.applicationforpractice.fragments

import android.content.ContentValues
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import com.example.applicationforpractice.R
import com.example.applicationforpractice.adapters.CharacterAdapter
import com.example.applicationforpractice.api.CharacterRepository
import com.example.applicationforpractice.data.Character
import com.example.applicationforpractice.databinding.ActivityHomeBinding
import com.example.applicationforpractice.viewmodels.CharacterViewModel
import com.example.applicationforpractice.viewmodels.CharacterViewModelFactory
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import java.io.OutputStream

class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private lateinit var characterViewModel: CharacterViewModel
    private lateinit var characterAdapter: CharacterAdapter
    private var _binding: ActivityHomeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Trying to access the binding outside of the view lifecycle.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val characterRecyclerView = binding.characterRecyclerView
        characterRecyclerView.layoutManager = LinearLayoutManager(context)

        val client = HttpClient(CIO)
        val repository = CharacterRepository(client)
        characterViewModel = ViewModelProvider(
            this,
            CharacterViewModelFactory(repository)
        ).get(CharacterViewModel::class.java)

        characterViewModel.fetchCharacters(characterViewModel.getCurrentPage())

        characterViewModel.characters.observe(viewLifecycleOwner) { characters ->
            characterAdapter = CharacterAdapter(characters)
            characterRecyclerView.adapter = characterAdapter
        }

        binding.previousPageButton.setOnClickListener {
            characterViewModel.previousPage()
        }

        binding.nextPageButton.setOnClickListener {
            characterViewModel.nextPage()
        }

        binding.imageButton4.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        characterViewModel.characters.observe(viewLifecycleOwner) { characters ->
            if (characters.isNotEmpty()) {
                saveToDocuments(characters)
            }
        }

        return view
    }

    // Метод для записи в /storage/emulated/0/Documents/
    fun saveToDocuments(characters: List<Character>) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "19.txt") // имя файла
            put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Documents/") // Путь, куда сохраняем файл
        }
        val contentResolver = requireContext().contentResolver
        val uri = contentResolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

        uri?.let {
            try {
                val outputStream: OutputStream? = contentResolver.openOutputStream(uri)
                outputStream?.bufferedWriter()?.use { writer ->
                    for (character in characters) {
                        writer.write(character.name) // или character.toString()
                        writer.newLine()
                    }
                }
                Log.d("HomeFragment", "File saved to Documents successfully.")
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error saving file", e)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
