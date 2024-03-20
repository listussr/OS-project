package com.example.app

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.widget.LinearLayout
import android.widget.Toast

class SettingsActivity : ComponentActivity() {

    /**
     * Файл с натсройками приложения
     */
    private lateinit var settings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        setContentView(R.layout.activity_settings)
        setColorTheme(getColorTheme())
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
     * @param colorTheme
     */
    private fun setColorTheme(colorTheme: Boolean) {
        val lightAppTheme: Boolean = getColorTheme()
        val mainLayout = findViewById<LinearLayout>(R.id.main_layout)
        if(lightAppTheme) {
            mainLayout.setBackgroundColor(Color.WHITE)
        } else {
            mainLayout.setBackgroundColor(Color.GRAY)
        }
    }

    fun backButtonCallback(view: View) {
        val intent = Intent(this, MainAppPage::class.java)
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