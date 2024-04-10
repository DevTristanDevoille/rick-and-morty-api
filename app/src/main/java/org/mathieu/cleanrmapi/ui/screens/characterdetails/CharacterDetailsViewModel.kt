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

sealed interface EpisodeAction {
    data class SelectedEpisode(val episode: Episode): EpisodeAction
}

class CharacterDetailsViewModel(application: Application) : ViewModel<CharacterDetailsState>(CharacterDetailsState(), application) {

    private val characterRepository: CharacterRepository by inject()
    private val episodeRepository: EpisodeRepository by inject()
    private lateinit var character : Character

    fun init(characterId: Int) {
        fetchData(
            source = { characterRepository.getCharacter(id = characterId) }
        ) {

            onSuccess {
                character = it
                updateState { copy(avatarUrl = it.avatarUrl, name = it.name, error = null) }
                getAllEpisode()
            }

            onFailure {
                updateState { copy(error = it.toString()) }
            }

            updateState { copy(isLoading = false) }
        }
    }

    fun handleAction(action: EpisodeAction) {
        when(action) {
            is EpisodeAction.SelectedEpisode -> selectedEpisode(action.episode)
        }
    }


    private fun selectedEpisode(episode: Episode){
        sendEvent(Destination.EpisodeDetails(episode.id.toString()))
    }

    fun getAllEpisode(){
        val episodes = ArrayList<String>()

        character.episode.forEach {
            episodes.add(it.replace("https://rickandmortyapi.com/api/episode/",""))
        }

        Log.e("Test",episodes.toString())

        viewModelScope.launch {
            collectData(
                source = { episodeRepository.getEpisode(episodes) }
            ) {

                onSuccess {
                    updateState { copy(episodes = it, error = null) }
                }

                onFailure {
                    updateState { copy(episodes = emptyList(), error = it.toString()) }
                }

                updateState { copy(isLoading = false) }
            }
        }

    }


}


data class CharacterDetailsState(
    val isLoading: Boolean = true,
    val avatarUrl: String = "",
    val name: String = "",
    val error: String? = null,
    val episodes: List<Episode> = emptyList()
)