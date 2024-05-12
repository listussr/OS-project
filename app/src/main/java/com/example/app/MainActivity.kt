package com.example.app

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.app.databinding.ActivityMainBinding
import com.example.app.databinding.ActivityReportBinding
import com.example.app.dataprocessing.APIServer
import com.example.app.dataprocessing.JsonToRawDataClass
import com.example.app.dataprocessing.ServerInteraction

class MainActivity : AppCompatActivity(){

    private var fragmentNum: Int = 2
    private lateinit var binding: ActivityMainBinding
    private lateinit var settings: SharedPreferences
    private var autocompleteList: Array<String> = arrayOf(
        "Еда",
        "Транспорт",
        "Бензин",
        "Бытовые расходы",
        "Food",
        "Transport",
        "House",
        "Entertainments"
    )
    private var outOfDictionaryFlag: Boolean = false
    private var word: String = ""

    private val dataModel: DataModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)!!
        chooseStartFragment()
        setLanguageView()
        setAutoCompleteList()
        //JsonToRawDataClass.testJson()
        //ServerInteraction.Category.apiGetEmployee()
        dataModel.message.observe(this) {
            settings.edit().putString("LastExpenses", it[0]).commit()
            settings.edit().putString("LastIncomes", it[1]).commit()
            Log.v("App", "Updated settings with pie chart")
        }
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
            findViewById<TextView>(R.id.textViewReportNav).setTextColor(Color.BLACK)
        } else {
            findViewById<TextView>(R.id.textViewSettingsNav).setTextColor(Color.BLACK)
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

    private fun lookForAutocompletes() {
        binding.autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(p0: Editable?) {
                /**
                 * TODO (Сделать кнопку на месте выкидного списка при отсутствии подсовпадений у ввода и списка)
                 */
                word.plus(p0)
                if(!outOfDictionaryFlag) {
                    var wordInDictionaryFlag: Boolean = false
                    for (i in autocompleteList) {
                        wordInDictionaryFlag = wordInDictionaryFlag || i.contains(word)
                    }
                    if (!wordInDictionaryFlag) {
                        outOfDictionaryFlag = true
                        /*
                        val addValue = arrayOf("Добавить категорию")
                        adapter = ArrayAdapter<String>(
                            this, android.R.layout.simple_dropdown_item_1line, addValue
                        )
                        binding.autoCompleteTextView.setAdapter(adapter)
                        */
                        binding.autoCompleteTextView.completionHint = "Добавить категорию"

                    }
                }
            }

        })
    }

    private fun setAutoCompleteList() {
        val adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_dropdown_item_1line, autocompleteList
        )
        binding.autoCompleteTextView.setAdapter(adapter)
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
            findViewById<TextView>(R.id.textViewOperationNav).setTextColor(Color.BLACK)
        } else {
            findViewById<TextView>(R.id.textViewSettingsNav).setTextColor(Color.BLACK)
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
            findViewById<TextView>(R.id.textViewOperationNav).setTextColor(Color.BLACK)
        } else {
            findViewById<TextView>(R.id.textViewReportNav).setTextColor(Color.BLACK)
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

    fun onAddIncomeClicked(view: View) {
        val layout = findViewById<LinearLayout>(R.id.addingFieldsLayout)
        layout.visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.addingPanel).visibility = View.GONE
        findViewById<ConstraintLayout>(R.id.main_layout).setOnClickListener{
            layout.visibility = View.GONE
        }
    }

}