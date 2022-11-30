package view.graphics.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Buttons() {
    val color = Color(109,36,177)

    Column(Modifier.fillMaxWidth()) {
        Row {
            Button(
                onClick = {},
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(backgroundColor = color)
            ) {
                Text("New Game")
            }
            Spacer(Modifier.width(10.dp))
            Button(
                onClick = {},
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(backgroundColor = color)
            ) {
                Text("Continue")
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
