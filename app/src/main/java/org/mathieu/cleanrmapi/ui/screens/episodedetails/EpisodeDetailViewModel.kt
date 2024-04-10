package org.mathieu.cleanrmapi.ui.screens.episodedetails

import android.app.Application
import org.mathieu.cleanrmapi.ui.core.ViewModel
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.models.episode.Episode
import org.mathieu.cleanrmapi.domain.repositories.EpisodeRepository
import org.mathieu.cleanrmapi.ui.screens.characterdetails.CharacterDetailsState


class EpisodeDetailsViewModel(application: Application) : ViewModel<EpisodeDetailState>(EpisodeDetailState(), application){

    private val episodeRepository: EpisodeRepository by inject()

    fun init(episodeId: Int){
        fetchData(
            source = { episodeRepository.getEpisodeById(episodeId) }
        ){
            onSuccess {
                updateState { copy(episode = it, isLoading = false, error = null) }
            }
        }
    }


}

data class EpisodeDetailState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val episode: Episode? = null
)