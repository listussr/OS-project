package com.example.app

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.app.databinding.ActivityMainBinding
import com.example.app.dataprocessing.APIServer
import com.example.app.dataprocessing.FilterClass
import com.example.app.dataprocessing.JsonConverter
import com.example.app.dataprocessing.MoneyInteractionClass
import com.example.app.dataprocessing.MoneyInteractionPostClass
import com.example.app.dataprocessing.ServerInteraction
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit

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
    private var userId = ""
    private var outOfDictionaryFlag: Boolean = false
    private var word: String = ""

    private val deferredResult: Deferred<String> = CoroutineScope(Dispatchers.IO).async {
        val url = "http://10.0.2.2:8080"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .build()
        val service = retrofit.create(APIServer::class.java)
        var successFlag: Boolean = true
        val requestBody = JsonConverter.ToJson.toFilterClassArrayJson(
            arrayOf(FilterClass("name", "Бензин", "EQUAL"))
        ).toRequestBody("application/json".toMediaTypeOrNull())
        val response = service.getCategoryByFilter(requestBody)
        Log.v("AppJson", "Response: ${response.toString()}")
        val gsonStr = ""
        withContext(Dispatchers.IO) {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val prettyJson = gson.toJson(
                JsonParser.parseString(
                    response.body()
                        ?.string()
                )
            )
            successFlag = if (response.isSuccessful) {
                Log.w("App", prettyJson)
                true
            } else {
                Log.e("App", response.code().toString())
                false
            }
            return@withContext prettyJson
        }
    }

    private val dataModel: DataModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)!!
        chooseStartFragment()
        setLanguageView()
        setAutoCompleteList()
        getUUID()
        var response: String? = ""
        runBlocking {
            response = ServerInteraction.Category.apiGetCategoryByFilter(
                JsonConverter.ToJson.toFilterClassArrayJson(
                    arrayOf(FilterClass("name", "Еда", ">=")
                )
            ))
            //Log.w("ApplicationJson", response!!)
        }
        if(response != null)
            Log.d("AppJson", "After coroutine: $response")
        else{
            Log.e("AppJson", "No elements")
        }
        //val categoryStr = JsonConverter.FromJson.categoriesListJson(response)!![0].id
        /*
        val response = ServerInteraction.Category.apiGetCategoryByFilter(
            JsonConverter.ToJson.toFilterClassArrayJson(
                arrayOf(FilterClass("name", "Бензин", "EQUAL"))
            )
        )
        Log.d("ApplicationJson", "Response: $response")
        */
        /*val categoryStr = ServerInteraction.Category.apiGetCategoryByFilter(
            JsonConverter.ToJson.toFilterClassArrayJson(
                arrayOf(FilterClass("name", "Бензин", "EQUAL"))
            )
        )*/
        val categoryStr = response
        Log.v("AppJson", "Id: $categoryStr")
        /*
        if (categoryStr == null) {
            Log.v("ApplicationJson", categoryStr)
            val categoryId = JsonConverter.FromJson.categoriesListJson(categoryStr)!![0].id
            var response2: String?
            runBlocking {
                val request = JsonConverter.ToJson.toMoneyInteractionPostClassJson(
                    MoneyInteractionPostClass(
                        "test expense",
                        20000,
                        categoryId,
                        userId,
                        "25.03.24 00:00:00"
                    )
                )
                Log.d("AppJson", request)
                response2 = ServerInteraction.Expense.apiPostExpensesTest(
                    request
                )
                Log.e("AppJson", "Got response: $response2")
            }
            if(response2 != null)
                Log.w("AppJson", "Response: $response2")
            else
                Log.e("AppJson", "Response is null")
        } else {
            Log.e("AppJson", "Impossible to connect to server")
        }
         */
    }

    /**
     * Получаем ID пользователя на сайте из настроек приложения
     */
    private fun getUUID() {
        userId = settings.getString("UserUUID", "").toString()
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

    /**
     * Обработка нажатия на кнопку добавления дохода
     */
    fun onAddIncomeClicked(view: View) {
        val layout = findViewById<LinearLayout>(R.id.addingFieldsLayout)
        layout.visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.addingPanel).visibility = View.GONE
        findViewById<ConstraintLayout>(R.id.main_layout).setOnClickListener{
            layout.visibility = View.GONE
        }
    }

}