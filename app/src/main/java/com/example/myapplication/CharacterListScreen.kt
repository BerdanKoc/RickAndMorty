package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.myapplication.network.Character
import com.example.myapplication.viewmodel.CharacterViewModel

@Composable
fun CharacterListScreen(viewModel: CharacterViewModel) {
    var characters by remember { mutableStateOf(listOf<Character>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.fetchCharacters {
            characters = viewModel.characters
            isLoading = false
        }
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
    } else {
        LazyColumn {
            items(characters) { character ->
                CharacterItem(character)
            }
        }
    }
}

@Composable
fun CharacterItem(character: Character) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(character.image),
            contentDescription = character.name,
            modifier = Modifier.size(128.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = character.name,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}
