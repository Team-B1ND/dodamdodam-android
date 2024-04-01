package com.b1nd.dodam.network.core

object DodamUrl {
    private const val BASE_URL = "https://dodam.b1nd.com/api"
    private const val TEST_URL = "http://101.101.209.184:33333"

    const val MEAL = "$TEST_URL/meal"
    const val AUTH = "$TEST_URL/auth"
    const val WAKEUP_SONG = "$TEST_URL/wakeup-song"
    const val SLEEPOVER = "$TEST_URL/out-sleeping"
    const val OUTING = "$TEST_URL/out-going"
    const val MEMBER = "$TEST_URL/member"
    const val NIGHT_STUDY = "$TEST_URL/night-study"
    const val SCHEDULE = "$TEST_URL/schedule"
    const val BANNER = "$TEST_URL/banner"

    object Meal {
        const val MONTH = "$MEAL/month"
    }

    object Auth {
        const val LOGIN = "$AUTH/login"
    }

    object Member {
        const val REGISTER = "$MEMBER/join-student"
        const val MY = "$MEMBER/my"
    }

    object WakeupSong {
        const val ALLOWED = "$WAKEUP_SONG/allowed"
        const val MY = "$WAKEUP_SONG/my"
        const val PENDING = "$WAKEUP_SONG/pending"
    }

    object Sleepover {
        const val MY = "$SLEEPOVER/my"
    }

    object Outing {
        const val MY = "$OUTING/my"
    }

    object NightStudy {
        const val MY = "$NIGHT_STUDY/my"
    }

    object Schedule {
        const val SEARCH = "$SCHEDULE/search"
    }

    object Banner {
        const val ACTIVE = "$BANNER/active"
    }
}
