package com.example.applicationforpractice

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val chatRecyclerView = findViewById<RecyclerView>(R.id.chatRecyclerView)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)

        val fakeChatList = listOf(
            Chat("Иван Петров", R.drawable.ic_user, "Привет! Как дела?", "12:45"),
            Chat("Анастасия Сидорова", R.drawable.ic_user, "Когда встречаемся?", "11:30"),
            Chat("Максим Смирнов", R.drawable.ic_user, "Увидимся завтра?", "10:15"),
            Chat("Дарья Иванова", R.drawable.ic_user, "Отлично!", "09:00")
        )

        val chatAdapter = ChatAdapter(fakeChatList)
        chatRecyclerView.adapter = chatAdapter
    }
}