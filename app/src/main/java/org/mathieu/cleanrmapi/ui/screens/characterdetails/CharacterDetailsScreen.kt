package org.mathieu.cleanrmapi.ui.screens.characterdetails

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import org.mathieu.cleanrmapi.domain.models.episode.Episode
import org.mathieu.cleanrmapi.ui.core.composables.PreviewContent
import org.mathieu.cleanrmapi.ui.core.theme.Purple40
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.navigate

private typealias UIState = CharacterDetailsState

@Composable
fun CharacterDetailsScreen(
    navController: NavController,
    id: Int
) {
    val viewModel: CharacterDetailsViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.events
            .onEach { event ->
                if (event is Destination.EpisodeDetails)
                    navController.navigate(destination = event)
            }.collect()
    }

    viewModel.init(characterId = id)

    CharacterDetailsContent(
        state = state,
        onAction = viewModel::handleAction,
        onClickBack = navController::popBackStack
    )

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun CharacterDetailsContent(
    state: UIState = UIState(),
    onAction: EpisodeAction,
    onClickBack: () -> Unit = { }
) = Scaffold(topBar = {

    Row(
        modifier = Modifier
            .background(Purple40)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(16.dp)
                .clickable(onClick = onClickBack),
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.White)
        )

        Text(
            text = state.name,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}) { paddingValues ->
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues), contentAlignment = Alignment.Center) {
        AnimatedContent(targetState = state.error != null, label = "") {
            state.error?.let { error ->
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = error,
                    textAlign = TextAlign.Center,
                    color = Purple40,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 36.sp
                )
            } ?: Box(modifier = Modifier.fillMaxSize()) {

                Box(Modifier.align(Alignment.TopCenter)) {

                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .blur(100.dp)
                            .alpha(0.3f)
                            .fillMaxWidth(),
                        model = state.avatarUrl,
                        contentDescription = null
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.background
                                    )
                                )
                            )
                    )


                }

                /*LazyColumn (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    items(state.episodes) {
                        EpisodeCard(
                            modifier = Modifier
                                .padding(8.dp),
                            episode = it
                        )
                    }
                }*/

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .shadow(3.dp),
                        model = state.avatarUrl,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = state.name)
                }
            }
        }
    }
}

@Composable
private fun EpisodeCard(
    modifier: Modifier, episode: Episode
){
    Row(
        modifier = modifier
            .shadow(5.dp)
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = episode.airDate)

        Spacer(modifier = Modifier.width(12.dp))

        Text(text = episode.episode + " - " + episode.name)

    }
}


@Preview
@Composable
private fun CharacterDetailsPreview() = PreviewContent {
    CharacterDetailsContent()
}

