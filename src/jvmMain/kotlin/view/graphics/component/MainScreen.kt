package view.graphics.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import model.field.Field

@Composable
fun MainScreen(fieldState: MutableState<Field?>, newGame: () -> Unit, exit: () -> Unit) {
    remember { fieldState.value }

    val field = fieldState.value
    Row {
        if (field != null) {
            Row(Modifier.weight(2f)) {
                FieldView(field)
            }
        } else {
            Spacer(Modifier.weight(2f))
        }

        Column(Modifier.weight(1f).background(Color.Yellow).fillMaxHeight()) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Scores()
                CurrentGame()
            }
            Buttons(newGame,exit)
            Games(emptyList())
        }
    }
}
