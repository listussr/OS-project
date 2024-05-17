package com.example.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

class LoadingActivity : AppCompatActivity() {

    /**
     * Файл с настройками приложения
     */
    private lateinit var settings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        setBasicSettings()
        processLoading()
    }

    /**
     * Устанавливаем базовые настройки приложения
     */
    private fun setBasicSettings() {
        val editor = settings.edit()
        if (!settings.contains("ColorTheme")){
            editor.putBoolean("ColorTheme", true)
            editor.commit()
        }
        if(!settings.contains("WasRegistered")){
            editor.putBoolean("WasRegistered", false)
            editor.commit()
        }
        if(!settings.contains("RememberUser")){
            editor.putBoolean("RememberUser", false)
            editor.commit()
        }
        if(!settings.contains("Language")){
            editor.putBoolean("Language", false)
            editor.commit()
        }
        if(!settings.contains("LastExpenses")){
            editor.putString("LastExpenses", "null")
            editor.commit()
        }
        if(!settings.contains("LastIncome")){
            editor.putString("LastIncome", "null")
            editor.commit()
        }
        if(!settings.contains("AnalyticsPeriod")){
            editor.putInt("AnalyticsPeriod", 1)
            editor.commit()
        }
        if(!settings.contains("LastExpensesCategory")){
            editor.putString("LastExpensesCategory", "null")
            editor.commit()
        }
        if(!settings.contains("LastIncomeCategory")){
            editor.putString("LastIncomeCategory", "null")
            editor.commit()
        }
        if(!settings.contains("UsersUUID")){
            editor.putString("UsersUUID", "null")
            editor.commit()
        }
        if(!settings.contains("UserPassword")){
            editor.putString("UserPassword", "12345")
            editor.commit()
        }
    }

    /**
     * Получаем из настроек флаг входа в приложение
     */
    private fun getWasRegisteredFlag() : Boolean {
        val wasRegistered: Boolean = settings.getBoolean("WasRegistered", true)
        //Toast.makeText(applicationContext, "Was registered, $wasRegistered", Toast.LENGTH_LONG).show()
        return wasRegistered
    }

    /**
     * Получаем флаг запоминания пользователя из настроек
     */
    private fun getRememberUserFlag() : Boolean {
        val rememberUserFlag: Boolean = settings.getBoolean("RememberUser", false)
        //Toast.makeText(applicationContext, "Was registered, $rememberUserFlag", Toast.LENGTH_LONG).show()
        return rememberUserFlag
    }

    /**
     * Переходим к вводу кода безопасности
     */
    private fun toPassword() {
        val intent = Intent(this@LoadingActivity, PINCodeActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Переходим к регистрации в приложении
     */
    private fun toRegistration() {
        val intent = Intent(this@LoadingActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * В зависимости от состояния флага входа в приложение переходим на следующую страницу
     */
    private fun processLoading() {
        if(getWasRegisteredFlag() && getRememberUserFlag()){
            toPassword()
        } else {
            toRegistration()
        }
    }
}