package com.b1nd.dodam.wakeup_song.api

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.wakeup_song.datasource.WakeupSongDataSource
import com.b1nd.dodam.wakeup_song.model.WakeupSongResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

internal class WakeupSongService @Inject constructor(
    private val client: HttpClient,
) : WakeupSongDataSource {
    override suspend fun getAllowedWakeupSongs(
        year: Int,
        month: Int,
        day: Int
    ): ImmutableList<WakeupSongResponse> {
        return safeRequest {
            client.get(DodamUrl.WakeupSong.ALLOWED) {
                parameter("year", year)
                parameter("month", month)
                parameter("day", day)
            }.body<Response<List<WakeupSongResponse>>>()
        }.toImmutableList()
    }
}
