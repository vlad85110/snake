package controller.command.snake

import model.Player
import model.math.Vector

class Left(player: Player): MoveSnakeCommand(player) {
    override fun getVector(): Vector {
        return Vector.LEFT
    }
}