package view

import model.field.Field

interface View {
    fun updateField(field: Field)
    fun showLoseMessage()
}