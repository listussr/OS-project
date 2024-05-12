package com.example.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.app.databinding.FragmentPieChartBinding
import com.example.app.dataprocessing.CategoryClass
import com.example.app.dataprocessing.JsonToRawDataClass
import com.example.app.dataprocessing.MoneyInteractionClass
import com.example.app.dataprocessing.UserClass

class PieChartFragment : Fragment(R.layout.fragment_pie_chart) {

    private val dataModel: DataModel by activityViewModels()

    private var expensesArray: Array<MoneyInteractionClass> = arrayOf()
    private var incomesArray: Array<MoneyInteractionClass> = arrayOf()

    private var periodNum = 1

    private var _binding : FragmentPieChartBinding? = null
    private val binding get() = _binding!!
    private lateinit var settings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settings = context?.getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPieChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCurrentMonthClicked()
        onLastMonthClicked()
        onThreeMonthsClicked()
        onCurrentYearClicked()
        translateViews()
        getLastExpenses()
        getLastIncomes()
    }

    /**
     * Отрисовка последних диаграмм при старте программы
     */
    override fun onStart() {
        super.onStart()
        updatePieCharts()
    }

    /**
     * Установка новых данных в круговые диаграммы
     */
    private fun updatePieCharts() {
        binding.pieChartExpenses.setInfoList(expensesArray)
        binding.pieChartIncome.setInfoList(incomesArray)
    }

    /**
     * Получаем последнюю версию диаграммы с расходами
     */
    private fun getLastExpenses() {
        val expensesString = settings.getString("LastExpenses", "")
        expensesArray = if(expensesString!! != "null") {
            JsonToRawDataClass.moneyInteractionListJson(expensesString)!!
        } else {
            arrayOf()
        }
    }

    /**
     * Получаем последнюю версию диаграммы с доходами
     */
    private fun getLastIncomes() {
        val incomesString = settings.getString("LastIncomes", "")
        incomesArray = if(incomesString!! != "null") {
            JsonToRawDataClass.moneyInteractionListJson(incomesString)!!
        } else {
            arrayOf()
        }
    }

    /**
     * Сохраняем в настройки посленднюю версию диаграммы с расходами
     */
    private fun getLastExpensesString() : String {
        val strArray: String = if(expensesArray.isNotEmpty()) {
            Log.v("App", "Expenses: ${expensesArray.toString()}")
            JsonToRawDataClass.toMoneyInteractionArrayJson(expensesArray)
        } else {
            "null"
        }
        return strArray
    }

    /**
     * Сохраняем в настройки посленднюю версию диаграммы с доходами
     */
    private fun getLastIncomesString() : String {
        val strArray: String = if(incomesArray.isNotEmpty()) {
            Log.v("App", "Expenses: ${expensesArray.toString()}")
            JsonToRawDataClass.toMoneyInteractionArrayJson(incomesArray)
        } else {
            "null"
        }
        return strArray
    }

    /**
     * Отмечаем изменения данных для круговых диаграмм
     */
    private fun sendUpdatesToActivity() {
        Log.v("App", "Expenses: ${getLastExpensesString()}")
        Log.v("App", "Incomes: ${getLastIncomesString()}")
        dataModel.message.value = listOf(
            getLastExpensesString(),
            getLastIncomesString()
        )
    }

    /**
     * Переводим все View на английский язык
     */
    private fun translateViews() {
        if(!getLanguageFlag()) {
            binding.IncomeTextViewPlan.text = getString(R.string.t_income_eng)
            binding.ExpensesTextViewPlan.text = getString(R.string.t_expenses_eng)
            binding.buttonCurrentMonth.text = "April"
            binding.buttonLastMonth.text = "March"
            binding.buttonThreeMonths.text = getString(R.string.t_3_months_eng)
            binding.buttonCurrentYear.text = getString(R.string.t_2024)
        }
    }

    /**
     * Получаем из настроек язык приложения
     */
    private fun getLanguageFlag() : Boolean {
        return settings.getBoolean("Language", true)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /**
     * Обработка нажатия на кнопку с текущим месяцем
     */
    private fun onCurrentMonthClicked() {
        binding.buttonCurrentMonth.setOnClickListener{
            incomesArray = arrayOf(
                MoneyInteractionClass(
                    "1",
                    "jknl",
                    4000,
                    "21.12.2004",
                    UserClass(
                        "Ivan",
                        "Ivan",
                        "list@mail.ru"
                    ),
                    CategoryClass(
                        "food",
                        "bknl"
                    )
                ),
                MoneyInteractionClass(
                    "2",
                    "",
                    20000,
                    "21.12.2004",
                    UserClass(
                        "Ivan",
                        "Ivan",
                        "list@mail.ru"
                    ),
                    CategoryClass(
                        "food",
                        "bknl"
                    )
                )
            )
            expensesArray = arrayOf(
                MoneyInteractionClass(
                    "2",
                    "jknl",
                    4000,
                    "21.12.2004",
                    UserClass(
                        "Ivan",
                        "Ivan",
                        "list@mail.ru"
                    ),
                    CategoryClass(
                        "food",
                        "bknl"
                    )
                ),
                MoneyInteractionClass(
                    "2",
                    "",
                    20000,
                    "21.12.2004",
                    UserClass(
                        "Ivan",
                        "Ivan",
                        "list@mail.ru"
                    ),
                    CategoryClass(
                        "food",
                        "bknl"
                    )
                )
            )
            binding.pieChartExpenses.setInfoList(expensesArray)
            binding.pieChartIncome.setInfoList(incomesArray)
            binding.pieChartExpenses.invalidate()
            binding.pieChartIncome.invalidate()
            changeButtonsCurrentMonth()
            sendUpdatesToActivity()
        }
    }

    /**
     * Обработка нажатия на кнопку с последним месяцем
     */
    private fun onLastMonthClicked() {
        binding.buttonLastMonth.setOnClickListener{
            incomesArray = arrayOf(
                MoneyInteractionClass(
                    "1",
                    "jknl",
                    4000,
                    "21.12.2004",
                    UserClass(
                        "Ivan",
                        "Ivan",
                        "list@mail.ru"
                    ),
                    CategoryClass(
                        "Еда",
                        "bknl"
                    )
                ),
                MoneyInteractionClass(
                    "2",
                    "",
                    20000,
                    "21.12.2004",
                    UserClass(
                        "Ivan",
                        "Ivan",
                        "list@mail.ru"
                    ),
                    CategoryClass(
                        "Дом",
                        "bknl"
                    )
                )
            )
            expensesArray = arrayOf(
                MoneyInteractionClass(
                    "2",
                    "jknl",
                    4000,
                    "21.12.2004",
                    UserClass(
                        "Ivan",
                        "Ivan",
                        "list@mail.ru"
                    ),
                    CategoryClass(
                        "Еда",
                        "bknl"
                    )
                ),
                MoneyInteractionClass(
                    "4",
                    "",
                    4000,
                    "21.12.2004",
                    UserClass(
                        "Ivan",
                        "Ivan",
                        "list@mail.ru"
                    ),
                    CategoryClass(
                        "Авто",
                        "bknl"
                    )
                )
            )
            binding.pieChartExpenses.setInfoList(expensesArray)
            binding.pieChartIncome.setInfoList(incomesArray)
            binding.pieChartExpenses.invalidate()
            binding.pieChartIncome.invalidate()
            changeButtonsLastMonth()
            sendUpdatesToActivity()
        }
    }

    /**
     * Обработка нажатия на кнопку с последними 3 месяцами
     */
    private fun onThreeMonthsClicked() {
        binding.buttonThreeMonths.setOnClickListener{
            incomesArray = arrayOf(
                MoneyInteractionClass(
                    "1",
                    "jknl",
                    4000,
                    "21.12.2004",
                    UserClass(
                        "Ivan",
                        "Ivan",
                        "list@mail.ru"
                    ),
                    CategoryClass(
                        "food",
                        "bknl"
                    )
                ),
                MoneyInteractionClass(
                    "2",
                    "",
                    5000,
                    "21.12.2004",
                    UserClass(
                        "Ivan",
                        "Ivan",
                        "list@mail.ru"
                    ),
                    CategoryClass(
                        "food",
                        "bknl"
                    )
                )
            )
            expensesArray = arrayOf(
                MoneyInteractionClass(
                    "2",
                    "jknl",
                    4000,
                    "21.12.2004",
                    UserClass(
                        "Ivan",
                        "Ivan",
                        "list@mail.ru"
                    ),
                    CategoryClass(
                        "food",
                        "bknl"
                    )
                ),
                MoneyInteractionClass(
                    "2",
                    "",
                    10000,
                    "21.12.2004",
                    UserClass(
                        "Ivan",
                        "Ivan",
                        "list@mail.ru"
                    ),
                    CategoryClass(
                        "food",
                        "bknl"
                    )
                )
            )
            binding.pieChartExpenses.setInfoList(expensesArray)
            binding.pieChartIncome.setInfoList(incomesArray)
            binding.pieChartExpenses.invalidate()
            binding.pieChartIncome.invalidate()
            changeButtonsThreeMonths()
            sendUpdatesToActivity()
        }
    }

    /**
     * Обработка нажатия на кнопку с последним годом
     */
    private fun onCurrentYearClicked() {
        binding.buttonCurrentYear.setOnClickListener{

            incomesArray = arrayOf(
                MoneyInteractionClass(
                    "1",
                    "jknl",
                    4000,
                    "21.12.2004",
                    UserClass(
                        "Ivan",
                        "Ivan",
                        "list@mail.ru"
                    ),
                    CategoryClass(
                        "food",
                        "bknl"
                    )
                ),
                MoneyInteractionClass(
                    "2",
                    "",
                    20000,
                    "21.12.2004",
                    UserClass(
                        "Ivan",
                        "Ivan",
                        "list@mail.ru"
                    ),
                    CategoryClass(
                        "food",
                        "bknl"
                    )
                )
            )
            expensesArray = arrayOf(
                MoneyInteractionClass(
                    "2",
                    "jknl",
                    4000,
                    "21.12.2004",
                    UserClass(
                        "Ivan",
                        "Ivan",
                        "list@mail.ru"
                    ),
                    CategoryClass(
                        "food",
                        "bknl"
                    )
                ),
                MoneyInteractionClass(
                    "2",
                    "",
                    20000,
                    "21.12.2004",
                    UserClass(
                        "Ivan",
                        "Ivan",
                        "list@mail.ru"
                    ),
                    CategoryClass(
                        "food",
                        "bknl"
                    )
                )
            )
            binding.pieChartExpenses.setInfoList(expensesArray)
            binding.pieChartIncome.setInfoList(incomesArray)
            binding.pieChartExpenses.invalidate()
            binding.pieChartIncome.invalidate()
            sendUpdatesToActivity()
            changeButtonsCurrentYear()
        }
    }

    /**
     * Меняем внешний вид кнопок по нажатию на кнопку с текущим месяцем
     */
    private fun changeButtonsCurrentMonth() {
        binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_on)
        binding.buttonCurrentMonth.setTextColor(Color.parseColor("#F1F1F1"))
        when(periodNum){
            2 -> {
                binding.buttonLastMonth.setBackgroundResource(R.drawable.rect)
                binding.buttonLastMonth.setTextColor(Color.BLACK)
            }
            3 -> {
                binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect)
                binding.buttonThreeMonths.setTextColor(Color.BLACK)
            }
            4 -> {
                binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_off)
                binding.buttonCurrentYear.setTextColor(Color.BLACK)
            }
        }
        periodNum = 1
    }

    /**
     * Меняем внешний вид кнопок по нажатию на кнопку с последним месяцем
     */
    private fun changeButtonsLastMonth() {
        Log.v("App", "On last month clicked")
        binding.buttonLastMonth.setBackgroundResource(R.drawable.rect_on)
        binding.buttonLastMonth.setTextColor(Color.parseColor("#F1F1F1"))
        when(periodNum){
            1 -> {
                binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_off)
                binding.buttonCurrentMonth.setTextColor(Color.BLACK)
            }
            3 -> {
                binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect)
                binding.buttonThreeMonths.setTextColor(Color.BLACK)
            }
            4 -> {
                binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_off)
                binding.buttonCurrentYear.setTextColor(Color.BLACK)
            }
        }
        periodNum = 2
    }

    /**
     * Меняем внешний вид кнопок при нажатии на кнопку с последними 3 месяцами
     */
    private fun changeButtonsThreeMonths() {
        binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect_on)
        binding.buttonThreeMonths.setTextColor(Color.parseColor("#F1F1F1"))
        when(periodNum){
            1 -> {
                binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_off)
                binding.buttonCurrentMonth.setTextColor(Color.BLACK)
            }
            2 -> {
                binding.buttonLastMonth.setBackgroundResource(R.drawable.rect)
                binding.buttonLastMonth.setTextColor(Color.BLACK)
            }
            4 -> {
                binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_off)
                binding.buttonCurrentYear.setTextColor(Color.BLACK)
            }
        }
        periodNum = 3
    }

    /**
     * Меняем внешний вид кнонок при нажатии на кнопку с последним годом
     */
    private fun changeButtonsCurrentYear() {
        binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_on)
        binding.buttonCurrentYear.setTextColor(Color.parseColor("#F1F1F1"))
        when(periodNum){
            1 -> {
                binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_off)
                binding.buttonCurrentMonth.setTextColor(Color.BLACK)
            }
            2 -> {
                binding.buttonLastMonth.setBackgroundResource(R.drawable.rect)
                binding.buttonLastMonth.setTextColor(Color.BLACK)
            }
            3 -> {
                binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect)
                binding.buttonThreeMonths.setTextColor(Color.BLACK)
            }
        }
        periodNum = 4
    }

    companion object {
        @JvmStatic
        fun newInstance() = PieChartFragment()
    }
}