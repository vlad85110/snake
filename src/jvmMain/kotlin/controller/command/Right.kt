package controller.command

import model.GamePlayer
import model.math.Vector

class Right(gamePlayer: GamePlayer): MoveSnakeCommand(gamePlayer) {
    override fun getVector(): Vector {
        return Vector.RIGHT
    }
}