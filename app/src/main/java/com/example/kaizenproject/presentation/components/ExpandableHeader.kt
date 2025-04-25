package com.example.kaizenproject.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kaizenproject.R
import com.example.kaizenproject.common.ui.theme.Red
import com.example.kaizenproject.common.ui.theme.White
import com.example.kaizenproject.common.ui.theme.Yellow

@Composable
fun ExpandableHeader(
    title: String,
    content: @Composable () -> Unit,
    isSwitchEnabled: Boolean = false,
    onSwitched: (Boolean) -> Unit,
) {
    val isExpanded = remember { mutableStateOf(false) }
    val isSwitchedOn = remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        getRotationFloatValue(isExpanded = isExpanded),
        label = stringResource(R.string.cd_animate_arrow_rotation)
    )

    Row(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .background(White)
            .clickable { isExpanded.value = !isExpanded.value }
    ) {
        ColoredCircle(
            color = Red,
            modifier = Modifier.padding(
                start = 4.dp,
                end = 4.dp,
                top = 8.dp
            )
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp, top = 6.dp)
                .weight(1f),
            text = title,
            fontWeight = FontWeight.Bold
        )
        Switch(
            enabled = isSwitchEnabled,
            checked = isSwitchedOn.value,
            onCheckedChange = { isChecked ->
                isSwitchedOn.value = isChecked
                onSwitched(isChecked)
            },
            thumbContent = {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = stringResource(R.string.cd_favorite_button),
                    tint = if (isSwitchedOn.value) Yellow else White
                )
            },
        )
        Icon(
            modifier = Modifier
                .padding(start = 8.dp, end = 4.dp, top = 8.dp)
                .graphicsLayer(rotationZ = rotation),
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = stringResource(R.string.cd_expansion_arrow)
        )
    }

    AnimatedVisibility(visible = isExpanded.value) {
        content()
    }
}

@Composable
private fun getRotationFloatValue(isExpanded: MutableState<Boolean>) =
    if (isExpanded.value) 180f else 0f

@Composable
private fun ColoredCircle(color: Color, modifier: Modifier) {
    Canvas(modifier = modifier.size(20.dp)) {
        drawCircle(color = color)
    }
}

@Preview
@Composable
fun ExpandableHeaderPreview() {
    ExpandableHeader(
        title = "Title",
        content = {
            Text("Content")
        },
        onSwitched = {}
    )
}
