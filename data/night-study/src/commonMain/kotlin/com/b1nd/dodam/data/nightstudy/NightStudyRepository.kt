package com.b1nd.dodam.data.nightstudy

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.core.model.NightStudyType
import com.b1nd.dodam.data.core.model.ProjectNightStudyType
import com.b1nd.dodam.data.nightstudy.model.MyBan
import com.b1nd.dodam.data.nightstudy.model.NightStudy
import com.b1nd.dodam.data.nightstudy.model.NightStudyStudent
import com.b1nd.dodam.data.nightstudy.model.Project
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface NightStudyRepository {
    fun getMyNightStudy(): Flow<Result<ImmutableList<NightStudy>>>

    fun getPendingNightStudy(): Flow<Result<ImmutableList<NightStudy>>>

    fun getStudyingNightStudy(): Flow<Result<ImmutableList<NightStudy>>>

    fun askNightStudy(
        content: String,
        type: NightStudyType,
        doNeedPhone: Boolean,
        reasonForPhone: String?,
        startAt: LocalDate,
        endAt: LocalDate,
    ): Flow<Result<Unit>>

    fun deleteNightStudy(id: Long): Flow<Result<Unit>>

    fun deleteProject(id: Long): Flow<Result<Unit>>

    fun getNightStudy(): Flow<Result<ImmutableList<NightStudy>>>

    fun getNightStudyPending(): Flow<Result<ImmutableList<NightStudy>>>

    fun allowNightStudy(id: Long): Flow<Result<Unit>>

    fun rejectNightStudy(id: Long): Flow<Result<Unit>>

    fun askProjectStudy(
        type: ProjectNightStudyType,
        name: String,
        description: String,
        startAt: LocalDate,
        endAt: LocalDate,
        students: List<Int>,
    ): Flow<Result<Unit>>

    fun myBan(): Flow<Result<MyBan?>>

    fun getNightStudyStudent(): Flow<Result<ImmutableList<NightStudyStudent>>>

    fun getProject(): Flow<Result<ImmutableList<Project>>>
    fun postNightStudyBan(student: Long, reason: String, ended: String): Flow<Result<Unit>>
}
