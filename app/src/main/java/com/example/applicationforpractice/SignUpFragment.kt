package com.example.applicationforpractice

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import java.util.regex.Pattern

class SignUpFragment : Fragment() {

    companion object {
        private const val TAG = "SignUpFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_sign_up, container, false)
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.sign_up)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val emailInput = view.findViewById<EditText>(R.id.emailInput)
        val loginInput = view.findViewById<EditText>(R.id.loginInput)
        val passwordInput = view.findViewById<EditText>(R.id.passwordInput)
        val registrationButton = view.findViewById<Button>(R.id.button2)

        registrationButton.setOnClickListener {
            if (!validateInputs(loginInput, emailInput, passwordInput)) {
                return@setOnClickListener
            }
            val login = loginInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            val bundle = Bundle().apply {
                putString("login", login)
                putString("email", email)
                putString("password", password)
            }

            (activity as MainActivity).navigateToSignIn(bundle)
        }
        return view
    }

    private fun validateInputs(loginInput: EditText, emailInput: EditText, passwordInput: EditText): Boolean {
        if (loginInput.text.isEmpty() || emailInput.text.isEmpty() || passwordInput.text.isEmpty()) {
            Toast.makeText(activity, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return false
        } else if (!isValidEmail(emailInput.text.toString().trim())) {
            Toast.makeText(activity, "Неверный формат электронной почты", Toast.LENGTH_SHORT).show()
            return false
        } else if (passwordInput.text.toString().trim().length < 3) {
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
}