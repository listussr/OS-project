package com.example.app

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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.app.databinding.ActivityMainBinding
import com.example.app.databinding.ActivityReportBinding

class MainActivity : AppCompatActivity() {

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
    //private lateinit var adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.reportFrameLayout, PieChartFragment.newInstance())
            .commit()
        setAutoCompleteList()
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

    fun onAddIncomeClicked(view: View) {
        val layout = findViewById<LinearLayout>(R.id.addingFieldsLayout)
        layout.visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.addingPanel).visibility = View.GONE
        findViewById<ConstraintLayout>(R.id.main_layout).setOnClickListener{
            layout.visibility = View.GONE
        }
    }
}