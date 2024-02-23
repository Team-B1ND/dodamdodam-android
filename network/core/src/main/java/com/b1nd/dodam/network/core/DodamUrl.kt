package com.b1nd.dodam.network.core

object DodamUrl {
    private const val BASE_URL = "https://dodam.b1nd.com/api"

    const val MEAL = "$BASE_URL/meal"
    const val AUTH = "$BASE_URL/auth"
    object Meal {
        const val CALORIE = "$MEAL/calorie"
        const val MONTH = "$MEAL/month"
        const val CALORIE_MONTH = "$CALORIE/month"
    }

    object Auth {
        const val LOGIN = "$AUTH/login"
    }
}
