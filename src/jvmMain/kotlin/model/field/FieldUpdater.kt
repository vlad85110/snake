package model.field

import model.GamePlayer
import model.GameState
import model.NodeRole
import model.field.`object`.Food
import model.field.`object`.Snake
import model.math.Point
import model.math.Rectangle
import model.math.Vector
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class FieldUpdater(
    private val field: Field,
    private val updateRate: Long,
    private val foodSize: Int,
    var sendUpdate: ((GamePlayer, GameState) -> Unit)?
) {
    private val updateThread: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    private val snakes: MutableMap<Int, Snake> = ConcurrentHashMap()
    private val players: MutableMap<Int,GamePlayer> = HashMap()

    private var playersToJoin = HashSet<GamePlayer>()
    private val foodPoints: MutableSet<Point> = HashSet()
    private val joinResults: MutableMap<GamePlayer, Boolean> = ConcurrentHashMap()
    private val playerLocks = ConcurrentHashMap<GamePlayer, Object>()
    private var foodCount = 0
    private var orderCnt = 0

    private val updateCommand = {
        val nextStepSnakes = ArrayList<Snake>()
        snakes.forEach { e -> nextStepSnakes.add(e.value.clone()) }

        nextStepSnakes.forEach { s -> s.move() }

        nextStepSnakes.forEach { s ->
            val head = s.headPoint
            if (isCollision(head, s)) {
                snakes.remove(s.gamePlayerId)
            } else if (field.isFood(head)) {
                snakes[s.gamePlayerId]?.grow()
                foodCount--
            } else {
                snakes[s.gamePlayerId]?.move()
            }
        }

//        if (snakes.isEmpty()) {
//            stop()
//        }
//
        field.clearSnakes()
        snakes.values.forEach { s ->
            s.points.forEach { p -> field[p] = s }
        }

        if (foodCount != foodSize) {
            addFood(foodSize - foodCount)
        }

        for (player in playersToJoin) {
            addSnake(player)
        }
        playersToJoin.clear()

        val state = GameState(
            orderCnt++,
            snakes.values.toMutableList(),
            foodPoints.toMutableList(),
            players.values.toMutableList()
        )

        for (player in players.values) {
            if (player.nodeRole != NodeRole.MASTER) {
                sendUpdate?.invoke(player, state)
            }
        }

        println(1)
    }

    private val random = Random()
    private fun addFood(count: Int) {
        val excludePoints = HashSet<Point>()

        for (s in snakes.values) {
            excludePoints.addAll(s.headLine)
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
        foodCount++
    }

    private fun addSnake(player: GamePlayer) {
        val startRectangle = Rectangle(Point(0, 0), Point(4, 4))
        var rectangle = startRectangle
        var isFree = true
        do {
            do {
                for (point in rectangle.getPoints(field.size)) {
                    isFree = isFree.and(field[point] == null)
                }
                if (isFree) {
                    val headPoint = rectangle.point1 + Point(2, 2)
                    val randomVector = Vector.values().random()
                    val tailPoint = headPoint.nextPoint(randomVector, field.size)
                    val snakeVector = randomVector.opposite
                    val snake = Snake(headPoint, snakeVector, tailPoint, field.size, player.id)
                    players[player.id] = player
                    snakes[player.id] = snake
                    joinResults[player] = true
                    val lock = playerLocks[player]
                    if (lock != null) {
                        synchronized(lock) {
                            lock.notify()
                        }
                    }
                    return
                }

                rectangle = rectangle.move(Vector.RIGHT, field.size)
            } while (startRectangle.point1.x != rectangle.point1.x)

            rectangle = rectangle.move(Vector.DOWN, field.size)
        } while (startRectangle.point2.y != rectangle.point2.y)

        joinResults[player] = false
    }

    fun run() {
        updateThread.scheduleAtFixedRate(updateCommand, updateRate, updateRate, TimeUnit.MILLISECONDS)
    }

    fun stop() {
        updateThread.shutdown()
    }

    fun joinSnake(player: GamePlayer): Any {
        playersToJoin.add(player)
        val lock = Object()
        playerLocks[player] = lock
        return lock
    }

    fun moveSnake(gamePlayer: GamePlayer, vector: Vector) {
        val snake = snakes[gamePlayer.id]
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
            if (s.gamePlayerId != snake.gamePlayerId && s.isIt(point)) {
                return true
            }
        }
        return false
    }

    fun isSuccessJoin(gamePlayer: GamePlayer): Boolean {
        val lock = playerLocks[gamePlayer]
        if (lock != null) {
            synchronized(lock) {
                while (!joinResults.containsKey(gamePlayer)) {
                    lock.wait()
                }
                return joinResults[gamePlayer]!!
            }
        } else return false
    }
}