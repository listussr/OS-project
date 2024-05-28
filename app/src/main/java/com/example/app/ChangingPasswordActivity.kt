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
import com.example.app.databinding.ActivityChangingPasswordBinding

class ChangingPasswordActivity : AppCompatActivity() {
    private lateinit var settings: SharedPreferences
    private var lightThemeFlag: Boolean = true
    private lateinit var binding: ActivityChangingPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangingPasswordBinding.inflate(layoutInflater)
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
            mainLayout.setBackgroundResource(R.drawable.rect_gray)
            changingPasswordLayout.setBackgroundResource(R.drawable.roundrect_dark)
            textView14.setTextColor(Color.parseColor("#F1F3F6"))
            editTextPasswordChangePassword.setTextColor(Color.parseColor("#F1F3F6"))
            editTextPasswordChangePassword.setBackgroundResource(R.drawable.roundrect_dark_gray)
            editTextPasswordChangePassword.setHintTextColor(Color.parseColor("#BCBCBC"))
            editTextPasswordApproveChangePassword.setTextColor(Color.parseColor("#F1F3F6"))
            editTextPasswordApproveChangePassword.setBackgroundResource(R.drawable.roundrect_dark_gray)
            editTextPasswordApproveChangePassword.setHintTextColor(Color.parseColor("#BCBCBC"))
            registrationButtonChangingPassword.setTextColor(Color.parseColor("#F1F3F6"))
            registrationButtonChangingPassword.setBackgroundResource(R.drawable.roundrect_dark)
        }
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