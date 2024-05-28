package com.example.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.app.databinding.ActivityRegistrationAcceptCodeBinding
import com.example.app.dataprocessing.FilterClass
import com.example.app.dataprocessing.JsonConverter
import com.example.app.dataprocessing.ServerInteraction
import com.example.app.dataprocessing.UserRegClass
import kotlinx.coroutines.runBlocking

class RegistrationAcceptCodeActivity : AppCompatActivity() {
    private val acceptCode = "12345"
    private var email = "list@mail.ru"
    private var password = "12345678"
    private lateinit var settings: SharedPreferences
    private var lightThemeFlag: Boolean = true
    private lateinit var binding: ActivityRegistrationAcceptCodeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationAcceptCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        email = intent.getStringExtra("Email").toString()
        password = intent.getStringExtra("Password").toString()
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        lightThemeFlag = settings.getBoolean("ColorTheme", true)
        if(!lightThemeFlag){
            setDarkTheme()
        }
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
     * Устанавливаем тёмную тему приложения
     */
    private fun setDarkTheme() {
        with(binding) {
            mainLayout.setBackgroundResource(R.drawable.rect_gray)
            wealthFamilyTextViewRegistrationAccept.setTextColor(Color.parseColor("#F1F3F6"))
            wealthFamilyTextViewRegistrationAccept.setBackgroundResource(R.drawable.roundrect_dark)
            RegistrationLayoutRegistrationAccept.setBackgroundResource(R.drawable.roundrect_dark)
            editTextEntryCodeRegistrationAccept.setBackgroundResource(R.drawable.roundrect_dark_gray)
            editTextEntryCodeRegistrationAccept.setTextColor(Color.parseColor("#F1F3F6"))
            editTextEntryCodeRegistrationAccept.setHintTextColor(Color.parseColor("#BCBCBC"))
            textViewRegistrationReg.setTextColor(Color.parseColor("#F1F3F6"))
            textViewRegistrationReg.setBackgroundResource(R.drawable.roundrect_dark)
            entryButtonRegistrationAccept.setTextColor(Color.parseColor("#F1F3F6"))
            entryButtonRegistrationAccept.setBackgroundResource(R.drawable.roundrect_dark)
        }
    }

    private fun generateName(): String {
        var name = "user"
        var length = (3..10).random()
        while(length != 0){
            name += (0..9).random().toString()
            length--
        }
        return name
    }

    /**
     * Проверяем существует ли такая почта в бд
     */
    private fun checkExistence(): Boolean {
        val request = JsonConverter.ToJson.toFilterClassArrayJson(
            arrayOf(
                FilterClass(
                    "email",
                    email,
                    "EQUAL"
                )
            )
        )
        val response: String?
        runBlocking {
            response = ServerInteraction.User.apiGetUserByFilter(settings.getString("Token", "")!!, request)
        }
        val user = JsonConverter.FromJson.userListJson(response)
        Log.d("AppJson", "Response for email: $response")
        return response == "[]"
    }

    /**
     * Создаём на сервере нового пользователя
     */
    private fun registerUser() {
        if (checkExistence()) {
            val request = JsonConverter.ToJson.toUserRegClassJson(
                UserRegClass(
                    name = generateName(),
                    email = email,
                    password = password
                )
            )
            Log.d("AppJson", "Registration Request: $request")
            runBlocking {
                Log.d("AppJson", "In runBlocking")
                ServerInteraction.User.apiRegister(request)
            }
            settings.edit().putString("UserPassword", password).commit()
        } else {
            Toast.makeText(applicationContext, "Пользователь с такой почтой существует!", Toast.LENGTH_LONG).show()
            val intent = Intent(this@RegistrationAcceptCodeActivity, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
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