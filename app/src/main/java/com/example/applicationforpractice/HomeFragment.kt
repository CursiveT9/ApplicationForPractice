package com.example.applicationforpractice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationforpractice.databinding.ActivityHomeBinding
import io.ktor.client.*
import io.ktor.client.engine.cio.*

class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private lateinit var characterViewModel: CharacterViewModel
    private lateinit var characterAdapter: CharacterAdapter
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Trying to access the binding outside of the view lifecycle.")

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
        characterViewModel = ViewModelProvider(this, CharacterViewModelFactory(repository)).get(CharacterViewModel::class.java)

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

        return view
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
