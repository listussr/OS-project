package com.example.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.app.databinding.ActivityChangingProfilePasswordAccessBinding

class ChangingProfilePasswordAccessActivity : AppCompatActivity() {
    private val password: String = "12345"
    private lateinit var settings: SharedPreferences
    private var lightThemeFlag: Boolean = true
    private lateinit var binding: ActivityChangingProfilePasswordAccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangingProfilePasswordAccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        lightThemeFlag = settings.getBoolean("ColorTheme", true)
        if(!lightThemeFlag){
            setDarkTheme()
        }
    }

    /**
     * Устанавливаем тёмную тему приложения
     */
    private fun setDarkTheme() {
        with(binding){
            textViewWealthFamilyAccessChangingPassword.setTextColor(Color.parseColor("#F1F3F6"))
            mainLayout.setBackgroundResource(R.drawable.rect_gray)
            gettingCodeLayoutAccess.setBackgroundResource(R.drawable.roundrect_dark)
            textView10.setTextColor(Color.parseColor("#F1F3F6"))
            editTextNumberPasswordAccess.setTextColor(Color.parseColor("#F1F3F6"))
            editTextNumberPasswordAccess.setBackgroundResource(R.drawable.roundrect_dark_gray)
            editTextNumberPasswordAccess.setHintTextColor(Color.parseColor("#BCBCBC"))
            registrationButtonAccess.setTextColor(Color.parseColor("#F1F3F6"))
            registrationButtonAccess.setBackgroundResource(R.drawable.roundrect_dark)
        }
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