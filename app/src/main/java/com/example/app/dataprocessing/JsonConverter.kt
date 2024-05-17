package com.example.app.dataprocessing

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object JsonConverter {
    object FromJson {
        fun testJson() {
            val categoriesList = arrayOf(
                CategoryClass("овощи", "1465416-id65")
            )
            val gsonCategoriesArray = Gson().toJson(categoriesList)
            val arrayTutorialType = object : TypeToken<Array<CategoryClass>>() {}.type
            val gson = GsonBuilder().create()
            val categories: Array<CategoryClass> =
                gson.fromJson(gsonCategoriesArray.toString(), arrayTutorialType)
            for (i in categories) {
                Log.v("App", "Id: ${i.id}: ${i.name}")
            }
        }

        fun categoriesListJson(gson: String?): Array<CategoryClass>? {
            /**
             * Пример получаемого json файла:
             *
             * [
             *   {
             *     "name": "Овощи",
             *     "id": "301acf9d-705a-4511-9ac9-2b859788ad7f"
             *   }
             * ]
             */
            return if (gson != null) {
                val arrayCategoryClass = object : TypeToken<Array<CategoryClass>>() {}.type
                val categories: Array<CategoryClass> =
                    Gson().fromJson(gson, arrayCategoryClass)
                for (category in categories) {
                    Log.v("Json parsing", "Category: Name: ${category.name}, Id: ${category.id}")
                }
                categories
            } else {
                null
            }
        }

        fun moneyInteractionListJson(gsonString: String?): Array<MoneyInteractionClass> {
            /**
             * Пример получаемого json файла:
             *
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
            return if (gsonString != null) {
                val arrayMoneyInteraction = object : TypeToken<Array<MoneyInteractionClass>>() {}.type
                val interactions: Array<MoneyInteractionClass> =
                    Gson().fromJson(gsonString, arrayMoneyInteraction)
                interactions
            } else {
                arrayOf()
            }
        }

        fun userListJson(gsonString: String?): Array<UserClass>? {
            /**
             * Пример получаемого json файла:
             *
             * [
             *   {
             *     "id": "301acf9d-705a-4511-9ac9-2b859788ad7f",
             *     "name": "Nagibator228",
             *     "email": "nagibator228@mail.ru"
             *   }
             * ]
             */
            return if (gsonString != null) {
                val arrayUsers = object : TypeToken<Array<UserClass>>() {}.type
                val users: Array<UserClass> =
                    Gson().fromJson(gsonString, arrayUsers)
                users
            } else {
                null
            }
        }

        fun categoryJson(gsonString: String?): CategoryClass? {
            return if (gsonString != null) {
                val category: CategoryClass = Gson().fromJson(gsonString, CategoryClass::class.java)
                return category
            } else {
                null
            }
        }

        fun moneyInteractionJson(gsonString: String?): MoneyInteractionClass? {
            return if (gsonString != null) {
                val moneyInteraction: MoneyInteractionClass =
                    Gson().fromJson(gsonString, MoneyInteractionClass::class.java)
                return moneyInteraction
            } else {
                null
            }
        }

        fun userJson(gsonString: String?): UserClass? {
            return if (gsonString != null) {
                val user: UserClass = Gson().fromJson(gsonString, UserClass::class.java)
                return user
            } else {
                null
            }
        }
    }
    object ToJson {
        fun toUserJson(user: UserClass?): String? {
            return if (user != null) {
                val gson: String = Gson().toJson(user)
                return gson
            } else {
                null
            }
        }

        fun toCategoryJson(category: CategoryClass?): String? {
            return if (category != null) {
                val gson: String = Gson().toJson(category)
                return gson
            } else {
                null
            }
        }

        fun toMoneyInteractionJson(moneyInteraction: MoneyInteractionPostClass): String? {
            return if (moneyInteraction != null) {
                val gson: String = Gson().toJson(moneyInteraction)
                return gson
            } else {
                null
            }
        }

        fun toMoneyInteractionArrayJson(arr: Array<MoneyInteractionClass>): String {
            val gson: String = Gson().toJson(arr)
            return gson
        }

        fun toCategoryArrayJson(arr: Array<CategoryClass>): String {
            val gson: String = Gson().toJson(arr)
            return gson
        }

        fun toUserRegClassJson(userReg: UserRegClass): String {
            val gson: String = Gson().toJson(userReg)
            Log.v("AppJson", "toUserRegJson: $gson")
            return gson
        }

        fun toFilterClassJson(filter: FilterClass): String {
            val gson: String = Gson().toJson(filter)
            return gson
        }

        fun toFilterClassArrayJson(filter: Array<FilterClass>): String {
            val gson: String = Gson().toJson(filter)
            return gson
        }

        fun toMoneyInteractionPostClassJson(moneyInteraction: MoneyInteractionPostClass): String {
            val gson: String = Gson().toJson(moneyInteraction)
            return gson
        }
    }
}