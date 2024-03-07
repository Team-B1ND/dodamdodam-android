package com.b1nd.dodam.network.core

object DodamUrl {
    private const val BASE_URL = "https://dodam.b1nd.com/api"
    private const val TEST_URL = "http://101.101.209.184:33333"

    const val MEAL = "$TEST_URL/meal"
    const val AUTH = "$TEST_URL/auth"
    const val MEMBER = "$TEST_URL/member"
    const val WAKEUP_SONG = "$BASE_URL/wakeup-song"
    const val OUT = "$TEST_URL/out"
    object Meal {
        const val MONTH = "$MEAL/month"
    }

    object Auth {
        const val LOGIN = "$AUTH/login"
    }

    object Member {
        const val REGISTER = "$MEMBER/join-student"
    }

    object WakeupSong {
        const val ALLOWED = "$WAKEUP_SONG/allowed"
    }

    object Out {
        const val OUTSLEEPING = "$OUT/out-sleeping"
        const val OUTGOING = "$OUT/out-going"
        const val OUTSLEEPING_MY = "$OUT/outsleeping/my"
        const val OUTGOING_MY = "$OUT/outgoing/my"
    }
}
