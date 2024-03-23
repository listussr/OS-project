package com.example.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class RegistrationActivity : ComponentActivity() {

    /**
     * Id виджетов с информацией
     */
    private val passwordEdit     = R.id.editTextPasswordRegistration
    private val passwordEditCopy = R.id.editTextPasswordRegistrationExtra
    private val emailEdit        = R.id.editTextEmailAddressRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

    /**
     * Проверка на корректность почты
     */
    private fun isEmailValid(email: String) : Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Считываем данные из виджетов, наследующихся от TextView
     * @param widget
     */
    private fun getViewInfo(widget: Int) : String {
        val viewText = findViewById<TextView>(widget)
        return viewText.text.toString()
    }

    /**
     * Сравниваем пароли на равенство
     * @param password
     * @param passwordCopy
     */
    private fun arePasswordsEqual(password: String, passwordCopy: String) : Boolean {
        return password == passwordCopy
    }

    /**
     * Обработка нажатия на кнопку "Уже зарегистрирован"
     */
    fun onEntryButtonClicked(view: View) {
        val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Очистка поля ввода под пароль
     * @param widget
     */
    private fun clearPassword(widget: Int) {
        val passwordText = findViewById<TextView>(widget)
        passwordText.text = ""
    }

    /**
     * Переход на страницу задания PIN кода приложения
     */
    private fun toSettingPIN() {
        val intent = Intent(this@RegistrationActivity, ChangingPinActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Обработка нажатия на кнопку "Войти"
     */
    fun onContinueButtonClicked(view: View) {
        val emailString: String = getViewInfo(emailEdit)
        val passwordString: String = getViewInfo(passwordEdit)
        val passwordStringCopy: String = getViewInfo(passwordEditCopy)

        if(passwordString.isEmpty()){
            clearPassword(passwordEditCopy)
            Toast.makeText(applicationContext, "Пароль не может быть пустым!", Toast.LENGTH_LONG).show()
        } else if(!arePasswordsEqual(passwordString, passwordStringCopy)){
            clearPassword(passwordEdit)
            clearPassword(passwordEditCopy)
            Toast.makeText(applicationContext, "Пароли не совпадают! Повторите ввод!", Toast.LENGTH_LONG).show()
        } else if(emailString.isEmpty()) {
            Toast.makeText(applicationContext, "Почта не может быть пустой!", Toast.LENGTH_LONG).show()
        } else if(!isEmailValid(emailString)){
            Toast.makeText(applicationContext, "Некорректная почта!", Toast.LENGTH_LONG).show()
        } else {
            toSettingPIN()
        }
    }
}