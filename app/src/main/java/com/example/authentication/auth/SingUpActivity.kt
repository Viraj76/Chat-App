package com.example.authentication.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.authentication.MainActivity
import com.example.authentication.databinding.ActivitySingUpBinding
import com.example.authentication.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SingUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySingUpBinding
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.textView.setOnClickListener {
            val intent = Intent(this, SingInActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnSignUp.setOnClickListener {
            val name = binding.nameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val password = binding.passET.text.toString()
            val confirmPassword = binding.confirmPassEt.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val currentUserId = firebaseAuth.currentUser?.uid
                                addUserToDatabase(name,email,currentUserId)
                                val intent = Intent(this, SingInActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Passwords are not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (email.isEmpty()) Toast.makeText(
                    this,
                    "Please Enter The Email",
                    Toast.LENGTH_SHORT
                ).show()
                else if (password.isEmpty()) Toast.makeText(
                    this,
                    "Please Enter the Password",
                    Toast.LENGTH_SHORT
                ).show()
                else Toast.makeText(this, "Please Reconfirm the passwords", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun addUserToDatabase(name: String, email: String, currentUserId: String?) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val user = Users(name,email,currentUserId)
        if (currentUserId != null) {
            databaseReference.child(currentUserId).setValue(user)
        }
    }
    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}