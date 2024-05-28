package com.example.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.app.databinding.FragmentTableBinding
import com.example.app.dataprocessing.FilterClass
import com.example.app.dataprocessing.JsonConverter
import com.example.app.dataprocessing.MoneyInteractionClass
import com.example.app.dataprocessing.ServerInteraction
import kotlinx.coroutines.runBlocking


class TableFragment : Fragment() {
    private var _binding : FragmentTableBinding? = null
    private val binding get() = _binding!!

    private var listOfInfo: ArrayList<Pair<MoneyInteractionClass, Boolean>> = ArrayList()
    private lateinit var settings: SharedPreferences

    private var lightThemeFlag: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settings = context?.getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTableBinding.inflate(inflater, container, false)
        lightThemeFlag = settings.getBoolean("ColorTheme", true)
        if(!lightThemeFlag){
            setDarkTheme()
        } else {
            binding.mainLayout.setBackgroundResource(R.drawable.rect_light)
        }
        setStartTableInfo()
        translateViews()
        return binding.root
    }

    /**
     * Переводим View на англиский
     */
    private fun translateViews() {
        if(!settings.getBoolean("Language", true)){
            with(binding){
                textViewCategory.text = "Category"
                textViewDate.text = "Date"
                textViewSum.text = "Sum"
            }
        }
    }

    /**
     * Проверка на подключение к интернету
     */
    private fun checkForConnection(context: Context) : Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    /**
     * Получаем доходы за определённый промежуток времени
     *
     * @param date дата и время в формате: "25.03.2024 12:42:41"
     */
    private fun getIncomesInDate(date: String) : Array<MoneyInteractionClass> {
        var response: String?
        val request = JsonConverter.ToJson.toFilterClassArrayJson(
            arrayOf(
                FilterClass(
                    "getDate",
                    date,
                    "GREATER_THAN_OR_EQUAL"
                )
            )
        )

        Log.d("AppJson", "getIncomesInDate request: $request")
        runBlocking {
            response = ServerInteraction.User.apiGetIncomesByIdAndFilter(
                settings.getString("Token", "")!!,
                request,
                settings.getString("UsersUUID", "")!!
            )
        }
        val incomesArray = JsonConverter.FromJson.moneyInteractionListJson(response)
        Log.d("AppJson", "Response getIncomes: $response")
        return incomesArray
    }

    /**
     * Получаем расходы за определённый промежуток времени
     *
     * @param date дата и время в формате: "25.03.2024 12:42:41"
     */
    private fun getExpensesInDate(date: String): Array<MoneyInteractionClass> {
        var response: String?
        val request = JsonConverter.ToJson.toFilterClassArrayJson(
            arrayOf(
                FilterClass(
                    "getDate",
                    date,
                    "GREATER_THAN_OR_EQUAL"
                )
            )
        )


        Log.d("AppJson", "getExpensesInDate request: $request")
        runBlocking {
            response = ServerInteraction.User.apiGetExpensesByIdAndFilter(
                settings.getString("Token", "")!!,
                request,
                settings.getString("UsersUUID", "")!!
            )
        }
        val expensesArray = JsonConverter.FromJson.moneyInteractionListJson(response)
        Log.d("AppJson", "Response getExpenses: $response")
        return expensesArray
    }

    /**
     * Устанавливаем значения в таблице по умолчанию
     */
    private fun setStartTableInfo() {
        if (checkForConnection(requireContext())) {
            val incomesArray = getIncomesInDate("01.01.1974 00:00:00")
            val expensesArray = getExpensesInDate("01.01.1974 00:00:00")
            for (expense in expensesArray) {
                listOfInfo.add(Pair(expense, false))
            }
            for (income in incomesArray) {
                listOfInfo.add(Pair(income, true))
            }
        } else {
            Toast.makeText(requireContext(), "Нет подключения к интернету!", Toast.LENGTH_LONG).show()
            val expensesArray = getLastExpenses()
            val incomesArray = getLastIncomes()
            for (expense in expensesArray) {
                listOfInfo.add(Pair(expense, false))
            }
            for (income in incomesArray) {
                listOfInfo.add(Pair(income, true))
            }
        }
        var counter = 0
        for (info in listOfInfo){
            updateTable(info, counter)
            counter++
        }
    }

    /**
     * Получаем последнюю версию диаграммы с расходами
     */
    private fun getLastExpenses(): Array<MoneyInteractionClass> {
        val expensesString = settings.getString("LastExpenses", "")
        val expensesArray = if(expensesString!! != "null") {
            JsonConverter.FromJson.moneyInteractionListJson(expensesString)
        } else {
            arrayOf()
        }
        return expensesArray
    }

    /**
     * Получаем последнюю версию диаграммы с доходами
     */
    private fun getLastIncomes(): Array<MoneyInteractionClass> {
        val incomesString = settings.getString("LastIncome", "")
        val incomesArray = if(incomesString!! != "null") {
            JsonConverter.FromJson.moneyInteractionListJson(incomesString)
        } else {
            arrayOf()
        }
        return incomesArray
    }

    private fun updateTable(info: Pair<MoneyInteractionClass, Boolean>, counter: Int){
        val tableRow = TableRow(context)

        // LinearLayout
        val linearLayout = LinearLayout(context)
        linearLayout.id = View.generateViewId()
        val params = TableRow.LayoutParams( // if the parent is FrameLayout, this should be FrameLayout.LayoutParams
            LinearLayout.LayoutParams.WRAP_CONTENT, // modify this if its not wrap_content
            LinearLayout.LayoutParams.WRAP_CONTENT // modify this if its not wrap_content
        )
        params.setMargins(40, 0, 0, 0) // last argument here is the bottom margin
        linearLayout.layoutParams = params
        if(lightThemeFlag) {
            linearLayout.setBackgroundResource(R.drawable.table_border)
        } else {
            linearLayout.setBackgroundResource(R.drawable.table_border_dark)
        }
        // ImageView
        val operationFlag = ImageView(context)
        if (info.second) {
            operationFlag.setImageResource(R.drawable.up_5800655)
        } else {
            operationFlag.setImageResource(R.drawable.down_6364428)
        }
        val paramsFlagView = TableRow.LayoutParams( // if the parent is FrameLayout, this should be FrameLayout.LayoutParams
            LinearLayout.LayoutParams.WRAP_CONTENT, // modify this if its not wrap_content
            LinearLayout.LayoutParams.WRAP_CONTENT // modify this if its not wrap_content
        )
        paramsFlagView.setMargins(5, 0, 0, 0) // last argument here is the bottom margin
        operationFlag.layoutParams = paramsFlagView

        // TextView
        val categoryView = TextView(context)
        if(lightThemeFlag) {
            categoryView.setTextColor(Color.BLACK)
        } else {
            categoryView.setTextColor(Color.parseColor("#F1F3F6"))
        }
        val paramsCategoryView = TableRow.LayoutParams( // if the parent is FrameLayout, this should be FrameLayout.LayoutParams
            LinearLayout.LayoutParams.WRAP_CONTENT, // modify this if its not wrap_content
            LinearLayout.LayoutParams.WRAP_CONTENT // modify this if its not wrap_content
        )
        paramsCategoryView.width = 280
        paramsCategoryView.setMargins(85, 0, 0, 0) // last argument here is the bottom margin
        categoryView.layoutParams = paramsCategoryView
        categoryView.text = info.first.category.name
        setViewParams(categoryView)

        val dateView = TextView(context)
        if(lightThemeFlag) {
            dateView.setTextColor(Color.BLACK)
        } else {
            dateView.setTextColor(Color.parseColor("#F1F3F6"))
        }
        val paramsDateView = TableRow.LayoutParams( // if the parent is FrameLayout, this should be FrameLayout.LayoutParams
            LinearLayout.LayoutParams.WRAP_CONTENT, // modify this if its not wrap_content
            LinearLayout.LayoutParams.WRAP_CONTENT // modify this if its not wrap_content
        )
        paramsDateView.width = 350
        paramsDateView.setMargins(20, 0, 0, 0) // last argument here is the bottom margin
        dateView.layoutParams = paramsDateView
        dateView.text = info.first.getDate
        setViewParams(dateView)

        val sumView = TextView(context)
        if(lightThemeFlag) {
            sumView.setTextColor(Color.BLACK)
        } else {
            sumView.setTextColor(Color.parseColor("#F1F3F6"))
        }
        val paramsSumView = TableRow.LayoutParams( // if the parent is FrameLayout, this should be FrameLayout.LayoutParams
            LinearLayout.LayoutParams.WRAP_CONTENT, // modify this if its not wrap_content
            LinearLayout.LayoutParams.WRAP_CONTENT // modify this if its not wrap_content
        )
        paramsSumView.width = 250
        paramsSumView.setMargins(40, 0, 0, 0) // last argument here is the bottom margin
        sumView.layoutParams = paramsSumView
        sumView.text = info.first.value.toString()
        setViewParams(sumView)
        operationFlag.setOnLongClickListener{
            toDeleteChangeActivity(counter)
            false
        }
        categoryView.setOnLongClickListener{
            toDeleteChangeActivity(counter)
            false
        }
        dateView.setOnLongClickListener{
            toDeleteChangeActivity(counter)
            false
        }
        sumView.setOnLongClickListener{
            toDeleteChangeActivity(counter)
            false
        }
        linearLayout.addView(operationFlag)
        linearLayout.addView(categoryView)
        linearLayout.addView(dateView)
        linearLayout.addView(sumView)
        linearLayout.setOnLongClickListener{
            toDeleteChangeActivity(counter)
            false
        }
        tableRow.addView(linearLayout)

        binding.table.addView(tableRow)
    }

    /**
     * Переходим к изменению/удалению расхода/дохода
     */
    private fun toDeleteChangeActivity(counter: Int) {
        val intent = Intent(requireActivity(), ChangeDeleteMoneyInteractionActivity::class.java)
        intent.putExtra("CategoryName", listOfInfo[counter].first.category.name)
        intent.putExtra("CategoryId", listOfInfo[counter].first.category.id)
        intent.putExtra("Sum", listOfInfo[counter].first.value.toString())
        intent.putExtra("Date", listOfInfo[counter].first.getDate)
        intent.putExtra("Comment", listOfInfo[counter].first.comment)
        intent.putExtra("Id", listOfInfo[counter].first.id)
        intent.putExtra("Type", listOfInfo[counter].second)
        startActivity(intent)
    }

    /**
     * Устанавливаем параметры текста
     */
    private fun setViewParams(view: TextView){
        val typeFace: Typeface? = ResourcesCompat.getFont(requireContext(), R.font.montserrat)
        view.typeface = typeFace
        view.textSize = 17f
        view.typeface = Typeface.DEFAULT_BOLD
    }

    /**
     * Устанавливаем тёмную тему
     */
    private fun setDarkTheme() {
        with(binding) {
            mainLayout.setBackgroundResource(R.drawable.rect_gray)
            textViewCategory.setTextColor(Color.parseColor("#F1F3F6"))
            textViewDate.setTextColor(Color.parseColor("#F1F3F6"))
            textViewSum.setTextColor(Color.parseColor("#F1F3F6"))
            headerLayout.setBackgroundResource(R.drawable.table_border_dark)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = TableFragment()
    }
}