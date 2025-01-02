package com.likhit.noteapp.feature_note.presentation.add_note

import android.widget.Toast
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.likhit.noteapp.R
import com.likhit.noteapp.feature_note.domain.Note
import com.likhit.noteapp.feature_note.presentation.add_note.components.TransparentHintTextField
import com.likhit.noteapp.ui.theme.NoteAppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddNoteScreenRoot(
    onSaveClick: () -> Unit,
    viewModel: AddNoteViewModel = hiltViewModel(),
    noteColor: Int
) {
    val context = LocalContext.current
    val events = viewModel.events

    AddNoteScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        noteColor = noteColor
    )

    LaunchedEffect(key1 = true) {
        events.collectLatest { event ->
            when(event){
                AddNoteEvent.SaveNote -> onSaveClick()
                is AddNoteEvent.ShowToast -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}

@Composable
private fun AddNoteScreen(
    noteColor: Int,
    state: AddNoteState,
    onAction: (AddNoteAction) -> Unit,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if(noteColor != -1) noteColor else state.noteColor)
        )
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction(AddNoteAction.SaveNote) }) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(id = R.string.save_note)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (state.noteColor == colorInt) {
                                    Color.Black
                                } else {
                                    Color.Transparent
                                },
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                onAction(AddNoteAction.ChangeColor(colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = state.titleText,
                hint = state.titleHint,
                onValueChange = {
                    onAction(AddNoteAction.EnteredTitle(it))
                },
                onFocusChange = {
                    onAction(AddNoteAction.ChangeTitleFocus(it))
                },
                isHintVisible = state.isTitleHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = state.contentText,
                hint = state.contentHint,
                onValueChange = {
                    onAction(AddNoteAction.EnteredContent(it))
                },
                onFocusChange = {
                    onAction(AddNoteAction.ChangeContentFocus(it))
                },
                isHintVisible = state.isContentHintVisible,
                singleLine = false,
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

@Preview
@Composable
private fun AddNoteScreenPreview() {
    NoteAppTheme {
        AddNoteScreen(
            state = AddNoteState(),
            noteColor = Note.noteColors.random().toArgb(),
            onAction = {}
        )
    }
}