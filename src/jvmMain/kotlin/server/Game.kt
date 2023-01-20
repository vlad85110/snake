package server

import model.GameAnnouncement
import model.GameConfig
import model.GamePlayer
import model.GameState
import model.field.Field
import model.field.FieldUpdater
import model.math.Vector

class Game(
    val gameName: String,
    private val gameConfig: GameConfig,
    private val canJoin: Boolean,
) : Runnable {
    val field = Field(30)
    private val players = HashMap<String, GamePlayer>()
    private var idCreator = 0
    var sendUpdate: ((GamePlayer, GameState) -> Unit)? = null

    private val fieldUpdater = FieldUpdater(field, 200, gameConfig.foodStatic, sendUpdate)

    val announcement: GameAnnouncement
        get() = GameAnnouncement(players.values.toList(), gameConfig, canJoin, gameName)

    override fun run() {
        fieldUpdater.run()
    }

    fun stop() {

    }

    fun joinPlayer(player: GamePlayer): Boolean {
        val id = newId
        player.id = id
        players[player.name] = player
        fieldUpdater.joinSnake(player)
        return fieldUpdater.isSuccessJoin(player)
    }

    fun moveSnake(playerName: String, vector: Vector) {
        val player = players[playerName]
        if (player != null) {
            fieldUpdater.moveSnake(player, vector)
        }
    }

    private val newId: Int
        get() {
            if (idCreator == 255) idCreator = 0
            return idCreator++
        }
}


