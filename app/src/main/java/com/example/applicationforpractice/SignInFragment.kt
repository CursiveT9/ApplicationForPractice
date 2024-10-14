package com.example.applicationforpractice

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

class SignInFragment : Fragment() {

    companion object {
        private const val TAG = "SignInFragment"
    }

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private var fakeEmail = "test@gmail.com"
    private var fakePassword = "123"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_sign_in, container, false)
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.sign_in)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        emailInput = view.findViewById(R.id.emailInput)
        passwordInput = view.findViewById(R.id.passwordInput)
        val loginButton = view.findViewById<Button>(R.id.button1)
        val registerButton = view.findViewById<Button>(R.id.button2)

        val login = arguments?.getString("login")
        val email = arguments?.getString("email")
        val password = arguments?.getString("password")

        fakeEmail = email ?: fakeEmail
        fakePassword = password ?: fakePassword

        emailInput.setText(email)

        registerButton.setOnClickListener {
            (activity as MainActivity).navigateToSignUp()
        }

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (!isValidEmail(email)) {
                Toast.makeText(activity, "Неверный формат электронной почты", Toast.LENGTH_SHORT).show()
            } else if (password.length < 3) {
                Toast.makeText(activity, "Пароль должен быть не менее 3 символов", Toast.LENGTH_SHORT).show()
            } else {
                if (email == fakeEmail && password == fakePassword) {
                    Toast.makeText(activity, "Успешный вход", Toast.LENGTH_SHORT).show()
                    (activity as MainActivity).navigateToHome()
                } else {
                    Toast.makeText(activity, "Неправильная электронная почта или пароль", Toast.LENGTH_SHORT).show()
                }
            }
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
}