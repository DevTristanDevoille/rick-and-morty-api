package org.mathieu.cleanrmapi.ui.screens.episodedetails

import org.mathieu.cleanrmapi.domain.models.episode.Episode


data class EpisodeDetailsState(
    val isLoading: Boolean = true,
    val avatarUrl: String = "",
    val name: String = "",
    val error: String? = null,
    val episodes: List<Episode> = emptyList()
)