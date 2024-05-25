package com.example.app

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginStart
import com.example.app.databinding.FragmentPieChartBinding
import com.example.app.databinding.FragmentTableBinding
import com.example.app.dataprocessing.CategoryClass
import com.example.app.dataprocessing.FilterClass
import com.example.app.dataprocessing.JsonConverter
import com.example.app.dataprocessing.MoneyInteractionClass
import com.example.app.dataprocessing.ServerInteraction
import com.example.app.dataprocessing.TableInfoClass
import com.example.app.dataprocessing.UserClass
import kotlinx.coroutines.runBlocking


class TableFragment : Fragment() {
    private var _binding : FragmentTableBinding? = null
    private val binding get() = _binding!!

    private var listOfInfo: ArrayList<Pair<MoneyInteractionClass, Boolean>> = ArrayList()
    private lateinit var settings: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settings = context?.getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTableBinding.inflate(inflater, container, false)
        setStartTableInfo()
        return binding.root
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
        for (info in listOfInfo){
            updateTable(info)
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

    private fun updateTable(info: Pair<MoneyInteractionClass, Boolean>){
        val tableRow = TableRow(context)

        // LinearLayout
        val linearLayout = LinearLayout(context)
        val params = TableRow.LayoutParams( // if the parent is FrameLayout, this should be FrameLayout.LayoutParams
            LinearLayout.LayoutParams.WRAP_CONTENT, // modify this if its not wrap_content
            LinearLayout.LayoutParams.WRAP_CONTENT // modify this if its not wrap_content
        )
        params.setMargins(40, 0, 0, 0) // last argument here is the bottom margin
        linearLayout.layoutParams = params
        linearLayout.setBackgroundResource(R.drawable.table_border)

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
        categoryView.setTextColor(Color.BLACK)
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
        dateView.setTextColor(Color.BLACK)
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
        sumView.setTextColor(Color.BLACK)
        val paramsSumView = TableRow.LayoutParams( // if the parent is FrameLayout, this should be FrameLayout.LayoutParams
            LinearLayout.LayoutParams.WRAP_CONTENT, // modify this if its not wrap_content
            LinearLayout.LayoutParams.WRAP_CONTENT // modify this if its not wrap_content
        )
        paramsSumView.width = 250
        paramsSumView.setMargins(40, 0, 0, 0) // last argument here is the bottom margin
        sumView.layoutParams = paramsSumView
        sumView.text = info.first.value.toString()
        setViewParams(sumView)

        linearLayout.addView(operationFlag)
        linearLayout.addView(categoryView)
        linearLayout.addView(dateView)
        linearLayout.addView(sumView)

        tableRow.addView(linearLayout)

        binding.table.addView(tableRow)
    }

    private fun setViewParams(view: TextView){
        val typeFace: Typeface? = ResourcesCompat.getFont(requireContext(), R.font.montserrat)
        view.typeface = typeFace
        view.textSize = 17f
        view.typeface = Typeface.DEFAULT_BOLD
    }

    companion object {
        @JvmStatic
        fun newInstance() = TableFragment()
    }
}