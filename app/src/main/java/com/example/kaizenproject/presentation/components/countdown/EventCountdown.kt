package com.example.kaizenproject.presentation.components.countdown

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kaizenproject.R
import com.example.kaizenproject.common.ui.theme.Blue
import com.example.kaizenproject.common.ui.theme.Red
import com.example.kaizenproject.common.ui.theme.White
import com.example.kaizenproject.common.ui.theme.Yellow

@Composable
fun EventCountdown(
    targetDuration: Long,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    competitor1: String,
    competitor2: String,
    viewModel: EventCountdownViewModel = hiltViewModel()
) {
    val countdownFlow = remember { viewModel.startCountdown(targetDuration) }
    val timerState = countdownFlow.collectAsStateWithLifecycle(initialValue = "").value

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Text(
            modifier = Modifier
                .border(width = 2.dp, color = Blue)
                .padding(4.dp),
            text = timerState,
            color = White
        )
        IconButton(onClick = onToggleFavorite) {
            Icon(
                painter = if (isFavorite) painterResource(id = R.drawable.ic_filled_star) else painterResource(id = R.drawable.ic_outlined_star),
                tint = Yellow,
                contentDescription = stringResource(R.string.cd_favorite_button)
            )
        }
        EventCompetitors(competitor1, competitor2)
    }
}

@Composable
private fun EventCompetitors(competitor1: String, competitor2: String) {
    Text(
        text = competitor1,
        color = White
    )
    Text(
        text = stringResource(R.string.event_versus_abbreviation),
        color = Red,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = competitor2,
        color = White
    )
}
