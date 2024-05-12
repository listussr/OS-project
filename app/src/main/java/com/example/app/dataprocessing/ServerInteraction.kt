package com.example.app.dataprocessing

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit


//val url = URL("http://localhost:8080")

object ServerInteraction {
    object Category {

        fun apiGetEmployee(): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getEmployees()
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                    Log.v("App", prettyJson)
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiGetCategory(): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getCategory()
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                            )
                        )
                    gsonRes = prettyJson
                    successFlag = if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        true
                    } else {
                        Log.e("App", response.code().toString())
                        false
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiPostCategory(jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.postCategory(requestBody)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    successFlag = if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        true
                    } else {
                        Log.e("App", response.code().toString())
                        false
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiGetCategoryById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getCategoryById(id)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    successFlag = if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        true
                    } else {
                        Log.e("App", response.code().toString())
                        false
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiPutCategoryById(jsonObjectString: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://reqres.in")
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            var successFlag: Boolean = true
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.putCategoryById(requestBody, id)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                    } else {
                        Log.e("App", response.code().toString())
                        successFlag = false
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiDeleteCategoryById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.deleteCategoryById(id)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    successFlag = if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        true
                    } else {
                        Log.e("App", response.code().toString())
                        false
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiGetIncomesById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getIncomesByIdCategory(id)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiGetExpensesById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getExpensesByIdCategory(id)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

    }

    object Expense {
        fun apiGetExpenses(): String? {
            /*
         * TODO(Change baseUrl())
         */
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getExpenses()
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiPostExpenses(jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var successFlag = false
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.postExpenses(requestBody)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiGetExpenseById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getExpenseById(id)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiPutExpenseById(jsonObjectString: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://reqres.in")
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            var successFlag = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.putExpenseById(requestBody, id)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiDeleteExpenseById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.deleteExpenseById(id)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }
    }

    object Income {
        fun apiGetIncomes(): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getIncomes()
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiPostIncomes(jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            var successFlag = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.postIncomes(requestBody)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiGetIncomeById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getIncomeById(id)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiPutIncomeById(jsonObjectString: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://reqres.in")
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            var successFlag = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.putIncomeById(requestBody, id)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiDeleteIncomeById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.deleteIncomeById(id)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }
    }

    object User {
        fun apiGetUser(): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getUser()
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiPostUser(jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            var successFlag = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.postUser(requestBody)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiGetUserById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getUserById(id)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiPutUserById(jsonObjectString: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://reqres.in")
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            var successFlag = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.putUserById(requestBody, id)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiDeleteUserById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.deleteCategoryById(id)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiGetIncomesById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getIncomesByIdUser(id)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        fun apiGetExpensesById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://dummy.restapiexample.com")
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getExpensesByIdUser(id)
                withContext(Dispatchers.Main) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    gsonRes = prettyJson
                    if (response.isSuccessful) {
                        Log.d("App", prettyJson)
                        successFlag = true
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }
    }
}