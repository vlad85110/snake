package model.math

data class Point(val x: Int, val y: Int) {
    fun nextPoint(vector: Vector, size: Int): Point {
        var newX: Int
        var newY: Int

        when (vector) {
            Vector.DOWN -> {
                newX = x
                newY = (y + 1) % size
            }

            Vector.UP -> {
                newX = x
                newY = y - 1
                if (newY < 0) {
                    newY = size - 1
                }
            }
            Vector.LEFT -> {
                newY = y
                newX = x - 1
                if (newX < 0) {
                    newX = size - 1
                }
            }

            Vector.RIGHT -> {
                newY = y
                newX = (x + 1) % size
            }
        }

        return Point(newX, newY)
        //todo проверить
    }

    operator fun times(other: Point): Int {
        return x * other.x + y * other.y
    }
}