package view.graphics.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Buttons() {
    val color = Color(109,36,177)

    Column {
        Row {
            Button(
                onClick = {},
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(backgroundColor = color)
            ) {
                Text("New Game")
            }
        }
        Row {
            Button(
                onClick = {},
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(backgroundColor = color)
            ) {
                Text("Exit")
            }
        }
    }
}

@Composable
@Preview
fun ButtonsPreview() {
    Buttons()
}
