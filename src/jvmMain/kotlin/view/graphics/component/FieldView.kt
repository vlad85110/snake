package view.graphics.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import model.Player
import model.field.Field
import model.field.`object`.Food
import model.field.`object`.Snake
import model.math.Point
import model.math.Vector

@Composable
fun FieldView(field: Field) {
    remember { field.field }
    val size = field.size

    Column() {
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
}

@Composable
@Preview
fun FieldPreview() {
    val field = Field(30)
    val snake = Snake(5, Vector.DOWN, Point(3, 5), field.size,Player("123"))
    for (i in 3..15) {
        field[i, 5] = snake
    }
    field[15, 6] = snake
    field[16, 6] = snake
    field[10, 15] = Food()
    FieldView(field)
}