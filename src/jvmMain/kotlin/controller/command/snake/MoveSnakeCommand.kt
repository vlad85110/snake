package controller.command.snake

import controller.command.AbstractCommand
import controller.command.Command
import model.Player
import model.math.Vector
import model.field.FieldUpdater
import view.View

abstract class MoveSnakeCommand(player: Player): AbstractCommand(player) {
    override fun run(fieldUpdater: FieldUpdater, view: View): Boolean {
        fieldUpdater.moveSnake(player, getVector())
        return true
    }

    abstract fun getVector(): Vector
}