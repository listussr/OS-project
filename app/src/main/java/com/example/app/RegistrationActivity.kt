package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class RegistrationActivity : ComponentActivity() {

    private val passwordEdit     = R.id.editTextTextPassword
    private val passwordEditCopy = R.id.editTextTextPassword2
    private val emailEdit        = R.id.editTextTextEmailAddress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

    private fun getEmail() : String {
        val emailEditText = findViewById<TextView>(emailEdit)
        return emailEditText.text.toString()
    }

    private fun getPassword(widget: Int) : String {
        val passwordText = findViewById<TextView>(widget)
        return passwordText.text.toString()
    }

    private fun arePasswordsEqual(password: String, passwordCopy: String) : Boolean {
        return password == passwordCopy
    }

    fun onEntryButtonClicked(view: View) {
        val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun clearPassword(widget: Int) {
        val passwordText = findViewById<TextView>(widget)
        passwordText.text = ""
    }

    private fun toMainPage() {
        val intent = Intent(this@RegistrationActivity, MainAppPage::class.java)
        startActivity(intent)
        finish()
    }

    fun onContinueButtonClicked(view: View) {
        val emailString: String = getEmail()
        val passwordString: String = getPassword(passwordEdit)
        val passwordStringCopy: String = getPassword(passwordEditCopy)
        if(passwordString.isEmpty()){
            clearPassword(passwordEditCopy)
            Toast.makeText(applicationContext, "Пароль не может быть пустым!", Toast.LENGTH_LONG).show()
        } else if(!arePasswordsEqual(passwordString, passwordStringCopy)){
            clearPassword(passwordEdit)
            clearPassword(passwordEditCopy)
            Toast.makeText(applicationContext, "Пароли не совпадают! Повторите ввод!", Toast.LENGTH_LONG).show()
        } else {
            toMainPage()
        }
    }
}