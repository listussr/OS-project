package com.example.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

class LoginActivity : ComponentActivity() {

    /**
     * Id виджета для ввода пароля
     */
    private val passwordEditor: Int = R.id.editTextPasswordLogin
    private val emailEditor: Int    = R.id.editTextEmailAddressLogin

    private val password: String = "1"

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
     * Убираем с экрана кнопку с сохранением пароля и заменяем её кнопкой со сменой пароля
     */
    private fun changeVisibility() {
        val checkbox = findViewById<CheckBox>(R.id.rememberUserCheckBoxLogin)
        val button   = findViewById<Button>(R.id.forgotPasswordButtonLogin)
        checkbox.visibility = CheckBox.GONE
        button.visibility = Button.VISIBLE
    }


    /**
     * Изменяем задний фон у полей ввода почты и пароля
     */
    private fun changeBackgroundLogin() {
        val loginInput = findViewById<TextView>(R.id.editTextEmailAddressLogin)
        loginInput.setBackgroundResource(R.drawable.mistake_field)
        loginInput.text = ""
        loginInput.hint = "    Неправильная почта!"
        loginInput.setHintTextColor(ContextCompat.getColor(this, R.color.mistake_text))
        loginInput.backgroundTintMode = null
        val passwordInput = findViewById<TextView>(R.id.editTextPasswordLogin)
        passwordInput.setBackgroundResource(R.drawable.mistake_field)
        passwordInput.text = ""
        passwordInput.hint = "    Пароль"
        passwordInput.backgroundTintMode = null
    }

    /**
     * Изменяем задний фон у полей ввода почты и пароля
     */
    private fun changeBackgroundPassword() {
        val loginInput = findViewById<TextView>(R.id.editTextEmailAddressLogin)
        loginInput.setBackgroundResource(R.drawable.mistake_field)
        loginInput.text = ""
        loginInput.hint = "    Почта"
        loginInput.backgroundTintMode = null
        val passwordInput = findViewById<TextView>(R.id.editTextPasswordLogin)
        passwordInput.setBackgroundResource(R.drawable.mistake_field)
        passwordInput.text = ""
        passwordInput.hint = "    Неправильный Пароль!"
        passwordInput.setHintTextColor(ContextCompat.getColor(this, R.color.mistake_text))
        passwordInput.backgroundTintMode = null
    }

    /**
     * Обработка нажатия на кнопку "Войти"
     */
    fun onEntryLoginButtonClicked(view: View) {
        val curPassword: String = getWidgetText(passwordEditor)
        val emailString: String = getWidgetText(emailEditor)
        if (curPassword.isEmpty()) {
            Toast.makeText(applicationContext, "Пароль не может быть пустым!", Toast.LENGTH_LONG).show()
            changeBackgroundPassword()
            changeVisibility()
        } else if (!isPasswordCorrect(curPassword)) {
            Toast.makeText(applicationContext, "Неверный пароль!", Toast.LENGTH_LONG).show()
            changeBackgroundPassword()
            changeVisibility()
        } else if (!isEmailValid(emailString)) {
            Toast.makeText(applicationContext, "Некорректная почта!", Toast.LENGTH_LONG).show()
            changeBackgroundLogin()
            changeVisibility()
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