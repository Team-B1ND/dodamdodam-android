package com.b1nd.dodam.network.schedule.api

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.network.schedule.datasource.ScheduleDataSource
import com.b1nd.dodam.network.schedule.model.ScheduleResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

internal class ScheduleService @Inject constructor(
    private val client: HttpClient
) : ScheduleDataSource {
    override suspend fun getScheduleBetweenPeriods(
        startDate: String,
        endDate: String
    ): ImmutableList<ScheduleResponse> {
        return safeRequest {
            client.get(DodamUrl.Schedule.SEARCH) {
                parameter("startDate", startDate)
                parameter("endDate", endDate)
            }.body<Response<List<ScheduleResponse>>>()
        }.toImmutableList()
    }
}