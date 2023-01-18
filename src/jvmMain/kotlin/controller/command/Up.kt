package controller.command

import model.GamePlayer
import model.math.Vector

class Up(gamePlayer: GamePlayer): MoveSnakeCommand(gamePlayer) {
    override fun getVector(): Vector {
        return Vector.UP
    }
}