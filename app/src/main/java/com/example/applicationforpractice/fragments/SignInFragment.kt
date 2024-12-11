package com.example.applicationforpractice.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.applicationforpractice.R
import com.example.applicationforpractice.databinding.ActivitySignInBinding
import com.example.applicationforpractice.viewmodels.UserViewModel
import java.util.regex.Pattern

class SignInFragment : Fragment() {

    companion object {
        private const val TAG = "SignInFragment"
    }

    private var _binding: ActivitySignInBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Trying to access the binding outside of the view lifecycle.")
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivitySignInBinding.inflate(inflater, container, false)
        val view = binding.root

        val args = arguments?.let { SignInFragmentArgs.fromBundle(it) }
        binding.emailInput.setText(args?.user?.email)

        binding.button1.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (!isValidEmail(email)) {
                Toast.makeText(activity, "Неверный формат электронной почты", Toast.LENGTH_SHORT).show()
            } else if (password.length < 3) {
                Toast.makeText(activity, "Пароль должен быть не менее 3 символов", Toast.LENGTH_SHORT).show()
            } else {
                if (userViewModel.isUserValid(email, password)) {
                    Toast.makeText(activity, "Успешный вход", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                } else {
                    Toast.makeText(activity, "Неправильная электронная почта или пароль", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_SignUpFragment)
        }

        return view
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return Pattern.compile(emailPattern).matcher(email).matches()
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