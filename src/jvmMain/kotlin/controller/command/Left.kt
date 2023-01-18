package controller.command

import model.GamePlayer
import model.math.Vector

class Left(gamePlayer: GamePlayer): MoveSnakeCommand(gamePlayer) {
    override fun getVector(): Vector {
        return Vector.LEFT
    }
}