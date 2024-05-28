package com.example.app

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.app.databinding.FragmentPieChartBinding
import com.example.app.dataprocessing.CategoryClass
import com.example.app.dataprocessing.FilterClass
import com.example.app.dataprocessing.JsonConverter
import com.example.app.dataprocessing.MoneyInteractionClass
import com.example.app.dataprocessing.ServerInteraction
import kotlinx.coroutines.runBlocking

class PieChartFragment : Fragment(R.layout.fragment_pie_chart) {

    private val dataModel: DataModel by activityViewModels()

    private var expensesArray: Array<MoneyInteractionClass> = arrayOf()
    private var incomesArray: Array<MoneyInteractionClass> = arrayOf()
    private var expenseCategoryArray: Array<CategoryClass> = arrayOf()
    private var incomeCategoryArray: Array<CategoryClass> = arrayOf()

    private var lightThemeFlag: Boolean = true

    private val engMonths: Array<String> = arrayOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )

    private val ruMonths: Array<String> = arrayOf(
        "Январь",
        "Февраль",
        "Март",
        "Апрель",
        "Май",
        "Июнь",
        "Июль",
        "Август",
        "Сентябрь",
        "Октябрь",
        "Ноябрь",
        "Декабрь"
    )

    private var periodNum = 1

    private var dateCur: String = ""

    private var _binding : FragmentPieChartBinding? = null
    private val binding get() = _binding!!
    private lateinit var settings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settings = context?.getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)!!
        lightThemeFlag = settings.getBoolean("ColorTheme", false)
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
        setStartButton()
        translateViews()
        getLastExpenses()
        getLastIncomes()
        getLocalTime()
        setCorrectMonths()
        if(!lightThemeFlag){
            setDarkTheme()
        }
    }

    /**
     * Устанавливаем корректное отображение нанешнего и предыдущего месяцев на кнопках
     */
    private fun setCorrectMonths() {
        binding.buttonCurrentMonth.text = if(getLanguageFlag()){
            ruMonths[(dateCur[3].toString() + dateCur[4].toString()).toInt() - 1]
        } else {
            engMonths[(dateCur[3].toString() + dateCur[4].toString()).toInt() - 1]
        }
        binding.buttonLastMonth.text = if(getLanguageFlag()){
            when((dateCur[3].toString() + dateCur[4].toString()).toInt() - 2) {
                -1 -> ruMonths[11]
                else -> ruMonths[(dateCur[3].toString() + dateCur[4].toString()).toInt() - 2]
            }
        } else {
            when((dateCur[3].toString() + dateCur[4].toString()).toInt() - 2) {
                -1 -> engMonths[11]
                else -> engMonths[(dateCur[3].toString() + dateCur[4].toString()).toInt() - 2]
            }
        }
    }

    /**
     * Отрисовка последних диаграмм при старте программы
     */
    override fun onStart() {
        super.onStart()
        updatePieCharts()
    }

    /**
     * Получаем правильное локальное время
     */
    private fun getLocalTime() {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        dateCur = formatter.format(time).toString()
    }

    /**
     * Установка новых данных в круговые диаграммы
     */
    private fun updatePieCharts() {
        binding.pieChartExpenses.setInfoList(expensesArray, expenseCategoryArray)
        binding.pieChartIncome.setInfoList(incomesArray, incomeCategoryArray)
    }

    /**
     * Задаём начальное положение для кнопки выбора периода
     */
    private fun setStartButton() {
        periodNum = settings.getInt("AnalyticsPeriod", 1)
        when(periodNum){
            1 -> {
                setCurrentMonthButtonOn()
            }
            2 -> {
                setLastMonthButtonOn()
            }
            3 -> {
                setThreeMonthButtonOn()
            }
            4 -> {
                setCurrentYearButtonOn()
            }
        }
        Log.d("App", "Ended button setting")
    }

    /**
     * Устанавливаем видимость нажатия на кнопку с текущим месяцем
     */
    private fun setCurrentMonthButtonOn() {
        if(lightThemeFlag) {
            binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_on)
            binding.buttonCurrentMonth.setTextColor(Color.parseColor("#F1F1F1"))
            binding.buttonLastMonth.setBackgroundResource(R.drawable.rect)
            binding.buttonLastMonth.setTextColor(Color.BLACK)
            binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect)
            binding.buttonThreeMonths.setTextColor(Color.BLACK)
            binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_off)
            binding.buttonCurrentYear.setTextColor(Color.BLACK)
        } else {
            binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_on)
            binding.buttonCurrentMonth.setTextColor(Color.parseColor("#F1F1F1"))
            binding.buttonLastMonth.setBackgroundResource(R.drawable.rect_dark)
            binding.buttonLastMonth.setTextColor(Color.parseColor("#F1F3F6"))
            binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect_dark)
            binding.buttonThreeMonths.setTextColor(Color.parseColor("#F1F3F6"))
            binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_dark)
            binding.buttonCurrentYear.setTextColor(Color.parseColor("#F1F3F6"))
        }
    }

    /**
     * Устанавливаем видимость нажатия на кнопку с прошлым месяцем
     */
    private fun setLastMonthButtonOn() {
        if(lightThemeFlag) {
            binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_off)
            binding.buttonCurrentMonth.setTextColor(Color.BLACK)
            binding.buttonLastMonth.setBackgroundResource(R.drawable.rect_on)
            binding.buttonLastMonth.setTextColor(Color.parseColor("#F1F1F1"))
            binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect)
            binding.buttonThreeMonths.setTextColor(Color.BLACK)
            binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_off)
            binding.buttonCurrentYear.setTextColor(Color.BLACK)
        } else {
            binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_dark)
            binding.buttonCurrentMonth.setTextColor(Color.parseColor("#F1F3F6"))
            binding.buttonLastMonth.setBackgroundResource(R.drawable.rect_on)
            binding.buttonLastMonth.setTextColor(Color.parseColor("#F1F3F6"))
            binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect_dark)
            binding.buttonThreeMonths.setTextColor(Color.parseColor("#F1F3F6"))
            binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_dark)
            binding.buttonCurrentYear.setTextColor(Color.parseColor("#F1F3F6"))
        }
    }

    /**
     * Устанавливаем видимость нажатия на кнопку с прошлыми 3 месяцами
     */
    private fun setThreeMonthButtonOn() {
        if(lightThemeFlag){
            binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_off)
            binding.buttonCurrentMonth.setTextColor(Color.BLACK)
            binding.buttonLastMonth.setBackgroundResource(R.drawable.rect)
            binding.buttonLastMonth.setTextColor(Color.BLACK)
            binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect_on)
            binding.buttonThreeMonths.setTextColor(Color.parseColor("#F1F1F1"))
            binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_off)
            binding.buttonCurrentYear.setTextColor(Color.BLACK)
        } else {
            binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_dark)
            binding.buttonCurrentMonth.setTextColor(Color.parseColor("#F1F3F6"))
            binding.buttonLastMonth.setBackgroundResource(R.drawable.rect_dark)
            binding.buttonLastMonth.setTextColor(Color.parseColor("#F1F3F6"))
            binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect_on)
            binding.buttonThreeMonths.setTextColor(Color.parseColor("#F1F3F6"))
            binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_dark)
            binding.buttonCurrentYear.setTextColor(Color.parseColor("#F1F3F6"))
        }
    }

    /**
     * Устанавливаем видимость нажатия на кнопку с текущим годом
     */
    private fun setCurrentYearButtonOn() {
        if (lightThemeFlag) {
            binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_off)
            binding.buttonCurrentMonth.setTextColor(Color.BLACK)
            binding.buttonLastMonth.setBackgroundResource(R.drawable.rect)
            binding.buttonLastMonth.setTextColor(Color.BLACK)
            binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect)
            binding.buttonThreeMonths.setTextColor(Color.BLACK)
            binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_on)
            binding.buttonCurrentYear.setTextColor(Color.parseColor("#F1F1F1"))
        } else {
            binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_dark)
            binding.buttonCurrentMonth.setTextColor(Color.parseColor("#F1F3F6"))
            binding.buttonLastMonth.setBackgroundResource(R.drawable.rect_dark)
            binding.buttonLastMonth.setTextColor(Color.parseColor("#F1F3F6"))
            binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect_dark)
            binding.buttonThreeMonths.setTextColor(Color.parseColor("#F1F3F6"))
            binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_on)
            binding.buttonCurrentYear.setTextColor(Color.parseColor("#F1F3F6"))
        }
    }

    /**
     * Получаем последнюю версию диаграммы с расходами
     */
    private fun getLastExpenses() {
        val expensesString = settings.getString("LastExpenses", "")
        expensesArray = if(expensesString!! != "null") {
            JsonConverter.FromJson.moneyInteractionListJson(expensesString)
        } else {
            arrayOf()
        }
        val expenseCategoryString = settings.getString("LastExpensesCategory", "")
        expenseCategoryArray = if(expenseCategoryString != "null") {
            JsonConverter.FromJson.categoriesListJson(expenseCategoryString)!!
        } else {
            arrayOf()
        }
    }

    /**
     * Получаем последнюю версию диаграммы с доходами
     */
    private fun getLastIncomes() {
        val incomesString = settings.getString("LastIncome", "")
        incomesArray = if(incomesString!! != "null") {
            JsonConverter.FromJson.moneyInteractionListJson(incomesString)
        } else {
            arrayOf()
        }
        val incomesCategoryString = settings.getString("LastIncomeCategory", "")
        Log.v("App", "Income category: $incomesCategoryString")
        incomeCategoryArray = if(incomesCategoryString != "null") {
            JsonConverter.FromJson.categoriesListJson(incomesCategoryString)!!
        } else {
            arrayOf()
        }
    }

    /**
     * Сохраняем в настройки последнюю версию диаграммы с расходами
     */
    private fun getLastExpensesString() : String {
        val strArray: String = if(expensesArray.isNotEmpty()) {
            JsonConverter.ToJson.toMoneyInteractionArrayJson(expensesArray)
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
            JsonConverter.ToJson.toMoneyInteractionArrayJson(incomesArray)
        } else {
            "null"
        }
        return strArray
    }

    /**
     * Сохраняем в настройки последнюю версию диаграммы с расходами
     */
    private fun getLastExpensesCategoryString() : String {
        val strArray: String = if(expenseCategoryArray.isNotEmpty()) {
            JsonConverter.ToJson.toCategoryArrayJson(expenseCategoryArray)
        } else {
            "null"
        }
        return strArray
    }

    /**
     * Сохраняем в настройки посленднюю версию диаграммы с доходами
     */
    private fun getLastIncomesCategoryString() : String {
        val strArray: String = if(incomeCategoryArray.isNotEmpty()) {
            Log.v("App", "Incomes: $incomeCategoryArray")
            JsonConverter.ToJson.toCategoryArrayJson(incomeCategoryArray)
        } else {
            "null"
        }
        return strArray
    }

    /**
     * Отмечаем изменения данных для круговых диаграмм
     */
    private fun sendUpdatesToActivity() {
        //dataModel.message.value = listOf(
        //    getLastExpensesString(),
        //    getLastIncomesString()
        //)
        settings.edit().putString("LastExpenses", getLastExpensesString()).commit()
        settings.edit().putString("LastIncome", getLastIncomesString()).commit()
        settings.edit().putString("LastExpensesCategory", getLastExpensesCategoryString()).commit()
        settings.edit().putString("LastIncomeCategory", getLastIncomesCategoryString()).commit()
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
     * Преобразуем дату, с которой будем делать запрос по фильтру
     */
    private fun getDateInBorderCurMonth() : String {
        var date = dateCur
        date = "01${dateCur.substring(2)}"
        return date
    }

    /**
     * Получаем дату начала прошлого месяца
     */
    private fun getDateInBorderLastMonth() : String {
        var date = dateCur
        date = "01${date.substring(2)}"
        date = when("${dateCur[3]}${dateCur[4]}"){
            "01" -> {
                "${date.substring(0, 3)}12.20${dateCur[8]}${dateCur[9].toString().toInt() - 1}"
            }

            "10" -> {
                "${date.substring(0, 3)}09${dateCur.substring(5)}"
            }

            else -> {
                "${date.substring(0, 3)}${dateCur[3]}${dateCur[4].toString().toInt() - 1}${date.substring(5)}"
            }
        }
        Log.d("Date", "Date: $date")
        return date
    }

    /**
     * Получаем дату начала прошлых 3 месяцев
     */
    private fun getDateInBorderThreeMonths() : String {
        var date = dateCur
        date = when("${dateCur[3]}${dateCur[4]}"){
            "01" -> {
                "${date.substring(0, 3)}10.20${dateCur[8]}${dateCur[9].toString().toInt() - 1}"
            }
            "02" -> {
                "${date.substring(0, 3)}11.20${dateCur[8]}${dateCur[9].toString().toInt() - 1}"
            }
            "03" -> {
                "${date.substring(0, 3)}12.20${dateCur[8]}${dateCur[9].toString().toInt() - 1}"
            }
            "10" -> {
                "${date.substring(0, 3)}07${dateCur.substring(5)}"
            }
            "11" -> {
                "${date.substring(0, 3)}08${dateCur.substring(5)}"
            }
            "12" -> {
                "${date.substring(0, 3)}09${dateCur.substring(5)}"
            }
            else -> {
                "${date.substring(0, 3)}${dateCur[3]}${dateCur[4].toString().toInt() - 3}${date.substring(5)}"
            }
        }
        Log.d("Date", "Date: $date")
        return date
    }

    /**
     * Преобразуем дату, с которой будем делать запрос по фильтру
     */
    private fun getDateInBorderCurYear() : String {
        var date = dateCur
        date = "01.01.20${dateCur[8]}${dateCur[9]}"
        return date
    }

    /**
     * Получаем расходы за определённый промежуток времени
     *
     * @param date дата и время в формате: "25.03.2024 12:42:41"
     */
    private fun getExpensesInDate(date: String) {
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
        expensesArray = JsonConverter.FromJson.moneyInteractionListJson(response)
        Log.d("AppJson", "Response getExpenses: $response")
    }

    /**
     * Получаем доходы за определённый промежуток времени
     *
     * @param date дата и время в формате: "25.03.2024 12:42:41"
     */
    private fun getIncomesInDate(date: String) {
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
        incomesArray = JsonConverter.FromJson.moneyInteractionListJson(response)
        Log.d("AppJson", "Response getIncomes: $response")
    }

    /**
     * Получаем категории
     */
    private fun getCategories() {
        val request = "{\"size\": \"100\", \"page\": \"0\"}"
        val response: String?
        runBlocking {
            response = ServerInteraction.Category.apiGetCategoryPagination(request, settings.getString("Token", "")!!)
        }
        expenseCategoryArray = JsonConverter.FromJson.categoriesListJson(response) ?: arrayOf()
        incomeCategoryArray = expenseCategoryArray
    }

    /**
     * Обработка нажатия на кнопку с текущим месяцем
     */
    private fun onCurrentMonthClicked() {
        binding.buttonCurrentMonth.setOnClickListener{
            if (checkForConnection(requireContext())) {
                getCategories()
                Log.d("AppJson", "Got categories")
                getExpensesInDate("${getDateInBorderCurMonth()} 00:00:00")
                Log.d("AppJson", "Got expenses")
                getIncomesInDate("${getDateInBorderCurMonth()} 00:00:00")
            } else {
                Toast.makeText(requireContext(), "Нет подключения к сети!", Toast.LENGTH_LONG).show()
                expensesArray = arrayOf()
                incomesArray = arrayOf()
                expenseCategoryArray = arrayOf()
                incomeCategoryArray = arrayOf()
            }
            binding.pieChartExpenses.setInfoList(expensesArray, expenseCategoryArray)
            binding.pieChartIncome.setInfoList(incomesArray, incomeCategoryArray)
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
            if(checkForConnection(requireContext())){
                getCategories()
                Log.d("AppJson", "Got categories")
                getExpensesInDate("${getDateInBorderLastMonth()} 00:00:00")
                Log.d("AppJson", "Got expenses")
                getIncomesInDate("${getDateInBorderLastMonth()} 00:00:00")
            } else {
                Toast.makeText(requireContext(), "Нет подключения к сети!", Toast.LENGTH_LONG).show()
                expensesArray = arrayOf()
                incomesArray = arrayOf()
                expenseCategoryArray = arrayOf()
                incomeCategoryArray = arrayOf()
            }
            binding.pieChartExpenses.setInfoList(expensesArray, expenseCategoryArray)
            binding.pieChartIncome.setInfoList(incomesArray, incomeCategoryArray)
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
            if(checkForConnection(requireContext())){
                getCategories()
                Log.d("AppJson", "Got categories")
                getExpensesInDate("${getDateInBorderThreeMonths()} 00:00:00")
                Log.d("AppJson", "Got expenses")
                getIncomesInDate("${getDateInBorderThreeMonths()} 00:00:00")
            } else {
                Toast.makeText(requireContext(), "Нет подключения к сети!", Toast.LENGTH_LONG).show()
                expensesArray = arrayOf()
                incomesArray = arrayOf()
                expenseCategoryArray = arrayOf()
                incomeCategoryArray = arrayOf()
            }
            binding.pieChartExpenses.setInfoList(expensesArray, expenseCategoryArray)
            binding.pieChartIncome.setInfoList(incomesArray, incomeCategoryArray)
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
            if(checkForConnection(requireContext())){
                getCategories()
                Log.d("AppJson", "Got categories")
                getExpensesInDate("${getDateInBorderCurYear()} 00:00:00")
                Log.d("AppJson", "Got expenses")
                getIncomesInDate("${getDateInBorderCurYear()} 00:00:00")
            } else {
                Toast.makeText(requireContext(), "Нет подключения к сети!", Toast.LENGTH_LONG).show()
                expensesArray = arrayOf()
                incomesArray = arrayOf()
                expenseCategoryArray = arrayOf()
                incomeCategoryArray = arrayOf()
            }
            binding.pieChartExpenses.setInfoList(expensesArray, expenseCategoryArray)
            binding.pieChartIncome.setInfoList(incomesArray, incomeCategoryArray)
            binding.pieChartExpenses.invalidate()
            binding.pieChartIncome.invalidate()
            sendUpdatesToActivity()
            changeButtonsCurrentYear()
        }
    }

    /**
     * Сохраняем состояние кнопки
     */
    private fun saveButtonState() {
        settings.edit()
            .putInt("AnalyticsPeriod", periodNum)
            .commit()
    }

    /**
     * Меняем внешний вид кнопок по нажатию на кнопку с текущим месяцем
     */
    private fun changeButtonsCurrentMonth() {
        binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_on)
        binding.buttonCurrentMonth.setTextColor(Color.parseColor("#F1F1F1"))
        if(lightThemeFlag) {
            when (periodNum) {
                1 -> {}
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
        } else {
            when(periodNum) {
                1 -> {}

                2 -> {
                    binding.buttonLastMonth.setBackgroundResource(R.drawable.rect_dark)
                    binding.buttonLastMonth.setTextColor(Color.parseColor("#E1E3E6"))
                }

                3 -> {
                    binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect_dark)
                    binding.buttonThreeMonths.setTextColor(Color.parseColor("#E1E3E6"))
                }

                4 -> {
                    binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_dark)
                    binding.buttonCurrentYear.setTextColor(Color.parseColor("#E1E3E6"))
                }
            }
        }
        periodNum = 1
        saveButtonState()
    }

    /**
     * Меняем внешний вид кнопок по нажатию на кнопку с последним месяцем
     */
    private fun changeButtonsLastMonth() {
        binding.buttonLastMonth.setBackgroundResource(R.drawable.rect_on)
        binding.buttonLastMonth.setTextColor(Color.parseColor("#F1F1F1"))
        if(lightThemeFlag) {
            when (periodNum) {
                1 -> {
                    binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_off)
                    binding.buttonCurrentMonth.setTextColor(Color.BLACK)
                }

                2 -> {}
                3 -> {
                    binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect)
                    binding.buttonThreeMonths.setTextColor(Color.BLACK)
                }

                4 -> {
                    binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_off)
                    binding.buttonCurrentYear.setTextColor(Color.BLACK)
                }
            }
        } else {
            when(periodNum) {
                1 -> {
                    binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_dark)
                    binding.buttonCurrentMonth.setTextColor(Color.parseColor("#E1E3E6"))
                }

                2 -> {}

                3 -> {
                    binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect_dark)
                    binding.buttonThreeMonths.setTextColor(Color.parseColor("#E1E3E6"))
                }

                4 -> {
                    binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_dark)
                    binding.buttonCurrentYear.setTextColor(Color.parseColor("#E1E3E6"))
                }
            }
        }
        periodNum = 2
        saveButtonState()
    }

    /**
     * Меняем внешний вид кнопок при нажатии на кнопку с последними 3 месяцами
     */
    private fun changeButtonsThreeMonths() {
        binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect_on)
        binding.buttonThreeMonths.setTextColor(Color.parseColor("#F1F1F1"))
        if(lightThemeFlag) {
            when (periodNum) {
                1 -> {
                    binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_off)
                    binding.buttonCurrentMonth.setTextColor(Color.BLACK)
                }

                2 -> {
                    binding.buttonLastMonth.setBackgroundResource(R.drawable.rect)
                    binding.buttonLastMonth.setTextColor(Color.BLACK)
                }

                3 -> {}
                4 -> {
                    binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_off)
                    binding.buttonCurrentYear.setTextColor(Color.BLACK)
                }
            }
        } else {
            when (periodNum) {
                1 -> {
                    binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_dark)
                    binding.buttonCurrentMonth.setTextColor(Color.parseColor("#E1E3E6"))
                }

                2 -> {
                    binding.buttonLastMonth.setBackgroundResource(R.drawable.rect_dark)
                    binding.buttonLastMonth.setTextColor(Color.parseColor("#E1E3E6"))
                }

                3 -> {}

                4 -> {
                    binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_dark)
                    binding.buttonCurrentYear.setTextColor(Color.parseColor("#E1E3E6"))
                }
            }
        }
        periodNum = 3
        saveButtonState()
    }

    /**
     * Меняем внешний вид кнонок при нажатии на кнопку с последним годом
     */
    private fun changeButtonsCurrentYear() {
        binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_on)
        binding.buttonCurrentYear.setTextColor(Color.parseColor("#F1F1F1"))
        if(lightThemeFlag) {
            when (periodNum) {
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

                4 -> {}
            }
        } else {
            when (periodNum) {
                1 -> {
                    binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_dark)
                    binding.buttonCurrentMonth.setTextColor(Color.parseColor("#E1E3E6"))
                }

                2 -> {
                    binding.buttonLastMonth.setBackgroundResource(R.drawable.rect_dark)
                    binding.buttonLastMonth.setTextColor(Color.parseColor("#E1E3E6"))
                }

                3 -> {
                    binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect_dark)
                    binding.buttonThreeMonths.setTextColor(Color.parseColor("#E1E3E6"))
                }

                4 -> {}
            }
        }
        periodNum = 4
        saveButtonState()
    }

    /**
     * Устанавливаем тёмную тему
     */
    private fun setDarkTheme() {
        with(binding) {
            IncomeTextViewPlan.setTextColor(Color.parseColor("#E1E3E6"))
            ExpensesTextViewPlan.setTextColor(Color.parseColor("#E1E3E6"))
            mainLayout.setBackgroundResource(R.drawable.rect_gray)
            when(periodNum){
                1 -> setCurrentMonthButtonOn()
                2 -> setLastMonthButtonOn()
                3 -> setThreeMonthButtonOn()
                4 -> setCurrentYearButtonOn()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PieChartFragment()
    }
}