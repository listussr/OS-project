package com.example.app.dataprocessing

import com.example.app.jsonClasses.UserFullDto
import com.google.gson.Gson

class JsonToRawDataClass {

    fun expensesToList(gson: Gson) : List<ExpensesClass> {
        /**
         * [
         *   {
         *     "id": "301acf9d-705a-4511-9ac9-2b859788ad7f",
         *     "comment": "Расход на продукты",
         *     "value": 20000,
         *     "getDate": "25.03.2024 12:42:41",
         *     "user": {
         *       "id": "301acf9d-705a-4511-9ac9-2b859788ad7f",
         *       "name": "Nagibator228",
         *       "email": "nagibator228@mail.ru"
         *     },
         *     "category": {
         *       "name": "Овощи",
         *       "id": "301acf9d-705a-4511-9ac9-2b859788ad7f"
         *     }
         *   }
         * ]
         */
        return listOf()
    }

}