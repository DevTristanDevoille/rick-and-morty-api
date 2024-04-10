package org.mathieu.cleanrmapi.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.mathieu.cleanrmapi.data.remote.responses.EpisodeResponse
import org.mathieu.cleanrmapi.domain.models.episode.Episode

interface EpisodeRepository {

    suspend fun getEpisode(episodes: ArrayList<String>) : Flow<List<Episode>>

    suspend fun getEpisodeById(episodeId: Int): Episode

}