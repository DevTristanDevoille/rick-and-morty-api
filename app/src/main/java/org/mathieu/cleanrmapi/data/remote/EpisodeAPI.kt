package org.mathieu.cleanrmapi.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import org.mathieu.cleanrmapi.data.remote.responses.EpisodeResponse
import org.mathieu.cleanrmapi.domain.models.episode.Episode

internal class EpisodeAPI (private val client: HttpClient){

    suspend fun getAllEpisode(episodes : ArrayList<String>): List<EpisodeResponse> = client
        .get("episode/$episodes")
        .accept(HttpStatusCode.OK)
        .body()
}