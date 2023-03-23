package com.example.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.authentication.databinding.ActivityMainBinding
import com.example.authentication.databinding.ActivitySingInBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvText.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, SingInActivity::class.java)
            startActivity(intent)
        }
    }
}