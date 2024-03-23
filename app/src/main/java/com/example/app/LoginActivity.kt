package com.example.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class LoginActivity : ComponentActivity() {

    /**
     * Id виджета для ввода пароля
     */
    private val passwordEditor: Int = R.id.editTextPasswordLogin
    private val emailEditor: Int    = R.id.editTextEmailAddressLogin

    private val password: String = "12345a"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    /**
     * Проверка на корректность почты
     */
    private fun isEmailValid(email: String) : Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Считываем введённый пользователем пароль
     */
    private fun getWidgetText(widget: Int) : String {
        val passwordView = findViewById<TextView>(widget)
        return passwordView.text.toString()
    }

    /**
     * Проврка на правильность пароля
     * @param passwordCur
     */
    private fun isPasswordCorrect(passwordCur: String) : Boolean {
        return passwordCur == password
    }

    /**
     * Переход на страницу задания PIN кода приложения
     */
    private fun toSettingPIN() {
        val intent = Intent(this@LoginActivity, ChangingPinActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Очищаем поле ввода пароля
     */
    private fun clearPassword() {
        val passwordText = findViewById<TextView>(passwordEditor)
        passwordText.text = ""
    }

    /**
     * Обработка нажатия на кнопку "Войти"
     */
    fun onEntryLoginButtonClicked(view: View) {
        val curPassword: String = getWidgetText(passwordEditor)
        val emailString: String = getWidgetText(emailEditor)
        if (curPassword.isEmpty()) {
            Toast.makeText(applicationContext, "Пароль не может быть пустым!", Toast.LENGTH_LONG)
                .show()
        } else if (!isPasswordCorrect(curPassword)) {
            Toast.makeText(applicationContext, "Неверный пароль!", Toast.LENGTH_LONG).show()
            clearPassword()
        } else if (!isEmailValid(emailString)) {
            Toast.makeText(applicationContext, "Некорректная почта!", Toast.LENGTH_LONG).show()
        } else {
            toSettingPIN()
        }
    }

    /**
     * Обработка нажатия на кнопку "Регистрация"
     */
    fun onRegistrationButtonClicked(view: View) {
        val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
        startActivity(intent)
        finish()
    }
}