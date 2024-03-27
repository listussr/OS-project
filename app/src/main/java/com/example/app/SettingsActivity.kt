package com.example.app

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast

class SettingsActivity : ComponentActivity() {

    /**
     * Файл с натсройками приложения
     */
    private lateinit var settings: SharedPreferences

    private var secondClickExitButtonFlag: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        setContentView(R.layout.activity_settings)
        setColorTheme()
    }

    /**
     * Переходим к регистрации в приложении
     */
    private fun toRegistration() {
        val intent = Intent(this@SettingsActivity, RegistrationActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Изменяем флаг входа в приложение
     */
    private fun changeWasRegisteredFlag() {
        val editor = settings.edit()
        editor.putBoolean("WasRegistered", false)
        editor.commit()
    }

    /**
     * Меняем текст кнопки при первом нажатии
     */
    private fun changeViewText() {
        val button = findViewById<Button>(R.id.buttonExit)
        button.text = "Если вы уверены, нажмите ещё раз"
    }

    /**
     * Обработка нажатия на кнопку выхода из учётной записи
     */
    fun onExitUserClicked(view: View) {
        if(secondClickExitButtonFlag) {
            secondClickExitButtonFlag = false
            changeWasRegisteredFlag()
            toRegistration()
        } else {
            changeViewText()
            secondClickExitButtonFlag = true
        }
    }

    /**
     * Функция получения цветовой темы приложения из SharedPreferences
     * @return lightThemeFlag
     */
    private fun getColorTheme() : Boolean {
        val lightThemeFlag: Boolean = settings.getBoolean("ColorTheme", true)
        Toast.makeText(applicationContext, "Light theme, $lightThemeFlag", Toast.LENGTH_LONG).show()
        return lightThemeFlag
    }

    /**
     * Функция установки цветовой темы, исходя из установленных настроек
     */
    private fun setColorTheme() {
        val lightAppTheme: Boolean = getColorTheme()
        val mainLayout = findViewById<LinearLayout>(R.id.main_layout)
        if(lightAppTheme) {
            mainLayout.setBackgroundColor(Color.WHITE)
        } else {
            mainLayout.setBackgroundColor(Color.GRAY)
        }
    }

    fun backButtonCallback(view: View) {
        val intent = Intent(this@SettingsActivity, MainAppPageActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun changeThemeButtonCallback(view: View) {
        val intent = Intent(this, ThemeChoosingActivity::class.java)
        startActivity(intent)
    }

    fun changePinCodeButtonCallback(view: View){
        val intent = Intent(this, ChangingPinActivity::class.java)
        startActivity(intent)
    }


}