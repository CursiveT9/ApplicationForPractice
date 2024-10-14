package com.example.applicationforpractice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SignUpActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sign_up)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val loginImput = findViewById<EditText>(R.id.loginInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val registrationButton = findViewById<Button>(R.id.button2)

        registrationButton.setOnClickListener {
            if (!validateInputs(loginImput, emailInput, passwordInput)) {
                return@setOnClickListener
            }
            val login = loginImput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            val resultIntent = Intent()

            resultIntent.putExtra("login", login)
            resultIntent.putExtra("email", email)
            resultIntent.putExtra("password", password)

            val user = User(login, email, password)
            resultIntent.putExtra("user", user)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun validateInputs(loginInput: EditText, emailInput: EditText, passwordInput: EditText): Boolean {
        if (loginInput.text.isEmpty() || emailInput.text.isEmpty() || passwordInput.text.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return false
        } else if (!isValidEmail(emailInput.text.toString().trim())) {
            Toast.makeText(this, "Неверный формат электронной почты", Toast.LENGTH_SHORT).show()
            return false
        } else if (passwordInput.text.toString().trim().length < 3) {
            Toast.makeText(this, "Пароль должен быть не менее 3 символов", Toast.LENGTH_SHORT).show()
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