package server

import config.ConfigParser
import model.GamePlayer
import model.field.Field
import model.math.Vector
import model.message.AnnouncementMessage
import model.message.StateMessage
import net.Endpoint
import net.ServerNetModule
import net.SnakeNetModule
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class SnakeServer(private val configParser: ConfigParser) : Server {
    val games: MutableMap<String, Game> = HashMap()
    private val netModule: ServerNetModule = SnakeNetModule(configParser.multicastAddress, configParser.multicastPort)
    private val endpoints = HashMap<GamePlayer, Endpoint>()
    private val players = HashMap<Int, GamePlayer>()
    private val playerGameMap = HashMap<Int, String>()
    private var deputy: GamePlayer? = null
    private val ackPlayers = HashSet<Int>()

    private val announcerThread: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    private val announcerRunnable = {
        if (games.isNotEmpty()) {
            val message = AnnouncementMessage()
            games.forEach { e ->
                message.gameAnnouncements.add(e.value.announcement)
            }
            netModule.sendAnnouncementMessage(message)
        }
    }

    private val messageReceiver = MessageReceiver(netModule, endpoints, players, games, playerGameMap, ackPlayers)

    override fun runNewGame(game: Game) {
        games[game.gameName] = game
        game.ackPlayers = ackPlayers
        game.sendUpdate = { player, state ->
            if (player.ipAddress != null && player.port != null) {
                netModule.sendStateMessage(StateMessage(state), Endpoint(player.ipAddress!!, player.port!!))
            }
        }
        game.run()
    }

    override fun stopGame(name: String) {
        games[name]?.stop()
    }

    override fun run() {
        val announceRate = configParser.gameAnnouncePeriod
        announcerThread.scheduleAtFixedRate(announcerRunnable, announceRate, announceRate, TimeUnit.MILLISECONDS)
        Thread(messageReceiver).start()
    }

    fun stop() {
        announcerThread.shutdown()
        val deputy = deputy
        val endpoint = endpoints[deputy]
        if (deputy != null && endpoint != null) {
            netModule.sendRoleChangeMessage(endpoint)
        }

        //todo rolechangeMessage
    }

    override fun getGameView(name: String): Field? {
        val view = games[name]?.field
        if (view != null) {
            return view
        }

        return null
    }

    override fun moveSnake(gameName: String, playerName: String, vector: Vector) {
        games[gameName]?.moveSnake(playerName, vector)
    }
}