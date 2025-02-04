package com.b1nd.dodam.network.division.datasource

import com.b1nd.dodam.network.division.response.DivisionOverviewResponse

interface DivisionDataSource {
    suspend fun getAllDivisions(lastId: Int, limit: Int): List<DivisionOverviewResponse>
    suspend fun getMyDivisions(lastId: Int, limit: Int): List<DivisionOverviewResponse>
}