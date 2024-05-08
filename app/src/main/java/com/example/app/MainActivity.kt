package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.reportFrameLayout, PieChartFragment.newInstance())
            .commit()
    }

    fun onTableClicked(view: View) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.reportFrameLayout, TableFragment.newInstance())
            .commit()

        findViewById<TextView>(R.id.reportTextViewMain).text = "Операции"
    }

    fun onReportButtonClicked(view: View) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.reportFrameLayout, PieChartFragment.newInstance())
            .commit()

        findViewById<TextView>(R.id.reportTextViewMain).text = "Отчёт"
    }
}