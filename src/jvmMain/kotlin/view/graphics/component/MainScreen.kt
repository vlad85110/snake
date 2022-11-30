package view.graphics.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import model.field.Field

@Composable
fun MainScreen(field: Field) {
    Column {
        FieldView(field)
        Buttons()
        Text("123")
    }
}