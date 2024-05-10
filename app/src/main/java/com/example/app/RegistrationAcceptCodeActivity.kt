package com.example.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class RegistrationAcceptCodeActivity : AppCompatActivity() {
    private val acceptCode = "12345"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_accept_code)
    }

    /**
     * Изменяем задний фон у полей ввода почты и пароля
     */
    private fun changeBackgroundCode() {
        val codeInput = findViewById<TextView>(R.id.editTextEntryCodeRegistrationAccept)
        codeInput.setBackgroundResource(R.drawable.mistake_field)
        codeInput.text = ""
        codeInput.hint = "    Неправильный код!"
        codeInput.setHintTextColor(ContextCompat.getColor(this, R.color.mistake_text))
        codeInput.backgroundTintMode = null
    }

    /**
     * Переход на страницу задания PIN кода приложения
     */
    private fun toSettingPIN() {
        val intent = Intent(this@RegistrationAcceptCodeActivity, ChangingPinActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Обработка нажатия на кнопку "Продолжить"
     */
    fun onContinueButtonClicked(view: View) {
        val codeInput = findViewById<TextView>(R.id.editTextEntryCodeRegistrationAccept)
        val code: String = codeInput.text.toString()
        if(code != acceptCode) {
            changeBackgroundCode()
        } else {
            toSettingPIN()
        }
    }

    /**
     * Переход на главную страницу
     */
    fun onEntryButtonClicked(view: View) {
        val intent = Intent(this@RegistrationAcceptCodeActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}