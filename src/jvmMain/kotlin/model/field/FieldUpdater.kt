package model.field

import model.Player
import model.field.`object`.Food
import model.field.`object`.Snake
import model.math.Point
import model.math.Vector
import view.View
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class FieldUpdater(
    private val field: Field,
    private val updateRate: Long,
    private val views: MutableMap<Player, View>,
) {
    private val updateThread: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    private val snakes: MutableMap<Player, Snake> = ConcurrentHashMap()
    private val foodPoints: MutableSet<Point> = HashSet()
    private var foodCount = 0

    private val updateCommand = {
        val nextStepSnakes = ArrayList<Snake>()
        snakes.forEach { e -> nextStepSnakes.add(e.value.clone()) }
        nextStepSnakes.forEach { s -> s.move() }

        nextStepSnakes.forEach { s ->
            val head = s.headPoint
            if (isCollision(head, s)) {
                snakes.remove(s.player)
                views[s.player]?.showLoseMessage()
                views.remove(s.player)
            } else if (field.isFood(head)) {
                snakes[s.player]?.grow()
                foodCount--
            } else {
                snakes[s.player]?.move()
            }
        }

        if (snakes.isEmpty()) {
            stop()
        }

        field.clearSnakes()
        snakes.values.forEach { s ->
            s.points.forEach { p -> field[p] = s }
        }

        if (foodCount != snakes.size) {
            addFood(snakes.size - foodCount)
        }
    }

    private val random = Random()
    private fun addFood(count: Int) {
        val excludePoints = HashSet<Point>()

        for (s in snakes.values) {
            excludePoints.addAll(s.getHeadLine)
            excludePoints.addAll(s.points)
            excludePoints.addAll(foodPoints)
        }

        val fieldPoints = field.points
        val points = (fieldPoints - excludePoints).toMutableList()

        for (i in 1..count) {
            val foodPointIndex = random.nextInt(points.size - 1)
            val foodPoint = points[foodPointIndex]
            field[foodPoint] = Food()
            foodPoints.add(foodPoint)
            points.remove(foodPoint)
        }

        views.values.forEach { view ->
            view.updateField(field)
        }

        foodCount++
    }

    fun run() {
        updateThread.scheduleAtFixedRate(updateCommand, updateRate, updateRate, TimeUnit.MILLISECONDS)
    }

    fun stop() {
        updateThread.shutdown()
    }

    fun addSnake(snake: Snake) {
        snakes[snake.player] = snake
    }

    fun moveSnake(player: Player, vector: Vector) {
        val snake = snakes[player]
        if (snake != null) {
            snake.vectorToMove = vector
        }
    }

    private fun isCollision(point: Point, snake: Snake): Boolean {
        for (s in snakes.values) {
            if (snake.isSelfCollision(point) || isOtherSnake(point, snake)) {
                return true
            }
        }
        return false
    }

    private fun isOtherSnake(point: Point, snake: Snake): Boolean {
        for (s in snakes.values) {
            if (s.player != snake.player && s.isIt(point)) {
                return true
            }
        }
        return false
    }
}