package com.example.numbergridcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MaterialTheme { NumberGridScreen() } }
    }
}

@Composable
private fun NumberGridScreen() {
    val initialCount = integerResource(R.integer.initial_count)
    var count by rememberSaveable { mutableIntStateOf(initialCount) }
    val spacing = dimensionResource(R.dimen.spacing)
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().safeDrawingPadding()
                .padding(horizontal = dimensionResource(R.dimen.screen_padding)),
            verticalArrangement = Arrangement.spacedBy(spacing)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(integerResource(R.integer.columns)),
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(vertical = spacing),
                horizontalArrangement = Arrangement.spacedBy(spacing),
                verticalArrangement = Arrangement.spacedBy(spacing)
            ) {
                items(count = count, key = { it }) { index -> NumberSquare(index + 1) }
            }
            Button(
                onClick = { count++ },
                modifier = Modifier.fillMaxWidth().padding(bottom = spacing)
            ) { Text(stringResource(R.string.add_item)) }
        }
    }
}

@Composable
private fun NumberSquare(number: Int) {
    val fontSize = with(LocalDensity.current) {
        dimensionResource(R.dimen.number_size).toSp()
    }
    Box(
        modifier = Modifier.fillMaxWidth().aspectRatio(1f).background(
            colorResource(if (number % 2 == 0) R.color.even_color else R.color.odd_color)
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.item_number, number),
            color = colorResource(R.color.number_color),
            fontSize = fontSize
        )
    }
}
