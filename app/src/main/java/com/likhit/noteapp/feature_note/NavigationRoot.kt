package com.likhit.noteapp.feature_note

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.likhit.noteapp.feature_note.presentation.add_note.AddNoteScreenRoot
import com.likhit.noteapp.feature_note.presentation.note_overview.NoteOverviewScreenRoot

@Composable
fun NavigationRoot(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "note_overview"
    ) {
        composable(
            route = "note_overview"
        ){
            NoteOverviewScreenRoot(
                onAddClick = {
                    navController.navigate("add_note")
                },
                onNoteClick = { id, color ->
                    navController.navigate("add_note" + "?noteId=${id}&noteColor=${color}")
                }
            )
        }
        composable(
            route = "add_note" + "?noteId={noteId}&noteColor={noteColor}",
            arguments = listOf(
                navArgument(
                    name = "noteId"
                ){
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "noteColor"
                ){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
            val color = it.arguments?.getInt("noteColor")?: -1
            AddNoteScreenRoot(
                noteColor = color,
                onSaveClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}