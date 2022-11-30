package model.math

data class Line(val type: LineType, val coordinate: Int): Comparable<Line> {
    override fun compareTo(other: Line): Int {
        if (coordinate < other.coordinate) return 0
        return 1
    }
}