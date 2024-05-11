package com.example.app.dataprocessing

import android.util.Log
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

class Category {

    fun apiGetEmployee() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()
        val service = retrofit.create(APIServer::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getEmployees()
            withContext(Dispatchers.Main){
                if(response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("App", prettyJson)
                } else {
                    Log.e("App", response.code().toString())
                }
            }
        }
    }

    fun apiGetCategory() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()
        val service = retrofit.create(APIServer::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getCategory()
            withContext(Dispatchers.Main){
                if(response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("App", prettyJson)
                } else {
                    Log.e("App", response.code().toString())
                }
            }
        }
    }

    fun apiPostCategory(jsonObjectString: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()

        val service = retrofit.create(APIServer::class.java)

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.postCategory(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("App", prettyJson)

                } else {

                    Log.e("App", response.code().toString())

                }
            }
        }
    }

    fun apiGetCategoryById(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()
        val service = retrofit.create(APIServer::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getCategoryById(id)
            withContext(Dispatchers.Main){
                if(response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("App", prettyJson)
                } else {
                    Log.e("App", response.code().toString())
                }
            }
        }
    }

    fun apiPutCategoryById(jsonObjectString: String, id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in")
            .build()

        val service = retrofit.create(APIServer::class.java)

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {

            val response = service.putCategoryById(requestBody, id)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("App", prettyJson)

                } else {

                    Log.e("App", response.code().toString())

                }
            }
        }
    }

    fun apiDeleteCategoryById(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/")
            .build()

        val service = retrofit.create(APIServer::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.deleteCategoryById(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("App", prettyJson)

                } else {

                    Log.e("App", response.code().toString())

                }
            }
        }
    }

    fun apiGetIncomesById(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()
        val service = retrofit.create(APIServer::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getIncomesByIdCategory(id)
            withContext(Dispatchers.Main){
                if(response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("App", prettyJson)
                } else {
                    Log.e("App", response.code().toString())
                }
            }
        }
    }

    fun apiGetExpensesById(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()
        val service = retrofit.create(APIServer::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getExpensesByIdCategory(id)
            withContext(Dispatchers.Main){
                if(response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("App", prettyJson)
                } else {
                    Log.e("App", response.code().toString())
                }
            }
        }
    }
}

class Expense {
    fun apiGetExpenses() {
        /*
         * TODO(Change baseUrl())
         */
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()
        val service = retrofit.create(APIServer::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getExpenses()
            withContext(Dispatchers.Main){
                if(response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("App", prettyJson)
                } else {
                    Log.e("App", response.code().toString())
                }
            }
        }
    }

    fun apiPostExpenses(jsonObjectString: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()

        val service = retrofit.create(APIServer::class.java)

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.postExpenses(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("App", prettyJson)

                } else {

                    Log.e("App", response.code().toString())

                }
            }
        }
    }

    fun apiGetExpenseById(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()
        val service = retrofit.create(APIServer::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getExpenseById(id)
            withContext(Dispatchers.Main){
                if(response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("App", prettyJson)
                } else {
                    Log.e("App", response.code().toString())
                }
            }
        }
    }

    fun apiPutExpenseById(jsonObjectString: String, id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in")
            .build()

        val service = retrofit.create(APIServer::class.java)

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {

            val response = service.putExpenseById(requestBody, id)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                        )
                    )

                    Log.d("App", prettyJson)

                } else {

                    Log.e("App", response.code().toString())

                }
            }
        }
    }

    fun apiDeleteExpenseById(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/")
            .build()

        val service = retrofit.create(APIServer::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.deleteExpenseById(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("App", prettyJson)

                } else {

                    Log.e("App", response.code().toString())

                }
            }
        }
    }
}

class Income {
    fun apiGetIncomes() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()
        val service = retrofit.create(APIServer::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getIncomes()
            withContext(Dispatchers.Main){
                if(response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("App", prettyJson)
                } else {
                    Log.e("App", response.code().toString())
                }
            }
        }
    }

    fun apiPostIncomes(jsonObjectString: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()

        val service = retrofit.create(APIServer::class.java)

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.postIncomes(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("App", prettyJson)

                } else {

                    Log.e("App", response.code().toString())

                }
            }
        }
    }

    fun apiGetIncomeById(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()
        val service = retrofit.create(APIServer::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getIncomeById(id)
            withContext(Dispatchers.Main){
                if(response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("App", prettyJson)
                } else {
                    Log.e("App", response.code().toString())
                }
            }
        }
    }

    fun apiPutIncomeById(jsonObjectString: String, id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in")
            .build()

        val service = retrofit.create(APIServer::class.java)

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {

            val response = service.putIncomeById(requestBody, id)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("App", prettyJson)

                } else {

                    Log.e("App", response.code().toString())

                }
            }
        }
    }

    fun apiDeleteIncomeById(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/")
            .build()

        val service = retrofit.create(APIServer::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.deleteIncomeById(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("App", prettyJson)

                } else {

                    Log.e("App", response.code().toString())

                }
            }
        }
    }
}

class User {
    fun apiGetUser() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()
        val service = retrofit.create(APIServer::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getUser()
            withContext(Dispatchers.Main){
                if(response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("App", prettyJson)
                } else {
                    Log.e("App", response.code().toString())
                }
            }
        }
    }

    fun apiPostUser(jsonObjectString: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()

        val service = retrofit.create(APIServer::class.java)

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.postUser(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("App", prettyJson)

                } else {

                    Log.e("App", response.code().toString())

                }
            }
        }
    }

    fun apiGetUserById(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()
        val service = retrofit.create(APIServer::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getUserById(id)
            withContext(Dispatchers.Main){
                if(response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("App", prettyJson)
                } else {
                    Log.e("App", response.code().toString())
                }
            }
        }
    }

    fun apiPutUserById(jsonObjectString: String, id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in")
            .build()

        val service = retrofit.create(APIServer::class.java)

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {

            val response = service.putUserById(requestBody, id)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                        )
                    )

                    Log.d("App", prettyJson)

                } else {

                    Log.e("App", response.code().toString())

                }
            }
        }
    }

    fun apiDeleteUserById(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/")
            .build()

        val service = retrofit.create(APIServer::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.deleteCategoryById(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("App", prettyJson)

                } else {

                    Log.e("App", response.code().toString())

                }
            }
        }
    }

    fun apiGetIncomesById(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()
        val service = retrofit.create(APIServer::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getIncomesByIdUser(id)
            withContext(Dispatchers.Main){
                if(response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("App", prettyJson)
                } else {
                    Log.e("App", response.code().toString())
                }
            }
        }
    }

    fun apiGetExpensesById(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com")
            .build()
        val service = retrofit.create(APIServer::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getExpensesByIdUser(id)
            withContext(Dispatchers.Main){
                if(response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("App", prettyJson)
                } else {
                    Log.e("App", response.code().toString())
                }
            }
        }
    }
}