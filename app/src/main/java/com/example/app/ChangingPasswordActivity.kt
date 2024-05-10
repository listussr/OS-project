package com.example.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

class ChangingPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changing_password)
    }

    /**
     * Переходу к смене пароля
     */
    private fun toChangePin() {
        val intent = Intent(this@ChangingPasswordActivity, ChangingPinActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Изменяем задний фон у полей ввода почты и пароля
     */
    private fun changeLoginWidgetMistake() {
        val textView = findViewById<TextView>(R.id.editTextPasswordApproveChangePassword)
        textView.text=""
        textView.hint = "Пароли не совпадают!"
        textView.setBackgroundResource(R.drawable.mistake_field)
        textView.backgroundTintMode = null
    }

    /**
     * Обработка нажатия на кнопку "Регистрация"
     */
    fun onRegistrationButtonClicked(view: View) {
        val intent = Intent(this@ChangingPasswordActivity, RegistrationActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Сравнение паролей на соответствие
     */
    private fun arePasswordsEqual(passwordEntered1: String, passwordEntered2: String) : Boolean {
        return passwordEntered1 == passwordEntered2
    }

    /**
     * Обработка нажатия на кнопку Сменить пароль
     */
    fun onChangePasswordButtonClicked(view: View) {
        val textView1 = findViewById<EditText>(R.id.editTextPasswordChangePassword)
        val textView2 = findViewById<EditText>(R.id.editTextPasswordApproveChangePassword)
        val passwordEntered1: String = textView1.text.toString()
        val passwordEntered2: String = textView2.text.toString()
        if(!arePasswordsEqual(passwordEntered1, passwordEntered2)){
            changeLoginWidgetMistake()
        } else {
            toChangePin()
        }
    }
}