package org.mathieu.cleanrmapi.data.local.objects

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import org.mathieu.cleanrmapi.data.remote.responses.EpisodeResponse
import org.mathieu.cleanrmapi.domain.models.episode.Episode

internal class EpisodeObject : RealmObject{
    var id: Int = -1
    var name: String = ""
    var airDate: String = ""
    var episode: String = ""
    @Ignore
    var characters: List<String> = emptyList()
    var url: String = ""
    var created: String = ""
}

internal fun EpisodeResponse.toRealmObject() = EpisodeObject().also { obj ->
    obj.id = id
    obj.name = name
    obj.airDate = airDate
    obj.url = url
    obj.characters = characters
    obj.episode = episode
    obj.created = created
}

internal fun EpisodeObject.toModel() = Episode(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode,
    characters = characters,
    url = url,
    created = created
)

