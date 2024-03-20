package com.example.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
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