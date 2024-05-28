package com.example.app

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.databinding.ActivityChangeDeleteMoneyInteractionBinding
import com.example.app.dataprocessing.CategoryClass
import com.example.app.dataprocessing.Item
import com.example.app.dataprocessing.JsonConverter
import com.example.app.dataprocessing.MoneyInteractionPostClass
import com.example.app.dataprocessing.ServerInteraction
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import java.util.Calendar

class ChangeDeleteMoneyInteractionActivity : AppCompatActivity() {
    private lateinit var settings: SharedPreferences
    private lateinit var binding: ActivityChangeDeleteMoneyInteractionBinding

    private var categoryName: String = ""
    private var categoryId: String = ""
    private var sum: String = ""
    private var date: String = ""
    private var time: String = "00:00:00"
    private var recipient: String = ""
    private var expenseFlag: Boolean = false
    private var comment: String = ""
    private var id: String = ""

    private var recipientsArray = ArrayList<String>()
    private var uuid = ""
    private var categoryArray = arrayOf<CategoryClass>()
    private var lightThemFlag: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeDeleteMoneyInteractionBinding.inflate(layoutInflater)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)!!
        setContentView(binding.root)
        getDataFromIntent()
        setData()
        setCorrectMoneyInteraction()
        lightThemFlag = settings.getBoolean("ColorTheme", true)
        if(!lightThemFlag) {
            setDarkTheme()
        }
        translateViews()
    }

    /**
     * Переводим View на англиский
     */
    private fun translateViews() {
        if(!settings.getBoolean("Language", true)){
            with(binding){
                textView.text = "Sum"
                textView2.text = "Category"
                textView4.text = "Date"
                textView5.text = "Time"
                textView7.text = "Recipient"
                textView8.text = "Type"
                textView11.text = "Comment"
                sumField.hint = "Sum"
                categoryButton.text = "Category"
                dateButton.text = "Date"
                timeButton.text = "Time"
                recipientButton.text = "Recipient"
                typeButton.text = "Type"
                commentField.hint = "Comment"
                textView19.text = "Hours"
                textView20.text = "Minutes"
                button8.text = "Ok"
                button9.text = "Cancel"
                button10.text = "One-time"
                button12.text = "Regular"
                textView23.text = "Amount of months"
                textView24.text = "Amount in one month"
                cancelCategoryButton.text = "Cancel"
                okCategoryButton.text = "Ok"
                cancelRecipientsButton.text = "Cancel"
                okRecipientsButton.text = "Ok"
                newRecipientField.hint = "Recipient"
                newCategoryField.hint = "Category"
                addMoneyInteractionButton.text = if(expenseFlag) "Delete expense" else "Delete Income"
            }
        }
    }

    /**
     * Устанавливаем тёмную тему приложения
     */
    private fun setDarkTheme() {
        with(binding){
            mainLayout.setBackgroundResource(R.drawable.rect_gray)
            linearLayout3.setBackgroundResource(R.drawable.table_border_dark)
            linearLayout4.setBackgroundResource(R.drawable.table_border_dark)
            linearLayout5.setBackgroundResource(R.drawable.table_border_dark)
            linearLayout6.setBackgroundResource(R.drawable.table_border_dark)
            linearLayout7.setBackgroundResource(R.drawable.table_border_dark)
            linearLayout8.setBackgroundResource(R.drawable.table_border_dark)
            linearLayout9.setBackgroundResource(R.drawable.table_border_dark)
            textView.setTextColor(Color.parseColor("#F1F3F6"))
            textView2.setTextColor(Color.parseColor("#F1F3F6"))
            textView4.setTextColor(Color.parseColor("#F1F3F6"))
            textView5.setTextColor(Color.parseColor("#F1F3F6"))
            textView7.setTextColor(Color.parseColor("#F1F3F6"))
            textView8.setTextColor(Color.parseColor("#F1F3F6"))
            textView11.setTextColor(Color.parseColor("#F1F3F6"))
            sumField.setTextColor(Color.parseColor("#F1F3F6"))
            sumField.setHintTextColor(Color.parseColor("#BCBCBC"))
            commentField.setTextColor(Color.parseColor("#F1F3F6"))
            commentField.setHintTextColor(Color.parseColor("#BCBCBC"))
            categoryButton.setTextColor(Color.parseColor("#BCBCBC"))
            dateButton.setTextColor(Color.parseColor("#BCBCBC"))
            timeButton.setTextColor(Color.parseColor("#BCBCBC"))
            typeButton.setTextColor(Color.parseColor("#BCBCBC"))
            recipientButton.setTextColor(Color.parseColor("#BCBCBC"))
            typePickerLayout.setBackgroundResource(R.drawable.roundrect_dark)
            linLayoutType.setBackgroundResource(R.drawable.roundrect_dark_gray)
            button10.setTextColor(Color.parseColor("#F1F3F6"))
            button12.setTextColor(Color.parseColor("#F1F3F6"))
            textView12.setTextColor(Color.parseColor("#F1F3F6"))
            textView19.setTextColor(Color.parseColor("#BCBCBC"))
            hoursEditText.setTextColor(Color.parseColor("#F1F3F6"))
            minutesEditText.setTextColor(Color.parseColor("#F1F3F6"))
            textView20.setTextColor(Color.parseColor("#BCBCBC"))
            textView13.setTextColor(Color.parseColor("#F1F3F6"))
            newCategoryField.setTextColor(Color.parseColor("#F1F3F6"))
            newCategoryField.setHintTextColor(Color.parseColor("#BCBCBC"))
            newRecipientField.setTextColor(Color.parseColor("#F1F3F6"))
            newCategoryField.setHintTextColor(Color.parseColor("#BCBCBC"))
            regularTypeSelector.setBackgroundResource(R.drawable.roundrect_dark)
            textView23.setTextColor(Color.parseColor("#F1F3F6"))
            textView24.setTextColor(Color.parseColor("#F1F3F6"))
            editTextText7.setTextColor(Color.parseColor("#F1F3F6"))
            editTextText8.setTextColor(Color.parseColor("#F1F3F6"))
            editTextText7.setHintTextColor(Color.parseColor("#BCBCBC"))
            editTextText8.setHintTextColor(Color.parseColor("#BCBCBC"))
            linLayoutRegular.setBackgroundResource(R.drawable.roundrect_dark_gray)
        }
    }


    /**
     * Получаем данные от TableFragment
     */
    private fun getDataFromIntent() {
        categoryId = intent.getStringExtra("CategoryId")!!
        categoryName = intent.getStringExtra("CategoryName")!!
        id = intent.getStringExtra("Id")!!
        sum = intent.getStringExtra("Sum")!!
        date = intent.getStringExtra("Date")!!
        comment = intent.getStringExtra("Comment")!!
        expenseFlag = !intent.getBooleanExtra("Type", false)
        time = getTime()
        date = getDate()
        getUUID()
        Log.d("AppJson", "Expense flag: $expenseFlag")
        Log.d("AppJson", "Interaction id: $id")
    }

    /**
     * Получаем время из даты
     */
    private fun getTime() : String {
        return date.substring(11)
    }

    /**
     * Получаем дату
     */
    private fun getDate() : String {
        return date.substring(0, 10)
    }

    /**
     * Устанавливаем данные в поля ввода
     */
    private fun setData() {
        binding.sumField.setText(sum)
        binding.categoryButton.text = categoryName
        binding.dateButton.text = date
        binding.timeButton.text = time
        binding.recipientButton.text = "-"
        binding.typeButton.text = "-"
        binding.commentField.setText(comment)
        binding.hoursEditText.setText("${time[0]}${time[1]}")
        binding.minutesEditText.setText("${time[3]}${time[4]}")
    }

    /**
     * Устанавливаем правильную надпись на кнопке добавления расхода/дохода
     */
    private fun setCorrectMoneyInteraction() {
        binding.addMoneyInteractionButton.text = if(expenseFlag){
            "Удалить расход"
        } else {
            "Удалить доход"
        }
    }

    /**
     * Получаем от сервера все категории
     */
    private fun getCategories() : ArrayList<String> {
        val request = "{\"size\": \"100\", \"page\": \"0\"}"
        val response: String?
        runBlocking{
            response = ServerInteraction.Category.apiGetCategoryPagination(request, settings.getString("Token", "")!!)
        }
        val categoryClassArray = JsonConverter.FromJson.categoriesListJson(response)
        val arrayOfCategory = if (categoryClassArray != null){
            categoryArray = categoryClassArray
            val arrayList = ArrayList<String>()
            for(i in categoryClassArray)
                arrayList.add(i.name)
            if(settings.getBoolean("Language", true))
                arrayList.add("Добавить новую категорию")
            else
                arrayList.add("Add new category")
            arrayList
        } else {
            categoryArray = arrayOf()
            if(settings.getBoolean("Language", true))
                arrayListOf("Добавить новую категорию")
            else
                arrayListOf("Add new category")
        }
        return arrayOfCategory
    }

    /**
     * Получаем id пользователя из настроек приложения
     */
    private fun getUUID(){
        uuid = settings.getString("UsersUUID", "").toString()
    }

    /**
     * Меняем расход/доход
     */
    private fun changeMoneyInteraction() {
        getDataFromFields()
        val request = JsonConverter.ToJson.toMoneyInteractionPostClassJson(
            MoneyInteractionPostClass(
                comment,
                sum.toInt(),
                categoryId,
                uuid,
                "$date $time"
            )
        )
        Log.v("AppJson", "Put expense/income request: $request")
        var response: String?
        runBlocking {
            Log.d("AppJson", "Put expense: $expenseFlag")
            response = if(expenseFlag) {
                ServerInteraction.Expense.apiPutExpenseById(settings.getString("Token", "")!!, request, id)
            } else {
                ServerInteraction.Income.apiPutIncomeById(settings.getString("Token", "")!!, request, id)
            }
        }
    }

    /**
     * Возвращаемся в MainActivity
     */
    private fun toMainActivity() {
        val intent = Intent(this@ChangeDeleteMoneyInteractionActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Обработка нажатия на задний фон приложения
     */
    fun onBackgroundClicked(view: View) {
        changeMoneyInteraction()
        toMainActivity()
    }

    /**
     * Обработка нажатия на кнопку удаления дохода
     */
    fun onDeleteClicked(view: View) {
        deleteMoneyInteraction()
        toMainActivity()
    }

    /**
     * Удаление дохода/расхода из базы данных
     */
    private fun deleteMoneyInteraction() {
        runBlocking {
            if(expenseFlag){
                ServerInteraction.Expense.apiDeleteExpenseById(settings.getString("Token", "")!!, id)
            } else {
                ServerInteraction.Income.apiDeleteIncomeById(settings.getString("Token", "")!!, id)
            }
        }
    }

    /**
     * Устанавливаем содержимое RecyclerView с категориями
     */
    private fun setCategoriesAdapter() {
        binding.categoriesSelector.layoutManager = LinearLayoutManager(this)
        binding.categoriesSelector.setHasFixedSize(true)
        val emplist = if(checkForConnection(applicationContext)) {
            getCategories()
        } else {
            arrayListOf()
        }
        var list = arrayListOf<Item>()
        for(i in emplist){
            list.add(Item(i, lightThemFlag))
        }
        val itemAdapter = ItemAdapter(list)
        binding.categoriesSelector.adapter = itemAdapter
        itemAdapter.setOnClickListener(object :
            ItemAdapter.OnClickListener {
            override fun onClick(position: Int, model: String) {
                if(model != "Добавить новую категорию" && model != "Add new category"){
                    for(i in categoryArray){
                        if(i.name == model) {
                            categoryId = i.id
                            break
                        }
                    }
                    binding.categoryButton.text = model
                    binding.addingLayout.visibility = View.VISIBLE
                    binding.categoriesSelector.visibility = View.GONE
                } else {
                    binding.addCategoryLayout.visibility = View.VISIBLE
                    binding.categoriesSelector.visibility = View.GONE
                }
            }
        })
    }

    /**
     * Получаем всех получателей из настроек
     */
    private fun getRecipients() {
        val recipientsStr = settings.getString("Recipients", "")
        Log.d("Recipients", "Recipients: $recipientsStr")
        if(recipientsStr == "null") {
            if(settings.getBoolean("Language", true))
                recipientsArray = arrayListOf("Добавить нового получателя")
            else
                recipientsArray = arrayListOf("Add new recipient")
            recipientsArray
        } else {

            recipientsArray = Gson().fromJson(recipientsStr, ArrayList::class.java) as ArrayList<String>
            if(settings.getBoolean("Language", true))
                recipientsArray.add("Добавить нового получателя")
            else
                recipientsArray.add("Add new recipient")
            recipientsArray
        }
    }

    /**
     * Устанавливаем содержимое RecyclerView с получателями
     */
    private fun setRecipientsAdapter() {
        binding.recipientsSelector.layoutManager = LinearLayoutManager(this)
        binding.recipientsSelector.setHasFixedSize(true)
        getRecipients()
        var list = arrayListOf<Item>()
        for(i in recipientsArray){
            list.add(Item(i, lightThemFlag))
        }
        val itemAdapter = ItemAdapter(list)
        binding.recipientsSelector.adapter = itemAdapter
        itemAdapter.setOnClickListener(object :
            ItemAdapter.OnClickListener {
            override fun onClick(position: Int, model: String) {
                if(model != "Добавить нового получателя" && model != "Add new recipient"){
                    recipient = model
                    binding.recipientButton.text = recipient
                    binding.addingLayout.visibility = View.VISIBLE
                    binding.recipientsSelector.visibility = View.GONE
                } else {
                    binding.addRecipientsLayout.visibility = View.VISIBLE
                    binding.recipientsSelector.visibility = View.GONE
                }
            }
        })
    }

    /**
     * Обработка нажатия на кнопку выбора получателя
     */
    fun onRecipientButtonClicked(view: View) {
        binding.addingLayout.visibility = View.GONE
        binding.recipientsSelector.visibility = View.VISIBLE
        binding.mainLayout.setOnClickListener{
            binding.mainLayout.setOnClickListener{}
            binding.recipientsSelector.visibility = View.GONE
            binding.addingLayout.visibility = View.VISIBLE
        }
        setRecipientsAdapter()
    }

    /**
     * Обработка нажатия на кнопку ОК при добавлении новой категории
     */
    fun onOkCategoryButtonClicked(view: View) {
        categoryName = binding.newCategoryField.text.toString()
        if(categoryName.isNotEmpty()) {
            val request = "{\"name\": \"$categoryName\"}"
            val response: String?
            binding.addCategoryLayout.visibility = View.GONE
            binding.addingLayout.visibility = View.VISIBLE
            runBlocking {
                response = ServerInteraction.Category.apiPostCategory(settings.getString("Token", "")!!, request)
            }
            if (response != null) {
                Log.d("AppJson", "Category has been added successfully! Id: $response")
                categoryId = response
                categoryId = categoryId.replace("\"", "")
            } else {
                Log.e("AppJson", "Failure in adding category")
            }
            binding.categoryButton.text = binding.newCategoryField.text.toString()
        } else {
            Toast.makeText(applicationContext, "Введите название категории!", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Обработка нажатия на кнопку Отмена при добавлении новой категории
     */
    fun onCancelCategoryButtonClicked(view: View) {
        binding.addCategoryLayout.visibility = View.GONE
        binding.categoriesSelector.visibility = View.VISIBLE
    }

    /**
     * Получаем корректную настоящую дату
     */
    fun getDate(view: View) {
        binding.addingLayout.visibility = View.GONE
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                _, mYear, mMonth, mDay -> date = "$mDay.${mMonth + 1}.$mYear"
            binding.dateButton.text = date
            binding.addingLayout.visibility = View.VISIBLE
        }, year, month, day)
        dpd.show()
    }

    /**
     * Получаем данные из полей ввода
     */
    private fun getDataFromFields() {
        comment = binding.commentField.text.toString()
        val sumStr = binding.sumField.text.toString()
        sum = if(sumStr.isNotEmpty()) {
            sumStr.toInt()
        } else {
            -1
        }.toString()
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
     * Обработка нажатия на кнопку выбора категории
     */
    fun onCategoriesPickerButtonClicked(view: View) {
        binding.addingLayout.visibility = View.GONE
        binding.categoriesSelector.visibility = View.VISIBLE
        binding.mainLayout.setOnClickListener{
            binding.mainLayout.setOnClickListener{}
            binding.categoriesSelector.visibility = View.GONE
            binding.addingLayout.visibility = View.VISIBLE
        }
        setCategoriesAdapter()
    }

    fun onTimePickerClicked(view: View) {
        binding.addingLayout.visibility = View.GONE
        binding.timePickerLayout.visibility = View.VISIBLE
    }

    /**
     * Обработка нажатия на кнопку Отмена при выборе времени
     */
    fun onTimePickerCancelButtonClicked(view: View) {
        binding.addingLayout.visibility = View.VISIBLE
        binding.timePickerLayout.visibility = View.GONE
    }

    /**
     * Обработка нажатия на кнопку ОК при добавлении нового получателя
     */
    fun onOkRecipientButtonClicked(view: View) {
        recipient = binding.newRecipientField.text.toString()
        if(recipient.isNotEmpty()){
            binding.recipientButton.text = recipient
            binding.addingLayout.visibility = View.VISIBLE
            binding.addRecipientsLayout.visibility = View.GONE
            recipientsArray.removeLast()
            recipientsArray.add(recipient)
            val strRecipients: String = Gson().toJson(recipientsArray)

            Log.d("Recipients", "Wrote: $strRecipients")
            settings.edit().putString("Recipients", strRecipients).commit()
        } else {
            Toast.makeText(applicationContext, "Получатель не может быть пустым!", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Обработка нажатия на кнопку Отмена при добавлении нового получателя
     */
    fun onCancelRecipientsButtonClicked(view: View) {
        binding.recipientsSelector.visibility = View.VISIBLE
        binding.addRecipientsLayout.visibility = View.GONE
    }

    /**
     * Обработка нажатия на кнопку Разовый доход
     */
    fun onIrregularButtonClicked(view: View) {
        binding.addingLayout.visibility = View.VISIBLE
        binding.typePickerLayout.visibility = View.GONE
        binding.typeButton.text = "Нерегулярный"
    }

    /**
     * Обработка нажатия на кнопку регулярный доход
     */
    fun onRegularButtonClicked(view: View) {
        binding.typePickerLayout.visibility = View.GONE
        binding.regularTypeSelector.visibility = View.VISIBLE
        binding.typeButton.text = "Регулярный"
        binding.mainLayout.setOnClickListener{
            binding.mainLayout.setOnClickListener{}
            binding.regularTypeSelector.visibility = View.GONE
            binding.addingLayout.visibility = View.VISIBLE
        }
    }

    /**
     * Обработка нажатия на кнопку ОК при задании времени
     */
    fun onTimePickerOkButtonClicked(view: View) {
        val hours = binding.hoursEditText.text.toString()
        val minutes = binding.minutesEditText.text.toString()
        if(hours.toInt() < 0 || hours.toInt() > 23 || minutes.toInt() < 0 || minutes.toInt() > 59){
            Toast.makeText(applicationContext, "Неверное время", Toast.LENGTH_LONG).show()
        } else {
            time = "$hours:$minutes:00"
            binding.timeButton.text = time
            binding.timeButton.setTextColor(Color.parseColor("#656565"))
            onTimePickerCancelButtonClicked(view)
        }
    }

    /**
     * Обработка нажатия на кнопку выбора типа дохода
     */
    fun onTypePickerClicked(view: View) {
        binding.addingLayout.visibility = View.GONE
        binding.typePickerLayout.visibility = View.VISIBLE
    }

    /**
     * Меняем фон при нажатии на ввод минут
     */
    fun onMinutesClicked(view: View) {
        binding.hoursEditText.setBackgroundResource(R.drawable.roundrect_off)
        binding.minutesEditText.setBackgroundResource(R.drawable.roundrect)
    }

    /**
     * Меняем фон при нажатии на часы
     */
    fun onHoursClicked(view: View) {
        binding.hoursEditText.setBackgroundResource(R.drawable.roundrect)
        binding.minutesEditText.setBackgroundResource(R.drawable.roundrect_off)
    }
}