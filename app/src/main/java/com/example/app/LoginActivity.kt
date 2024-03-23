package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onEntryLoginButtonClicked(view: View) {
        val intent = Intent(this@LoginActivity, MainAppPage::class.java)
        startActivity(intent)
        finish()
    }

    fun onRegistrationButtonClicked(view: View) {
        val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
        startActivity(intent)
        finish()
    }
}