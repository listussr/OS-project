package com.example.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.app.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(){

    private var fragmentNum: Int = 2
    private lateinit var binding: ActivityMainBinding
    private lateinit var settings: SharedPreferences
    private var userId = ""
    private var lightThemeFlag: Boolean = true

    //private val dataModel: DataModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)!!
        chooseStartFragment()
        setLanguageView()
        getUUID()
        Log.w("AppJson", "Token: ${settings.getString("Token", "")}")
        Log.w("AppJson", "UUID: ${settings.getString("UsersUUID", "")}")
        getColorTheme()
        if(!lightThemeFlag) {
            setDarkTheme()
        }
    }

    /**
     * Получаем ID пользователя на сайте из настроек приложения
     */
    private fun getUUID() {
        userId = settings.getString("UserUUID", "").toString()
    }

    /**
     * Получаем цветовую тему приложения
     */
    private fun getColorTheme() {
        lightThemeFlag = settings.getBoolean("ColorTheme", true)
    }

    /**
     * Устанавливаем фрагмент, с которого начнём показ
     */
    private fun chooseStartFragment() {
        val extras = intent.extras
        Log.v("App", "Category: $extras")
        if(extras != null){
            onSettingsButtonClicked(binding.imageButton7)
        }
        else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.reportFrameLayout, PieChartFragment.newInstance())
                .commit()
            val flag = getLanguageFlag()
            if(!flag) {
                findViewById<TextView>(R.id.reportTextViewMain).text = getString(R.string.t_report_eng)
            }
        }
    }

    /**
     * Обработка нажатия на кнопку отчёта в таблице
     */
    fun onTableClicked(view: View) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.reportFrameLayout, TableFragment.newInstance())
            .commit()

        if(getLanguageFlag()) {
            findViewById<TextView>(R.id.reportTextViewMain).text = getString(R.string.t_operations)
        } else {
            findViewById<TextView>(R.id.reportTextViewMain).text = getString(R.string.t_operations_eng)
        }
        if(fragmentNum == 2){
            if(lightThemeFlag)
                findViewById<TextView>(R.id.textViewReportNav).setTextColor(Color.BLACK)
            else
                binding.textViewReportNav.setTextColor(Color.parseColor("#F1F3F6"))
        } else {
            if(lightThemeFlag)
                findViewById<TextView>(R.id.textViewSettingsNav).setTextColor(Color.BLACK)
            else
                binding.textViewSettingsNav.setTextColor(Color.parseColor("#F1F3F6"))
        }
        findViewById<TextView>(R.id.textViewOperationNav).setTextColor(Color.parseColor("#346BBC"))
        fragmentNum = 1
    }

    /**
     * Утанавливаем язык на кнопках приложения
     */
    private fun setLanguageView() {
        if(!getLanguageFlag()){
            binding.textViewOperationNav.text = getString(R.string.t_operations_eng)
            binding.textViewReportNav.text = getString(R.string.t_report_eng)
            binding.textViewPlanNav.text = getString(R.string.t_plan_eng)
            binding.textViewSettingsNav.text = getString(R.string.t_settings_page_eng)
            binding.addExpensButton.text = "Add expense"
            binding.addIncomeButton.text = "Add income"
            when(fragmentNum){
                1 -> binding.reportTextViewMain.text = getString(R.string.t_operations_eng)
                2 -> binding.reportTextViewMain.text = getString(R.string.t_report_eng)
                3 -> binding.reportTextViewMain.text = getString(R.string.t_settings_page_eng)
            }
        }
    }

    /**
     * Получаем из настроек язык приложения
     */
    private fun getLanguageFlag() : Boolean {
        return settings.getBoolean("Language", true)
    }

    /**
     * Обработка нажатия на кнопку "Отчёт"
     */
    fun onReportButtonClicked(view: View) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.reportFrameLayout, PieChartFragment.newInstance())
            .commit()

        if(getLanguageFlag()) {
            findViewById<TextView>(R.id.reportTextViewMain).text = getString(R.string.t_report)
        } else {
            findViewById<TextView>(R.id.reportTextViewMain).text = getString(R.string.t_report_eng)
        }
        if(fragmentNum == 1){
            if(lightThemeFlag)
                findViewById<TextView>(R.id.textViewOperationNav).setTextColor(Color.BLACK)
            else
                binding.textViewOperationNav.setTextColor(Color.parseColor("#F1F3F6"))
        } else {
            if(lightThemeFlag)
                findViewById<TextView>(R.id.textViewSettingsNav).setTextColor(Color.BLACK)
            else
                binding.textViewSettingsNav.setTextColor(Color.parseColor("#F1F3F6"))
        }
        findViewById<TextView>(R.id.textViewReportNav).setTextColor(Color.parseColor("#346BBC"))
        fragmentNum = 2
    }

    /**
     * Обработка нажатия на кнопку настроек
     */
    fun onSettingsButtonClicked(view: View) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.reportFrameLayout, SettingsFragment.newInstance())
            .commit()

        if(getLanguageFlag()) {
            findViewById<TextView>(R.id.reportTextViewMain).text = getString(R.string.t_settings_page)
        } else {
            findViewById<TextView>(R.id.reportTextViewMain).text = getString(R.string.t_settings_page_eng)
        }
        if(fragmentNum == 1){
            if(lightThemeFlag)
                findViewById<TextView>(R.id.textViewOperationNav).setTextColor(Color.BLACK)
            else
                binding.textViewOperationNav.setTextColor(Color.parseColor("#F1F3F6"))
        } else {
            if(lightThemeFlag)
                findViewById<TextView>(R.id.textViewReportNav).setTextColor(Color.BLACK)
            else
                binding.textViewReportNav.setTextColor(Color.parseColor("#F1F3F6"))
        }
        findViewById<TextView>(R.id.textViewSettingsNav).setTextColor(Color.parseColor("#346BBC"))
        fragmentNum = 3
    }

    /**
     * Обработка нажатия на кнопку плана
     */
    fun onPlanButtonClicked(view: View) {
        Toast.makeText(applicationContext, "Раздел находится в разработке", Toast.LENGTH_LONG).show()
    }

    /**
     * Обработка нажатия на кнопку добавления расхода/дохода
     */
    fun onAddButtonClicked(view: View) {
        val panel = findViewById<LinearLayout>(R.id.addingPanel)
        if(panel.visibility == View.GONE) {
            panel.visibility = View.VISIBLE
            var darkColor: Int = Color.parseColor("#333333")
            if(lightThemeFlag)
                darkColor = Color.parseColor("#80000000")
            val layout = findViewById<ConstraintLayout>(R.id.mainLayout)
            layout.setBackgroundColor(darkColor)
            layout.setOnClickListener {
                panel.visibility = View.GONE
                if(lightThemeFlag)
                    layout.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            val fragmentLayout = findViewById<FrameLayout>(R.id.reportFrameLayout)
            fragmentLayout.setOnClickListener {
                panel.visibility = View.GONE
                if(lightThemeFlag)
                    layout.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            val navigationLayout = findViewById<LinearLayout>(R.id.navigationLayout)
            navigationLayout.setOnClickListener {
                panel.visibility = View.GONE
                if(lightThemeFlag)
                    layout.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        } else {
            val layout = findViewById<ConstraintLayout>(R.id.mainLayout)
            panel.visibility = View.GONE
            if(lightThemeFlag)
                layout.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    /**
     * Обработка нажатия на кнопку добавления дохода
     */
    fun onAddIncomeClicked(view: View) {
        findViewById<LinearLayout>(R.id.addingPanel).visibility = View.GONE
        val intent = Intent(this@MainActivity, CreateMoneyInteractionActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Обработка нажатия на кнопки добавления расхода и дохода
     */
    fun onAddMoneyInteractionClicked(view: View) {
        binding.addingPanel.visibility = View.GONE
        val intent = Intent(this@MainActivity, CreateMoneyInteractionActivity::class.java)
        if(view == binding.addExpensButton){
            intent.putExtra("ExpensesFlag", true)
        } else {
            intent.putExtra("ExpensesFlag", false)
        }
        startActivity(intent)
        finish()
    }

    /**
     * Устанавливаем тёмную тему приложения
     */
    private fun setDarkTheme() {
        with(binding) {
            addingPanel.setBackgroundResource(R.drawable.circle_dark)
            reportTextViewMain.setTextColor(Color.parseColor("#F1F3F6"))
            reportTextViewMain.setBackgroundResource(R.drawable.rect_dark)
            mainLayout.setBackgroundResource(R.drawable.rect_gray)
            navigationLayout.setBackgroundResource(R.drawable.rect_dark)
            textViewSettingsNav.setTextColor(Color.parseColor("#F1F3F6"))
            textViewReportNav.setTextColor(Color.parseColor("#F1F3F6"))
            textViewPlanNav.setTextColor(Color.parseColor("#F1F3F6"))
            textViewOperationNav.setTextColor(Color.parseColor("#F1F3F6"))
            imageButton3.setImageResource(R.drawable.tasks_9151641)
            imageButton4.setImageResource(R.drawable.krugovaya_diagramma_58rmx56nvp7n_32)
            imageButton6.setImageResource(R.drawable.baza_dannyh_5fdkbqmeo746_32)
            imageButton7.setImageResource(R.drawable.nastrojki_k5mpx6n84ssf_32)
        }
    }
}