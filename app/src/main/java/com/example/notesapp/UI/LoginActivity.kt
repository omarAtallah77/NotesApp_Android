package com.example.notesapp.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        val username: EditText = findViewById(R.id.etUsername)
        val password: EditText = findViewById(R.id.etPassword)
        val button: Button = findViewById(R.id.btnLogin)

        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        // ✅ If already logged in before → go directly to next screen
        if (isLoggedIn) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        button.setOnClickListener {
            val usernameTxt = username.text.toString()
            val passwordTxt = password.text.toString()

            // ✅ Check login credentials
            if (usernameTxt == "Omar Atallah" && passwordTxt == "323230") {
                // Save login state
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.putString("Name", usernameTxt)
                editor.putString("Password", passwordTxt)
                editor.apply()

                // Go to next screen
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}