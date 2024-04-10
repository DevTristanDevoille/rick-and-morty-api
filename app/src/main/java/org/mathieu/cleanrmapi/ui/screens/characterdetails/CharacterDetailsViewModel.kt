package org.mathieu.cleanrmapi.ui.screens.characterdetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.models.character.Character
import org.mathieu.cleanrmapi.domain.models.episode.Episode
import org.mathieu.cleanrmapi.domain.repositories.CharacterRepository
import org.mathieu.cleanrmapi.domain.repositories.EpisodeRepository
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.ViewModel
import org.mathieu.cleanrmapi.ui.screens.characters.CharactersAction

sealed interface CharacterAction {
    data class SelectedEpisode(val episode: Episode): CharacterAction
}

class CharacterDetailsViewModel(application: Application) : ViewModel<CharacterDetailsState>(CharacterDetailsState(), application) {

    private val characterRepository: CharacterRepository by inject()
    private val episodeRepository: EpisodeRepository by inject()

    fun init(characterId: Int) {
        fetchData(
            source = { characterRepository.getCharacter(id = characterId) }
        ) {

            onSuccess {
                updateState { copy(avatarUrl = it.avatarUrl, name = it.name, error = null, character = it) }
                getAllEpisode()
            }

            onFailure {
                updateState { copy(error = it.toString()) }
            }

            updateState { copy(isLoading = false) }
        }
    }

    fun handleAction(action: CharacterAction) {
        when(action) {
            is CharacterAction.SelectedEpisode -> selectedEpisode(action.episode)
        }
    }


    private fun selectedEpisode(episode: Episode){
        sendEvent(Destination.EpisodeDetails(episode.id.toString()))
    }

    private fun getAllEpisode(){
        val episodes = ArrayList<String>()

        updateState { copy(episodes = emptyList()) }

        state.value.character!!.episode.forEach {
            episodes.add(it.replace("https://rickandmortyapi.com/api/episode/",""))
        }

        if (episodes.size != 0){
            viewModelScope.launch {
                collectData(
                    source = { episodeRepository.getEpisode(episodes) }
                ) {
                    onSuccess {
                        updateState { copy(episodes = emptyList(), error = null) }
                        updateState { copy(episodes = it.sortedBy { it.id }, error = null) }
                    }

                    onFailure {
                        updateState { copy(episodes = emptyList(), error = it.toString()) }
                    }

                    updateState { copy(isLoading = false) }
                }
            }
        }
    }
}


data class CharacterDetailsState(
    val isLoading: Boolean = true,
    val avatarUrl: String = "",
    val name: String = "",
    val error: String? = null,
    val character: Character? = null,
    val episodes: List<Episode> = emptyList()
)