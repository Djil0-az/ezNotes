package com.djilocodes.eznotes.note_feature.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

// class for our Hint Box this is the first part of the ADDEDIT screen
// which handles the hint text fields


@Composable
fun TransparentHintText(
    text: String,// actual text
    hint: String,// hint when no text
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValChange: (String)-> Unit,// toggle for visibility
    textStyle: TextStyle = TextStyle(), // default compose text style
    singleLine: Boolean = false,// is our hint single line
    onFocusChange: (FocusState)-> Unit // same as focus state in view model

) {
    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = onValChange,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it)
                })
        if (isHintVisible) {
            Text(text = hint, style = textStyle, color = Color.DarkGray)
        }
    }
}