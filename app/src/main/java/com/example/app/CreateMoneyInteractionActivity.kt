package com.example.app

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.databinding.ActivityCreateMoneyInteractionBinding
import com.example.app.dataprocessing.CategoryClass
import com.example.app.dataprocessing.JsonConverter
import com.example.app.dataprocessing.MoneyInteractionPostClass
import com.example.app.dataprocessing.ServerInteraction
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import java.util.Calendar

class CreateMoneyInteractionActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var settings: SharedPreferences

    private lateinit var binding: ActivityCreateMoneyInteractionBinding

    private var category: String = ""
    private var sum: Int = 0
    private var date: String = ""
    private var time: String = "00:00:00"
    private var recipient: String = ""
    private var type: String = ""
    private var comment: String = ""
    private var recipientsArray = ArrayList<String>()
    private var uuid = ""
    private var categoryArray = arrayOf<CategoryClass>()
    private var expenseFlag: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateMoneyInteractionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        expenseFlag = intent.getBooleanExtra("ExpenseFlag", true)
        Log.v("Set", "ExpensesFlag = $expenseFlag")
        getUUID()
    }

    private fun getCategories() : ArrayList<String> {
        val request = "{\"size\": \"100\", \"page\": \"0\"}"
        val response: String?
        runBlocking{
            response = ServerInteraction.Category.apiGetCategoryPagination(request)
        }
        val categoryClassArray = JsonConverter.FromJson.categoriesListJson(response)
        val arrayOfCategory = if (categoryClassArray != null){
            categoryArray = categoryClassArray
            val arrayList = ArrayList<String>()
            for(i in categoryClassArray)
                arrayList.add(i.name)
            arrayList.add("Добавить новую категорию")
            arrayList
        } else {
            categoryArray = arrayOf()
            arrayListOf("Добавить новую категорию")
        }
        return arrayOfCategory
    }

    private fun getUUID(){
        uuid = settings.getString("UsersUUID", "").toString()
    }

    private fun setCategoriesAdapter() {
        binding.categoriesSelector.layoutManager = LinearLayoutManager(this)
        binding.categoriesSelector.setHasFixedSize(true)
        val emplist = getCategories()
        val itemAdapter = ItemAdapter(emplist)
        binding.categoriesSelector.adapter = itemAdapter
        itemAdapter.setOnClickListener(object :
            ItemAdapter.OnClickListener {
            override fun onClick(position: Int, model: String) {
                if(model != "Добавить новую категорию"){
                    for(i in categoryArray){
                        if(i.name == model) {
                            category = i.id
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

    private fun getRecipients() {
        val recipientsStr = settings.getString("Recipients", "")
        Log.d("Recipients", "Recipients: $recipientsStr")
        if(recipientsStr == "null") {
            recipientsArray = arrayListOf("Добавить нового получателя")
            recipientsArray
        } else {

            recipientsArray = Gson().fromJson(recipientsStr, ArrayList::class.java) as ArrayList<String>
            recipientsArray.add("Добавить нового получателя")
            recipientsArray
        }
    }

    private fun setRecipientsAdapter() {
        binding.recipientsSelector.layoutManager = LinearLayoutManager(this)
        binding.recipientsSelector.setHasFixedSize(true)
        getRecipients()
        val itemAdapter = ItemAdapter(recipientsArray)
        binding.recipientsSelector.adapter = itemAdapter
        itemAdapter.setOnClickListener(object :
            ItemAdapter.OnClickListener {
            override fun onClick(position: Int, model: String) {
                if(model != "Добавить нового получателя"){
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

    fun onOkCategoryButtonClicked(view: View) {
        category = binding.newCategoryField.text.toString()
        if(category.isNotEmpty()) {
            val request = "{\"name\": \"$category\"}"
            val response: String?
            binding.addCategoryLayout.visibility = View.GONE
            binding.addingLayout.visibility = View.VISIBLE
            runBlocking {
                response = ServerInteraction.Category.apiPostCategory(request)
            }
            if (response != null) {
                Log.d("AppJson", "Category has been added successfully! Id: $response")
                category = response
                category = category.replace("\"", "")
            } else {
                Log.e("AppJson", "Failure in adding category")
            }
            binding.categoryButton.text = binding.newCategoryField.text.toString()
        } else {
            Toast.makeText(applicationContext, "Введите название категории!", Toast.LENGTH_LONG).show()
        }
    }

    fun onCancelCategoryButtonClicked(view: View) {
        binding.addCategoryLayout.visibility = View.GONE
        binding.categoriesSelector.visibility = View.VISIBLE
    }

    fun getDate(view: View) {
        binding.addingLayout.visibility = View.GONE
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                _, mYear, mMonth, mDay -> date = "$mDay.$mMonth.$mYear"
            binding.dateButton.text = date
            binding.addingLayout.visibility = View.VISIBLE
        }, year, month, day)
        dpd.show()
    }

    private fun getDataFromFields() {
        comment = binding.commentField.text.toString()
        val sumStr = binding.sumField.text.toString()
        sum = if(sumStr.isNotEmpty()){
            sumStr.toInt()
        } else {
            -1
        }
    }

    private fun postMoneyInteraction() {
        if(date[1] == '.'){
            date = "0$date"
        }
        if(date[4] == '.'){
            date = date.replaceRange(3, 3, "0")
        }
        Log.d("Set", "Date: $date")
        val getDate = "$date $time"
        val moneyInteractionPost = MoneyInteractionPostClass(
            comment = comment,
            value = sum,
            categoryId = category,
            userId = uuid,
            getDate = getDate
        )
        val request = JsonConverter.ToJson.toMoneyInteractionPostClassJson(moneyInteractionPost)
        val response: String?
        runBlocking {
            response = if(expenseFlag){
                ServerInteraction.Expense.apiPostExpenses(request)
            } else {
                ServerInteraction.Income.apiPostIncomes(request)
            }
        }
        if(response != null) {
            Log.w("AppJson", "Response Post Interaction: $response")
        } else {
            Log.e("AppJson", "NotPosted")
        }
        Log.w("AppJson", "Post request json: $request")
    }

    fun onCreateMoneyInteractionClicked(view: View) {
        getDataFromFields()
        if(sum <= 0) {
            Toast.makeText(applicationContext, "Сумма не может быть отрицательной", Toast.LENGTH_LONG).show()
        } else if(comment.isEmpty()){
            Toast.makeText(applicationContext, "Комментарий не может быть пустым", Toast.LENGTH_LONG).show()
        } else if (date.isEmpty()) {
            Toast.makeText(applicationContext, "Дата не может быть пустой!", Toast.LENGTH_LONG).show()
        } else if(category.isEmpty()){
            Toast.makeText(applicationContext, "Категория не может быть пустой!", Toast.LENGTH_LONG).show()
        } else if(recipient.isEmpty()){
            Toast.makeText(applicationContext, "Получатель не может быть не указан!", Toast.LENGTH_LONG).show()
        } else {
            postMoneyInteraction()
            val intent = Intent(this@CreateMoneyInteractionActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

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

    fun onTimePickerCancelButtonClicked(view: View) {
        binding.addingLayout.visibility = View.VISIBLE
        binding.timePickerLayout.visibility = View.GONE
    }

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

    fun onCancelRecipientsButtonClicked(view: View) {
        binding.recipientsSelector.visibility = View.VISIBLE
        binding.addRecipientsLayout.visibility = View.GONE
    }

    fun onIrregularButtonClicked(view: View) {
        binding.addingLayout.visibility = View.VISIBLE
        binding.typePickerLayout.visibility = View.GONE
        binding.typeButton.text = "Нерегулярный"
    }

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

    fun onTimePickerOkButtonClicked(view: View) {
        val hours = binding.hoursEditText.text.toString()
        val minutes = binding.minutesEditText.text.toString()
        time = "$hours:$minutes:00"
        binding.timeButton.text = time
        binding.timeButton.setTextColor(Color.parseColor("#656565"))
        onTimePickerCancelButtonClicked(view)
    }

    fun onTypePickerClicked(view: View) {
        binding.addingLayout.visibility = View.GONE
        binding.typePickerLayout.visibility = View.VISIBLE
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }
}