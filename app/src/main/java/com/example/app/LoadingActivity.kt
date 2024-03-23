package com.example.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.activity.ComponentActivity

class LoadingActivity : ComponentActivity() {

    /**
     * Файл с настройками приложения
     */
    private lateinit var settings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        processLoading()
    }

    /**
     * Получаем из настроек флаг входа в приложение
     */
    private fun getWasRegisteredFlag() : Boolean {
        val wasRegistered: Boolean
        if(settings.contains("WasRegistered")){
            wasRegistered = settings.getBoolean("WasRegistered", true)
        }
        else {
            wasRegistered = false
            val editor = settings.edit()
            editor.putBoolean("WasRegistered", false)
            editor.commit()
        }
        Toast.makeText(applicationContext, "Was registered, $wasRegistered", Toast.LENGTH_LONG).show()
        return wasRegistered
    }

    /**
     * Переходим к вводу кода безопасности
     */
    private fun toPassword() {
        val intent = Intent(this@LoadingActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Переходим к регистрации в приложении
     */
    private fun toRegistration() {
        val intent = Intent(this@LoadingActivity, RegistrationActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * В зависимости от состояния флага входа в приложение переходим на следующую страницу
     */
    private fun processLoading() {
        if(getWasRegisteredFlag()){
            toPassword()
        } else {
            toRegistration()
        }
    }
}