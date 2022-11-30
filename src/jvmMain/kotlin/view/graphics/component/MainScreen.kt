package view.graphics.component

import androidx.compose.runtime.Composable
import model.field.Field

@Composable
fun MainScreen(field: Field) {
    Buttons()
    FieldView(field)
}