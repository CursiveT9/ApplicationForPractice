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
import com.example.applicationforpractice.data.User
import com.example.applicationforpractice.databinding.ActivitySignUpBinding
import com.example.applicationforpractice.viewmodels.UserViewModel
import java.util.regex.Pattern

class SignUpFragment : Fragment() {

    companion object {
        private const val TAG = "SignUpFragment"
    }

    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Trying to access the binding outside of the view lifecycle.")
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivitySignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.button2.setOnClickListener {
            if (!validateInputs()) {
                return@setOnClickListener
            }

            val login = binding.loginInput.text.toString().trim()
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            userViewModel.addUser(email, password)
            Toast.makeText(activity, "Регистрация успешна", Toast.LENGTH_SHORT).show()
            val user = User(login, email, password)
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment(user)
            findNavController().navigate(action)
        }
        return view
    }

    private fun validateInputs(): Boolean {
        if (binding.loginInput.text.isEmpty() || binding.emailInput.text.isEmpty() || binding.passwordInput.text.isEmpty()) {
            Toast.makeText(activity, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return false
        } else if (!isValidEmail(binding.emailInput.text.toString().trim())) {
            Toast.makeText(activity, "Неверный формат электронной почты", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.passwordInput.text.toString().trim().length < 3) {
            Toast.makeText(activity, "Пароль должен быть не менее 3 символов", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
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