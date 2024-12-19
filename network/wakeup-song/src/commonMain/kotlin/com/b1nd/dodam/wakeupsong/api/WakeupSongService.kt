package com.b1nd.dodam.wakeupsong.api

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.DefaultResponse
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.defaultSafeRequest
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.wakeupsong.datasource.WakeupSongDataSource
import com.b1nd.dodam.wakeupsong.model.MelonChartSongResponse
import com.b1nd.dodam.wakeupsong.model.SearchWakeupSongRequest
import com.b1nd.dodam.wakeupsong.model.SearchWakeupSongResponse
import com.b1nd.dodam.wakeupsong.model.VideoUrlWakeupSongRequest
import com.b1nd.dodam.wakeupsong.model.WakeupSongResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

internal class WakeupSongService(
    private val client: HttpClient,
) : WakeupSongDataSource {
    override suspend fun getAllowedWakeupSongs(year: Int, month: Int, day: Int): ImmutableList<WakeupSongResponse> {
        return safeRequest {
            client.get(DodamUrl.WakeupSong.ALLOWED) {
                parameter("year", year)
                parameter("month", month)
                parameter("day", day)
            }.body<Response<List<WakeupSongResponse>>>()
        }.toImmutableList()
    }

    override suspend fun getMyWakeupSongs(): ImmutableList<WakeupSongResponse> {
        return safeRequest {
            client.get(DodamUrl.WakeupSong.MY)
                .body<Response<List<WakeupSongResponse>>>()
        }.toImmutableList()
    }

    override suspend fun getPendingWakeupSongs(): ImmutableList<WakeupSongResponse> {
        return safeRequest {
            client.get(DodamUrl.WakeupSong.PENDING)
                .body<Response<List<WakeupSongResponse>>>()
        }.toImmutableList()
    }

    override suspend fun deleteWakeupSong(id: Long) {
        return defaultSafeRequest {
            client.delete(DodamUrl.WakeupSong.MY + "/$id")
                .body<DefaultResponse>()
        }
    }

    override suspend fun postWakeupSong(artist: String, title: String) {
        return defaultSafeRequest {
            client.post(DodamUrl.WakeupSong.KEY_WORD) {
                contentType(ContentType.Application.Json)
                setBody(
                    SearchWakeupSongRequest(
                        artist = artist,
                        title = title,
                    ),
                )
            }.body<DefaultResponse>()
        }
    }

    override suspend fun postWakeupSongFromYoutubeUrl(url: String) {
        return defaultSafeRequest {
            client.post(DodamUrl.WAKEUP_SONG) {
                contentType(ContentType.Application.Json)
                setBody(
                    VideoUrlWakeupSongRequest(
                        videoUrl = url
                    ),
                )
            }.body<DefaultResponse>()
        }
    }

    override suspend fun searchWakeupSong(keyWord: String): ImmutableList<SearchWakeupSongResponse> {
        return safeRequest {
            client.get(DodamUrl.WakeupSong.SEARCH) {
                parameter("keyword", keyWord)
            }
                .body<Response<List<SearchWakeupSongResponse>>>()
        }.toImmutableList()
    }

    override suspend fun getMelonChart(): ImmutableList<MelonChartSongResponse> {
        return safeRequest {
            client.get(DodamUrl.WakeupSong.CHART)
                .body<Response<List<MelonChartSongResponse>>>()
        }.toImmutableList()
    }
}
