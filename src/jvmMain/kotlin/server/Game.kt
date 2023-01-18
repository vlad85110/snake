package server

import model.GameAnnouncement
import model.GameConfig
import model.GamePlayer
import model.GameState
import model.field.Field
import model.field.FieldUpdater

class Game(
    val gameName: String,
    private val gameConfig: GameConfig,
    private val canJoin: Boolean,
) : Runnable {
    private val field = Field(30)
    private val players = ArrayList<GamePlayer>()
    private var idCreator = 0
    var sendUpdate: ((GamePlayer, GameState) -> Unit)? = null

    private val fieldUpdater = FieldUpdater(field, 200, gameConfig.foodStatic, sendUpdate)

    val announcement: GameAnnouncement
        get() = GameAnnouncement(players, gameConfig, canJoin, gameName)

    override fun run() {
        TODO("Not yet implemented")
    }

    fun stop() {

    }

    fun joinPlayer(player: GamePlayer): Boolean {
        val id = newId
        player.id = id
        fieldUpdater.joinSnake(player)
        return fieldUpdater.isSuccessJoin(player)
    }

    fun moveSnake(gamePlayer: GamePlayer) {

    }

    private val newId: Int
        get() {
            if (idCreator == 255) idCreator = 0
            return idCreator++
        }
}


