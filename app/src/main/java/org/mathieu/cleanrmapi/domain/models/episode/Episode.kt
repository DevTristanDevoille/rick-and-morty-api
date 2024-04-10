package org.mathieu.cleanrmapi.domain.models.episode

import kotlinx.serialization.Serializable

/**
 * Represents a detailed characterization, typically derived from a data source or API.
 *
 * @property id The unique identifier for the character.
 * @property name The name of the character.
 * @property status The current status of the character (Alive, Dead, or Unknown).
 * @property species The species or classification of the character.
 * @property type Further description or subspecies of the character.
 * @property gender The gender of the character.
 * @property origin The origin location of the character, represented as a name and an id of location.
 * @property location The current or last known location of the character, represented as a name and an id of location.
 * @property avatarUrl A URL pointing to an avatar or image of the character.
 */
@Serializable
data class Episode (
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)