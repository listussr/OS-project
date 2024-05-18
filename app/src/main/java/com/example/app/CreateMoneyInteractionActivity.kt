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
import com.example.app.dataprocessing.JsonConverter
import com.example.app.dataprocessing.ServerInteraction
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateMoneyInteractionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
    }

    private fun getCategories() : ArrayList<String> {
        val request = "{\"size\": \"100\", \"page\": \"0\"}"
        val response: String?
        runBlocking{
            response = ServerInteraction.Category.apiGetCategoryPagination(request)
        }
        val categoryClassArray = JsonConverter.FromJson.categoriesListJson(response)
        val arrayOfCategory = if (categoryClassArray != null){
            val arrayList = ArrayList<String>()
            for(i in categoryClassArray)
                arrayList.add(i.name)
            arrayList.add("Добавить новую категорию")
            arrayList
        } else {
            arrayListOf("Добавить новую категорию")
        }
        return arrayOfCategory
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
                    category = model
                    binding.categoryButton.text = category
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
            } else {
                Log.e("AppJson", "Failure in adding category")
            }
            binding.categoryButton.text = category
        } else {
            Toast.makeText(applicationContext, "Введите название категории!", Toast.LENGTH_LONG).show()
        }
    }

    fun onCancelCategoryButtonClicked(view: View) {
        binding.addCategoryLayout.visibility = View.GONE
        binding.categoriesSelector.visibility = View.VISIBLE
    }

    fun onCreateMoneyInteractionClicked(view: View) {
        val intent = Intent(this@CreateMoneyInteractionActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
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
        val recipient = binding.newRecipientField.text.toString()
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