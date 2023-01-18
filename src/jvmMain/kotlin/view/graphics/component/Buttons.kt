package view.graphics.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Buttons(newGame: () -> Unit) {
    val color = Color(109, 36, 177)

    Row(Modifier.fillMaxWidth()) {
        Row(Modifier.weight(1f)) {
            Button(
                onClick = newGame,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = color)
            ) {
                Text("New Game")
            }
        }
        Row(Modifier.weight(1f)) {
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = color)
            ) {
                Text("Exit")
            }
        }
    }
}
