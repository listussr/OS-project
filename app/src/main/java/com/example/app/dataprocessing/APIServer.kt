package com.example.app.dataprocessing

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface APIServer {

    /**
     * test variant
     */
    @GET("/api/v1/employees")
    suspend fun getEmployees(): Response<ResponseBody>

    @Headers("Content-type: application/json")
    @POST("/categories")
    suspend fun postCategory(@Header("Authorization") token: String, @Body requestBody: RequestBody) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @POST("/categories/pagination")
    suspend fun getCategoryPagination(@Header("Authorization") token: String, @Body requestBody: RequestBody) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @POST("/categories/getByFilter")
    suspend fun getCategoryByFilter(@Header("Authorization") token: String, @Body requestBody: RequestBody) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @GET("/categories/categoryById")
    suspend fun getCategoryById(@Query("id") id: String) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @PUT("/categories/categoryById")
    suspend fun putCategoryById(@Header("Authorization") token: String, @Body requestBody: RequestBody, @Query("id") id: String) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @DELETE("/categories/categoryById")
    suspend fun deleteCategoryById(@Header("Authorization") token: String, @Query("id") id: String) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @GET("/category/incomesById")
    suspend fun getIncomesByIdCategory(@Header("Authorization") token: String, @Query("id") id: String) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @GET("/category/expensesById")
    suspend fun getExpensesByIdCategory(@Header("Authorization") token: String, @Query("id") id: String) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @POST("/expenses/pagination")
    suspend fun getExpensesPagination(@Header("Authorization") token: String, @Body requestBody: RequestBody) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @POST("/expenses/getByFilter")
    suspend fun getExpensesGetByFilter(@Header("Authorization") token: String, @Body requestBody: RequestBody) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @POST("/expenses")
    suspend fun postExpenses(@Header("Authorization") token: String, @Body requestBody: RequestBody) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @GET("/expenses/expensesById")
    suspend fun getExpenseById(@Header("Authorization") token: String, @Query("id") id: String) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @PUT("/expenses/expensesById")
    suspend fun putExpenseById(@Header("Authorization") token: String, @Body requestBody: RequestBody, @Query("id") id: String) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @DELETE("/expenses/expensesById")
    suspend fun deleteExpenseById(@Header("Authorization") token: String, @Query("id") id: String) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @POST("/incomes/pagination")
    suspend fun getIncomesPagination(@Header("Authorization") token: String, @Body requestBody: RequestBody) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @POST("/incomes/getByFilter")
    suspend fun getIncomesGetByFilter(@Header("Authorization") token: String, @Body requestBody: RequestBody) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @POST("/incomes")
    suspend fun postIncomes(@Header("Authorization") token: String, @Body requestBody: RequestBody) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @GET("/incomes/incomesById")
    suspend fun getIncomeById(@Header("Authorization") token: String, @Query("id") id: String) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @PUT("/incomes/incomesById")
    suspend fun putIncomeById(@Header("Authorization") token: String, @Body requestBody: RequestBody, @Query("id") id: String) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @DELETE("/incomes/incomesById")
    suspend fun deleteIncomeById(@Header("Authorization") token: String, @Query("id") id: String) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @POST("/users/pagination")
    suspend fun getUserPagination(@Header("Authorization") token: String, @Body requestBody: RequestBody) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @POST("/users/getByFilter")
    suspend fun getUserGetByFilter(@Header("Authorization") token: String, @Body requestBody: RequestBody) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @POST("/users")
    suspend fun postUser(@Header("Authorization") token: String, @Body requestBody: RequestBody) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @GET("/users/usersById")
    suspend fun getUserById(@Header("Authorization") token: String, @Query("id") id: String) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @PUT("/users/usersById")
    suspend fun putUserById(@Header("Authorization") token: String, @Body requestBody: RequestBody, @Query("id") id: String) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @DELETE("/users/usersById")
    suspend fun deleteUserById(@Header("Authorization") token: String, @Query("id") id: String) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @GET("/users/incomesById")
    suspend fun getIncomesByIdUser(@Header("Authorization") token: String, @Query("id") id: String) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @GET("/users/expensesById")
    suspend fun getExpensesByIdUser(@Header("Authorization") token: String, @Query("id") id: String) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @POST("/users/expensesByIdAndFilter")
    suspend fun getExpensesByIdAndFilter(@Header("Authorization") token: String, @Query("id") id: String, @Body requestBody: RequestBody) : Response<ResponseBody>

    @Headers("Content-type: application/json")
    @POST("/users/incomesByIdAndFilter")
    suspend fun getIncomesByIdAndFilter(@Header("Authorization") token: String, @Query("id") id: String, @Body requestBody: RequestBody) : Response<ResponseBody>

    @POST("/register")
    suspend fun register(@Body requestBody: RequestBody) : Response<ResponseBody>

    @POST("/login")
    suspend fun login(@Body requestBody: RequestBody) : Response<ResponseBody>
}