package model

import model.field.`object`.Snake
import model.math.Point

class GameState(
    var stateOrder: Int,
    val snakes: MutableList<Snake> = ArrayList(),
    val foods: MutableList<Point> = ArrayList(),
    val players: MutableList<GamePlayer> = ArrayList()
)