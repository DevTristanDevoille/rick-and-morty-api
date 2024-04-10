package org.mathieu.cleanrmapi.data.local.objects

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import org.mathieu.cleanrmapi.data.remote.responses.EpisodeResponse
import org.mathieu.cleanrmapi.domain.models.episode.Episode

internal class EpisodeObject : RealmObject{
    var id: Int = -1
    var name: String = ""
    var airDate: String = ""
    var episode: String = ""
    var characters: RealmList<String> = realmListOf()
    var url: String = ""
    var created: String = ""
}

internal fun EpisodeResponse.toRealmObject() = EpisodeObject().also { obj ->
    obj.id = id
    obj.name = name
    obj.airDate = air_date
    obj.url = url
    obj.characters.addAll(characters)
    obj.episode = episode
    obj.created = created
}

internal fun EpisodeObject.toModel() = Episode(
    id = id,
    name = name,
    air_date = airDate,
    episode = episode,
    characters = characters,
    url = url,
    created = created
)

