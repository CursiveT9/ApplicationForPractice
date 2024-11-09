package com.example.applicationforpractice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.applicationforpractice.databinding.ActivityOnboardBinding

class OnboardFragment : Fragment() {

    companion object {
        private const val TAG = "OnboardFragment"
    }

    private var _binding: ActivityOnboardBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View binding is only sas")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityOnboardBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.button1.setOnClickListener {
            val user = User("", "", "")
            val action = OnboardFragmentDirections.actionOnboardFragmentToSignInFragment(user)
            findNavController().navigate(action)
        }

        binding.textView.setOnClickListener {
            val action = OnboardFragmentDirections.actionOnboardFragmentToSignUpFragment()
            findNavController().navigate(action)
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
}