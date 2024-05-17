package com.example.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.app.databinding.ActivityLoginBinding
import com.example.app.databinding.ActivityRegistrationBinding
import com.example.app.dataprocessing.ServerInteraction
import com.example.app.dataprocessing.UserRegClass

class RegistrationActivity : AppCompatActivity() {

    /**
     * Id виджетов с информацией
     */
    private val passwordEdit     = R.id.editTextPasswordRegistration
    private val passwordEditCopy = R.id.editTextEntryCodeRegistrationAccept
    private val emailEdit        = R.id.editTextEmailAddressRegistration

    private lateinit var settings: SharedPreferences

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        setContentView(R.layout.activity_registration)
        setLanguageInViews()
    }

    /**
     * Устанавливаем язык во всех виджетах на activity
     */
    private fun setLanguageInViews() {
        Log.v("App", "Entered into language")
        val language = getLanguageFlag()
        Log.v("App", "Russian: $language")
        if(!language){
            Log.v("App", "Entered into eng")
            findViewById<TextView>(R.id.textViewRegistrationReg).text =  getString(R.string.t_registration_eng)
            //binding.textViewRegistrationReg.text = getString(R.string.t_registration_eng)
            Log.v("App", getString(R.string.t_registration_eng))
            findViewById<EditText>(R.id.editTextEmailAddressRegistration).hint = getString(R.string.t_email_input_eng)
            findViewById<EditText>(R.id.editTextPasswordRegistration).hint = getString(R.string.t_imagine_password_eng)
            findViewById<EditText>(R.id.editTextEntryCodeRegistrationAccept).hint = getString(R.string.t_approve_new_password_eng)
            findViewById<Button>(R.id.acceptButton).text = getString(R.string.t_continue_eng)
            findViewById<Button>(R.id.enterButton).text = getString(R.string.t_was_registered_eng)
        }
    }

    /**
     * Получаем из настроек язык приложения
     */
    private fun getLanguageFlag() : Boolean {
        return settings.getBoolean("Language", true)
    }

    /** textViewRegistrationReg
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
    private fun toAcceptCode(email: String, password: String) {
        val intent = Intent(this@RegistrationActivity, RegistrationAcceptCodeActivity::class.java)
        intent.putExtra("Email", email)
        intent.putExtra("Password", password)
        startActivity(intent)
        //finish()
    }

    /**
     * Изменяем задний фон у полей ввода почты и пароля
     */
    private fun changeBackgroundLogin() {
        val loginInput = findViewById<TextView>(R.id.editTextEmailAddressRegistration)
        loginInput.setBackgroundResource(R.drawable.mistake_field)
        if(getLanguageFlag()){
            loginInput.hint = "    Неправильная почта!"
        } else {
            loginInput.hint = "    Incorrect email!"
        }
        loginInput.text = ""
        loginInput.setHintTextColor(ContextCompat.getColor(this, R.color.mistake_text))
        loginInput.backgroundTintMode = null
    }

    /**
     * Изменяем задний фон у поля подтверждения пароля
     */
    private fun changeBackgroundPasswordApprove() {
        val textView = findViewById<TextView>(R.id.editTextEntryCodeRegistrationAccept)
        textView.text=""
        if(getLanguageFlag()){
            textView.hint = "    Неправильный Пароль"
        } else {
            textView.hint = "    Incorrect Password"
        }
        textView.setBackgroundResource(R.drawable.mistake_field)
        textView.backgroundTintMode = null
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
            if(getLanguageFlag()){
                Toast.makeText(applicationContext, "Пароль не может быть пустым!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "Password can't be empty!", Toast.LENGTH_LONG).show()
            }
        } else if(!isEmailValid(emailString)){
            changeBackgroundLogin()
        } else if(!arePasswordsEqual(passwordString, passwordStringCopy)){
            changeBackgroundPasswordApprove()
        } else if(passwordString.length < 8){
            Toast.makeText(applicationContext, "Минимальная длина пароля составляет 8 символов", Toast.LENGTH_LONG).show()
        } else {
            toAcceptCode(emailString, passwordString)
        }
    }
}