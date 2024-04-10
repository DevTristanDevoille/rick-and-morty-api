package org.mathieu.cleanrmapi.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.mathieu.cleanrmapi.data.local.EpisodeLocal
import org.mathieu.cleanrmapi.data.local.objects.EpisodeObject
import org.mathieu.cleanrmapi.data.local.objects.toModel
import org.mathieu.cleanrmapi.data.local.objects.toRealmObject
import org.mathieu.cleanrmapi.data.remote.EpisodeAPI
import org.mathieu.cleanrmapi.data.remote.responses.EpisodeResponse
import org.mathieu.cleanrmapi.domain.models.episode.Episode
import org.mathieu.cleanrmapi.domain.repositories.EpisodeRepository

internal class EpisodeRepositoryImpl (
    private val episodeApi: EpisodeAPI,
    private val episodeLocal: EpisodeLocal
) : EpisodeRepository {
    override suspend fun getEpisode(episodes: ArrayList<String>): Flow<List<Episode>> =
        episodeLocal
            .getEpisodes()
            .mapElement(transform = EpisodeObject::toModel)
            .also { if (it.first().isEmpty()) fetchNext(episodes) }


    private suspend fun fetchNext(episodes : ArrayList<String>) {

        val response = episodeApi.getAllEpisode(episodes)

        episodeLocal.deleteEpisodes()

        val objects = response.map(transform = EpisodeResponse::toRealmObject)

        episodeLocal.saveEpisodes(objects)

    }
}