package model.field.`object`

import model.SnakeState
import model.math.Point
import model.math.Vector

class Snake: FieldObject, Cloneable {
    constructor(headPoint: Point,
                startVector: Vector,
                tailPoint: Point,
                fieldSize: Int,
                gamePlayerId: Int) {
        this.headPoint = headPoint
        this.currentVector = startVector
        this.vectorToMove = startVector
        this.tailPoint = tailPoint
        this.fieldSize = fieldSize
        this.gamePlayerId = gamePlayerId
        this.points = arrayListOf(headPoint, tailPoint)
    }

    constructor(gamePlayerId: Int, snakeState: SnakeState, vector: Vector, points: MutableList<Point>) {
        this.headPoint = points.first()
        this.tailPoint = points.last()
        this.currentVector = vector
        this.points = points
        this.vectorToMove = vector
        this.gamePlayerId = gamePlayerId
    }


    var headPoint: Point
    private var tailPoint: Point
    private var fieldSize: Int? = null
    val gamePlayerId: Int

    val points: MutableList<Point>
    var currentVector: Vector
    var state = SnakeState.ALIVE

    var vectorToMove: Vector
        set(value) {
            if (!currentVector.isCollinear(value)) {
                field = value
            }
        }

    public override fun clone(): Snake {
        return super.clone() as Snake
    }

    fun isIt(point: Point): Boolean {
        return getPointInSnakeCount(point) >= 1
    }

    fun isSelfCollision(point: Point): Boolean {
        return getPointInSnakeCount(point) >= 2
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

    val headLine: List<Point>
        get() {
            val res = ArrayList<Point>()
            var point = headPoint

            do {
                point = point.nextPoint(currentVector, fieldSize!!)
                res.add(point)
            } while (point != headPoint)

            return res
        }

    fun move() {
        val vector = if (vectorToMove == null) {
            currentVector
        } else {
            vectorToMove
        }

        val newHeadPoint = headPoint.nextPoint(vector, fieldSize!!)
        currentVector = vector
        points.add(newHeadPoint)
        headPoint = newHeadPoint

        points.remove(tailPoint)
        tailPoint = points.first()
    }

    fun grow() {
        val newHeadPoint = headPoint.nextPoint(vectorToMove, fieldSize!!)
        currentVector = vectorToMove
        points.add(newHeadPoint)
        headPoint = newHeadPoint
    }
}