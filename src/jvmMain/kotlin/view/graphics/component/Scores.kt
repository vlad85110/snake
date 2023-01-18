package view.graphics.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun Scores() {
    Column {
        Row {
            Text("Vlad")
            Text("80")
        }
        Row {
            Text("Stas")
            Text("70")
        }
    }
}