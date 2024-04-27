package com.example.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.app.databinding.ActivityReportBarChartBinding
import com.example.app.databinding.ActivityReportBinding
import com.example.app.databinding.ActivityReportTableBinding

class ReportTableActivity : ComponentActivity() {
    private lateinit var settings: SharedPreferences

    private lateinit var binding: ActivityReportTableBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_table)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        setColorTheme()
        binding = ActivityReportTableBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        val mainLayout = findViewById<RelativeLayout>(R.id.main_layout)
        if(lightAppTheme) {
            mainLayout.setBackgroundColor(Color.WHITE)
        } else {
            mainLayout.setBackgroundColor(Color.GRAY)
        }
    }

    fun onToBarChartButtonClicked(view: View) {
        val intent = Intent(this@ReportTableActivity, ReportBarChartActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onToPieChartButtonClicked(view: View) {
        val intent = Intent(this@ReportTableActivity, ReportActivity::class.java)
        startActivity(intent)
        finish()
    }
}