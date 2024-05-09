package com.example.app

import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.app.databinding.ActivityMainBinding
import com.example.app.databinding.ActivityReportBinding

class MainActivity : AppCompatActivity() {

    private var fragmentNum = 2
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v("App", "CreatedView1")

        super.onCreate(savedInstanceState)
        Log.v("App", "CreatedView2")
        binding = ActivityMainBinding.inflate(layoutInflater)
        Log.v("App", "CreatedView3")
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)
        Log.v("App", "CreatedView4")
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
        if(fragmentNum == 2){
            findViewById<TextView>(R.id.textViewReportNav).setTextColor(Color.BLACK)
        } else {
            findViewById<TextView>(R.id.textViewSettingsNav).setTextColor(Color.BLACK)
        }
        findViewById<TextView>(R.id.textViewOperationNav).setTextColor(Color.parseColor("#346BBC"))
        fragmentNum = 1
    }



    fun onReportButtonClicked(view: View) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.reportFrameLayout, PieChartFragment.newInstance())
            .commit()

        findViewById<TextView>(R.id.reportTextViewMain).text = "Отчёт"
        if(fragmentNum == 1){
            findViewById<TextView>(R.id.textViewOperationNav).setTextColor(Color.BLACK)
        } else {
            findViewById<TextView>(R.id.textViewSettingsNav).setTextColor(Color.BLACK)
        }
        findViewById<TextView>(R.id.textViewReportNav).setTextColor(Color.parseColor("#346BBC"))
        fragmentNum = 2
    }

    fun onSettingsButtonClicked(view: View) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.reportFrameLayout, SettingsFragment.newInstance())
            .commit()

        findViewById<TextView>(R.id.reportTextViewMain).text = "Настройки"
        if(fragmentNum == 1){
            findViewById<TextView>(R.id.textViewOperationNav).setTextColor(Color.BLACK)
        } else {
            findViewById<TextView>(R.id.textViewReportNav).setTextColor(Color.BLACK)
        }
        findViewById<TextView>(R.id.textViewSettingsNav).setTextColor(Color.parseColor("#346BBC"))
        fragmentNum = 3
    }

    fun onPlanButtonClicked(view: View) {
        Toast.makeText(applicationContext, "Раздел находится в разработке", Toast.LENGTH_LONG).show()
    }

    fun onAddButtonClicked(view: View) {
        val panel = findViewById<LinearLayout>(R.id.addingPanel)
        if(panel.visibility == View.GONE) {
            panel.visibility = View.VISIBLE
            val darkColor = Color.parseColor("#80000000")
            val layout = findViewById<ConstraintLayout>(R.id.main_layout)
            layout.setBackgroundColor(darkColor)
            layout.setOnClickListener {
                panel.visibility = View.GONE
                layout.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            val fragmentLayout = findViewById<FrameLayout>(R.id.reportFrameLayout)
            fragmentLayout.setOnClickListener {
                panel.visibility = View.GONE
                layout.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            val navigationLayout = findViewById<LinearLayout>(R.id.navigationLayout)
            navigationLayout.setOnClickListener {
                panel.visibility = View.GONE
                layout.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        } else {
            val layout = findViewById<ConstraintLayout>(R.id.main_layout)
            panel.visibility = View.GONE
            layout.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }
}