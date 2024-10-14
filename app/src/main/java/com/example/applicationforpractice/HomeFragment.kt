package com.example.applicationforpractice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_home, container, false)

        val chatRecyclerView = view.findViewById<RecyclerView>(R.id.chatRecyclerView)
        chatRecyclerView.layoutManager = LinearLayoutManager(context)

        val fakeChatList = listOf(
            Chat("Иван Петров", R.drawable.ic_user, "Привет! Как дела?", "12:45"),
            Chat("Анастасия Сидорова", R.drawable.ic_user, "Когда встречаемся?", "11:30"),
            Chat("Максим Смирнов", R.drawable.ic_user, "Увидимся завтра?", "10:15"),
            Chat("Дарья Иванова", R.drawable.ic_user, "Отлично!", "09:00")
        )

        val chatAdapter = ChatAdapter(fakeChatList)
        chatRecyclerView.adapter = chatAdapter

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