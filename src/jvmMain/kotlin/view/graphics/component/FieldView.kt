package view.graphics.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import model.field.Field
import model.field.`object`.Snake

@Composable
fun FieldView(field: Field?) {
    if (field != null) {
        remember { field.field }
        val size = field.size

        Column {
            for (i in 0 until size) {
                Row(modifier = Modifier.background(Color.Black).weight(1f)) {
                    for (j in 0 until size) {
                        val color = when (field[j, i]) {
                            is Snake -> Color.Red
                            null -> Color.Black
                            else -> Color.Blue
                        }

                        Box(Modifier.background(color).weight(1f).fillMaxHeight().zIndex(1f)) {
                            Text(" ")
                        }
                    }
                }
            }
        }
    } else {

    }
}

