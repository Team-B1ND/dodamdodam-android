package com.b1nd.dodam.network.division.api

import com.b1nd.dodam.network.division.datasource.DivisionDataSource
import io.ktor.client.HttpClient

internal class DivisionService constructor(
    private val httpClient: HttpClient
): DivisionDataSource {
}