package com.example.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.app.databinding.ActivityLoginBinding
import com.example.app.databinding.ActivityMainBinding
import com.example.app.dataprocessing.FilterClass
import com.example.app.dataprocessing.JsonConverter
import com.example.app.dataprocessing.ServerInteraction
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {

    /**
     * Id виджета для ввода пароля
     */
    private val passwordEditor: Int = R.id.editTextPasswordLogin
    private val emailEditor: Int    = R.id.editTextEmailAddressLogin

    private lateinit var settings: SharedPreferences

    private lateinit var binding: ActivityLoginBinding

    private val password: String = "12345678"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        updateCheckbox()
        setLanguageInViews()
    }

    /**
     * Проверка на корректность почты
     */
    private fun isEmailValid(email: String) : Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Устанавливаем язык во всех виджетах на activity
     */
    private fun setLanguageInViews() {
        val language = getLanguageFlag()
        if(!language){
            binding.textViewEntryWealthFamilyLogin.text = getString(R.string.t_entry_wf_eng)
            binding.editTextEmailAddressLogin.hint = getString(R.string.t_email_eng)
            binding.editTextPasswordLogin.hint = getString(R.string.t_password_eng)
            binding.rememberUserCheckBoxLogin.text = getString(R.string.t_remember_user_eng)
            binding.entryButtonLogin.text = getString(R.string.t_entry_eng)
            binding.registrationButtonAccess.text = getString(R.string.t_registration_eng)
            binding.forgotPasswordButtonLogin.text = getString(R.string.t_forgot_password_eng)
        }
    }

    /**
     * Получаем из настроек язык приложения
     */
    private fun getLanguageFlag() : Boolean {
        return settings.getBoolean("Language", true)
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
     * Ставим checkbox в корректное положение
     */
    private fun updateCheckbox() {
        val checkBox = findViewById<CheckBox>(R.id.rememberUserCheckBoxLogin)
        checkBox.isChecked = settings.getBoolean("RememberUser", true)
    }

    /**
     * Изменяем задний фон у полей ввода почты и пароля
     */
    private fun changeBackgroundLogin() {
        val loginInput = findViewById<TextView>(R.id.editTextEmailAddressLogin)
        loginInput.setBackgroundResource(R.drawable.mistake_field)
        loginInput.text = ""
        if(!getLanguageFlag()){
            loginInput.hint = "    Неправильная почта!"
        } else {
            loginInput.hint = "    Incorrect email!"
        }
        loginInput.setHintTextColor(ContextCompat.getColor(this, R.color.mistake_text))
        loginInput.backgroundTintMode = null
        val passwordInput = findViewById<TextView>(R.id.editTextPasswordLogin)
        passwordInput.setBackgroundResource(R.drawable.mistake_field)
        passwordInput.text = ""
        if(!getLanguageFlag()){
            passwordInput.hint = "    Неправильный Пароль"
        } else {
            passwordInput.hint = "    Incorrect Password"
        }
        passwordInput.backgroundTintMode = null
    }

    /**
     * Изменяем задний фон у полей ввода почты и пароля
     */
    private fun changeBackgroundPassword() {
        val passwordInput = findViewById<TextView>(R.id.editTextPasswordLogin)
        passwordInput.setBackgroundResource(R.drawable.mistake_field)
        passwordInput.text = ""
        if(!getLanguageFlag()){
            passwordInput.hint = "    Неправильный Пароль!"
        } else {
            passwordInput.hint = "    Incorrect password!"
        }
        passwordInput.setHintTextColor(ContextCompat.getColor(this, R.color.mistake_text))
        passwordInput.backgroundTintMode = null
    }

    /**
     * Обработка нажатия кнопки запомнить пользователя
     */
    fun onRememberUserClicked(view: View) {
        val checkBox = findViewById<CheckBox>(R.id.rememberUserCheckBoxLogin)
        val rememberFlag: Boolean = checkBox.isChecked
        val editor = settings.edit()
        editor.putBoolean("RememberUser", rememberFlag)
        editor.commit()
    }

    /**
     * Получаем из настроек флаг входа в приложение
     */
    private fun getWasRegisteredFlag() : Boolean {
        val wasRegistered: Boolean = settings.getBoolean("WasRegistered", true)
        Toast.makeText(applicationContext, "Was registered, $wasRegistered", Toast.LENGTH_LONG).show()
        return wasRegistered
    }

    /**
     * Переход на главную страницу
     */
    private fun toEnteringPin() {
        val intent = Intent(this@LoginActivity, PINCodeActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Проверка на подключение к интернету
     */
    private fun checkForConnection(context: Context) : Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

        /**
     * Образаемся к серверу с запросом о существовании пользователя с такой почтой
     */
    private fun getUserExistenceFlag(): Boolean {
        var response: Pair<String?, String?>
        val request = "{\"name\": \"user1\", \"password\":\"$password\"}"
        runBlocking {
            Log.d("AppJson", request)
            response = ServerInteraction.User.apiLogin(
                request
            )
            Log.d("AppJson", "Response login: $response")
        }
        return if(response.second == null || response.first == null) {
            Toast.makeText(applicationContext, "Пользователя с таким email не существует. Пройдите регистрацию.", Toast.LENGTH_LONG).show()
            false
        } else {
            Log.d("AppJson", "Login response: ${response.first}")
            Log.d("AppJson", "Login UUID: ${response.second}")
            settings.edit().putString("Token", response.first).commit()
            settings.edit().putString("UsersUUID", response.second).commit()
            true
        }
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
        } else if(!checkForConnection(applicationContext)){
            Toast.makeText(applicationContext, "Для входа в аккаунт необходимо подключение к интернету", Toast.LENGTH_LONG).show()
        } else if(!getUserExistenceFlag()) {
            changeBackgroundLogin()
        } else if(!getWasRegisteredFlag()){
            toSettingPIN()
        } else {
            toEnteringPin()
        }
    }

    /**
     * Обработка нажатия на кнопку забыли пароль
     */
    fun onForgotPasswordClicked(view: View) {
        val intent = Intent(this@LoginActivity, ChangingProfilePasswordAccessActivity::class.java)
        startActivity(intent)
        finish()
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