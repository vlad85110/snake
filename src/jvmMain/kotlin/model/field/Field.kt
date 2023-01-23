package model.field

import androidx.compose.runtime.mutableStateListOf
import model.GameState
import model.field.`object`.FieldObject
import model.field.`object`.Food
import model.field.`object`.Snake
import model.math.Point

class Field {
    private val mutableField: MutableList<FieldObject?>
    val size : Int
    private val fieldObjects: Array<FieldObject?>

    constructor(size: Int) {
        this.fieldObjects = Array(size * size, init = { null })
        mutableField = mutableStateListOf(*fieldObjects)
        this.size = size
    }

    constructor(gameState: GameState) {
        this.size = 30 //todo

        this.fieldObjects = Array(size * size, init = { null })
        mutableField = mutableStateListOf(*fieldObjects)

        for (s in gameState.snakes) {
            for (p in s.points) {
                this[p] = s
            }
        }

        for (f in gameState.foods) {
            this[f] = Food()
        }
    }
    val field: List<FieldObject?>
        get() {
            return mutableField
        }

    init {

    }

    val points: Set<Point>
        get() {
            val res = HashSet<Point>()
            for (i in 0 until size) {
                for (j in 0 until size) {
                    res.add(Point(i, j))
                }
            }
            return res
        }

    @Synchronized
    operator fun get(point: Point): FieldObject? {
        return mutableField[point.y * size + point.x]
    }

    @Synchronized
    operator fun get(x: Int, y: Int): FieldObject? {
        return mutableField[y * size + x]
    }

    @Synchronized
    operator fun set(point: Point, value: FieldObject) {
        mutableField[point.y * size + point.x] = value
    }

    @Synchronized
    operator fun set(x: Int, y: Int, value: FieldObject) {
        mutableField[y * size + x] = value
    }

    fun clearSnakes() {
        for (i in mutableField.indices) {
            if (mutableField[i] is Snake) {
                mutableField[i] = null
            }
        }
    }

    fun isFood(point: Point): Boolean {
        return this[point] is Food
    }
}