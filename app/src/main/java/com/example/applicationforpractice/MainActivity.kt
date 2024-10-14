package com.example.applicationforpractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigateToOnboard()
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    fun navigateToOnboard() {
        navigateToFragment(OnboardFragment())
    }

    fun navigateToSignIn() {
        navigateToFragment(SignInFragment())
    }

    fun navigateToSignIn(bundle: Bundle? = null) {
        val fragment = SignInFragment().apply {
            arguments = bundle
        }
        navigateToFragment(fragment)
    }

    fun navigateToSignUp() {
        navigateToFragment(SignUpFragment())
    }

    fun navigateToHome() {
        navigateToFragment(HomeFragment())
    }
}