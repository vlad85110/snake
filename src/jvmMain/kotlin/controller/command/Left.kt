package controller.command

import model.math.Vector

class Left: MoveSnakeCommand() {
    override fun getVector(): Vector {
        return Vector.LEFT
    }
}