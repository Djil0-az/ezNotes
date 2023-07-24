package com.djilocodes.eznotes.note_feature.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.djilocodes.eznotes.note_feature.domain.model.Note
import com.djilocodes.eznotes.note_feature.presentation.add_edit_note.components.TransparentHintText
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    // navigate back to home page
    navController: NavController,
    noteColor: Int, // we will pass id and color as arguments for nav control
    // color is an argument to pass becuase otherwise a random color will be selected
    viewModel : AddEditNoteVM = hiltViewModel()
)
{
    // state references
    val titleState = viewModel.noteTitle.value
    val descriptionState = viewModel.noteDescription.value

    val scaffoldState = rememberScaffoldState()

    // fading animation for changing colors
    val noteBackgroundAnimatable = remember{
        Animatable(
            Color(
                if(noteColor!=-1) // see if note already exists
                    noteColor else viewModel.noteColor.value)
        )
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is AddEditNoteVM.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditNoteVM.UiEvent.SaveNote ->{
                    navController.navigateUp()
                }
            }
        }
    }
    Scaffold(
        floatingActionButton ={
            FloatingActionButton(onClick =
            {viewModel.onEvent(AddEditEvent.SaveNote) },
            backgroundColor = MaterialTheme.colors.primary )
            {
                Icon(imageVector = Icons.Default.Save, contentDescription ="Save note" )
            }
        },
        scaffoldState = scaffoldState
    ) {
        // coloumn + rows + texts , most prominent UI elements go here
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(16.dp)
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // one circle for each color
                Note.noteColors.forEach{
                        color -> val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp) // todo Scale for different screen sizes
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.value == colorInt) {
                                    Color.Black // if selected black else transparent
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            // make color circle clickable and animate it to new colors
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 550
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditEvent.changeColor(colorInt))
                            }
                    )

                }
             }
            // below are text fields

            Spacer(modifier = Modifier.height(16.dp ))
            //title text field
            TransparentHintText(text =
                titleState.text,
                hint =titleState.hint ,
                onValChange ={
                             viewModel.onEvent(AddEditEvent.EnterTitle(it))
                } ,
                onFocusChange = {
                    viewModel.onEvent(AddEditEvent.noHintTitle(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            //content text field
            Spacer(modifier = Modifier.height(16.dp ))
            TransparentHintText(text =
            descriptionState.text,
                hint =descriptionState.hint ,
                onValChange ={
                    viewModel.onEvent(AddEditEvent.EnterDescription(it))
                } ,
                onFocusChange = {
                    viewModel.onEvent(AddEditEvent.noHintDescription(it))
                },
                isHintVisible = descriptionState.isHintVisible,
                singleLine = false,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}
/* to preview a screen
@Preview
@Composable
fun MyViewPreview() {
    MyView()
}
* */