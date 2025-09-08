package com.b1nd.dodam.data.core.model

enum class NightStudyType(val type: String) {
    NIGHT_STUDY_1("심자 1"),
    NIGHT_STUDY_2("심자 2"),
}
fun String.toNightStudyType(): NightStudyType = when (this) {
    "심자 1" -> NightStudyType.NIGHT_STUDY_1
    "심자 2" -> NightStudyType.NIGHT_STUDY_2
    else -> NightStudyType.NIGHT_STUDY_2
}
enum class ProjectNightStudyType(val type: String) {
    NIGHT_STUDY_PROJECT_1("심자 1"),
    NIGHT_STUDY_PROJECT_2("심자 2"),
}
fun String.toProjectNightStudyType(): ProjectNightStudyType = when (this) {
    "심자 1" -> ProjectNightStudyType.NIGHT_STUDY_PROJECT_1
    "심자 2" -> ProjectNightStudyType.NIGHT_STUDY_PROJECT_2
    else -> ProjectNightStudyType.NIGHT_STUDY_PROJECT_2
}
