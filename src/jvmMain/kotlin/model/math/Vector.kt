package model.math

enum class Vector {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    private fun getCoords(): Point {
        return when (this) {
            UP -> Point(0, 1)
            DOWN -> Point(0, -1)
            LEFT -> Point(-1, 0)
            RIGHT -> Point(1, 0)
        }
    }

    fun isCollinear(other: Vector): Boolean {
        if (this.getCoords() * other.getCoords() == 0) return false
        return true
    }

    val opposite: Vector
        get() = when (this) {
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
        }
}