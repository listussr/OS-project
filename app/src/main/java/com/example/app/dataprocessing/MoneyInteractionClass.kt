package com.example.app.dataprocessing

data class MoneyInteractionClass (
    var id: String,
    var comment: String,
    var value: Int,
    var getDate: String,
    var user: UserClass,
    var category: CategoryClass
)