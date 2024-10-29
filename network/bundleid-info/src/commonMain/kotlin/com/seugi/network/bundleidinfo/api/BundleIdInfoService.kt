package com.seugi.network.bundleidinfo.api

import com.b1nd.dodam.network.core.DodamUrl
import com.seugi.network.bundleidinfo.datasource.BundleIdInfoDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class BundleIdInfoService (
    private val network: HttpClient
): BundleIdInfoDataSource {

    override suspend fun getBundleId(): String? {
        val response = network.get(DodamUrl.GET_BUNDLE_ID)

        if (response.status.value == 200) {
            // JSON 파싱
            val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
            val results = json["results"]?.jsonArray

            if (results != null && results.isNotEmpty()) {
                val firstResult = results[0].jsonObject
                val currentVersion = firstResult["version"]?.jsonPrimitive?.content
                return currentVersion
            }
        }
        return null
    }
}