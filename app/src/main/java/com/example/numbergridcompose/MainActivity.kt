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
import androidx.compose.foundation.clickable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.lazy.grid.items

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

    var squares by rememberSaveable {
        mutableStateOf((1..initialCount).toList())
    }

    var deletedSquares by rememberSaveable {
        mutableStateOf(emptyList<Int>())
    }

    var nextNumber by rememberSaveable {
        mutableIntStateOf(initialCount + 1)
    }

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
                items(
                    items = squares,
                    key = { number -> number }
                ) { number ->
                    NumberSquare(
                        number = number,
                        onClick = {
                            squares = squares - number
                            deletedSquares = deletedSquares + number
                        }
                    )
                }
            }

            Button(
                onClick = {
                    if (deletedSquares.isNotEmpty()) {
                        val restoredNumber = deletedSquares.last()

                        squares = squares + restoredNumber
                        deletedSquares = deletedSquares.dropLast(1)
                    } else {
                        squares = squares + nextNumber
                        nextNumber++
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = spacing)
            ) {
                Text(
                    text = stringResource(R.string.add_item)
                )
            }
        }
    }
}

@Composable
private fun NumberSquare(
    number: Int,
    onClick: () -> Unit
) {
    val fontSize = with(LocalDensity.current) {
        dimensionResource(R.dimen.number_size).toSp()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(
                colorResource(
                    if (number % 2 == 0) {
                        R.color.even_color
                    } else {
                        R.color.odd_color
                    }
                )
            )
            .clickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(
                R.string.item_number,
                number
            ),
            color = colorResource(R.color.number_color),
            fontSize = fontSize
        )
    }
}