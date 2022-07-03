package com.example.icare

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.icare.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView

@SuppressLint("CheckResult")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

//  Auth
        auth = FirebaseAuth.getInstance()

//  Email Validation
        val emailStream = RxTextView.textChanges(binding.erEmail)
            .skipInitialValue()
            .map { email ->
                email.isEmpty()
            }
        emailStream.subscribe{
            showEmailValidAlert(it)
        }

//  Password Validation
        val passwordStream = RxTextView.textChanges(binding.erPassword)
            .skipInitialValue()
            .map { password ->
                password.isEmpty()
            }
        passwordStream.subscribe{
            showPasswordMinimalAlert(it)
        }

        binding.back1.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.btnlogin.setOnClickListener{
            val email = binding.erEmail.text.toString().trim()
            val password = binding.erPassword.text.toString().trim()
            loginUser(email, password)
        }
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        binding.erPassword.error = if (isNotValid) "Password tidak boleh kosong!" else null
    }

    private fun showEmailValidAlert(isNotValid: Boolean) {
        binding.erEmail.error = if (isNotValid) "Email tidak boleh kosong!" else null
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { login ->
                if (login.isSuccessful) {
                    Intent(this, HomeActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                        Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, login.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

    }
}