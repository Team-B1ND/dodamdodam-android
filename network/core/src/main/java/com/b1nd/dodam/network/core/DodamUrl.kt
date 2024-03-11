package com.b1nd.dodam.network.core

object DodamUrl {
    private const val BASE_URL = "https://dodam.b1nd.com/api"
    private const val TEST_URL = "http://101.101.209.184:33333"

    const val MEAL = "$TEST_URL/meal"
    const val AUTH = "$TEST_URL/auth"
    const val MEMBER = "$TEST_URL/member"

    object Meal {
        const val MONTH = "$MEAL/month"
    }

    object Auth {
        const val LOGIN = "$AUTH/login"
    }

    object Member {
        const val REGISTER = "$MEMBER/join-student"
    }
}
