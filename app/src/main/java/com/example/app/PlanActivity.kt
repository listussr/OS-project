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
import com.example.app.databinding.ActivityPlanBinding
import com.example.app.diagrams.PieChart

class PlanActivity : ComponentActivity() {
    /**
     * Файл с натсройками приложения
     */
    private lateinit var settings: SharedPreferences

    private lateinit var binding: ActivityPlanBinding

    private val listOfInfo: List<Pair<Int, String>> = listOf(Pair(10, "Food"), Pair(10, "Car"), Pair(30, "GKH"), Pair(50, "For future"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        setColorTheme()
        binding = ActivityPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //findViewById<PieChart>(R.id.pieChart)
    }


    override fun onStart() {
        super.onStart()
        binding.pieChart.setInfoList(listOfInfo)
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
}