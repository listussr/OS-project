package com.example.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

class ChangingProfilePasswordAccessActivity : ComponentActivity() {
    private val password: String = "12345"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changing_profile_password_access)
    }

    /**
     * Переходим к странице смены пароля
     */
    private fun toChangePassword() {
        val intent = Intent(this@ChangingProfilePasswordAccessActivity, ChangingPasswordActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Изменяем задний фон у полей ввода почты и пароля
     */
    private fun changeLoginWidgetMistake() {
        val textView = findViewById<TextView>(R.id.editTextNumberPasswordAccess)
        textView.text=""
        textView.hint = "Неправильный код!"
        textView.setBackgroundResource(R.drawable.mistake_field)
        textView.backgroundTintMode = null
    }

    /**
     * Обработка нажатия на кнопку "Регистрация"
     */
    fun onRegistrationButtonClicked(view: View) {
        val intent = Intent(this@ChangingProfilePasswordAccessActivity, RegistrationActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Проверка правильности введённего кода из письма
     */
    private fun arePasswordsEqual(passwordEntered: String) : Boolean {
        return passwordEntered == password
    }

    /**
     * Обработка нажатия на кнопку изменить пароль
     */
    fun onChangePasswordButtonClicked(view: View) {
        val textView = findViewById<EditText>(R.id.editTextNumberPasswordAccess)
        val passwordEntered: String = textView.text.toString()
        if(!arePasswordsEqual(passwordEntered)){
            changeLoginWidgetMistake()
        } else {
            toChangePassword()
        }
    }
}