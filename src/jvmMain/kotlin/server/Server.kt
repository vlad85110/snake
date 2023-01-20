package server

import model.field.Field
import model.math.Vector

interface Server: Runnable {
    fun getGameView(name: String): Field?
    fun runNewGame(game: Game)
    fun stopGame(name: String)
    fun moveSnake(gameName: String, playerName: String, vector: Vector)
}