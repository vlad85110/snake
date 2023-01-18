package view.graphics.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MainScreen(newGame: () -> Unit) {
    Row {
        if (false) {
            FieldView(null)
        } else {
            Spacer(Modifier.weight(2f))
        }

        Column(Modifier.weight(1f).background(Color.Yellow).fillMaxHeight()) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Scores()
                CurrentGame()
            }
            Buttons(newGame)
            Games(emptyList())
        }
    }
}
