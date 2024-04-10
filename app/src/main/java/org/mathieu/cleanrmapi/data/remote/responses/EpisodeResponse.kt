package org.mathieu.cleanrmapi.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
internal data class EpisodeResponse(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)