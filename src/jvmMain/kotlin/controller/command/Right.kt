package controller.command

import model.math.Vector

class Right: MoveSnakeCommand() {
    override fun getVector(): Vector {
        return Vector.RIGHT
    }
}