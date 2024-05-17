package com.example.app.dataprocessing

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIServer {

    /**
     * test variant
     */
    @GET("/api/v1/employees")
    suspend fun getEmployees(): Response<ResponseBody>



    //@POST("/categories")
    //suspend fun postCategory(@Body requestBody: RequestBody) : Response<ResponseBody>

    @POST("/categories")
    suspend fun postCategory(@Body requestBody: RequestBody) : Response<ResponseBody>

    @POST("/categories/pagination")
    suspend fun getCategoryPagination(@Body requestBody: RequestBody) : Response<ResponseBody>

    @POST("/categories/getByFilter")
    suspend fun getCategoryByFilter(@Body requestBody: RequestBody) : Response<ResponseBody>

    @GET("/categories/categoryById/{Id}")
    suspend fun getCategoryById(@Path("Id") id: String) : Response<ResponseBody>

    @PUT("/categories/categoryById/{Id}")
    suspend fun putCategoryById(@Body requestBody: RequestBody, @Path("Id") id: String) : Response<ResponseBody>

    @DELETE("/categories/categoryById/{Id}")
    suspend fun deleteCategoryById(@Path("Id") id: String) : Response<ResponseBody>

    @GET("/category/incomesById/{ID}")
    suspend fun getIncomesByIdCategory(@Path("Id") id: String) : Response<ResponseBody>

    @GET("/category/expensesById/{Id}")
    suspend fun getExpensesByIdCategory(@Path("Id") id: String) : Response<ResponseBody>

    //@GET("/expenses")
    //suspend fun getExpenses() : Response<ResponseBody>

    @POST("/expenses/pagination")
    suspend fun getExpensesPagination(@Body requestBody: RequestBody) : Response<ResponseBody>

    @POST("/expenses/getByFilter")
    suspend fun getExpensesGetByFilter(@Body requestBody: RequestBody) : Response<ResponseBody>

    @POST("/expenses")
    suspend fun postExpenses(@Body requestBody: RequestBody) : Response<ResponseBody>

    @GET("/expenses/expensesById/{Id}")
    suspend fun getExpenseById(@Path("Id") id: String) : Response<ResponseBody>

    @PUT("/expenses/expensesById/{Id}")
    suspend fun putExpenseById(@Body requestBody: RequestBody, @Path("Id") id: String) : Response<ResponseBody>

    @DELETE("/expenses/expensesById/{Id}")
    suspend fun deleteExpenseById(@Path("Id") id: String) : Response<ResponseBody>

    //@GET("/incomes")
    //suspend fun getIncomes() : Response<ResponseBody>

    @POST("/incomes/pagination")
    suspend fun getIncomesPagination(@Body requestBody: RequestBody) : Response<ResponseBody>

    @POST("/incomes/getByFilter")
    suspend fun getIncomesGetByFilter(@Body requestBody: RequestBody) : Response<ResponseBody>

    @POST("/incomes")
    suspend fun postIncomes(@Body requestBody: RequestBody) : Response<ResponseBody>

    @GET("/incomes/incomesById/{Id}")
    suspend fun getIncomeById(@Path("Id") id: String) : Response<ResponseBody>

    @PUT("/incomes/incomesById/{Id}")
    suspend fun putIncomeById(@Body requestBody: RequestBody, @Path("Id") id: String) : Response<ResponseBody>

    @DELETE("/incomes/incomesById/{Id}")
    suspend fun deleteIncomeById(@Path("Id") id: String) : Response<ResponseBody>

    //@GET("/users")
    //suspend fun getUser() : Response<ResponseBody>

    @POST("/users/pagination")
    suspend fun getUserPagination(@Body requestBody: RequestBody) : Response<ResponseBody>

    @POST("/users/getByFilter")
    suspend fun getUserGetByFilter(@Body requestBody: RequestBody) : Response<ResponseBody>

    @POST("/users")
    suspend fun postUser(@Body requestBody: RequestBody) : Response<ResponseBody>

    @GET("/users/usersById/{Id}")
    suspend fun getUserById(@Path("Id") id: String) : Response<ResponseBody>

    @PUT("/users/usersById/{Id}")
    suspend fun putUserById(@Body requestBody: RequestBody, @Path("Id") id: String) : Response<ResponseBody>

    @DELETE("/users/usersById/{Id}")
    suspend fun deleteUserById(@Path("Id") id: String) : Response<ResponseBody>

    @GET("/users/incomesById/{Id}")
    suspend fun getIncomesByIdUser(@Path("Id") id: String) : Response<ResponseBody>

    @GET("/users/expensesById/{Id}")
    suspend fun getExpensesByIdUser(@Path("Id") id: String) : Response<ResponseBody>
}