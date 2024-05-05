package com.example.app

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {

    /**
     * Файл с настройками приложения
     */
    private lateinit var settings: SharedPreferences

    private lateinit var binding: ActivityReportBinding

    private val listOfInfoIncome: List<Pair<Int, String>> = listOf(Pair(80, "Job"), Pair(10, "Contribution"), Pair(10, "Friends"))

    private val listOfInfoExpenses: List<Pair<Int, String>> = listOf(Pair(10, "Food"), Pair(10, "Car"), Pair(30, "GKH"), Pair(50, "For future"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        setColorTheme()
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onStart() {
        super.onStart()
        //binding.IncomePieChartPlan.setInfoList(listOfInfoIncome)
        //binding.ExpensesPieChartPlan.setInfoList(listOfInfoExpenses)
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
        val intent = Intent(this@ReportActivity, ReportBarChartActivity::class.java)
        startActivity(intent)
        finish()
    }

    /*fun onToTableButtonClicked(view: View) {
        val intent = Intent(this@ReportActivity, ReportTableActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onLastMonthClicked(view: View) {
        val currentIncome: List<Pair<Int, String>> = listOf(Pair(80, "Job"), Pair(20, "Contribution"))
        val currentExpenses: List<Pair<Int, String>> = listOf(Pair(5, "Games"), Pair(20, "Car"), Pair(20, "Food"), Pair(30, "Gkh"), Pair(25, "Investments"))
        //binding.ExpensesPieChartPlan.setInfoList(currentExpenses)
        //binding.IncomePieChartPlan.setInfoList(currentIncome)
        //binding.IncomePieChartPlan.invalidate()
        //binding.ExpensesPieChartPlan.invalidate()
    }*/
}