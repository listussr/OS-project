package com.example.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.SharedPreferences
import android.graphics.Color
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.app.databinding.ActivityPlanBinding
import com.example.app.databinding.ActivityReportBarChartBinding
import com.example.app.databinding.ActivityReportBinding


class ReportBarChartActivity : ComponentActivity() {

    /**
     * Файл с натсройками приложения
     */
    private lateinit var settings: SharedPreferences

    private lateinit var binding: ActivityReportBarChartBinding

    private val listOfInfoData: List<Pair<Int, Int>> = listOf(
        Pair(10000, 9000),
        Pair(11000, 8000),
        Pair(12000, 10000),
        Pair(7000, 13000),
        Pair(11000, 12000),
        Pair(8000, 9000),
        Pair(10500, 8000),
        Pair(6000, 2000),
        Pair(14000, 12000),
        Pair(10000, 9000),
        Pair(15000, 11000),
        Pair(5000, 20000),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_bar_chart)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        setColorTheme()
        binding = ActivityReportBarChartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        //binding.barChart.setInfoList(listOfInfoData)
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


    fun onToReportButtonClicked(view: View) {
        val intent = Intent(this@ReportBarChartActivity, ReportActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onToTableButtonClicked(view: View) {
        val intent = Intent(this@ReportBarChartActivity, ReportTableActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onTwelveMonthsClicked(view: View) {
        binding.barChart.setInfoList(listOfInfoData)
        binding.barChart.invalidate()
    }
}