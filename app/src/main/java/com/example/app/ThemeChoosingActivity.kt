package com.example.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.ComponentActivity

class ThemeChoosingActivity : ComponentActivity() {

    /**
     * Файл с настройками приложения
     */
    private lateinit var settings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_choosing)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        setColorTheme()
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
     * Выставляем флажки выбора тем оформления в соответствующее настройкам состояние
     */
    private fun setRadioButtonFlag(lightThemeFlag: Boolean) {
        val lightButton = findViewById<RadioButton>(R.id.lightRadioButton)
        val darkButton  = findViewById<RadioButton>(R.id.darkRadioButton)
        if(lightThemeFlag){
            lightButton.isChecked = true
            darkButton.isChecked = false
        } else {
            darkButton.isChecked = true
            lightButton.isChecked = false
        }
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
        setRadioButtonFlag(lightAppTheme)
    }

    /**
     * Обработка нажатия на кнопку выбора тёмной темы приложения
     */
    fun lightRadioButtonCallback(view: View){
        val mainLayout = findViewById<LinearLayout>(R.id.main_layout)
        mainLayout.setBackgroundColor(Color.WHITE)
        val editor = settings.edit()
        editor.putBoolean("ColorTheme", true)
        editor.commit()
    }

    /**
     * Обработка нажатия на кнопку выбора тёмной темы приложения
     */
    fun darkRadioButtonCallback(view: View){
        val mainLayout = findViewById<LinearLayout>(R.id.main_layout)
        mainLayout.setBackgroundColor(Color.GRAY)
        val editor = settings.edit()
        editor.putBoolean("ColorTheme", false)
        editor.commit()
    }

    /**
     * Обработка нажатия на кнопку возврата на страницу выше
     */
    fun backButtonCallback(view: View){
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }
}