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
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.IOException

object ServerInteraction {
    private const val url = "http://10.0.2.2:8080"
    object Category {
        suspend fun apiGetCategoryByFilter(jsonObjectString: String, token: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.getCategoryByFilter(token, requestBody)
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


        suspend fun apiGetCategoryPagination(jsonObjectString: String, token: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaType())
            Log.d("AppJson", "Token in categoryPagination: $token")
            Log.d("AppJson", "Request cat.page.: $requestBody")
            val response = service.getCategoryPagination(token, requestBody)
            Log.v("AppJson", "CategoryPagination response: $response")
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

        suspend fun apiPostCategory(token: String, jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            val response = service.postCategory(token, requestBody)
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

        suspend fun apiPutCategoryById(token: String, jsonObjectString: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val response = service.putCategoryById(token, requestBody, id)
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

        suspend fun apiDeleteCategoryById(token: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val response = service.deleteCategoryById(token, id)
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

        suspend fun apiGetIncomesById(token: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getIncomesByIdCategory(token, id)
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

        suspend fun apiGetExpensesById(token: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = false
            val response = service.getExpensesByIdCategory(token, id)
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
        suspend fun apiGetExpensesByFilter(token: String, jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.getExpensesGetByFilter(token, requestBody)
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

        suspend fun apiGetExpensesPagination(token: String, jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.getExpensesPagination(token, requestBody)
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

        suspend fun apiPostExpenses(token: String, jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var successFlag = false
            Log.v("AppJson", "Request Post Expenses: $jsonObjectString")
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaType())
            var gsonRes: String = ""
            val response = service.postExpenses(token, requestBody)
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

        suspend fun apiGetExpenseById(token: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            val response = service.getExpenseById(token, id)
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

        suspend fun apiPutExpenseById(token: String, jsonObjectString: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            Log.v("AppJson", "Created request")
            val service = retrofit.create(APIServer::class.java)
            Log.v("AppJson", "1")
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            Log.v("AppJson", "2")
            var gsonRes: String = ""
            var successFlag = false
            Log.v("AppJson", "3")
            val response = service.putExpenseById(token, requestBody, id)
            Log.v("AppJson", "Response put Exp: $response")
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

        suspend fun apiDeleteExpenseById(token: String, id: String) {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.deleteExpenseById(token, id)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Log.d("App", "Deleted successfully")
                    } else {
                        Log.e("App", response.code().toString())
                    }
                }
            }
        }
    }

    object Income {
        suspend fun apiGetIncomesByFilter(token: String, jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.getIncomesGetByFilter(token, requestBody)
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

        suspend fun apiGetIncomesPagination(token: String, jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaType())
            Log.d("AppJson", "Request inc.page. $jsonObjectString")
            Log.v("AppJson", "Token inc.page: $token")
            val response = service.getIncomesPagination(token, requestBody)
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

        suspend fun apiPostIncomes(token: String, jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            var successFlag = false
            val response = service.postIncomes(token, requestBody)
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

        suspend fun apiGetIncomeById(token: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            val response = service.getIncomeById(token, id)
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

        suspend fun apiPutIncomeById(token: String, jsonObjectString: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaType())
            var gsonRes: String = ""
            var successFlag = false
            Log.d("AppJson", "Request id put: $id")
            val response = service.putIncomeById(token, requestBody, id)
            Log.d("AppJson", "Response putIncome: $response")
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

        suspend fun apiDeleteIncomeById(token: String, id: String) {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            Log.v("AppJson", "Request for delete: $id")
            val response = service.deleteIncomeById(token, id)
            Log.v("AppJson", "Response for delete: $response")
            withContext(Dispatchers.IO) {
                if (response.isSuccessful) {
                    Log.d("App", "Deleted successfully")
                    successFlag = true
                } else {
                    Log.e("App", response.code().toString())
                }
            }
        }
    }

    object User {
        suspend fun apiGetUserByFilter(token: String, jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.getUserGetByFilter(token, requestBody)
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

        suspend fun apiGetUserPagination(token: String, jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag: Boolean = true
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.getUserPagination(token, requestBody)
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

        suspend fun apiPostUser(token: String, jsonObjectString: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaType())
            var gsonRes: String = ""
            Log.d("AppJson", "")
            var successFlag = false
            Log.d("AppJson", "Request PostUser: $jsonObjectString")
            val response = service.postUser(token, requestBody)
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

        suspend fun apiGetUserById(token: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            val response = service.getUserById(token, id)
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

        suspend fun apiPutUserById(token: String, jsonObjectString: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            var gsonRes: String = ""
            var successFlag = false
            val response = service.putUserById(token, requestBody, id)
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

        suspend fun apiDeleteUserById(token: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            val response = service.deleteCategoryById(token, id)
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

        suspend fun apiGetIncomesById(token: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            val response = service.getIncomesByIdUser(token, id)
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

        suspend fun apiGetExpensesById(token: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            val response = service.getExpensesByIdUser(token, id)
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

        suspend fun apiGetIncomesByIdAndFilter(token: String, jsonObjectString: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaType())
            var gsonRes: String = ""
            var successFlag = false
            val response = service.getIncomesByIdAndFilter(token, id, requestBody)
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

        suspend fun apiGetExpensesByIdAndFilter(token: String, jsonObjectString: String, id: String): String? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaType())
            var gsonRes = ""
            var successFlag = false
            Log.d("AppJson", "Request id: $id")
            Log.d("AppJson", "Request: $jsonObjectString")
            val response = service.getExpensesByIdAndFilter(token, id, requestBody)
            Log.d("AppJson", "Response exp.id+filter: $response")
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


        suspend fun apiRegister(jsonObjectString: String) {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            Log.v("AppJson", "Request registration: $jsonObjectString")
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaType())
            val response = service.register(requestBody)
            Log.d("AppJson", "Response registration: $response")
            withContext(Dispatchers.IO) {
                val gson = GsonBuilder().setPrettyPrinting().create()
                val prettyJson = gson.toJson(
                    JsonParser.parseString(
                        response.body()
                            ?.string()
                    )
                )
                if (response.isSuccessful) {
                    Log.d("App", prettyJson)
                } else {
                    Log.e("App", response.code().toString())
                }
            }
        }

        suspend fun apiLogin(jsonObjectString: String) : Pair<String?, String?> {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .build()
            val service = retrofit.create(APIServer::class.java)
            var gsonRes: String = ""
            var successFlag = false
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaType())
            val response = service.login(requestBody)
            Log.d("AppJson", "Response for login {969}: $response")
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
                    Log.d("AppJson", prettyJson)
                    successFlag = true
                } else {
                    Log.e("AppJson", response.code().toString())
                }
            }
            if(successFlag){
                Log.d("AppJson", "Token: ${response.headers()["Authorization"]}")
                Log.d("AppJson", "Id: ${response.headers()["UserId"]}")
                return Pair(response.headers()["Authorization"], response.headers()["UserId"])
            }
            return Pair(null, null)
        }

    }
}