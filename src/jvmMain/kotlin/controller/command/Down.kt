package controller.command

import model.math.Vector

class Down: MoveSnakeCommand() {
    override fun getVector(): Vector {
        return Vector.DOWN
    }
}