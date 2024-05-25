package com.example.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.app.dataprocessing.JsonConverter
import com.example.app.dataprocessing.ServerInteraction
import com.example.app.dataprocessing.UserRegClass
import kotlinx.coroutines.runBlocking

class RegistrationAcceptCodeActivity : AppCompatActivity() {
    private val acceptCode = "12345"
    private var email = "list@mail.ru"
    private var password = "12345678"
    private lateinit var settings: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_accept_code)
        email = intent.getStringExtra("Email").toString()
        password = intent.getStringExtra("Password").toString()
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
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
     * Создаём на сервере нового пользователя
     */
    private fun registerUser() {
        val request = JsonConverter.ToJson.toUserRegClassJson(
            UserRegClass(
                name="user2",
                email=email,
                password=password
            )
        )
        runBlocking {
            Log.d("AppJson", "In runBlocking")
            ServerInteraction.User.apiRegister(request)
        }
        settings.edit().putString("UserPassword", password).commit()
    }

    /**
     * Переход на страницу задания PIN кода приложения
     */
    private fun toSettingPIN() {
        val intent = Intent(this@RegistrationAcceptCodeActivity, ChangingPinActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun toLogin() {
        val intent = Intent(this@RegistrationAcceptCodeActivity, LoginActivity::class.java)
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
            registerUser()
            toLogin()
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