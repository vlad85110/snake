package controller.command

import model.Player
import model.field.FieldUpdater
import view.View

interface Command {
    fun run(fieldUpdater: FieldUpdater, view: View): Boolean
    val player: Player
}