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

class SignInActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SignInActivity"
    }

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private var fakeEmail = "test@gmail.com"
    private var fakePassword = "123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sign_in)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        emailInput = findViewById<EditText>(R.id.emailInput)
        passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.button1)
        val registerButton = findViewById<Button>(R.id.button2)

        registerButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivityForResult(intent, 1001)
        }

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Неверный формат электронной почты", Toast.LENGTH_SHORT).show()
            } else if (password.length < 3) {
                Toast.makeText(this, "Пароль должен быть не менее 3 символов", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (email == fakeEmail && password == fakePassword) {
                    Toast.makeText(this, "Успешный вход", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "Неправильная электронная почта или пароль",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == RESULT_OK) {

            val login = data?.getStringExtra("login")
            val email = data?.getStringExtra("email")
            val password = data?.getStringExtra("password")
            if (email != null) {
                fakeEmail = email
            }
            if (password != null) {
                fakePassword = password
            }

            val user = data?.getParcelableExtra<User>("user")

            if (login != null && email != null) {
                emailInput.setText(email)
                Toast.makeText(this, "Добро пожаловать, $login!", Toast.LENGTH_SHORT).show()
            }
            if (user != null) {
                emailInput.setText(user.email)
                Toast.makeText(this, "Добро пожаловать 2, ${user.login}!", Toast.LENGTH_LONG).show()
            }
        }
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