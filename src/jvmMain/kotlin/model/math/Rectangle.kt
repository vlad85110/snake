package model.math

data class Rectangle(val point1: Point, val point2: Point) {
    fun getPoints(fieldSize: Int): List<Point> {
        val res = ArrayList<Point>()
        var point = point1

        do {
            do {
                res.add(point)
                point = point.nextPoint(Vector.RIGHT, fieldSize)
            } while (point.x != point2.x)

            point = Point(point1.x, point.y)
            point = point.nextPoint(Vector.DOWN, fieldSize)
        } while (point.y != point2.y)

        return res
    }

    fun move(vector: Vector, fieldSize: Int): Rectangle {
        return Rectangle(point1.nextPoint(vector, fieldSize), point2.nextPoint(vector, fieldSize))
    }
}