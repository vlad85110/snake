package controller.command

import model.math.Vector

class Up: MoveSnakeCommand() {
    override fun getVector(): Vector {
        return Vector.UP
    }
}