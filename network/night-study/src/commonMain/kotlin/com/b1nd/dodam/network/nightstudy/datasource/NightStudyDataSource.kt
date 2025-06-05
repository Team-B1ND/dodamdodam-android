package com.b1nd.dodam.network.nightstudy.datasource

import com.b1nd.dodam.network.nightstudy.model.MyBanResponse
import com.b1nd.dodam.network.nightstudy.model.NightStudyResponse
import com.b1nd.dodam.network.nightstudy.model.NightStudyStudentResponse
import com.b1nd.dodam.network.nightstudy.model.ProjectResponse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate

interface NightStudyDataSource {
    suspend fun getMyNightStudy(): ImmutableList<NightStudyResponse>

    suspend fun getPendingNightStudy(): ImmutableList<NightStudyResponse>

    suspend fun getStudyingNightStudy(): ImmutableList<NightStudyResponse>

    suspend fun askNightStudy(content: String, type: String, doNeedPhone: Boolean, reasonForPhone: String?, startAt: LocalDate, endAt: LocalDate)

    suspend fun deleteNightStudy(id: Long)

    suspend fun deleteProject(id: Long)

    suspend fun getNightStudy(): ImmutableList<NightStudyResponse>

    suspend fun getNightStudyPending(): ImmutableList<NightStudyResponse>

    suspend fun allowNightStudy(id: Long)

    suspend fun rejectNightStudy(id: Long)

    suspend fun askProjectStudy(type: String, name: String, description: String, startAt: LocalDate, endAt: LocalDate, students: List<Int>)

    suspend fun myBan(): MyBanResponse?

    suspend fun getNightStudyStudent(): ImmutableList<NightStudyStudentResponse>

    suspend fun getProject(): ImmutableList<ProjectResponse>
}
