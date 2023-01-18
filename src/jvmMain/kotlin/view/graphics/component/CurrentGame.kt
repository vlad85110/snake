package view.graphics.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import model.GameAnnouncement

@Composable
fun CurrentGame() {
    Column {

        Row {
            Text("Ведущий: Vlad")
        }
        Row {
            Text("Размер: 40х30")
        }
        Row {
            Text("Еда: 2х+1")
        }
    }
}