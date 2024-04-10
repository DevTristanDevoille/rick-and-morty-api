package org.mathieu.cleanrmapi.ui.screens.episodedetails

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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.navigate
import org.mathieu.cleanrmapi.ui.core.theme.Purple40

private typealias UIState = EpisodeDetailState

@Composable
fun EpisodeDetailScreen(
    navController: NavController,
    id: Int
) {
    val viewModel: EpisodeDetailsViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.events
            .onEach { event ->
                if (event is Destination.EpisodeDetails)
                    navController.navigate(destination = event)
            }.collect()
    }

    viewModel.init(episodeId = id)

    EpisodeDetailsContent(
        state = state,
        onClickBack = navController::popBackStack
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun EpisodeDetailsContent(
    state: UIState = UIState(),
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

        state.episode?.let {
            Text(
                text = it.name,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
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
                
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    
                    Row {
                        state.episode?.let { it1 -> Text(text = it1.air_date) }
                    }

                    Row {
                        Text(text = (state.episode?.episode ?: "") + " - " + (state.episode?.name ?: ""))
                    }
                }
            }
        }
    }
}