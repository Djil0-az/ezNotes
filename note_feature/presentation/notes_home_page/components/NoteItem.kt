package com.djilocodes.eznotes.note_feature.presentation.notes_home_page.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.djilocodes.eznotes.note_feature.domain.model.Note

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutcornerSize:Dp = 30.dp,
    onDeleteClick: ()-> Unit
//use canvas to get cut corner and note page 
){
    Box(modifier = modifier) {
        androidx.compose.foundation.Canvas(modifier = Modifier.matchParentSize()){
            //clip path cut corner
            //draw a path along the box
            val clipPath = Path().apply {   lineTo(size.width - cutcornerSize.toPx(),0f)
                lineTo(size.width,cutcornerSize.toPx())
                lineTo(size.width,size.height)
                lineTo(0f,size.height)
                close()
            }
            clipPath(clipPath){
                drawRoundRect(
                    color = Color(note.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())

                )
                // where the corner fold is
                drawRoundRect(
                    color = Color(ColorUtils.blendARGB(note.color,0x0000000,0.2f)),
                    topLeft = Offset(size.width - cutcornerSize.toPx(),-100f),
                    size = Size(cutcornerSize.toPx()+100f,cutcornerSize.toPx()+100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())

                )
            }
        }

        // text title and Description
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp) // to prevent overlap with icon 
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
                )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.description,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(

            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector =Icons.Default.Delete ,
                contentDescription ="Delete Note" )

        }
    }
}