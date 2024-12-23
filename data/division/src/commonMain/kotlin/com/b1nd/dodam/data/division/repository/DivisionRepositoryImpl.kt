package com.b1nd.dodam.data.division.repository

import com.b1nd.dodam.data.division.DivisionRepository
import com.b1nd.dodam.network.division.datasource.DivisionDataSource

internal class DivisionRepositoryImpl(
    private val divisionDataSource: DivisionDataSource
): DivisionRepository {

}