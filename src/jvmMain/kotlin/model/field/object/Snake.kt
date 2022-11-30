package model.field.`object`

import model.Player
import model.math.Point
import model.math.Vector
import java.util.Collections

class Snake(length: Int, startVector: Vector, startPoint: Point, private val fieldSize: Int, val player: Player) :
    FieldObject(), Cloneable {
    val points: MutableList<Point> = ArrayList()
    var headPoint: Point
    private var tailPoint: Point = startPoint
    var currentVector = startVector

    var vectorToMove: Vector = startVector
        set(value) {
            if (!currentVector.isCollinear(value)) {
                field = value
            }
        }

    init {
        var point = startPoint
        points.add(point)

        for (i in 0 until length) {
            point = point.nextPoint(startVector, fieldSize)
            points.add(point)
        }

        headPoint = point
    }

    public override fun clone(): Snake {
        return super.clone() as Snake
    }

    override fun hashCode(): Int {
        return player.name.hashCode()
    }

    fun isIt(point: Point): Boolean {
       return getPointInSnakeCount(point) >= 1
    }

    fun isSelfCollision(point: Point): Boolean {
        return getPointInSnakeCount(point) >=  2
    }

    private fun getPointInSnakeCount(point: Point): Int {
        var cnt = 0

        for (p in points) {
            if (p == point) {
                cnt++
            }
        }

        return cnt
    }

    val getHeadLine: List<Point>
        get() {
            val res  = ArrayList<Point>()
            var point = headPoint

            do {
                point = point.nextPoint(currentVector, fieldSize)
                res.add(point)
            } while (point != headPoint)

            return res
        }

    fun move() {
        val newHeadPoint = headPoint.nextPoint(vectorToMove, fieldSize)
        currentVector = vectorToMove
        points.add(newHeadPoint)
        headPoint = newHeadPoint

        points.remove(tailPoint)
        tailPoint = points.first()
    }

    fun grow() {
        val newHeadPoint = headPoint.nextPoint(vectorToMove, fieldSize)
        currentVector = vectorToMove
        points.add(newHeadPoint)
        headPoint = newHeadPoint
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Snake

        if (fieldSize != other.fieldSize) return false
        if (player != other.player) return false
        if (points != other.points) return false
        if (headPoint != other.headPoint) return false
        if (tailPoint != other.tailPoint) return false
        if (currentVector != other.currentVector) return false
        if (vectorToMove != other.vectorToMove) return false

        return true
    }
}