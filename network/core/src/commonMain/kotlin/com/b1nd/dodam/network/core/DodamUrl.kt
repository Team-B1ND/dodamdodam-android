package com.b1nd.dodam.network.core

object DodamUrl {
    private const val BASE_URL = "https://dodamapi.b1nd.com"

    const val MEAL = "$BASE_URL/meal"
    const val AUTH = "$BASE_URL/auth"
    const val BUS = "$BASE_URL/bus"
    const val WAKEUP_SONG = "$BASE_URL/wakeup-song"
    const val SLEEPOVER = "$BASE_URL/out-sleeping"
    const val OUTING = "$BASE_URL/out-going"
    const val MEMBER = "$BASE_URL/member"
    const val NIGHT_STUDY = "$BASE_URL/night-study"
    const val SCHEDULE = "$BASE_URL/schedule"
    const val BANNER = "$BASE_URL/banner"
    const val POINT = "$BASE_URL/point"
    const val GET_BUNDLE_ID = "https://itunes.apple.com/lookup?bundleId=com.b1nd.dodam.teacher&country=br"
    const val UPLOAD = "$BASE_URL/upload"
    const val DIVISION = "$BASE_URL/divisions"
    const val NOTICE = "$BASE_URL/notice"

    object Meal {
        const val MONTH = "$MEAL/month"
    }

    object Auth {
        const val LOGIN = "$AUTH/login"
    }

    object Bus {
        const val APPLY = "$BUS/apply"
    }

    object Member {
        const val REGISTER = "$MEMBER/join-student"
        const val REGISTER_TEACHER = "$MEMBER/join-teacher"
        const val MY = "$MEMBER/my"
        const val DEACTIVATION = "$MEMBER/deactivate"
        const val STATUS = "$MEMBER/status"
        const val EDIT = "$MEMBER/info"
        const val CODE = "$MEMBER/code"
    }

    object WakeupSong {
        const val ALLOWED = "$WAKEUP_SONG/allowed"
        const val MY = "$WAKEUP_SONG/my"
        const val PENDING = "$WAKEUP_SONG/pending"
        const val KEY_WORD = "$WAKEUP_SONG/keyword"
        const val SEARCH = "$WAKEUP_SONG/search"
        const val CHART = "$WAKEUP_SONG/chart"
    }

    object Sleepover {
        const val MY = "$SLEEPOVER/my"
        const val VALID = "$SLEEPOVER/valid"
        const val ALL = SLEEPOVER
    }

    object Outing {
        const val MY = "$OUTING/my"
    }

    object NightStudy {
        const val MY = "$NIGHT_STUDY/my"
        const val PENDING = "$NIGHT_STUDY/pending"
    }

    object Schedule {
        const val SEARCH = "$SCHEDULE/search"
    }

    object Banner {
        const val ACTIVE = "$BANNER/active"
    }

    object Point {
        const val MY = "$POINT/my"
        const val REASON = "$POINT/reason"
    }

    object Division {
        const val MY = "$DIVISION/my"
    }
}
