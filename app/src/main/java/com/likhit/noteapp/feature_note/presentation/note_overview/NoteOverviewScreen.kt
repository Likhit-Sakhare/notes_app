package com.likhit.noteapp.feature_note.presentation.note_overview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.likhit.noteapp.R
import com.likhit.noteapp.feature_note.presentation.note_overview.components.NoteItem
import com.likhit.noteapp.feature_note.presentation.note_overview.components.NoteOrderSection
import kotlinx.coroutines.launch

@Composable
fun NoteOverviewScreenRoot(
    onAddClick: () -> Unit,
    viewModel: NoteOverviewViewModel = hiltViewModel(),
    onNoteClick: (Int, Int) -> Unit
) {
    NoteOverviewScreenScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action){
                NoteOverviewAction.OnAddClick -> onAddClick()
                else -> Unit
            }
            viewModel.onAction(action)
        },
        onNoteClick = onNoteClick
    )
}

@Composable
private fun NoteOverviewScreenScreen(
    state: NoteOverviewState,
    onAction: (NoteOverviewAction) -> Unit,
    onNoteClick: (Int, Int) -> Unit
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAction(NoteOverviewAction.OnAddClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_note)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.my_notes),
                    style = MaterialTheme.typography.headlineLarge
                )
                IconButton(onClick = {
                    onAction(NoteOverviewAction.OnToggleClick)
                }) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = stringResource(id = R.string.sort_notes)
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                NoteOrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    noteOrder = state.noteOrder,
                    onOrderChange = { noteOrder ->
                        onAction(NoteOverviewAction.OnOrderClick(noteOrder))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.notes){ note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNoteClick(note.id!!, note.color) },
                        onDeleteClick = {
                            onAction(NoteOverviewAction.OnDeleteClick(note))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Note Deleted",
                                    actionLabel = "Undo",
                                    duration = SnackbarDuration.Short
                                )
                                if(result == SnackbarResult.ActionPerformed){
                                    onAction(NoteOverviewAction.OnRestoreClick)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}