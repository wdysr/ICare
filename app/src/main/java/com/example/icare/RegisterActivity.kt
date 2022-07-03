package com.example.icare

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.icare.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView

@SuppressLint("CheckResult")
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        
//  Auth
        auth = FirebaseAuth.getInstance()

//  FullName Validation
        val nameStream = RxTextView.textChanges(binding.erFullName)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        nameStream.subscribe{
            showNameExistAlert(it)
        }

//  Email Validation
        val emailStream = RxTextView.textChanges(binding.erEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe{
            showEmailValidAlert(it)
        }

//  Username Validation
        val usernameStream = RxTextView.textChanges(binding.erUsername)
            .skipInitialValue()
            .map { username ->
                username.length < 6
            }
        usernameStream.subscribe {
            showUsernameMinimalAlert(it)
        }

//  Password Validation
        val passwordStream = RxTextView.textChanges(binding.erPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 6
            }
        passwordStream.subscribe {
            showPasswordMinimalAlert(it)
        }

//  Click Button
        binding.btnback.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.btnregis.setOnClickListener{
            val email = binding.erEmail.text.toString().trim()
            val password = binding.erPassword.text.toString().trim()
            registerUser(email, password)
        }
        binding.txtclick.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun showNameExistAlert(isNotValid: Boolean) {
        binding.erFullName.error = if (isNotValid) "Nama tidak boleh kosong!" else null
    }

    private fun showUsernameMinimalAlert(isNotValid: Boolean) {
        binding.erUsername.error = if (isNotValid) "Username harus lebih dari 5 huruf" else null
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        binding.erPassword.error = if (isNotValid) "Password harus lebih dari 6 angka" else null
    }

    private fun showEmailValidAlert(isNotValid: Boolean) {
        binding.erEmail.error = if (isNotValid) "Email tidak valid!" else null
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    Toast.makeText(this, "Pendaftaran Akun Berhasil!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

}