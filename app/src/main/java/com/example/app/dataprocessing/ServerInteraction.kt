package com.example.app.dataprocessing

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.ContextCompat.getString
import com.example.app.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ServerInteraction {
    private const val url = "http://10.0.2.2:8080"
    object Category {
        suspend fun apiGetCategoryByFilter(jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.getCategoryByFilter(requestBody)
            Log.v("AppJson", "Response: ${response.toString()}")
            withContext(Dispatchers.IO) {
                val gson = GsonBuilder().setPrettyPrinting().create()
                val prettyJson = gson.toJson(
                    JsonParser.parseString(
                        response.body()
                            ?.string()
                    )
                )
                gsonRes = java.lang.String(prettyJson.toString()).toString()
                successFlag = if (response.isSuccessful) {
                    Log.w("App", gsonRes)
                    true
                } else {
                    Log.e("App", response.code().toString())
                    false
                }
            }
            Log.v("ApplicationJson", "Success flag: $successFlag, Gson res: $gsonRes")
            if(successFlag){
                return gsonRes
            }
            return null
        }


        suspend fun apiGetCategoryPagination(jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.getCategoryPagination(requestBody)
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiPostCategory(jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            val response = service.postCategory(requestBody)
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiGetCategoryById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val response = service.getCategoryById(id)
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiPutCategoryById(jsonObjectString: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val response = service.putCategoryById(requestBody, id)
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiDeleteCategoryById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val response = service.deleteCategoryById(id)
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiGetIncomesById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
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

        suspend fun apiGetExpensesById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = false
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

    }

    object Expense {
        suspend fun apiGetExpensesByFilter(jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.getExpensesGetByFilter(requestBody)
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiGetExpensesPagination(jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.getExpensesPagination(requestBody)
            withContext(Dispatchers.IO) {
                val gson = GsonBuilder().setPrettyPrinting().create()
                val prettyJson = gson.toJson(
                    JsonParser.parseString(
                        response.body()
                            ?.string()
                    )
                )
                gsonRes = prettyJson
                successFlag = if (response.isSuccessful) {
                    Log.d("ApplicationJson", prettyJson)
                    true
                } else {
                    Log.e("ApplicationJson", response.code().toString())
                    false
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiPostExpenses(jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var successFlag = false
            Log.v("AppJson", "Request Post Expenses: $jsonObjectString")
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaType())
            var gsonRes: String = ""
            val response = service.postExpenses(requestBody)
            Log.d("AppJson", "Response inside interaction: $response")
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiGetExpenseById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            val response = service.getExpenseById(id)
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiPutExpenseById(jsonObjectString: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            var successFlag = false
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiDeleteExpenseById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
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
        suspend fun apiGetIncomesByFilter(jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.getIncomesGetByFilter(requestBody)
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiGetIncomesPagination(jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.getIncomesPagination(requestBody)
            withContext(Dispatchers.IO) {
                val gson = GsonBuilder().setPrettyPrinting().create()
                val prettyJson = gson.toJson(
                    JsonParser.parseString(
                        response.body()
                            ?.string()
                    )
                )
                gsonRes = prettyJson
                successFlag = if (response.isSuccessful) {
                    Log.d("ApplicationJson", prettyJson)
                    true
                } else {
                    Log.e("ApplicationJson", response.code().toString())
                    false
                }
            }
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiPostIncomes(jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            var successFlag = false
            val response = service.postIncomes(requestBody)
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiGetIncomeById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            val response = service.getIncomeById(id)
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiPutIncomeById(jsonObjectString: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            var successFlag = false
            val response = service.putIncomeById(requestBody, id)
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiDeleteIncomeById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            val response = service.deleteIncomeById(id)
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }
    }

    object User {
        suspend fun apiGetUserByFilter(jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.getUserGetByFilter(requestBody)
            Log.v("AppJson", "Response: ${response.toString()}")
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiGetUserPagination(jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.getUserPagination(requestBody)
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiPostUser(jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaType())
            var gsonRes: String = ""
            Log.d("AppJson", "")
            var successFlag = false
            Log.d("AppJson", "Request PostUser: $jsonObjectString")
            val response = service.postUser(requestBody)
            Log.v("AppJson", "Response: $response")
            Log.d("AppJson", "UserPost response: response")
            Log.d("AppJson", "UserPost responsebody: ${response.body()}")
            withContext(Dispatchers.IO) {
                val gson = GsonBuilder().setPrettyPrinting().create()
                if(response.body() == null){
                    Log.e("AppJson", "UserPost not working")
                } else {
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

        suspend fun apiGetUserById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            val response = service.getUserById(id)
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiPutUserById(jsonObjectString: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            var successFlag = false
            val response = service.putUserById(requestBody, id)
            withContext(Dispatchers.IO) {
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiDeleteUserById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiGetIncomesById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
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
            if(successFlag){
                return gsonRes
            }
            return null
        }

        suspend fun apiGetExpensesById(id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
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
            if(successFlag){
                return gsonRes
            }
            return null
        }
    }
}